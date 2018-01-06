//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.ServiceLoader;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.annotations.ValueObject;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.Arrays.sort;
import static java.util.Comparator.comparing;
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
        pro.projo.trigintuples.Intermediate<_Artifact_> { /**/ }

    private static Projo implementation = initialize();

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
    * @return the {@link ProjoHandler.ProjoInitializer}
    **/
    public abstract <_Artifact_> ProjoHandler<_Artifact_>.ProjoInitializer initializer(Class<_Artifact_> type);

    /**
     * Returns the Projo implementation class. This abstract method is implemented by
     * the actual Projo implementation.
     *
     * @param type the Projo interface
     * @return the implementation class
     **/
    public abstract Class<?> getImplementationClass(Class<?> type);

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
        return creates(type).initialize().members(forAllGetters(type)).returnInstance();
    }

    /**
    * Determines if the given interface would produce Projo Value Objects.
    *
    * @param projo the Projo interface
    * @return {@code true} if the interface will produce Value Objects, {@code false} otherwise
    **/
    public static boolean isValueObject(Class<?> projo)
    {
        return projo.getDeclaredAnnotation(ValueObject.class) != null
            || methodExists(projo, "equals", Object.class)
            || methodExists(projo, "hashCode");
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

    private static <_Artifact_> Function<_Artifact_, ?>[] forAllGetters(Class<_Artifact_> type)
    {
        Predicate<Method> getters = method -> Predicates.getter.test(method, new Object[method.getParameterCount()]);
        Function<Method, Function<_Artifact_, ?>> toFunction = method -> artifact -> invoke(method, artifact);
        Method[] declaredMethods = type.getDeclaredMethods();
        sort(declaredMethods, comparing(Method::getName));
        @SuppressWarnings("unchecked")
        Function<_Artifact_, ?>[] result = Stream.of(declaredMethods).filter(getters).map(toFunction).toArray(Function[]::new);
        return result;
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

    private static Object invoke(Method method, Object artifact)
    {
        try
        {
            return method.invoke(artifact);
        }
        catch (IllegalAccessException illegalAccess)
        {
            throw new IllegalAccessError(illegalAccess.getMessage());
        }
        catch (InvocationTargetException exception)
        {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
}
