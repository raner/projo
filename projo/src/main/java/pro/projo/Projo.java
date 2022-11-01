//                                                                          //
// Copyright 2016 - 2022 Mirko Raner                                        //
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
package pro.projo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.annotations.ValueObject;
import pro.projo.internal.FactoryBuilder;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.utilities.MethodFunctionConverter;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

/**
* The {@link Projo} class provides static methods for creating Projo objects, as well as other
* related utility methods.
*
* @author Mirko Raner
**/
public abstract class Projo
{
    /**
    * The {@link Intermediate} interface provides access to all generated Projo factories.
    * It is the union of all generated intermediate interfaces.
    *
    * @param <_Artifact_> the artifact type
    **/
    public static interface Intermediate<_Artifact_> extends
        pro.projo.singles.Intermediate<_Artifact_>,
        pro.projo.doubles.Intermediate<_Artifact_>,
        pro.projo.triples.Intermediate<_Artifact_>,
        pro.projo.quadruples.Intermediate<_Artifact_>,
        pro.projo.quintuples.Intermediate<_Artifact_>,
        pro.projo.sextuples.Intermediate<_Artifact_>,
        pro.projo.septuples.Intermediate<_Artifact_>,
        pro.projo.octuples.Intermediate<_Artifact_>,
        pro.projo.nonuples.Intermediate<_Artifact_>,
        pro.projo.decuples.Intermediate<_Artifact_>,
        pro.projo.undecuples.Intermediate<_Artifact_>,
        pro.projo.duodecuples.Intermediate<_Artifact_>,
        pro.projo.tredecuples.Intermediate<_Artifact_>,
        pro.projo.quattuordecuples.Intermediate<_Artifact_>,
        pro.projo.quindecuples.Intermediate<_Artifact_>,
        pro.projo.sexdecuples.Intermediate<_Artifact_>,
        pro.projo.septendecuples.Intermediate<_Artifact_>,
        pro.projo.octodecuples.Intermediate<_Artifact_>,
        pro.projo.novemdecuples.Intermediate<_Artifact_>,
        pro.projo.vigintuples.Intermediate<_Artifact_>,
        pro.projo.unvigintuples.Intermediate<_Artifact_>,
        pro.projo.duovigintuples.Intermediate<_Artifact_>,
        pro.projo.trevigintuples.Intermediate<_Artifact_>,
        pro.projo.quattuorvigintuples.Intermediate<_Artifact_>,
        pro.projo.quinvigintuples.Intermediate<_Artifact_>,
        pro.projo.sexvigintuples.Intermediate<_Artifact_>,
        pro.projo.septenvigintuples.Intermediate<_Artifact_>,
        pro.projo.octovigintuples.Intermediate<_Artifact_>,
        pro.projo.novemvigintuples.Intermediate<_Artifact_>,
        pro.projo.trigintuples.Intermediate<_Artifact_>
    {
        default Method[] methods()
        {
            Predicate<Method> withMethod = method -> "with".equals(method.getName());
            Comparator<Method> parameterCount = Comparator.comparing(Method::getParameterCount);
            return Stream.of(getClass().getMethods()).filter(withMethod).sorted(parameterCount).toArray(Method[]::new);
        }
    }

    private static Projo implementation = initialize();

    /**
    * Returns the {@link ClassLoader} to be used for loading dynamic classes that were generated by Projo.
    * By default, this method returns the {@link ClassLoader} that was used to load the {@link Projo}
    * class itself. Subclasses may override this implementation, if necessary.
    *
    * @return the {@link ClassLoader} for Projo classes
    **/
    public ClassLoader getClassLoader()
    {
        return Projo.class.getClassLoader();
    }

    /**
    * Returns the {@link ProjoHandler} for the given interface.
    *
    * @param type the interface
    * @param <_Artifact_> the type of artifact handled by the {@link ProjoHandler}
    * @return the {@link ProjoHandler}
    **/
    public abstract <_Artifact_> ProjoHandler<_Artifact_> getHandler(Class<_Artifact_> type);

    /**
    * Specifies the order of precedence of a particular Projo implementation. A higher number indicates
    * higher precedence. The Projo framework will always select the implementation with the highest precedence.
    *
    * @return the precedence between {@link Integer#MIN_VALUE} (inclusive) and {@link Integer#MAX_VALUE} (inclusive)
    **/
    protected abstract int precedence();

    /**
    * Returns the Projo implementation's {@link ProjoHandler.ProjoInitializer}. This abstract method is implemented by
    * the actual Projo implementation.
    *
    * @param type the Projo interface
    * @param <_Artifact_> the type of the generated artifact
    * @param defaultPackage boolean indicating if generated code should be placed in the default package
    * @param additionalInterfaces additional interfaces that should be implemented by the generated objects
    * @return the {@link ProjoHandler.ProjoInitializer}
    **/
    public abstract <_Artifact_> ProjoHandler<_Artifact_>.ProjoInitializer initializer(Class<_Artifact_> type, boolean defaultPackage, Class<?>... additionalInterfaces);

    /**
    * Determines if the given class is an implementation class generated by the particular {@link Projo}
    * implementation.
    *
    * @param type the class to be tested
    * @return {@code true} if the class was generated by {@link Projo}, {@code false} otherwise
    **/
    protected boolean isProjoImplementation(Class<?> type)
    {
        return ProjoObject.class.isAssignableFrom(type);
    }

    /**
    * Returns the Projo implementation's {@link Builder} implementation. By default, this method
    * returns a {@link FactoryBuilder}, but Projo implementations may override with a more specific
    * or optimized implementation.
    *
    * @param type the type to be built with the builder
    * @param <_Artifact_> the type of artifact built by the builder
    * @return the {@link Builder} implementation
    **/
    protected <_Artifact_> Builder<_Artifact_> getBuilderImplementation(Class<_Artifact_> type)
    {
        return new FactoryBuilder<>(type);
    }

    /**
    * Creates a new {@link Intermediate} object that provides factories for creating artifacts.
    *
    * @param type the Projo interface
    * @param <_Artifact_> the type of the generated artifact
    * @return a new {@link Intermediate} object
    **/
    public static <_Artifact_> Intermediate<_Artifact_> creates(Class<_Artifact_> type)
    {
        return () -> type;
    }

    /**
    * Creates a new mutable Projo object. This method assumes that the object's interface provides
    * setter methods that allow setting the object's fields.
    *
    * @param type the Projo interface class
    * @param <_Artifact_> the type of the generated artifact
    * @return a new object whose fields will be initialized to default values
    **/
    public static <_Artifact_> _Artifact_ create(Class<_Artifact_> type)
    {
        return creates(type).initialize().members(getGetterFunctions(type)).returnInstance();
    }

    /**
    * Returns a new, empty delegate mapping.
    *
    * @param <TYPE> the inferred mapping type
    * @return a new {@link Mapping}
    **/
    public static <TYPE> Mapping<TYPE> mapping()
    {
        return new Mapping<TYPE>()
        {
            @Override
            Class<TYPE> current()
            {
                return null;
            }

            @Override
            Map<Class<?>, Class<?>> syntheticToDelegate()
            {
                return new HashMap<>();
            }

            @Override
            Map<Class<?>, Class<?>> delegateToSynthetic()
            {
                return new HashMap<>();
            }

            @Override
            public Map<Class<?>, Adapter<?, ?>> adapters()
            {
                return new HashMap<>();
            }
        };
    }

    /**
    * Creates a delegate object that wraps another object. All methods are directly forwarded to the wrapped
    * object with the exact same signatures (no type transformation is performed). The wrapped original object
    * must implement the primary interface and all additional interfaces (if any), or at least have methods
    * with the matching signatures (if the object does not technically implement the interface).
    *
    * @param <_Artifact_> the primary artifact type
    * @param original the original object to be wrapped
    * @param primaryInterface the primary interface
    * @param additionalInterfaces additional interfaces
    * @return the proxy object
    **/
    public static <_Artifact_>
    _Artifact_ delegate(Object original, Class<_Artifact_> primaryInterface, Class<?>... additionalInterfaces)
    {
        return creates(primaryInterface).initialize(additionalInterfaces).proxy(original, null, false).returnInstance();
    }

    /**
     * Creates a delegate object that wraps another object. All methods are directly forwarded to the wrapped
     * object with the exact same signatures (no type transformation is performed). The wrapped original object
     * must implement the primary interface and all additional interfaces (if any), or at least have methods
     * with the matching signatures (if the object does not technically implement the interface).
     *
     * @param <_Artifact_> the primary artifact type
     * @param original the original object to be wrapped
     * @param primaryInterface the primary interface
     * @param defaultPackage boolean indicating if generated code should be placed in the default package
     * @param additionalInterfaces additional interfaces
     * @return the proxy object
     **/
    public static <_Artifact_>
    _Artifact_ delegate(Object original, Class<_Artifact_> primaryInterface, boolean defaultPackage, Class<?>... additionalInterfaces)
    {
        return creates(primaryInterface).initialize(additionalInterfaces).proxy(original, null, defaultPackage).returnInstance();
    }

    /**
    * Creates a proxy object that wraps another object with the goal of overriding and reimplementing
    * certain methods. This is useful in cases where an interface can be sub-typed but there is no mechanism
    * to create the objects (e.g., because they are created by some other framework). By wrapping the object
    * the original object can be re-used but its behavior can be modified. All methods are directly forwarded
    * to the wrapped object with the exact same signatures (no type transformation is performed). However,
    * default methods in the provided interfaces can be used to modify the behavior of the proxies (instead
    * of forwarding to the wrapped object, the code in the default method will be executed). The wrapped
    * original object must implement the interface's sole super-interface.
    *
    * @param <_Original_> the original artifact type
    * @param <_Artifact_> the primary artifact type
    * @param original the original object to be wrapped
    * @param overrideInterface the primary interface
    * @return the proxy object
    **/
    public static <_Original_, _Artifact_ extends _Original_>
    _Artifact_ delegateOverride(_Original_ original, Class<_Artifact_> overrideInterface)
    {
        if (overrideInterface.getInterfaces().length == 0)
        {
            throw new IllegalArgumentException("interface must have a super-interface");
        }
        return creates(overrideInterface).initialize().proxy(original, overrideInterface, true).returnInstance();
    }

    public static <_Artifact_, _Delegate_>
    _Artifact_ delegate(Class<_Artifact_> type, _Delegate_ delegate, Mapping<?> mapping)
    {
        @SuppressWarnings("unchecked")
        Class<_Artifact_> realType = type == Object.class?
            (Class<_Artifact_>)mapping.getSynthetic(delegate.getClass()):
            type;
        return creates(realType).initialize(Delegated.class).delegate(delegate, mapping).returnInstance();
    }

    public static <_Artifact_, _Delegate_>
    _Delegate_ unwrap(_Artifact_ artifact)
    {
        @SuppressWarnings("unchecked")
        _Delegate_ delegate = (_Delegate_)((Delegated)artifact).getDelegate();
        return delegate;
    }

    /**
    * Finds a type's "self" type parameter (if present). A "self" type parameter is a type variable
    * that is recursively bound to the owning type itself. For example, in the following interface,
    * {@code S} is the type's "self" type variable:
    * <pre>
    * interface Pair&lt;S extends Pair&lt;S, L, R&gt;, L, R&gt;
    * </pre>
    * "Self" type variables can occur at any position, but are typically either the first or the last
    * type parameter.
    *
    * @param type the {@link Class} whose self type variable is to be identified
    * @return an {@link Optional} {@link TypeVariable}, or {@link Optional#empty()} if no self type
    *         parameter was found
    **/
    public static Optional<TypeVariable<?>> getSelfType(Class<?> type)
    {
        Stream<TypeVariable<?>> typeParameters = Stream.of(type.getTypeParameters());
        Stream<Entry<TypeVariable<?>, Type>> upperBounds =
            typeParameters.flatMap(variable ->
                Stream.of(variable.getBounds()).map(it -> new SimpleEntry<>(variable, it)));
        List<TypeVariable<?>> candidates = upperBounds
            .filter(entry -> isSelfType(entry.getKey(), entry.getValue(), type))
            .map(Entry::getKey)
            .collect(toList());
        return candidates.size() == 1? Optional.of(candidates.get(0)):Optional.empty();
    }

    /**
    * Determines if given type bounds describe a type's "self" type variable.
    * For example, given the bounded type variable {@code S extends Example<S>} of the raw type
    * {@code Example}, {@link TypeVariable} {@code S} would be passed as the first parameter,
    * {@code Example<S>} as the second parameter, and {@code Example} as the third parameter. The
    * result for this example would be {@code true}, because (1) {@code Example<S>} is a
    * {@link ParameterizedType}, (2) the raw type of {@code Example<S>} is {@code Example}, and
    * (3) the type arguments of {@code Example<S>} do contain the type variable {@code S}.
    *
    * @param parameter the type variable
    * @param bounds the type variables's upper bounds to be checked (could be the only one or one of many)
    * @param selfTypeCandidate the raw type 
    * @return {@code true} if and only if the bounds' raw type is the same raw type as provided and
    * the bounds' list of type arguments contains the provided type variable
    **/
    public static boolean isSelfType(TypeVariable<?> parameter, Type bounds, Class<?> selfTypeCandidate)
    {
        return bounds instanceof ParameterizedType
            && ((ParameterizedType)bounds).getRawType().equals(selfTypeCandidate)
            && Arrays.asList(((ParameterizedType)bounds).getActualTypeArguments()).contains(parameter);
    }

    /**
    * Provides a Projo {@link Builder} for the given interface.
    *
    * @param type the interface
    * @param <_Artifact_> the type of artifact built by the builder
    * @return the {@link Builder}
    **/
    public static <_Artifact_> Builder<_Artifact_> builder(Class<_Artifact_> type)
    {
        return getImplementation().getBuilderImplementation(type);
    }

    /**
    * Determines if a given class is a Projo class (i.e., an implementation class generated by Projo).
    *
    * @param type the class to be tested
    * @return {@code true} if the class was generated by {@link Projo}, {@code false} otherwise
    **/
    public static boolean isProjoClass(Class<?> type)
    {
        return getImplementation().isProjoImplementation(type);
    }

    /**
    * Determines if the given interface would produce Projo Value Objects.
    *
    * @param projo the Projo interface
    * @return {@code true} if the interface will produce Value Objects, {@code false} otherwise
    **/
    public static boolean isValueObject(Class<?> projo)
    {
        Set<Class<?>> allSuperInterfaces = superInterfaces(projo);
        return allSuperInterfaces.stream()
            .anyMatch(type -> type.getDeclaredAnnotation(ValueObject.class) != null
                || methodExists(type, "equals", Object.class)
                || methodExists(type, "hashCode"));
    }

    /**
    * Determines if the given interface has a custom {@link #toString()} method.
    *
    * @param projo the Projo interface
    * @return {@code true} if the interface has a custom {@link #toString()} method
    **/
    public static boolean hasCustomToString(Class<?> projo)
    {
        return methodExists(projo, "toString");
    }

    /**
    * Returns the actual Projo implementation that is being used. Projo can be implemented in several different ways,
    * including Java proxies or runtime code generation.
    *
    * @return the {@link Projo} implementation class currently active
    **/
    public static Projo getImplementation()
    {
        return implementation;
    }

    /**
    * Gets the concrete Projo implementation class of an interface or abstract type.
    *
    * @param type the interface
    * @param <_Artifact_> the interface type
    * @return the concrete implementation of that type, as generated by Projo
    **/
    public static <_Artifact_> Class<? extends _Artifact_> getImplementationClass(Class<_Artifact_> type)
    {
        return getImplementationClass(type, false);
    }

    /**
    * Gets the concrete Projo implementation class of an interface or abstract type.
    *
    * @param type the interface
    * @param <_Artifact_> the interface type
    * @param defaultPackage {@code true} if generated code should be placed in the default package, {@code false} otherwise
    * @return the concrete implementation of that type, as generated by Projo
    **/
    public static <_Artifact_> Class<? extends _Artifact_> getImplementationClass(Class<_Artifact_> type, boolean defaultPackage)
    {
        return getImplementation().getHandler(type).getImplementationOf(type, defaultPackage);
    }

    /**
    * Gets the Projo interface of a Projo implementation class.
    *
    * @param type the implementation class
    * @param <_Artifact_> the interface type
    * @return the Projo interface
    **/
    public static <_Artifact_> Class<_Artifact_> getInterfaceClass(Class<? extends _Artifact_> type)
    {
        Predicate<Class<?>> isProjoObject = ProjoObject.class::equals;
        @SuppressWarnings("unchecked")
        Class<_Artifact_> result = (Class<_Artifact_>)Stream.of(type.getInterfaces()).filter(isProjoObject.negate()).findFirst().get();
        return result;
    }

    /**
    * Gets the factory (if any) of a Projo interface.
    *
    * @param projoInterface the Projo interface
    * @return the interface's {@link Factory}, or {@code null} if no factory was found
    **/
    public static Factory getFactory(Class<?> projoInterface)
    {
        Stream<Field> fields = Stream.of(projoInterface.getDeclaredFields());
        Predicate<Field> isFactory = field -> Factory.class.isAssignableFrom(field.getType()) && isStatic(field.getModifiers());
        return fields.filter(isFactory).map(Projo::getFactory).findFirst().orElse(null);
    }

    private static Projo initialize()
    {
        if (implementation != null)
        {
            Error error = new ExceptionInInitializerError("Projo already initialized: " + implementation);
            implementation = null;
            throw error;
        }
        ServiceLoader<Projo> serviceLoader = ServiceLoader.load(Projo.class);
        Stream<Projo> implementations = stream(serviceLoader.spliterator(), false);
        return implementations.max(by(Projo::precedence)).get();
    }

    private static Comparator<Projo> by(Function<Projo, Integer> property)
    {
        return (object1, object2) ->
        {
            int value1 = property.apply(object1), value2 = property.apply(object2);
            return value1 < value2? -1: value1 == value2? 0:1;
        };
    }

    private static Factory getFactory(Field field)
    {
        try
        {
            return (Factory)field.get(null);
        }
        catch (IllegalAccessException illegalAccess)
        {
            throw new IllegalArgumentException(illegalAccess.getMessage());
        }
    }

    /**
    * Returns methods of a class that match at least one of a list of predicates.
    *
    * @param type the {@link Class}
    * @param predicates the predicates (if no predicate is supplied the method will return no methods)
    * @return all methods of the class that match at least one of the given predicates
    **/
    @SafeVarargs
    public static Stream<Method> getMethods(Class<?> type, Predicate<Method>... predicates)
    {
        return getMethods(type, emptyList(), predicates);
    }

    /**
    * Returns methods of a class that match at least one of a list of predicates.
    *
    * @param type the {@link Class}
    * @param additionalImplements additionally implemented interfaces, by way of {@link pro.projo.annotations.Implements} annotations
    * @param predicates the predicates (if no predicate is supplied the method will return no methods)
    * @return all methods of the class that match at least one of the given predicates
    **/
    @SafeVarargs
    public static Stream<Method> getMethods(Class<?> type, List<String> additionalImplements, Predicate<Method>... predicates)
    {
        Stream<Class<?>> additional = additionalImplements.stream().map(Projo::forName);
        Stream<Method> methods = Stream.concat(Stream.of(type.getMethods()), additional.flatMap(it -> Stream.of(it.getMethods())));
        return methods.filter(Stream.of(predicates).reduce(always -> false, Predicate::or));
    }

    /**
    * Returns all property getter methods of a class as {@link Method}s.
    *
    * @param type the {@link Class}
    * @param <_Artifact_> the type
    * @return an array of getter {@link Function}s
    **/
    public static <_Artifact_> Method[] getGetterMethods(Class<_Artifact_> type)
    {
        return getMethods(type, Predicates.getter).sorted(comparing(Method::getName)).toArray(Method[]::new);
    }

    /**
    * Returns all property getter methods of a class as {@link Function}s.
    *
    * @param type the {@link Class}
    * @param <_Artifact_> the type
    * @return an array of getter {@link Function}s
    **/
    public static <_Artifact_> Function<_Artifact_, ?>[] getGetterFunctions(Class<_Artifact_> type)
    {
        MethodFunctionConverter converter = new MethodFunctionConverter();
        return Stream.of(getGetterMethods(type)).map(converter::convert).<Function<_Artifact_, ?>>toArray(Function[]::new);
    }

    /**
    * Loads a class by name without throwing exceptions.
    *
    * @param typeName the fully qualified class name
    * @return a {@link Class} object or {@code null} if a problem occurred
    **/
    public static Class<?> forName(String typeName)
    {
        int index = typeName.indexOf('<');
        String className = index == -1? typeName:typeName.substring(0, index);
        try
        {
            return Class.forName(className);
        }
        catch (ClassNotFoundException classNotFound)
        {
            throw new NoClassDefFoundError(classNotFound.getMessage());
        }
    }

    private static Set<Class<?>> superInterfaces(Class<?> type)
    {
        Set<Class<?>> superInterfaces = new HashSet<>();
        superInterfaces.add(type);
        Stream<Class<?>> stream = Stream.of(type.getInterfaces()).flatMap(it -> superInterfaces(it).stream());
        return (Set<Class<?>>)stream.collect(toCollection(() -> superInterfaces));
    }

    private static boolean methodExists(Class<?> type, String methodName, Class<?>... parameters)
    {
        try
        {
            type.getDeclaredMethod(methodName, parameters);
            return true;
        }
        catch (@SuppressWarnings("unused") NoSuchMethodException methodDoesNotExist)
        {
            return false;
        }
    }
}
