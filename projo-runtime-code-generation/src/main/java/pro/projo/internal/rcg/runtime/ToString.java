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

import java.lang.reflect.Field;
import pro.projo.internal.Default;

/**
* The {@link ToString} interface provides a default implementation of the {@link #description(Field)} method,
* which describes the contents of an individual field.
*
* @author Mirko Raner
**/
public interface ToString
{
    /**
    * Provides a string description of an individual field.
    *
    * @param field the {@link Field}
    * @return the field description, typically <i>field</i>=<i>value</i>
    **/
    default String description(Field field)
    {
        field.setAccessible(true);
        try
        {
            Object value = field.get(this);
            value = value != null? value : Default.VALUES.get(field.getType());
            value = value instanceof String? "\"" + value + "\"" : value;
            return field.getName() + "=" + value;
        }
        catch (IllegalAccessException illegalAccess)
        {
            IllegalAccessError error = new IllegalAccessError(illegalAccess.getMessage());
            error.initCause(illegalAccess);
            throw error;
        }
    }
}
