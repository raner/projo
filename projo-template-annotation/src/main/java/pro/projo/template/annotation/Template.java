//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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
package pro.projo.template.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Template} annotations marks template classes that are used for Projo's internal code generation.
*
* @author Mirko Raner
**/
@Target(TYPE)
@Retention(RUNTIME) 
public @interface Template
{
    /**
    * @return the {@link Configuration}s for the {@link Template}
    **/
    Class<? extends Collection<? extends Configuration>> input();
}
