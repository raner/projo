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

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* The {@link DefaultAttributeNameConverter} converts XML/SGML attribute names from
* kebab-case to camel-case (e.g., {@code data-widget-id} becomes {@code dataWidgetId})
* and also escapes Java keywords by adding an extra underscore ({@code _}). It is
* the default {@link AttributeNameConverter} used by Projo.
*
* @author Mirko Raner
**/
public class DefaultAttributeNameConverter extends AttributeNameConverter
{
    private final static String[] KEYWORD_LIST =
    {
        "abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package",
        "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements",
        "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum",
        "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char",
        "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile",
        "const", "float", "native", "super", "while"
    };

    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(KEYWORD_LIST));

    @Override
    public String convertAttributeName(String name)
    {
        String converted = super.convertAttributeName(name);
        return KEYWORDS.contains(converted)? converted + "_" : converted;
    }
}
