//                                                                          //
// Copyright 2022 - 2023 Mirko Raner                                        //
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

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import pro.projo.Projo;
import pro.projo.annotations.Expects;
import pro.projo.annotations.Returns;

/**
* {@link MethodInfo} is a quick-and-dirty representation of simplified method signatures
* that supports {@link #equals(Object)} and {@link #hashCode()}. It is used for method
* comparison and disambiguation.
*
* @author Mirko Raner
**/
public class MethodInfo extends ArrayList<Object>
{
    private final static long serialVersionUID = 740034975850247166L;

    /**
    * Creates a {@link MethodInfo} from a {@link Method} handle, applying possible
    * {@link Expects @Expects} and {@link Returns @Returns} annotations.
    *
    * @param method the {@link Method} handle
    **/
    public MethodInfo(Method method)
    {
        this(returns(method, null), method.getName(), expects(method, null));
    }

    /**
    * Creates a {@link MethodInfo} from a {@link Method} handle, applying possible
    * {@link Expects @Expects} and {@link Returns @Returns} annotations.
    *
    * @param method the {@link Method} handle
    * @param classLoader the {@link ClassLoader} to be used for loading additional types
    * specified in {@link Expects @Expects} and {@link Returns @Returns} annotations;
    * {@code null} indicates using the default class loader used by {@link Class#forName(String)}
    **/
    public MethodInfo(Method method, ClassLoader classLoader)
    {
        this(returns(method, classLoader), method.getName(), expects(method, classLoader));
    }

    public MethodInfo(Class<?> returnType, String methodName, Class<?>[] parameterTypes)
    {
        super(Arrays.asList(returnType, methodName, Arrays.asList(parameterTypes)));
    }

    public String methodName()
    {
        return (String)get(1);
    }

    public Class<?> returnType()
    {
        return (Class<?>)get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Class<?>> parameterTypes()
    {
        return (List<Class<?>>)get(2);
    }

    public static Class<?>[] expects(Method method, ClassLoader classLoader)
    {
        Stream<Parameter> parameters = Stream.of(method.getParameters());
        Function<Parameter, Class<?>> parameterType = parameter ->
        {
            Expects expects = parameter.getAnnotation(Expects.class);
            return expects == null? parameter.getType():Projo.forName(expects.value(), classLoader);
        };
        return parameters.map(parameterType).toArray(Class[]::new);
    }

    public static Class<?> returns(Method method, ClassLoader classLoader)
    {
        Returns returns = method.getAnnotation(Returns.class);
        return returns == null? method.getReturnType():Projo.forName(returns.value(), classLoader);
    }
}
