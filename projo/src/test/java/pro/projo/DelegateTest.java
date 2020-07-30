//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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

import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DelegateTest implements AbstractTypeMappingTest
{
    @Test
    public void testIntegerAdd()
    {
        Mapping mapping = Projo.mapping().map(IntegerImpl.class).to(BigInteger.class);
        IntegerImpl one = Projo.delegate(IntegerImpl.class, BigInteger.ONE, mapping);
        IntegerImpl ten = Projo.delegate(IntegerImpl.class, BigInteger.TEN, mapping);
        IntegerImpl eleven = ten.add(one);
        BigInteger result = Projo.unwrap(eleven);
        assertEquals(new BigInteger("11"), result);
    }

    @Test
    public void testIntegerSubtract()
    {
        Mapping mapping = Projo.mapping().map(Integer.class).to(BigInteger.class);
        Integer<?> one = Projo.delegate(Integer.class, BigInteger.ONE, mapping);
        Integer<?> ten = Projo.delegate(Integer.class, BigInteger.TEN, mapping);
        Integer<?> nine = ten.subtract(one);
        BigInteger result = Projo.unwrap(nine);
        assertEquals(new BigInteger("9"), result);
    }
}
