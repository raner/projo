//                                                                          //
// Copyright 2018 Mirko Raner                                               //
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
@Interface(generate="Math", from=Math.class, modifiers={PUBLIC, STATIC})
@Interface(generate="ObjectFactory", from=ObjectFactory.class, modifiers={PUBLIC, STATIC})
@Interface(generate="Observable", from=Observable.class, modifiers={PUBLIC, FINAL})
@Interface(generate="Observables", from=Observable.class, modifiers={PUBLIC, STATIC}, map=@Map(type=Observable.class, to="Observable"))
@Interface(generate="NewType", from=Type.class)
package pro.projo.generation.interfaces.test;

import java.util.concurrent.Callable;
import io.reactivex.Observable;
import pro.projo.generation.interfaces.test.classes.ObjectFactory;
import pro.projo.generation.interfaces.test.classes.Type;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
* This {@code package-info} class contains the annotations that are tested by the
* {@link InterfaceTest} class.
*
* @author Mirko Raner
**/