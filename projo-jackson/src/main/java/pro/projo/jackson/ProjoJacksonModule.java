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

import com.fasterxml.jackson.databind.module.SimpleModule;

/**
* The {@link ProjoJacksonModule} configures Jackson so that it can serialize and
* deserialize Projo objects.
*
* @author Mirko Raner
**/
public class ProjoJacksonModule extends SimpleModule
{
    private static final long serialVersionUID = 7412117452716589192L;

    @Override
    public void setupModule(SetupContext context)
    {
        super.setupModule(context);
        context.addSerializers(new ProjoJacksonSerializers());
        context.addAbstractTypeResolver(new ProjoJacksonTypeResolver());
        context.addValueInstantiators(new ProjoJacksonValueInstantiators());
    }
}
