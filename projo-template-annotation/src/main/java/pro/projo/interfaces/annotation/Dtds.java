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

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Dtds} annotation is the container annotation for
* {@link java.lang.annotation.Repeatable @Repeatable} {@link Dtd @Dtd} annotations.
*
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
public @interface Dtds
{
    /**
    * @return the {@link Dtd @Dtd} annotations contained within this annotation
    **/
    Dtd[] value();
}
