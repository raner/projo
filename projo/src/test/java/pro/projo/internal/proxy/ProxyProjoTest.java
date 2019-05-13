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
package pro.projo.internal.proxy;

import java.lang.reflect.Proxy;
import org.junit.Test;
import pro.projo.Projo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProxyProjoTest
{
    static interface Interface
    {
        int value();
    }

    @Test
    public void testProxyProjoImplementation()
    {
        assertEquals(ProxyProjo.class, Projo.getImplementation().getClass());
    }

    @Test
    public void testProxyProjoImplementationClass()
    {
        Class<Interface> type = Interface.class;
        Class<? extends Interface> implementation = Projo.getImplementation().getHandler(type).getImplementationOf(type);
        assertTrue(Proxy.isProxyClass(implementation));
    }
}
