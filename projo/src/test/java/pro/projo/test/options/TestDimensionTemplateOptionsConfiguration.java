//                                                                          //
// Copyright 2024 Mirko Raner                                               //
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
package pro.projo.test.options;

import pro.projo.interfaces.annotation.Options;
import pro.projo.test.TestDimensionTemplateConfiguration;

/**
* The {@link TestDimensionTemplateOptionsConfiguration} is used for internal testing.
*
* @author Mirko Raner
**/
public class TestDimensionTemplateOptionsConfiguration extends TestDimensionTemplateConfiguration
{
    private final static long serialVersionUID = -8492805138996252572L;

    @Override
    protected Options options()
    {
        return getClass().getPackage().getAnnotation(Options.class);
    }

    @Override
    protected String targetPackage()
    {
        return "pro.projo.generation.template.options.test.dimensions";
    }
}
