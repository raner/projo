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
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import pro.projo.interfaces.annotation.utilities.DefaultAttributeNameConverter;
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
    * Returns the base interface to be used for non-empty, non-text elements.
    * This must be an interface with three type parameters: the first parameter for the parent content
    * element type, the second for the element's own content input type, and the third for the
    * element's content output type. For unrestricted content, the content input type and content
    * output type will typically be the same, and the code generator will assign the same type for
    * both type variables. However, for example for sequences of mandatory content objects, the
    * code generator will create a type hierarchy of a base content type, and several individual
    * types for the members of the first, second, third, etc. element of the sequence. The base
    * interface will be parameterized with the sequences's first element type and the content input
    * type, and the base content type as the output type.
    *
    * @return the default base interface to use for model elements ({@link Object} indicates that no
    * specific base interface will be extended)
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

    /**
    * @return the {@link AttributeNameConverter} to be used for converting XML/SGML attribute
    * names to Java method names
    **/
    Class<? extends AttributeNameConverter> attributeNameConverter() default DefaultAttributeNameConverter.class;
}
