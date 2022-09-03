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

import org.junit.Test;
import pro.projo.test.interfaces.Natural;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static org.junit.Assert.assertEquals;
import static pro.projo.test.implementations.Natural.factory;

/**
* {@link ProjoNaturalTest} verifies Projo's {@link pro.projo.annotations.Implements @Implements},
* {@link pro.projo.annotations.Returns @Returns} and {@link pro.projo.annotations.Expects @Expects}
* annotations in a more real-world example.
* {@link pro.projo.test.implementations.Natural} implements {@link pro.projo.test.interfaces.Natural}
* without making any direct reference to it. Return types and parameter types are adjusted based
* on their annotations.
*
* @author Mirko Raner
**/
public class ProjoNaturalTest
{
    @Test
    public void testSuccessor()
    {
        Natural<?> one = (Natural<?>)factory.create(ONE);
        assertEquals("2", one.successor().toString());
    }

    @Test
    public void testPlus() throws Exception
    {
        Natural<?> one = (Natural<?>)factory.create(ONE);
        Natural<?> ten = (Natural<?>)factory.create(TEN);
        assertEquals("11", one.plus(ten).toString());
    }

    @Test
    public void testTimes() throws Exception
    {
        Natural<?> two = (Natural<?>)factory.create(ONE).successor();
        Natural<?> ten = (Natural<?>)factory.create(TEN);
        assertEquals("20", two.times(ten).toString());
    }
}
