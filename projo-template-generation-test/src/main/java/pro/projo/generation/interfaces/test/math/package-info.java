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
@Options(skip=@Unmapped(includingPrimitives=true))
@Interface(generate="Random", from=Random.class)
@Interface(generate="Integer", from=BigInteger.class, modifiers= {PUBLIC, STATIC},
    map={@Map(type=int.class, to="Integer"), @Map(type=Random.class, to="Random")},
    options=@Options(skip=@Unmapped(includingPrimitives=false)))
package pro.projo.generation.interfaces.test.math;

import java.math.BigInteger;
import java.util.Random;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Unmapped;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
* The {@code pro.projo.generation.interfaces.test.math} contains test cases
* for package-wide handling of {@link Unmapped} types.
* Specifically:
* <ul>
*  <li> the {@code Random} class inherits the package-level options
*       ({@code @Options(skip=@Unmapped(includingPrimitives=true))}); the original class
*       only uses unmapped types and primitives, therefore the expected generated class
*       should have no methods at all</li>
*  <li> the {@code Integer} class overrides the package level options with
*       {@code @Options(skip=@Unmapped(includingPrimitives=false))}; {@code int} and
*       {@code Random} are properly mapped, but {@code long} isn't; however, since
*       {@code includingPrimitives=false} was specified, the
*       {@code Integer valueOf(long val)} method is still included in the generated
*       interface</li>
* </ul>
*
* @author Mirko Raner
**/
