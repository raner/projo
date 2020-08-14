//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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
@Interface(generate="GroupedObservable", from=GroupedObservable.class, modifiers={PUBLIC})
@Interface(generate="NewType", from=Type.class)
@Interface(generate="NewThing", from=Thing.class)
@Interface(generate="EvenNewerThing", from=NewerThing.class)
@Interface(generate="Mapper", from=Converter.class)
@Interface(generate="Watchable", from=Watchable.class)
@Interface(generate="Walker", from=Runner.class)
@Interface(generate="Nested", from=Nested.class)
@Interface(generate="UseEnum", from=UseEnum.class)
@Interface(generate="FromOtherPackage", from=UseGeneratedClassFromOtherPackage.class, map=@Map(type=Other.class, to="pro.projo.generation.interfaces.test.additional.Other"))
@Interface(generate="ExtendedMap", from=Extends.class)
@Interface(generate="ShadowedTypeVariable", from=ShadowedTypeVariable.class)
@Interface(generate="NotAShadowedTypeVariable", from=NotAShadowedTypeVariable.class)
@Interface(generate="NotAShadowedTypeVariableStatic", from=NotAShadowedTypeVariableEither.class, modifiers={PUBLIC, STATIC})
@Interface(generate="Runnable", from=Runnable.class, options=@Options(fileExtension=".kava"))
@Interface(generate="Integer", from=Number.class, modifiers={PUBLIC})
@Interface(generate="Comparable", from=Comparable.class, map=@Map(type=int.class, to="Integer"))
@Interface(generate="Function", from=Function.class)
@Enum(generate="Enumeration", from=Enumeration.class)
@Enum(generate="BackpressureStrategy", from=BackpressureStrategy.class)
@Enum(generate="RoundingMode", from=RoundingMode.class, options=@Options(fileExtension=".kava"))
package pro.projo.generation.interfaces.test;

import java.math.RoundingMode;
import java.util.concurrent.Callable;
import java.util.function.Function;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import pro.projo.generation.interfaces.test.classes.Converter;
import pro.projo.generation.interfaces.test.classes.Enumeration;
import pro.projo.generation.interfaces.test.classes.Extends;
import pro.projo.generation.interfaces.test.classes.Nested;
import pro.projo.generation.interfaces.test.classes.NewerThing;
import pro.projo.generation.interfaces.test.classes.NotAShadowedTypeVariable;
import pro.projo.generation.interfaces.test.classes.NotAShadowedTypeVariableEither;
import pro.projo.generation.interfaces.test.classes.ObjectFactory;
import pro.projo.generation.interfaces.test.classes.Runner;
import pro.projo.generation.interfaces.test.classes.ShadowedTypeVariable;
import pro.projo.generation.interfaces.test.classes.Thing;
import pro.projo.generation.interfaces.test.classes.Type;
import pro.projo.generation.interfaces.test.classes.UseEnum;
import pro.projo.generation.interfaces.test.classes.UseGeneratedClassFromOtherPackage;
import pro.projo.generation.interfaces.test.classes.Watchable;
import pro.projo.generation.interfaces.test.classes.additional.Other;
import pro.projo.interfaces.annotation.Enum;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

/**
* This {@code package-info} class contains the annotations that are tested by the
* {@link InterfaceTest} class.
*
* @author Mirko Raner
**/
