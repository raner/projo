//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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

import java.util.HashMap;
import java.util.Map;
import static java.util.Collections.unmodifiableMap;

/**
* A {@link Mapping} maintains a bidirectional map between synthetic Projo types and
* their delegate types. {@link Mapping}s are used only for delegates, not for simple
* getter/setter-style Projo objects.
* The {@link Mapping} class follows an <i>immutable builder</i> pattern.
*
* @author Mirko Raner
**/
public abstract class Mapping
{
    public abstract class Source
    {
        abstract Class<?> source();
        
        Mapping to(Class<?> destination)
        {
            return new Mapping()
            {
                @Override
                Map<Class<?>, Class<?>> syntheticToDelegate()
                {
                    HashMap<Class<?>, Class<?>> mapping = new HashMap<>(Mapping.this.syntheticToDelegate());
                    mapping.put(source(), destination);
                    return unmodifiableMap(mapping);
                }

                @Override
                Map<Class<?>, Class<?>> delegateToSynthetic()
                {
                    HashMap<Class<?>, Class<?>> mapping = new HashMap<>(Mapping.this.delegateToSynthetic());
                    mapping.put(destination, source());
                    return unmodifiableMap(mapping);
                }
            };
        }
    }

    abstract Map<Class<?>, Class<?>> syntheticToDelegate();

    abstract Map<Class<?>, Class<?>> delegateToSynthetic();

    public Class<?> getDelegate(Class<?> type)
    {
        return syntheticToDelegate().getOrDefault(type, type);
    }
    
    Source map(Class<?> source)
    {
        return new Source()
        {
            @Override
            Class<?> source()
            {
              return source;
            }
        };
    }
}
