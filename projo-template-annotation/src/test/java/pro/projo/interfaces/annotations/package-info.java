//                                                                          //
// Copyright 2018 - 2021 Mirko Raner                                        //
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
@Interface(generate="Callable", from=Callable.class)
@Interface(generate="Math", from=Math.class, isStatic=TRUE, visibility=PUBLIC)
package pro.projo.interfaces.annotations;

import java.util.concurrent.Callable;
import pro.projo.interfaces.annotation.Interface;
import static pro.projo.interfaces.annotation.Ternary.TRUE;
import static pro.projo.interfaces.annotation.Visibility.PUBLIC;

/**
* This {@code package-info} class contains the annotations that are tested by the
* {@link InterfaceTest} class.
*
* @author Mirko Raner
**/
