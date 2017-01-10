//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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
import java.util.function.Function;
import java.util.stream.Stream;
import pro.projo.template.annotation.Configuration;
import static java.util.stream.Collectors.joining;

/**
* The {@link ProjoBaseTemplateConfiguration} provides shared data and functionality for Projo template
* configurations.
*
* @author Mirko Raner
**/
public interface ProjoBaseTemplateConfiguration
{
    String[] NAMES =
    {
        "singles", "doubles", "triples", "quadruples", "quintuples", "sextuples", "septuples",
        "octuples", "nonuples", "decuples", "undecuples", "duodecuples", "tredecuples", "quattuordecuples",
        "quindecuples", "sexdecuples", "septendecuples", "octodecuples", "novemdecuples", "vigintuples",
        "unvigintuples", "duovigintuples", "trevigintuples", "quattuorvigintuples", "quinvigintuples",
        "sexvigintuples", "septenvigintuples", "octovigintuples", "novemvigintuples", "trigintuples"
    };

    String[] ORDINALS =
    {
        "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
        "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth",
        "eighteenth", "nineteenth", "twentieth", "twentyFirst", "twentySecond", "twentyThird", "twentyFourth",
        "twentyFifth", "twentySixth", "twentySeventh", "twentyEighth", "twentyNinth", "thirtieth"
    };

    interface BaseConfiguration extends Configuration
    {
        Format typeParameterDocumentationFormat();

        Format methodParameterDocumentationFormat();

        default Stream<String> ordinals(int limit)
        {
            return Stream.of(ORDINALS).limit(limit+1);
        }

        default String format(int from, int to, Function<String, String> mapper, String delimiter)
        {
            return ordinals(to).skip(from).map(mapper).collect(joining(delimiter));
        }

        default String typeParameterName(String regularName)
        {
            return "_" + regularName.substring(0, 1).toUpperCase() + regularName.substring(1) + "_";
        }

        default String typeAndParameterName(String regularName)
        {
            return typeParameterName(regularName) + " " + regularName;
        }

        default String hyphenatedName(String regularName)
        {
            final String TWENTY = "twenty";
            boolean twentySomething = regularName.startsWith(TWENTY);
            return (twentySomething? "twenty-" + regularName.substring(TWENTY.length()):regularName).toLowerCase();
        }

        default String typeParameterDocumentation(String regularName)
        {
            Object[] arguments = {typeParameterName(regularName), hyphenatedName(regularName)};
            return typeParameterDocumentationFormat().format(arguments);
        }

        default String methodParameterDocumentation(String regularName)
        {
            Object[] arguments = {regularName, hyphenatedName(regularName)};
            return methodParameterDocumentationFormat().format(arguments);
        }
    }
}
