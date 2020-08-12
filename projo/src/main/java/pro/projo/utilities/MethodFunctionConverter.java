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
package pro.projo.utilities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;
import pro.projo.internal.Default;

/**
* The {@link MethodFunctionConverter} provides utilities for converting between the realm
* of {@link Method}s and the realm of {@link Function}s.
*
* @author Mirko Raner
**/
public class MethodFunctionConverter implements TryCatchUtilities
{
    /**
    * Converts a {@link Function} to a {@link Method}.
    *
    * @param type the type that declares the method, represented as a {@link Class}
    * @param method the method, represented as a {@link Function}
    * @param <_ObjectType_> the object type
    * @param <_ReturnValue_> the methods's return type
    * @return the corresponding {@link Method}
    **/
    public <_ObjectType_, _ReturnValue_> Method convert(Class<_ObjectType_> type, Function<_ObjectType_, _ReturnValue_> method)
    {
        Class<?>[] interfaces = {type};
        Mutable<Method> methodHolder = new Mutable<>();
        InvocationHandler handler = (Object proxy, Method invokedMethod, Object[] arguments) ->
        {
            methodHolder.set(invokedMethod);
            return Default.VALUES.get(invokedMethod.getReturnType());
        };
        @SuppressWarnings("unchecked")
        _ObjectType_ instance = (_ObjectType_)Proxy.newProxyInstance(type.getClassLoader(), interfaces, handler);
        method.apply(instance);
        return methodHolder.get();
    }

    /**
    * Converts a {@link Function} to a {@link Method}.
    * @param method the method, represented as a {@link Function}
    * @param <_ObjectType_> the object type
    * @param <_ReturnValue_> the methods's return type
    * @return the corresponding {@link Method}
    **/
    public <_ObjectType_, _ReturnValue_> Function<_ObjectType_, _ReturnValue_> convert(Method method)
    {
        return object ->
        {
            method.setAccessible(true);
            @SuppressWarnings("unchecked")
            _ReturnValue_ result = tryCatch(() -> (_ReturnValue_)method.invoke(object))
                .rethrow(IllegalAccessException.class, InvocationTargetException.class).as(RuntimeException.class)
                .andReturn();
            return result;
        };
    }
}
