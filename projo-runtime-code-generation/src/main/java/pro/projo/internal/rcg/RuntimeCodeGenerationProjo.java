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
package pro.projo.internal.rcg;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import pro.projo.Projo;
import pro.projo.internal.Default;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.PropertyMatcher;

/**
* {@link RuntimeCodeGenerationProjo} is a Projo implementation based on runtime code generation.
*
* The {@link RuntimeCodeGenerationProjo} has a {@link #precedence() precedence} of 0.
*
* @author Mirko Raner
**/
public class RuntimeCodeGenerationProjo extends Projo
{
    private RuntimeCodeGenerationHandler<?> handler = new RuntimeCodeGenerationHandler<>();

    @Override
    protected int precedence()
    {
        return 0;
    }

    @Override
    public <_Artifact_> ProjoHandler<_Artifact_> getHandler(Class<_Artifact_> type)
    {
        @SuppressWarnings("unchecked")
        RuntimeCodeGenerationHandler<_Artifact_> projoHandler = (RuntimeCodeGenerationHandler<_Artifact_>)handler;
        return projoHandler;
    }

    @Override
    public <_Artifact_> ProjoHandler<_Artifact_>.ProjoInitializer initializer(Class<_Artifact_> type)
    {
        @SuppressWarnings("unchecked")
        RuntimeCodeGenerationHandler<_Artifact_> projoHandler = (RuntimeCodeGenerationHandler<_Artifact_>)handler;
        return projoHandler.new ProjoInitializer()
        {
            @Override
            @SafeVarargs
            public final ProjoHandler<_Artifact_>.ProjoInitializer.ProjoMembers members(Function<_Artifact_, ?>... getters)
            {
                return new ProjoMembers()
                {
                    @Override
                    public _Artifact_ returnInstance()
                    {
                        try
                        {
                            return projoHandler.getImplementationOf(type).newInstance();
                        }
                        catch (InstantiationException exception)
                        {
                            throw new RuntimeException(exception.getMessage(), exception);
                        }
                        catch (IllegalAccessException illegalAccess)
                        {
                            throw new IllegalAccessError(illegalAccess.getMessage());
                        }
                    }

                    @Override
                    public _Artifact_ with(Object... values)
                    {
                        if (values.length == getters.length)
                        {
                            _Artifact_ instance = returnInstance();
                            Class<?> implementationClass = instance.getClass();
                            String[] fieldNames = getFieldNames(type, getters);
                            for (int index = 0; index < values.length; index++)
                            {
                                try
                                {
                                    Field field = implementationClass.getDeclaredField(fieldNames[index]);
                                    Object value = values[index] != null? values[index]:Default.VALUES.get(field.getType());
                                    field.setAccessible(true);
                                    field.set(instance, value);
                                }
                                catch (NoSuchFieldException noSuchField)
                                {
                                    throw new NoSuchFieldError(noSuchField.getMessage());
                                }
                                catch (IllegalAccessException illegalAccess)
                                {
                                    throw new IllegalAccessError(illegalAccess.getMessage());
                                }
                            }
                            return instance;
                        }
                        throw new RuntimeException("Mismatch: " + getters.length + " getters, " + values.length + " values");
                    }
                };
            }
        };
    }

    public <_Artifact_> String[] getFieldNames(Class<_Artifact_> type, Function<_Artifact_, ?>[] getters)
    {
        List<String> fieldNames = new ArrayList<>();
        PropertyMatcher matcher = new PropertyMatcher();
        InvocationHandler invocationHandler = (Object proxy, Method method, Object[] args) ->
        {
            fieldNames.add(matcher.propertyName(method.getName()));
            return Default.VALUES.get(method.getReturnType());
        };
        Class<?>[] interfaces = {type};
        ClassLoader classLoader = type.getClassLoader();
        @SuppressWarnings("unchecked")
        _Artifact_ instance = (_Artifact_)Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        Stream.of(getters).forEach(getter -> getter.apply(instance));
        return fieldNames.toArray(new String[] {});
    }
}
