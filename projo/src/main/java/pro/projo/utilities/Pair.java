//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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
package pro.projo.utilities;

import java.util.AbstractMap.SimpleEntry;
import java.util.AbstractMap;
import java.util.Map;

/**
* The {@link Pair} class implements a tuple type based on {@link AbstractMap.Entry}.
* It adds a number of simple convenience methods that my come in handy inside lambda
* expressions.
*
* @param <KEY> the key type
* @param <VALUE> the value type
*
* @author Mirko Raner
**/
public class Pair<KEY, VALUE> extends SimpleEntry<KEY, VALUE>
{
    private final static long serialVersionUID = -6081087530785312183L;

    public Pair(KEY key, VALUE value)
    {
        super(key, value);
    }

    public boolean hasKey()
    {
        return getKey() != null;
    }

    public boolean hasValue()
    {
        return getValue() != null;
    }

    public void addTo(Map<KEY, ? super VALUE> map)
    {
        map.put(getKey(), getValue());
    }

    public static <KEY, VALUE> Pair<KEY, VALUE> pair(KEY key, VALUE value)
    {
        return new Pair<>(key, value);
    }
}
