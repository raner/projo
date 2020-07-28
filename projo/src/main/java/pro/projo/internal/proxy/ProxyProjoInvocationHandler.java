//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.Mapping;
import pro.projo.Projo;
import pro.projo.internal.Default;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.internal.PropertyMatcher;
import static java.lang.System.identityHashCode;
import static java.lang.reflect.Proxy.getProxyClass;
import static java.util.stream.Collectors.joining;
import static pro.projo.Projo.isValueObject;

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
        properties.add(new SimpleEntry<>(propertyName, method.getReturnType()));

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
        public ProjoHandler<_Artifact_>.ProjoInitializer.ProjoMembers delegate(Object delegate, Mapping mapping)
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
                invoker = ProxyProjoInvocationHandler.this.regularInvoker;
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

    InvocationHandler regularInvoker = (Object proxy, Method method, Object... arguments) ->
    {
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
        throw new NoSuchMethodError(String.valueOf(method));
    };

    InvocationHandler delegateInvoker(Mapping mapping)
    {
        return (Object proxy, Method method, Object... arguments) ->
        {
            // If this is call to the getDelegate() method, return the delegate immediately:
            //
            if (getDelegate.test(method))
            {
                return state.get(DELEGATE);
            }

            // Get the delegate object and its type (e.g. BigInteger):
            //
            Object delegate = state.get(DELEGATE);
            Class<?> delegateType = delegate.getClass();

            // Find the corresponding method in the delegate type (e.g., BigInteger):
            //
            Stream<Class<?>> parameterTypes = Stream.of(method.getParameterTypes());
            Stream<Class<?>> delegateParameterTypes = parameterTypes.map(mapping::getDelegate);
            Method delegateMethod = delegateType.getMethod(method.getName(), delegateParameterTypes.toArray(Class[]::new));

            // Convert the arguments to their corresponding delegate (non-synthetic) types (i.e., unwrap them):
            //
            Stream<Object> unwrappedArguments = stream(arguments).map(Projo::unwrap);

            // Invoke the delegate method:
            //
            Object result = delegateMethod.invoke(delegate, unwrappedArguments.toArray());

            // Convert the result back to the synthetic type:
            //
            Object wrappedResult = Projo.delegate(method.getReturnType(), result, mapping);
            return wrappedResult;
        };
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
    public Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type)
    {
        // TODO: some code overlap with ProxyProjo.initializer(Class)
        Class<?>[] interfaces = {type, ProjoObject.class};
        ClassLoader classLoader = Projo.getImplementation().getClassLoader();
        @SuppressWarnings({"unchecked", "deprecation"})
        Class<? extends _Artifact_> proxyClass = (Class<? extends _Artifact_>)getProxyClass(classLoader, interfaces);
        return proxyClass;
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
