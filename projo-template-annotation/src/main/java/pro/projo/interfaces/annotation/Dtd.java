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
    * @return the default base interface to use for model elements (must be an interface;
    * {@link Object} indicates that no specific base interface will be extended)
    **/
    Class<?> baseInterface() default Object.class;

    /**
     * @return the default base interface to use for empty (or "void") model elements
     * (must be an interface; {@link Object} indicates that no specific base interface will
     * be extended)
     **/
    Class<?> baseInterfaceEmpty() default Object.class;
}
