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
package pro.projo.internal.proxy;

import pro.projo.Projo;
import static java.lang.reflect.Proxy.newProxyInstance;

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
    public <_Artifact_> ProxyProjoInvocationHandler<_Artifact_>.Initializer initializer(Class<_Artifact_> type)
    {
        Class<?>[] interfaces = {type};
        ClassLoader loader = Projo.class.getClassLoader();
        ProxyProjoInvocationHandler<_Artifact_> handler = new ProxyProjoInvocationHandler<>(type);
        @SuppressWarnings("unchecked")
        _Artifact_ instance = (_Artifact_)newProxyInstance(loader, interfaces, handler);
        return handler.initialize(instance);
    }
}
