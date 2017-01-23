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
package pro.projo.internal;

import java.util.Map;
import static java.util.EnumSet.allOf;
import static java.util.stream.Collectors.toMap;

/**
* The {@link Default} class provides the default values for Java's primitive types.
*
* @author Mirko Raner
**/
public enum Default
{
    INT(int.class, 0),
    LONG(long.class, 0L),
    FLOAT(float.class, 0F),
    DOUBLE(double.class, 0D),
    BYTE(byte.class, (byte)0),
    SHORT(short.class, (short)0),
    BOOLEAN(boolean.class, false),
    CHAR(char.class, '\0');

    private Class<?> type;
    private Object value;

    private Default(Class<?> type, Object value)
    {
        this.type = type;
        this.value = value;
    }

    /**
    * The map containing the default values.
    **/
    public final static Map<Class<?>, Object> VALUES = allOf(Default.class).stream().collect(toMap(item -> item.type, item -> item.value));
}
