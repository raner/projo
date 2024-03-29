//                                                                          //
// Copyright 2022 - 2023 Mirko Raner                                        //
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
package pro.projo.generation.utilities;

import java.util.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.template.annotation.Configuration;

/**
* {@link DefaultConfiguration} is a simple default implementation of the {@link Configuration}
* interface that provides a class name and a set of parameters.
*
* @author Mirko Raner
**/
public class DefaultConfiguration implements Configuration
{
    private String fullyQualifiedClassName;
    private Map<String, Object> parameters;
    private Options options;

    public DefaultConfiguration(String fullyQualifiedClassName, Map<String, Object> parameters)
    {
        this(fullyQualifiedClassName, parameters, null);
    }

    public DefaultConfiguration(String fullyQualifiedClassName, Map<String, Object> parameters, Options options)
    {
      this.fullyQualifiedClassName = fullyQualifiedClassName;
      this.parameters = parameters;
      this.options = options;
    }

    @Override
    public String fullyQualifiedClassName()
    {
        return fullyQualifiedClassName;
    }

    @Override
    public Map<String, Object> parameters()
    {
        return parameters;
    }

    @Override
    public Options options()
    {
        return options == null? Configuration.super.options():options;
    }
}
