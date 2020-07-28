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
package pro.projo.internal.proxy;

import java.util.ArrayList;
import java.util.List;
import pro.projo.Projo;
import pro.projo.internal.ProjoObject;
import static java.lang.reflect.Proxy.newProxyInstance;
import static java.util.Arrays.asList;

/**
* {@link ProxyProjo} is Projo's rather inefficient default implementation based on Java proxies.
* It is highly recommended to use {@code RuntimeCodeGenerationProjo} instead.
*
* {@link ProxyProjo} has a {@link #precedence() precedence} of -2147483647 ({@link Integer#MIN_VALUE}{@code +1}).
*
* @author Mirko Raner
**/
public class ProxyProjo extends Projo
{
    @Override
    protected int precedence()
    {
        return Integer.MIN_VALUE+1;
    }

    @Override
    public <_Artifact_> ProxyProjoInvocationHandler<_Artifact_> getHandler(Class<_Artifact_> type)
    {
        return new ProxyProjoInvocationHandler<>(type);
    }

    @Override
    public <_Artifact_> ProxyProjoInvocationHandler<_Artifact_>.Initializer initializer(Class<_Artifact_> type, Class<?>... additionalInterfaces)
    {
        List<Class<?>> interfaces = new ArrayList<Class<?>>(asList(type, ProjoObject.class));
        interfaces.addAll(asList(additionalInterfaces));
        ProxyProjoInvocationHandler<_Artifact_> handler = getHandler(type);
        @SuppressWarnings("unchecked")
        _Artifact_ instance = (_Artifact_)newProxyInstance(getClassLoader(), interfaces.toArray(Class<?>[]::new), handler);
        return handler.initialize(instance);
    }
}
