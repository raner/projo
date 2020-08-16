//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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

/**
* The {@link Unmapped} option is used for indicating whether the code generation
* should skip methods that are using unmapped types.
* The main variations are:
* <ul>
*  <li> {@code @Unmapped} - skip methods that use unmapped types, unless the
*       unmapped types are primitives</li>
*  <li> {@code @Unmapped(includingPrimitives=true)} - skip methods that use
*       unmapped types, including those that use unmapped primitives
*  <li> {@code @Unmapped(false)} - do not skip any methods, regardless of whether
*       they use unmapped types</li>
* </ul>
* While, technically, it is possible to specify
* {@code @Unmapped(value=false, includingPrimitives=true)} this combination does
* not make sense and is treated identical to {@code @Unmapped(false)}.
*
* @author Mirko Raner
**/
public @interface Unmapped
{
    /**
    * Indicates whether to skip methods using unmapped types.
    *
    * @return {@code true} if methods using unmapped types should be skipped,
    * {@code false} otherwise
    **/
    boolean value() default true;

    /**
    * Indicates whether to also skip methods that use unmapped primitives.
    *
    * @return {@code true} if methods using unmapped primitives should be skipped,
    * {@code false} otherwise
    **/
    boolean includingPrimitives() default false;
}
