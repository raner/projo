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
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import pro.projo.Projo;

/**
* {@link ProjoJacksonSerializers} returns a special {@link ProjoJacksonSerializer} whenever
* it detects a Projo class.
*
* @author Mirko Raner
**/
public class ProjoJacksonSerializers extends SimpleSerializers
{
    private final static long serialVersionUID = 3618292130159662693L;

    @Override
    public JsonSerializer<?> findSerializer(SerializationConfig configuration, JavaType type, BeanDescription bean)
    {
        if (Projo.isProjoClass(type.getRawClass()))
        {
            return new ProjoJacksonSerializer(configuration, type, bean);
        }
        return super.findSerializer(configuration, type, bean);
    }
}
