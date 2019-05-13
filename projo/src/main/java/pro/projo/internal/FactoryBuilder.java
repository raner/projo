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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.Builder;
import pro.projo.Factory;
import pro.projo.Projo;
import pro.projo.Projo.Intermediate;
import pro.projo.utilities.MethodFunctionConverter;
import pro.projo.utilities.TryCatchUtilities;
import static java.util.Collections.unmodifiableMap;

/**
* The {@link FactoryBuilder} is a {@link Builder} implementation that leverages the
* existing {@link pro.projo.Factory Factory} and {@link Prototype} mechanism for constructing
* Projo objects.
* It is not an efficient implementation, but it provides pragmatic reuse of code for
* both the proxy-based as well as the runtime-code-generation-based implementations of
* Projo. More efficient specific implementations may be added at a later point.
*
* @author Mirko Raner
**/
public class FactoryBuilder<_Artifact_> implements Builder<_Artifact_>, TryCatchUtilities
{
    private final Class<_Artifact_> type;
    private final Map<Method, Object> attributes;

    public FactoryBuilder(Class<_Artifact_> type)
    {
        this.type = type;
        attributes = unmodifiableMap(new HashMap<>());
    }

    private FactoryBuilder(Class<_Artifact_> type, Map<Method, ?> attributes)
    {
        this.type = type;
        this.attributes = unmodifiableMap(attributes);
    }

    @Override
    public <_Property_> Builder<_Artifact_> with(Function<_Artifact_, _Property_> property, _Property_ value)
    {
        Map<Method, Object> newProperties = new HashMap<>();
        newProperties.putAll(attributes);
        MethodFunctionConverter converter = new MethodFunctionConverter();
        newProperties.put(converter.convert(type, property), value);
        return new FactoryBuilder<>(type, newProperties);
    }

    @Override
    public _Artifact_ build()
    {
        Intermediate<_Artifact_> intermediate = Projo.creates(type);
        Function<_Artifact_, ?>[] getters = Projo.getGetterFunctions(type);
        MethodFunctionConverter converter = new MethodFunctionConverter();
        Method[] getterMethods = Stream.of(getters).map((Function<_Artifact_, ?> method) -> converter.convert(type, method)).toArray(Method[]::new);
        Method with = intermediate.methods()[getterMethods.length-1];
        Object[] argument = new Object[with.getParameterCount()];
        for (int getter = 0; getter < argument.length; getter++)
        {
            Method getterMethod = getterMethods[getter];
            argument[getter] = attributes.getOrDefault(getterMethod, Default.VALUES.get(getterMethod.getReturnType()));
        }
        Object[] getterFunctions = getters;
        Factory factory = tryCatch(() -> (Factory)with.invoke(intermediate, getterFunctions))
            .rethrow(IllegalAccessException.class, InvocationTargetException.class).as(RuntimeException.class)
            .andReturn();
        Predicate<Method> createMethod = method -> "create".equals(method.getName());
        Predicate<Method> parameterCount = method -> method.getParameterCount() == argument.length;
        Method create = Stream.of(factory.getClass().getMethods()).filter(createMethod.and(parameterCount)).findFirst().get();
        create.setAccessible(true);
        @SuppressWarnings("unchecked")
        _Artifact_ result = tryCatch(() -> (_Artifact_)create.invoke(factory, argument))
            .rethrow(IllegalAccessException.class, InvocationTargetException.class).as(RuntimeException.class)
            .andReturn();
        return result;
    }
}
