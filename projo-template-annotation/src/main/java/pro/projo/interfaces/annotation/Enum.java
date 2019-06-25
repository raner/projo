//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
* The {@link Enum} annotation captures the necessary information for generating an {@code enum}
* from an existing an existing one.
*
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
@Repeatable(Enums.class)
public @interface Enum
{
    /**
    * @return the simple name of the enum to be generated
    **/
    String generate();

    /**
    * @return the original {@code enum} from which the new {@code enum} will be generated
    **/
    Class<? extends java.lang.Enum<?>> from();
}
