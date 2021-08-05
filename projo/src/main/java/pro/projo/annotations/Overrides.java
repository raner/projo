//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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
package pro.projo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Overrides @Overrides} annotation is used for marking methods of a proxy
* interface that cannot be directly overridden because they are declared by {@link java.lang.Object}.
* The overriding method cannot have the original name, so it must be declared with a slightly
* different name, but the annotation specifies the name of the real method that is intended to be
* overridden.
*
* @author Mirko Raner
**/
@Target(METHOD)
@Retention(RUNTIME)
public @interface Overrides
{
    static String equals = "equals";

    static String hashCode = "hashCode";

    static String toString = "toString";

    String value();
}
