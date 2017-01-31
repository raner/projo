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
package pro.projo.internal;

import java.text.Format;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* The {@link PropertyMatches} converts between Projo's internal property names and the corresponding getter/setter
* method names and vice versa. This class is intended to be used as a stateless singleton.
* <br>
* <b>NOTE:</b> For standard getter and setter names, Projo's internal field names will start with a capital letter,
* not a lower-case one.
*
* @author Mirko Raner
**/
public class PropertyMatcher
{
    private final Pattern pattern = Pattern.compile("(?:[gs]et)??([A-Z][A-Za-z0-9]*)");
    private final Format getterName = new MessageFormat("get{0}");

    public String getterName(String propertyName)
    {
        Object[] name = {propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1)};
        return getterName.format(name);
    }

    public String propertyName(String methodName)
    {
        Matcher matcher = pattern.matcher(methodName);
        if (matcher.matches())
        {
            String matched = matcher.group(1);
            return matched.substring(0, 1).toLowerCase() + matched.substring(1);
        }
        return methodName;
    }
}
