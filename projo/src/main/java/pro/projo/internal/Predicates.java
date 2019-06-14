//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
import java.util.function.Predicate;

/**
* The {@link Predicates} class is a utility class that defines several commonly used predicates.
*
* @author Mirko Raner
**/
public interface Predicates
{
    static Predicate<Method> equals = method -> method.getName().equals("equals")
        && method.getParameterCount() == 1
        && method.getParameterTypes()[0] == Object.class
        && method.getReturnType() == boolean.class;
    static Predicate<Method> hashCode = method -> method.getName().equals("hashCode")
        && method.getParameterCount() == 0
        && int.class.equals(method.getReturnType());
    static Predicate<Method> toString = method -> method.getName().equals("toString")
        && method.getParameterCount() == 0
        && String.class.equals(method.getReturnType());
    static Predicate<Method> getter = method -> method.getParameterCount() == 0
        && !method.getDeclaringClass().equals(Object.class)
        && !hashCode.test(method)
        && !toString.test(method);
    static Predicate<Method> setter = method -> method.getParameterCount() == 1
        && !method.getDeclaringClass().equals(Object.class)
        && !equals.test(method);
}
