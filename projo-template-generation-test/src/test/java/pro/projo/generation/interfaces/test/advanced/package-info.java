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
@Interface(generate="ObservableSource", from=ObservableSource.class, isStatic=FALSE, visibility=PUBLIC)
@Interface(generate="Observable", from=Observable.class, isStatic=FALSE, visibility=PUBLIC)
@Interface(generate="Observables", from=Observable.class, isStatic=TRUE, visibility=PUBLIC,
    map=@Map(type=Observable.class, to="Observable"))
package pro.projo.generation.interfaces.test.advanced;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Unmapped;
import static pro.projo.interfaces.annotation.Ternary.FALSE;
import static pro.projo.interfaces.annotation.Ternary.TRUE;
import static pro.projo.interfaces.annotation.Visibility.PUBLIC;
