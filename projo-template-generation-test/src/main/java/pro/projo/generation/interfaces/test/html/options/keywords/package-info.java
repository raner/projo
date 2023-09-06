//                                                                          //
// Copyright 2023 Mirko Raner                                               //
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
@Dtd
(
    path="html5/html5.dtd",
    attributeNameConverter=AttributeNameConverter.class,
    aliases=
    {
        @Alias({"head", "metadata"}),
        @Alias({"body", "content"}),
        @Alias({"div", "vbox"}),
        @Alias({"span", "hbox"})
    },
    attributes=
    {
        @Attribute(name="pattern", type=Pattern.class),
        @Attribute(name="class", type=ElementClass.class)
    },
    options=@Options
    (
        fileExtension=".kava",
        outputLocation=SOURCE_OUTPUT
    )
)
package pro.projo.generation.interfaces.test.html.options.keywords;

import java.util.regex.Pattern;
import pro.projo.interfaces.annotation.Alias;
import pro.projo.interfaces.annotation.Attribute;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;

/**
* The {@code pro.projo.generation.interfaces.test.html} package contains test cases
* for the {@link Dtd} annotation, based on a DTD for HTML5.
*
* @author Mirko Raner
**/
