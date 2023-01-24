//                                                                          //
// Copyright 2016 - 2023 Mirko Raner                                        //
//                                                                          //
// Licensed under the Apache License, Version 2.0 (the "License");          //
// you may not use this file except in compliance with the License.         //
// You may obtain a copy of the License at                                  //
//                                                                          //
//     http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                          //
// Unless required by applicable law or agreed to in writing, software      //
// distributed under the License is distributed on an "AS IS" BASIS,        //
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. //
// See the License for the specific language governing permissions and      //
// limitations under the License.                                           //
//                                                                          //
package pro.projo.internal.proxy;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import pro.projo.Delegated;
import pro.projo.Mapping;
import pro.projo.Projo;
import pro.projo.annotations.Overrides;
import pro.projo.annotations.Cached;
import pro.projo.annotations.Delegate;
import pro.projo.internal.Default;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.internal.PropertyMatcher;
import pro.projo.utilities.Pair;
import static java.lang.System.identityHashCode;
import static java.lang.reflect.Proxy.getProxyClass;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static pro.projo.Projo.isValueObject;
import static pro.projo.utilities.Pair.pair;

/**
* The {@link ProxyProjoInvocationHandler} is the {@link InvocationHandler} for Projo objects that are
* implemented by Java proxies. It implements the functionality of getters and setters as well as
* the {@link Object#equals(Object) equals()} and {@link Object#hashCode() hashCode()} methods.
*
* @param <_Artifact_> the type of the Projo object
*
* @author Mirko Raner
**/
public class ProxyProjoInvocationHandler<_Artifact_> extends ProjoHandler<_Artifact_> implements Predicates, InvocationHandler
{
	private final static String DELEGATE = "/delegate";
	private final static String TYPE_VARIABLE = "/typevariable/";

    private static PropertyMatcher matcher = new PropertyMatcher();

    private Map<String, Object> state = new HashMap<>();
    private Class<_Artifact_> reifiedType;
    Function<_Artifact_, ?>[] getters;
    Stack<Object> initializationStack = new Stack<>();
    List<Entry<String, Class<?>>> properties = new ArrayList<>();

    InvocationHandler invoker = (Object proxy, Method method, Object... arguments) ->
    {
        String propertyName = matcher.propertyName(method.getName());
        state.put(propertyName, initializationStack.pop());
        properties.add(pair(propertyName, method.getReturnType()));

        // Avoid NPEs during auto-unboxing of return values of methods that return a primitive type:
        //
        return Default.VALUES.get(method.getReturnType());
    };

    class Initializer extends ProjoHandler<_Artifact_>.ProjoInitializer
    {
        _Artifact_ instance;

        Initializer(_Artifact_ object)
        {
            this.instance = object;
        }

        @Override
        @SafeVarargs
        public final ProjoMembers members(Function<_Artifact_, ?>... members)
        {
            return new Members(members);
        }

        @Override
        public ProjoHandler<_Artifact_>.ProjoInitializer.ProjoMembers delegate(Object delegate, Mapping<?> mapping)
        {
            return new ProjoMembers()
            {
                @Override
                public _Artifact_ returnInstance()
                {
                    invoker = ProxyProjoInvocationHandler.this.delegateInvoker(mapping);
                    state.put(DELEGATE, delegate);
                    initializationStack = null;
                    return instance;
                }
                
                @Override
                public _Artifact_ with(Object... values)
                {
                    throw new UnsupportedOperationException("with" + Arrays.asList(values));
                }
            };
        }

        @Override
        public ProjoMembers proxy(Object delegate, Class<?> proxyInterface, boolean override)
        {
            return new ProjoMembers()
            {
                @Override
                public _Artifact_ returnInstance()
                {
                    invoker = ProxyProjoInvocationHandler.this.proxyInvoker(delegate, proxyInterface);
                    state.put(DELEGATE, delegate);
                    initializationStack = null;
                    return instance;
                }

                @Override
                public _Artifact_ with(Object... values)
                {
                    throw new UnsupportedOperationException("with" + Arrays.asList(values));
                }
            };
        }

        class Members extends ProjoMembers
        {
            @SafeVarargs
            public Members(Function<_Artifact_, ?>... members)
            {
                getters = members;
            }

            @Override
            public _Artifact_ with(Object... values)
            {
                Iterator<Function<_Artifact_, ?>> members = Arrays.asList(getters).iterator();
                for (Object value: values)
                {
                    initializationStack.push(value);
                    members.next().apply(instance);
                }
                if (members.hasNext() || !initializationStack.isEmpty())
                {
                    throw new IllegalStateException();
                }
                if (isProxiedInterface(reifiedType))
                {
                    invoker = ProxyProjoInvocationHandler.this.proxyInvoker(values[0], reifiedType);
                }
                else
                {
                    invoker = ProxyProjoInvocationHandler.this.regularInvoker(reifiedType);
                }
                initializationStack = null;
                return instance;
            }

            @Override
            public _Artifact_ returnInstance()
            {
                return with(new Object[getters.length]);
            }
        }
    }

    Map<String, Method> overrides(Method... methods)
    {
        // Find methods that are annotated with an @Overrides annotation:
        //
        return Stream.of(methods)
            .map(method -> new SimpleEntry<>(method, method.getAnnotation(Overrides.class)))
            .filter(pair -> pair.getValue() != null)
            .map(pair -> new SimpleEntry<>(pair.getValue().value(), pair.getKey()))
            .collect(toMap(Entry::getKey, Entry::getValue));
    }

    InvocationHandler regularInvoker(Class<?> declaringType)
    {
        return (Object proxy, Method method, Object... arguments) ->
        {
            Method overridden = overrides(declaringType.getDeclaredMethods()).get(method.getName());
            if (overridden != null)
            {
                return overridden.invoke(proxy, arguments);
            }
            if (setter.test(method))
            {
                return state.put(matcher.propertyName(method.getName()), arguments[0]);
            }
            if (getter.test(method))
            {
                Object value = state.get(matcher.propertyName(method.getName()));
                return value != null? value:Default.VALUES.get(method.getReturnType());
            }
            if (equals.test(method))
            {
                return isValueObject(reifiedType)? isEqual(artifact(proxy), artifact(arguments[0])):proxy == arguments[0];
            }
            if (hashCode.test(method))
            {
                @SuppressWarnings("unchecked")
                _Artifact_ artifact = (_Artifact_)proxy;
                return isValueObject(reifiedType)? hashCode(artifact):identityHashCode(artifact);
            }
            if (toString.test(method))
            {
                @SuppressWarnings("unchecked")
                _Artifact_ artifact = (_Artifact_)proxy;
                return toString(artifact);
            }
            if (method.isDefault())
            {
                List<Object> key = null;
                Map<List<Object>, Object> cache = null;
                Cached cached = method.getAnnotation(Cached.class);
                if (cached != null)
                {
                    String property = matcher.propertyName(method.getName());
                    @SuppressWarnings("unchecked")
                    Map<List<Object>, Object> cacheState = (Map<List<Object>, Object>)state.get(property);
                    cache = cacheState;
                    if (cache == null)
                    {
                        cache = new HashMap<>();
                        state.put(property, cache);
                    }
                    key = arguments == null? emptyList():Arrays.asList(arguments);
                    Object value = cache.get(key);
                    if (value != null)
                    {
                        return value;
                    }
                }
                if (System.getProperty("java.version").startsWith("1.8"))
                {
                    Class<?> type = method.getDeclaringClass();
                    Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class);
                    constructor.setAccessible(true);
                    Object value = constructor
                        .newInstance(type)
                        .in(type)
                        .unreflectSpecial(method, type)
                        .bindTo(proxy)
                        .invokeWithArguments(arguments);
                    if (cached != null)
                    {
                        // Add value to cache if there is still space:
                        //
                        if (cache.size() < cached.cacheSize())
                        {
                            cache.put(key, value);
                        }
                    }
                    return value;
                }
                else // assuming Java 9+
                {
                    throw new UnsupportedOperationException("proxy functionality no longer fully available in Java 9 or higher");
                }
            }
            throw new NoSuchMethodError(String.valueOf(method));
        };
    }

    InvocationHandler proxyInvoker(Object delegate, Class<?> proxyInterface)
    {
        Method[] declaredMethods = proxyInterface != null? proxyInterface.getDeclaredMethods():new Method[] {};
        Method delegateMethod = getDelegateMethod(declaredMethods).orElse(null);

        // TODO: this handler captures the delegate object from the closure;
        //       should it use the ProxyInvocationHandler's state map instead?
        //
        return (Object proxy, Method method, Object... arguments) ->
        {
            Method overrideMethod = null;
            if (method.isDefault())
            {
                // Execute the default method code:
                //
                return rebindAndInvoke(method, proxy, arguments);
            }
            else if (method.getAnnotation(Delegate.class) != null)
            {
                return delegate;
            }
            else if (method.getDeclaringClass().equals(Object.class)
                && ((overrideMethod = overrides(declaredMethods).get(method.getName()))) != null)
            {
                // Invoke override method:
                //
                return rebindAndInvoke(overrideMethod, proxy, arguments);
            }
            else if (method.getDeclaringClass().equals(proxyInterface)
                && ((method.getModifiers() & Modifier.ABSTRACT) != 0)
                && ((method.getParameterCount() == 0)))
            {
                // Return new custom attributes (in lieu of delegate object):
                //
                Object value = state.get(matcher.propertyName(method.getName()));
                return value != null? value:Default.VALUES.get(method.getReturnType());
            }
            else
            {
                // Forward to delegate object:
                //
                return method.invoke(delegateMethod != null && delegateMethod.isDefault()? delegateMethod.invoke(proxy):delegate, arguments);
            }
        };
    }

    InvocationHandler delegateInvoker(Mapping<?> mapping)
    {
        // TODO: ensure that self type variables are handled correctly when different classes use
        //       different variable names
        //
        return (Object proxy, Method method, Object... arguments) ->
        {
            // If this is call to the getDelegate() method, return the delegate immediately:
            //
            if (getDelegate.test(method))
            {
                return state.get(DELEGATE);
            }

            // If this is call to the getState() method, return the state map immediately:
            //
            if (getState.test(method))
            {
                return state;
            }

            // Get the delegate object and its type (e.g. BigInteger):
            //
            Object delegate = state.get(DELEGATE);
            Class<?> delegateType = delegate != null?
                delegate.getClass():
                mapping.getDelegate(method.getDeclaringClass());

            // Bind any type variables:
            //
            Stream<Entry<String, Type>> typeMap;
            typeMap = zip(method.getGenericParameterTypes(), arguments, (Type type, Object argument) ->
            {
                if (type instanceof TypeVariable)
                {
                    String typeVariableName = ((TypeVariable<?>)type).getName();
                    Type reifiedArgumentType = getPrimaryProxyType(argument);
                    return pair(typeVariableName, reifiedArgumentType);
                }
                return null;
            });

            // Find the corresponding method in the delegate type (e.g., BigInteger):
            //
            Class<?> declaringClass = method.getDeclaringClass();
            Map<TypeVariable<?>, Type> bindings = getBindings(declaringClass);
            Optional<TypeVariable<?>> self = Projo.getSelfType(declaringClass);
            Type primaryProxyType = getPrimaryProxyType(proxy);
            Stream<Type> genericParameterTypes = Stream.of(method.getGenericParameterTypes());
            Stream<Class<?>> parameterTypes = genericParameterTypes
                .map(parameter -> getEffectiveType(parameter, self, primaryProxyType, bindings));
            Stream<Class<?>> delegateParameterTypes = parameterTypes.map(mapping::getDelegate);
            Entry<Method, Boolean> foundMethod = findMethod(mapping, delegateType, method.getName(), delegateParameterTypes.toArray(Class[]::new));
            Method delegateMethod = foundMethod.getKey();
            boolean useAdapters = foundMethod.getValue();
            Function<Object, ?> adapt = useAdapters?
                object ->
                {
                    @SuppressWarnings("unchecked")
                    Function<Object, ?> from = (Function<Object, ?>)mapping.adapters().getOrDefault(object.getClass(), null).from();
                    return from.apply(object);
                }:
                Function.identity();

            // Convert the arguments to their corresponding delegate (non-synthetic) types (i.e., unwrap them):
            //
            Function<Object, Object> unwrap = object ->
            {
                return object instanceof Delegated? adapt.apply(Projo.unwrap(object)):object;
            };
            Stream<Object> unwrappedArguments = stream(arguments).map(unwrap);

            // Invoke the delegate method:
            //
            Object result = delegateMethod.invoke(delegate, unwrappedArguments.toArray());

            // Convert the result back to the synthetic type (unless it already is):
            //
            Class<?> returnType = getEffectiveType(method.getGenericReturnType(), self, primaryProxyType, bindings);
            Delegated wrappedResult = result instanceof Delegated?
                (Delegated)result:
                (Delegated)Projo.delegate(returnType, result, mapping);
            Map<String, Object> state = wrappedResult.getState();

            // Determine if the type variables that were bound previously are present in the
            // return type, and if so, at which index positions:
            //
            Type genericReturnType = method.getGenericReturnType();
            if (genericReturnType instanceof ParameterizedType)
            {
                Type[] typeArguments = ((ParameterizedType)genericReturnType).getActualTypeArguments();
                Map<String, Type> map = typeMap.filter(Objects::nonNull).collect(toMap(Entry::getKey, Entry::getValue, (a, b) -> b, () -> new HashMap<>()));
                IntStream.range(0, typeArguments.length)
                    .mapToObj(index -> pair(TYPE_VARIABLE + index, map.get(typeArguments[index].getTypeName())))
                    .filter(Pair::hasValue)
                    .forEach(entry -> entry.addTo(state));

                // Transitively bind previously bound type variables if the current type
                // has variables <..., X, ...> and the return type is the same base type and has the
                // the same variables:
                //
                if (returnType.equals(declaringClass))
                {
                    // TODO: this should also work if the type variables don't have the
                    //       same index, e.g.
                    //
                    //       BijectiveFunction<A, B>
                    //       {
                    //           BijectiveFunction<B, A> invert();
                    //       }
                    //
                    TypeVariable<?>[] typeParameters = declaringClass.getTypeParameters();
                    IntStream.range(0, typeParameters.length)
                        .filter(index -> typeArguments[index].equals(typeParameters[index]))
                        .mapToObj(index -> pair(TYPE_VARIABLE + index, this.state.get(TYPE_VARIABLE + index)))
                        .filter(Pair::hasValue)
                        .forEach(pair -> pair.addTo(state));
                }
            }
            return wrappedResult;
        };
    }

    Object rebindAndInvoke(Method method, Object proxy, Object... arguments) throws Throwable
    {
        Class<?> declaringClass = method.getDeclaringClass();
        Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class);
        constructor.setAccessible(true);
        return constructor.newInstance(declaringClass)
            .in(declaringClass)
            .unreflectSpecial(method, declaringClass)
            .bindTo(proxy)
            .invokeWithArguments(arguments);
    }

    Map<TypeVariable<?>, Type> getBindings(Class<?> declaringClass)
    {
        TypeVariable<?>[] typeParameters = declaringClass.getTypeParameters();
        return IntStream.range(0, typeParameters.length)
            .mapToObj(index -> pair(index, (Type)state.get(TYPE_VARIABLE + index)))
            .filter(Pair::hasValue)
            .collect(toMap(pair -> typeParameters[pair.getKey()], Pair::getValue));
    }

    Entry<Method, Boolean> findMethod(Mapping<?> mapping, Class<?> delegateType, String name, Class<?>[] parameterTypes) throws NoSuchMethodException
    {
        // Very simplified search for now:
        // - look for a method with the specified parameter types
        // - if that fails, look for a method with adapted parameter types (for all parameters, if adapter exists)
        // - assume that there is either 0 or 1 adapter for each parameter type
        try
        {
            return pair(delegateType.getMethod(name, parameterTypes), false);
        }
        catch (NoSuchMethodException noSuchMethod)
        {
            Class<?>[] adaptedParameterTypes = Stream.of(parameterTypes).map(mapping::getAdaptedType).toArray(Class[]::new);
            return pair(delegateType.getMethod(name, adaptedParameterTypes), true);
        }
    }

    Type getPrimaryProxyType(Object object)
    {
        return object.getClass().getGenericInterfaces()[0];
    }

    Class<?> getEffectiveType(Type type, Optional<TypeVariable<?>> self, Type substitute, Map<TypeVariable<?>, Type> bindings)
    {
        if (self.isPresent() && self.get().equals(type))
        {
            return (Class<?>)substitute;
        }
        if (type instanceof ParameterizedType)
        {
            return (Class<?>)((ParameterizedType)type).getRawType();
        }
        if (type instanceof TypeVariable)
        {
            Type binding = bindings.get(type);
            if (binding != null)
            {
                return (Class<?>)binding;
            }

            // TODO: what to do if there is more than one upper bound? e.g. T extends Number & Runnable
            //
            Type upperBound = ((TypeVariable<?>)type).getBounds()[0];
            return (Class<?>)upperBound;
        }
        return (Class<?>)type;
    }

    public ProxyProjoInvocationHandler(Class<_Artifact_> type)
    {
        reifiedType = type;
    }

    public Initializer initialize(_Artifact_ artifact)
    {
        return new Initializer(artifact);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
    {
        return invoker.invoke(proxy, method, arguments);
    }

    @Override
    public Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type, boolean defaultPackage, ClassLoader classLoader)
    {
        // TODO: some code overlap with ProxyProjo.initializer(Class)
        Class<?>[] interfaces = {type, ProjoObject.class};
        if (classLoader == null)
        {
            classLoader = Projo.getImplementation().getClassLoader();
        }

        @SuppressWarnings({"unchecked"})
        Class<? extends _Artifact_> proxyClass = (Class<? extends _Artifact_>)getProxyClass(classLoader, interfaces);
        return proxyClass;
    }

    <_First_, _Second_, _Result_> Stream<_Result_> zip(_First_[] first, _Second_[] second,
        BiFunction<_First_, _Second_, _Result_> zipper)
    {
        if (first == null || second == null)
        {
            return Stream.empty();
        }
        return IntStream
            .range(0, Math.min(first.length, second.length))
            .mapToObj(index -> zipper.apply(first[index], second[index]));
    }
    @SafeVarargs
    private final <_Any_> Stream<_Any_> stream(_Any_... items)
    {
        return items == null? Stream.empty():Stream.of(items);
    }

    private String toString(_Artifact_ proxy)
    {
        if (Projo.hasCustomToString(reifiedType))
        {
            String fields = properties.stream().map(this::propertyDescription).collect(joining(", "));
            return reifiedType.getName() + "[" + fields + "]";
        }
        return reifiedType.getName() + "@" + Integer.toHexString(proxy.hashCode());
    }

    private int hashCode(_Artifact_ proxy)
    {
        return Objects.hash(Stream.of(getters).map(method -> method.apply(proxy)).toArray(Object[]::new));
    }

    private boolean isEqual(_Artifact_ proxy, _Artifact_ object)
    {
        Predicate<Function<_Artifact_, ?>> equal = member -> equalOrBothNull(member.apply(proxy), member.apply(object));
        return reifiedType.isInstance(object) && Stream.of(getters).allMatch(equal);
    }

    private boolean equalOrBothNull(Object one, Object two)
    {
        return one == null? two == null:one.equals(two);
    }

    private String propertyDescription(Entry<String, Class<?>> description)
    {
        String property = description.getKey();
        Object rawValue = state.get(property);
        Object value = rawValue != null? rawValue:Default.VALUES.get(description.getValue());
        return property + "=" + (value instanceof String? "\"" + value + "\"":String.valueOf(value));
    }

    @SuppressWarnings("unchecked")
    private _Artifact_ artifact(Object object)
    {
        return reifiedType.isInstance(object)? (_Artifact_)object:null;
    }
}
