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
* The {@link Ternary} enumeration implements three-valued logic (also known as
* tri-state or trits). Specifically, it is used for filtering binary attributes
* (such as whether a method is {@code static} or not) in a way that allows to
* express that the attribute is present ({@link #TRUE}), absent ({@link #FALSE}),
* or irrelevant for filtering ({@link #EITHER}).
*
* @author Mirko Raner
**/
public enum Ternary
{
    TRUE,
    FALSE,
    EITHER;
}
