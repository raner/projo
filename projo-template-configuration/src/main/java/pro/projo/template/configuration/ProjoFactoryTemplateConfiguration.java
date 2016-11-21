//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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
import java.util.function.Function;
import java.util.stream.Stream;
import pro.projo.template.annotation.Configuration;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

/**
* The {@link ProjoFactoryTemplateConfiguration} creates the template {@link Configuration}s for
* generating Projo factory interfaces.
*
* @author Mirko Raner
**/
public class ProjoFactoryTemplateConfiguration extends ArrayList<Configuration>
{
    private final static long serialVersionUID = 3050346253598962997L;

    public ProjoFactoryTemplateConfiguration()
    {
        final Format typeParameterDocumentationFormat = new MessageFormat("@param <{0}> the type of the {1} field");
        final Format methodParameterDocumentationFormat = new MessageFormat("@param {0} the {0} parameter");
        final String[] name = {"singles", "doubles", "triples"};
        final String[] ordinal = {"first", "second", "third"};
        for (int index = 0; index < name.length; index++)
        {
            final int number = index;
            Configuration configuration = new Configuration()
            {
                @Override
                public String fullyQualifiedClassName()
                {
                    return "pro.projo." + name[number] + ".Factory";
                }

                @Override
                public Map<String, String> parameters()
                {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("template", name[number]);
                    parameters.put("n", String.valueOf(number+1));
                    parameters.put("s", number > 0? "s":"");
                    parameters.put("First", "_First_");
                    parameters.put("AdditionalTypeParameters", format(number, this::typeParameterName, ", "));
                    parameters.put("additionalTypeParameterDocumentation", format(number, this::typeParameterDocumentation, lineSeparator() + "* "));
                    parameters.put("additionalMethodParameterDocumentation", format(number, this::methodParameterDocumentation, lineSeparator() + "    * "));
                    parameters.put("argumentAndAdditionalParameters", format(number, this::typeAndParameterName, ", ").substring("_First_ ".length()));
                    return parameters;
                }

                @Override
                public String toString()
                {
                    return fullyQualifiedClassName() + ":" + parameters();
                }

                private String format(int count, Function<String, String> mapper, String delimiter)
                {
                    return ordinals(count).map(mapper).collect(joining(delimiter));
                }

                private Stream<String> ordinals(int limit)
                {
                    return Stream.of(ordinal).limit(limit+1);
                }

                private String typeParameterName(String regularName)
                {
                    return "_" + regularName.substring(0, 1).toUpperCase() + regularName.substring(1) + "_";
                }

                private String typeAndParameterName(String regularName)
                {
                    return typeParameterName(regularName) + " " + regularName;
                }

                private String typeParameterDocumentation(String regularName)
                {
                    Object[] arguments = {typeParameterName(regularName), regularName};
                    return typeParameterDocumentationFormat.format(arguments);
                }

                private String methodParameterDocumentation(String regularName)
                {
                    Object[] arguments = {regularName};
                    return methodParameterDocumentationFormat.format(arguments);
                }
            };
            add(configuration);
        }
    }
}
