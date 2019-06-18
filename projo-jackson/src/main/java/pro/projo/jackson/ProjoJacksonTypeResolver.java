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

import com.fasterxml.jackson.databind.AbstractTypeResolver;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import pro.projo.Projo;

public class ProjoJacksonTypeResolver extends AbstractTypeResolver
{
    @Override
    public JavaType findTypeMapping(DeserializationConfig config, JavaType type)
    {
        JavaType typeMapping = super.findTypeMapping(config, type);
        if (typeMapping == null && type.isInterface() && !type.isContainerType())
        {
            Class<?> rawClass = type.getRawClass();
            Class<?> implementation = Projo.getImplementationClass(rawClass);
            typeMapping = SimpleType.constructUnsafe(implementation);
        }
        return typeMapping;
    }
}
