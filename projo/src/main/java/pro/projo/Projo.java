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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.annotations.ValueObject;
import static java.lang.reflect.Modifier.isStatic;
import static java.util.stream.Collectors.toList;
import static pro.projo.internal.ProjoInvocationHandler.getter;

/**
* The {@link Projo} class provides static methods for creating Projo objects, as well as other
* related utility methods.
*
* @author Mirko Raner
**/
public class Projo
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

    /**
    * Creates a new {@link Intermediate} object that provides factories for creating artifacts.
    *
    * @param type the Projo interface
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
    * @return a new object whose fields will be initialized to default values
    **/
    public static <_Artifact_> _Artifact_ create(Class<_Artifact_> type)
    {
        return creates(type).initialize().new Members(forAllGetters(type)).returnInstance();
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
        @SuppressWarnings("unchecked")
        Function<_Artifact_, ?>[] array = (Function<_Artifact_, ?>[])new Function<?,?>[0];
        Predicate<Method> getters = method -> getter.test(method, new Object[method.getParameterCount()]);
        Function<Method, Function<_Artifact_, ?>> toFunction = method -> artifact -> invoke(method, artifact);
        return Stream.of(type.getDeclaredMethods()).filter(getters).map(toFunction).collect(toList()).toArray(array);
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
