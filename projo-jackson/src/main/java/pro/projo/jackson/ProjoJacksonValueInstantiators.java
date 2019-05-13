//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
package pro.projo.jackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.module.SimpleValueInstantiators;
import pro.projo.Projo;

public class ProjoJacksonValueInstantiators extends SimpleValueInstantiators
{
    private final static long serialVersionUID = 2764150436818905349L;

    @Override
    public ValueInstantiator findValueInstantiator(DeserializationConfig configuration,
        BeanDescription beanDescription, ValueInstantiator defaultInstantiator)
    {
        Class<?> type = beanDescription.getType().getRawClass();
        if (Projo.isProjoClass(type))
        {
            return new ProjoJacksonValueInstantiator<>(type);
        }
        return super.findValueInstantiator(configuration, beanDescription, defaultInstantiator);
    }
}
