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
package pro.projo.interfaces.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Attribute} annotation defines a custom type for a {@link Dtd}
* element attribute. An {@link Attribute} annotation acts globally, e.g.
* {@code @Attribute(name="class", type=Class.class)} will assign the type
* {@code Class} to <i>every</i> attribute called {@code class}, regardless
* of which DTD element it belongs to.
*
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
public @interface Attribute
{
    /**
    * @return the attribute name
    **/
    String name();

    /**
    * @return the attribute type
    **/
    Class<?> type();

    /**
    * @return additional type arguments (if any)
    **/
    String[] typeArguments() default {};
}
