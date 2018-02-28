//                                                                          //
// Copyright 2018 Mirko Raner                                               //
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
package pro.projo.template.configuration;

import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pro.projo.template.annotation.Configuration;
import static java.lang.System.lineSeparator;

/**
* The {@link ProjoFactoryTemplateConfiguration} creates the template {@link Configuration}s for
* generating Projo factory interfaces.
*
* @author Mirko Raner
**/
public class ProjoFactoryTemplateConfiguration extends ArrayList<Configuration> implements ProjoBaseTemplateConfiguration
{
    private final static long serialVersionUID = 3050346253598962997L;

    public ProjoFactoryTemplateConfiguration()
    {
        for (int index = 0; index < NAMES.length; index++)
        {
            final int number = index;
            Configuration configuration = new BaseConfiguration()
            {
                @Override
                public String fullyQualifiedClassName()
                {
                    return "pro.projo." + NAMES[number] + ".Factory";
                }

                @Override
                public Format typeParameterDocumentationFormat()
                {
                    return new MessageFormat("@param <{0}> the type of the {1} field");
                }

                @Override
                public Format methodParameterDocumentationFormat()
                {
                    return new MessageFormat("@param {0} the {1} parameter");
                }

                @Override
                public Map<String, Object> parameters()
                {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("template", NAMES[number]);
                    parameters.put("n", String.valueOf(number+1));
                    parameters.put("s", number > 0? "s":"");
                    parameters.put("First", "_First_");
                    parameters.put("AdditionalTypeParameters", format(0, number, this::typeParameterName, ", "));
                    parameters.put("additionalTypeParameterDocumentation", format(0, number, this::typeParameterDocumentation, lineSeparator() + "* "));
                    parameters.put("additionalMethodParameterDocumentation", format(0, number, this::methodParameterDocumentation, lineSeparator() + "    * "));
                    parameters.put("argumentAndAdditionalParameters", format(0, number, this::typeAndParameterName, ", ").substring("_First_ ".length()));
                    return parameters;
                }

                @Override
                public String toString()
                {
                    return fullyQualifiedClassName() + ":" + parameters();
                }
            };
            add(configuration);
        }
    }
}
