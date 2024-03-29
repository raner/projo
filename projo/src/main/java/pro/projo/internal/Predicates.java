//                                                                          //
// Copyright 2019 - 2022 Mirko Raner                                        //
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
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.annotations.Cached;
import pro.projo.annotations.Delegate;
import pro.projo.annotations.Expects;
import pro.projo.annotations.Overrides;
import pro.projo.annotations.Property;
import pro.projo.annotations.Returns;

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
        && (!method.isDefault() || method.getAnnotation(Property.class) != null)
        && !hashCode.test(method)
        && !toString.test(method)
        && method.getAnnotation(Delegate.class) == null;
    static Predicate<Method> setter = method -> method.getParameterCount() == 1
        && !method.getDeclaringClass().equals(Object.class)
        && !method.isDefault()
        && !equals.test(method);
    static Predicate<Method> cached = method -> method.isDefault()
        && method.getAnnotation(Cached.class) != null;
    static Predicate<Method> overrides = method -> method.isDefault()
        && method.getAnnotation(Overrides.class) != null;
    static Predicate<Method> returns = method -> method.isDefault()
        && method.getAnnotation(Returns.class) != null;
    static Predicate<Method> expects = method -> method.isDefault()
        && Stream.of(method.getParameters()).map(it -> it.getAnnotation(Expects.class)).anyMatch(Objects::nonNull);
    static Predicate<Method> getDelegate = method -> method.getName().equals("getDelegate")
        && method.getParameterCount() == 0
        && Object.class.equals(method.getReturnType());
    static Predicate<Method> getState = method -> method.getName().equals("getState")
        && method.getParameterCount() == 0
        && Map.class.equals(method.getReturnType());
    static Predicate<Method> declaredAttribute = method -> (method.getModifiers() & Modifier.ABSTRACT) != 0
        && method.getParameterCount() == 0;
}
