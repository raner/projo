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
import java.util.List;
import org.junit.Test;
import pro.projo.test.interfaces.Real;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.junit.Assert.assertEquals;
import static pro.projo.test.implementations.Real.factory;

/**
* {@link ProjoInheritsTest} verifies Projo's {@link pro.projo.annotations.Inherits @Inherits}
* annotation in a real-world example.
* {@link pro.projo.test.implementations.Real} implements {@link pro.projo.test.interfaces.Real}
* without making any direct reference to it.
* This test verifies the following scenario:
* <ul>
*  <li>{@link pro.projo.test.interfaces.Real} has abstract methods {@code plus}, {@code plusMinus},
*      {@code negate} and {@code equals}; it has a non-abstract {@code minus} method that is
*      implemented based on {@code plus} and {@code negate}</li>
*  <li>{@link pro.projo.test.implementations.Real} implements all abstract methods; notably,
*      {@code plusMinus} is implemented by calling both the implementation of {@code plus} as
*      well as the inherited implementation of {@code minus}</li>
*  <li>as there is no "real" inheritance, the {@code minus} method must be replaced by a stand-in
*      delegate, which should redirect to the original {@code minus} method in
*      {@link pro.projo.test.interfaces.Real}</li>
*  <li>the stand-in method (necessarily) uses {@link pro.projo.test.implementations.Real} as the parameter
*      and return type, and the {@code plusMinus} implementation invokes it with a signature of
*      {@code pro/projo/test/implementations/Real.minus:(Lpro/projo/test/implementations/Real;)Lpro/projo/test/implementations/Real;}</li>
*  <li>the generated implementation class, {@code Real$Projo}, must implement the {@code minus} method
*      in such a way that it calls the original {@link minus} implementation from the interface,
*      which {@link Real$Projo} properly inherits</li>
* </ul>
*
* @author Mirko Raner
**/
public class ProjoInheritsTest
{
    @Test
    public void testPlusMinus()
    {
        Real<?> one = (Real<?>)factory.create(ONE);
        Real<?> ten = (Real<?>)factory.create(TEN);
        List<Real<?>> result = ten.plusMinus(one);
        Real<?> nine = (Real<?>)factory.create(TEN.subtract(ONE));
        Real<?> eleven = (Real<?>)factory.create(TEN.add(ONE));
        assertEquals(Arrays.asList(eleven, nine), result);
    }
}
