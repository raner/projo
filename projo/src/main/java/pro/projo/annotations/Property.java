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
package pro.projo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Property @Property} annotation indicates that a {@code default} method
* of an interface should also be turned into a property. Normally, any {@code default}
* method would be inherited as is, but this annotation overrides this behavior.
* Notably, the existing implementation of the {@code default} method will be
* <i>overridden</i> with the standard behavior of retrieving a property value.
* Typically, the {@code default} method will contain some kind of dummy behavior so
* that sub-types do not need to implement the method (which is the main reason this
* annotation is needed).
*
* Methods with a {@link Property @Property} annotation should satisfy the following
* criteria:
* <ul>
*  <li> the method should be a {@code default} method </li>
*  <li> the method should have zero parameters </li>
*  <li> the existing implementation should be safe to override (i.e., it is a
*       "dummy" implementation that returns {@code null} or throws an exception)
*        </li>
* </ul>
*
* @since 1.4
*
* @author Mirko Raner
**/
@Target(METHOD)
@Retention(RUNTIME)
public @interface Property
{
    // No attributes
}
