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
package pro.projo.interfaces.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Dtd} annotation captures the necessary information for generating an API from a DTD.
*
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
@Repeatable(Dtds.class)
public @interface Dtd
{
    /**
    * @return the path to the main DTD file (may reference additional entity files)
    **/
    String path();

    /**
    * @return the default base interface to use for model elements (this must be an interface
    * with two type parameters: the first parameter for the parent content element type, and
    * the second for the element's own content type;
    * {@link Object} indicates that no specific base interface will be extended)
    **/
    Class<?> baseInterface() default Object.class;

    /**
    * @return the default base interface to use for empty (or "void") model elements
    * (this must be an interface with one type parameter, which designates the parent content
    * element type; {@link Object} indicates that no specific base interface will
    * be extended)
    **/
    Class<?> baseInterfaceEmpty() default Object.class;

    /**
    * @return the default base interface to use for mixed content elements that do not
    * have any permitted child elements, i.e. they can only contain plain text
    * (this must be an interface with one type parameter, which designates the parent content
    * element type; {@link Object} indicates that no specific base interface will
    * be extended)
    **/
    Class<?> baseInterfaceText() default Object.class;

    /**
    * @return the name format for generated element interfaces (by default this is just
    * the capitalized element name)
    * @see java.text.MessageFormat
    **/
    String elementNameFormat() default "{0}";

    /**
    * @return the name format for generated content interfaces (by default this is
    * the capitalized element name followed by the word {@code Content})
    * @see java.text.MessageFormat
    **/
    String contentNameFormat() default "{0}Content";
}
