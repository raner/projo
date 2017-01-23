//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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
package pro.projo.internal;

import java.lang.reflect.Method;
import java.util.function.BiPredicate;

/**
* The {@link Predicates} class is a utility class that defines several commonly used predicates.
*
* @author Mirko Raner
**/
public interface Predicates
{
    static BiPredicate<Method, Object[]> equals = (method, arguments) -> method.getName().equals("equals")
        && method.getParameterCount() == 1
        && method.getParameterTypes()[0] == Object.class
        && method.getReturnType() == boolean.class;
    static BiPredicate<Method, Object[]> hashCode = (method, arguments) -> method.getName().equals("hashCode")
        && method.getParameterCount() == 0;
    static BiPredicate<Method, Object[]> getter = (method, arguments) -> (arguments == null || arguments.length == 0)
        && !hashCode.test(method, arguments);
    static BiPredicate<Method, Object[]> setter = (method, arguments) -> (arguments != null && arguments.length == 1)
        && !equals.test(method, arguments);
}
