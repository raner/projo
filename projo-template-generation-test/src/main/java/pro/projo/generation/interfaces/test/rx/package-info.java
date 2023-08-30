//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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
@Options(skip=@Unmapped(includingPrimitives=true, includingArrays=true))
@Interface(generate="FlowSource", from=Publisher.class)
@Interface
(
    generate="Flow", from=Flowable.class, isStatic=FALSE, visibility=PUBLIC,
    map=
    {
        @Map(type=int.class, to="Natural"),
        @Map(type=long.class, to="Natural"),
        @Map(type=Predicate.class, to="java.util.function.Predicate"),
        @Map(type=Function.class, to="java.util.function.Function"),
        @Map(type=BiFunction.class, to="java.util.function.BiFunction"),
        @Map(type=Publisher.class, to="FlowSource")
    }
)
@Interface
(
    generate="Flows", from=Flowable.class, isStatic=TRUE, visibility=PUBLIC,
    map=
    {
        @Map(type=Flowable.class, to="Flow"),
        @Map(type=Iterable.class, to="java.lang.Iterable"),
        @Map(type=BiFunction.class, to="java.util.function.BiFunction")
    }
)
package pro.projo.generation.interfaces.test.rx;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Unmapped;
import static pro.projo.interfaces.annotation.Ternary.FALSE;
import static pro.projo.interfaces.annotation.Ternary.TRUE;
import static pro.projo.interfaces.annotation.Visibility.PUBLIC;

import org.reactivestreams.Publisher;
