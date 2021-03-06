//                                                                          //
// Copyright 2018 - 2021 Mirko Raner                                        //
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
package pro.projo.interfaces.annotations;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.Test;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Interfaces;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pro.projo.interfaces.annotation.Ternary.TRUE;
import static pro.projo.interfaces.annotation.Visibility.PUBLIC;

public class InterfaceTest
{
    @Test
    public void testInterfaces()
    {
        Package testPackage = getClass().getPackage();
        assertNotNull(testPackage.getAnnotation(Interfaces.class));
    }

    @Test
    public void testInterfaceAnnotationForCallable()
    {
        Package testPackage = getClass().getPackage();
        Interfaces interfaces = testPackage.getAnnotation(Interfaces.class);
        Interface callable = Stream.of(interfaces.value()).filter(equals(Interface::generate, "Callable")).findFirst().get();
        assertEquals(Callable.class, callable.from());
    }

    @Test
    public void testInterfaceAnnotationForMath()
    {
        Package testPackage = getClass().getPackage();
        Interfaces interfaces = testPackage.getAnnotation(Interfaces.class);
        Interface math = Stream.of(interfaces.value()).filter(equals(Interface::generate, "Math")).findFirst().get();
        Object[][] expected = {{Math.class}, {TRUE}, {PUBLIC}};
        Object[][] actual = {{math.from()}, {math.isStatic()}, math.visibility()};
        assertArrayEquals(expected, actual);
    }

    private <I, T> Predicate<I> equals(Function<I, T> method, T value)
    {
        return object -> method.apply(object).equals(value);
    }
}
