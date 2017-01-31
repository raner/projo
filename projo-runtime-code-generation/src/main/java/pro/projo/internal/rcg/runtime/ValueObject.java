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
package pro.projo.internal.rcg.runtime;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static pro.projo.internal.rcg.RuntimeCodeGenerationHandler.getInterfaceName;

/**
* Projo uses the {@link ValueObject} class as the base class for runtime-generated Projo objects that
* are determined to be Value Objects (per {@link pro.projo.Projo#isValueObject(Class)}).
* This special base class is a compromise between proxy/reflection-based implementation and fully
* runtime-generated code (which turns out to be more involved that one would think at first glance).
* {@link ValueObject} is part of a hybrid approach where all fields, getters, and setters are generated
* at runtime (and neither reading nor writing a field requires reflection), but where the
* {@link Object#equals(Object)} and {@link Object#hashCode()} are still implemented via reflection.
* All object properties are exclusively access via the getter methods, so even if there is a discrepancy
* between getters and generated fields the equals/hashCode contract will still be fulfilled.
*
* @author Mirko Raner
**/
public class ValueObject
{
    private final static Predicate<Method> gettersOnly = method ->
    {
        return method.getParameterCount() == 0
            && !void.class.equals(method.getReturnType())
            && !"hashCode".equals(method.getName());
    };

    /**
    * Determines the equality of two objects based on the equality of their properties.
    * The method is implemented by iterating over all identified getter methods and counting for how many
    * of those getters the values that were extracted from the two objects are different. The implementation
    * will return {@code true} of there are either (a) no getter methods, or (b) none of the detected getter
    * methods return different values for the two objects. If there is at least one getter method that
    * extracts different values from the compared objects, the implementation will return {@code false}.
    *
    * @param other the object to compare with
    * @return {@code true} if the two objects are considered equal, {@code false} otherwise
    **/
    @Override
    public boolean equals(Object other)
    {
        if (getClass().isInstance(other))
        {
            ValueObject that = (ValueObject)other;
            Predicate<Method> valuesEqual = method ->
            {
                Object thisValue = this.value(method);
                Object thatValue = that.value(method);
                return thisValue == null? thatValue == null:thisValue.equals(thatValue);
            };
            Predicate<Method> valuesNotEqual = valuesEqual.negate();
            return getters().filter(valuesNotEqual).count() == 0;
        }
        return false;
    }

    /**
    * Calculates the hash code of an object based on the hash code of its member objects.
    * This implementation will extract the values for all identified getter methods and pass them to the
    * {@link Objects#hash(Object...)} method.
    *
    * @return the object's hash code
    **/
    @Override
    public int hashCode()
    {
        return Objects.hash(getters().map(this::value).toArray(Object[]::new));
    }

    @Override
    public String toString()
    {
        return getInterfaceName(getClass()) + "@" + Integer.toHexString(hashCode());
    }

    private Stream<Method> getters()
    {
        return Stream.of(getClass().getDeclaredMethods()).filter(gettersOnly);
    }

    private Object value(Method getter)
    {
        try
        {
            return getter.invoke(this);
        }
        catch (IllegalAccessException illegalAccess)
        {
            IllegalAccessError error = new IllegalAccessError(illegalAccess.getMessage());
            error.initCause(illegalAccess);
            throw error;
        }
        catch (InvocationTargetException exception)
        {
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }
}
