//                                                                          //
// Copyright 2022 Mirko Raner                                               //
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
package pro.projo;

import java.util.Arrays;
import org.junit.Test;
import pro.projo.annotations.Implements;
import pro.projo.internal.proxy.ProxyProjo;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;

public class ImplementsTest
{
    // Utility interfaces:

    public static interface Parameterized<PARAMETER1, PARAMETER2> {}

    // Test interfaces:

    @Implements("java.util.RandomAccess")
    public static interface Serializable {}

    @Implements({"java.util.RandomAccess", "java.util.EventListener"})
    public static interface RandomAccessEventListener {}

    @Implements("pro.projo.ImplementsTest$Parameterized<java.lang.Runnable, java.lang.String>")
    public static interface ImplementingParameterizedTypes {}

    // Tests:

    @Test
    public void testImplementsSimpleInterface()
    {
        Serializable serializable = Projo.create(Serializable.class);
        assertTrue(serializable instanceof java.util.RandomAccess);
    }

    @Test
    public void testImplementsMultipleInterfaces()
    {
        RandomAccessEventListener object = Projo.create(RandomAccessEventListener.class);
        assertTrue(object instanceof java.util.RandomAccess && object instanceof java.util.EventListener);
    }

    @Test
    public void testImplementsParameterizedInterface()
    {
        // Due to type erasure, the proxy-based implementation cannot handle parameterized types:
        //
        assumeFalse(Projo.getImplementation() instanceof ProxyProjo);

        ImplementingParameterizedTypes object = Projo.create(ImplementingParameterizedTypes.class);
        String types = Arrays.asList(object.getClass().getGenericInterfaces()).toString();
        assertTrue(types.contains("pro.projo.ImplementsTest$Parameterized<java.lang.Runnable, java.lang.String>"));
    }
}
