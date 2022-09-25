//                                                                          //
// Copyright 2022 Mirko Raner                                               //
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
package pro.projo.interfaces.annotation.utilities;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.lang.Character.toUpperCase;

/**
* The {@link AttributeNameConverter} is Projo's way of converting XML/SGML attribute names
* to method names. The basic implementation converts from kebab-case to camel-case (e.g.,
* {@code data-widget-id} becomes {@code dataWidgetId}). It does not escape Java keywords,
* which means its implementation may not necessarily be suitable for generating Java APIs.
* For that purpose, the {@link DefaultAttributeNameConverter} may be a better choice (and
* is also the default choice if no other {@link AttributeNameConverter}) is specified.
*
* @author Mirko Raner
**/
public class AttributeNameConverter
{
    /**
    * Converts an SGML or XML attribute name to a Java-style method or field name.
    *
    * @param name the SGML/XML name (e.g., {@code data-widget-id})
    * @return the corresponding name according to Java conventions (e.g., {@code dataWidgetId})
    **/
    public String convertAttributeName(final String name)
    {
        Predicate<Character> hyphen = Character.valueOf('-')::equals;
        Stream<Character> characters = IntStream.range(0, name.length())
            .mapToObj(index -> index > 0 && hyphen.test(name.charAt(index-1))? toUpperCase(name.charAt(index)):name.charAt(index));
        return characters.filter(hyphen.negate()).map(Object::toString).collect(Collectors.joining());
    }
}
