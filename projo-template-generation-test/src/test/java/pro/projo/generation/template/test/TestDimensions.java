//                                                                          //
// Copyright 2024 Mirko Raner                                               //
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
package pro.projo.generation.template.test;

import pro.projo.generation.template.test.dimensions.Dimension;
import pro.projo.generation.template.exponents.Minus4;
import pro.projo.generation.template.exponents.Minus3;
import pro.projo.generation.template.exponents.Minus2;
import pro.projo.generation.template.exponents.Minus1;
import pro.projo.generation.template.exponents.Zero;
import pro.projo.generation.template.exponents.Plus1;
import pro.projo.generation.template.exponents.Plus2;
import pro.projo.generation.template.exponents.Plus3;
import pro.projo.generation.template.exponents.Plus4;

/**
* {@link TestDimensions} is a non-executable test that verifies the type safety of
* the generated dimensions API. The test is considered passing if it compiles without
* errors. Any problems with the dimensions API will be detected at compile time.
*
* @author Mirko Raner
**/
public interface TestDimensions
{
    Dimension<Zero, Zero, Zero, Zero, Zero, Zero, Zero> dimensions();

    default void testDimensions()
    {
        Dimension<Plus1, Zero, Zero, Zero, Zero, Zero, Zero> time1 = dimensions().t();
        Dimension<Plus1, Zero, Zero, Zero, Zero, Zero, Zero> time2 = dimensions().t().plus1();
        Dimension<Plus1, Zero, Zero, Zero, Zero, Zero, Zero> time3 = dimensions().m().zero().t();
        Dimension<Minus1, Zero, Zero, Zero, Zero, Zero, Zero> frequency = dimensions().t().minus1();
        Dimension<Minus1, Plus1, Zero, Zero, Zero, Zero, Zero> velocity1 = dimensions().t().minus1().l();
        Dimension<Minus1, Plus1, Zero, Zero, Zero, Zero, Zero> velocity2 = dimensions().t().minus1().l().plus1();
        Dimension<Minus1, Plus1, Zero, Zero, Zero, Zero, Zero> velocity3 = dimensions().t().minus1().l().plus1().m().zero();
        Dimension<Minus1, Plus1, Zero, Zero, Zero, Zero, Zero> velocity4 = dimensions().l().t().minus1();
        Dimension<Minus1, Plus1, Zero, Zero, Zero, Zero, Zero> velocity5 = dimensions().l().plus1().t().minus1();
        Dimension<Minus2, Minus1, Plus1, Zero, Zero, Zero, Zero> pressure1 = dimensions().t().minus2().l().minus1().m();
        Dimension<Minus2, Minus1, Plus1, Zero, Zero, Zero, Zero> pressure2 = dimensions().t().minus2().l().minus1().m().plus1();
        Dimension<Minus2, Minus1, Plus1, Zero, Zero, Zero, Zero> pressure3 = dimensions().m().plus1().t().minus2().l().minus1();
        Dimension<Minus2, Minus1, Plus1, Zero, Zero, Zero, Zero> pressure4 = dimensions().m().t().minus2().l().minus1();
        Dimension<Plus4, Minus2, Minus1, Plus2, Zero, Zero, Zero> capacitance = dimensions().t().plus4().l().minus2().m().minus1().i().plus2();
        Dimension<Minus3, Plus2, Plus1, Zero, Minus1, Zero, Zero> thermalConductance = dimensions().t().minus3().l().plus2().m().plus1().θ().minus1();
        Dimension<Minus1, Zero, Zero, Zero, Zero, Plus1, Zero> catalyticActivity = dimensions().n().t().minus1();
        Dimension<Plus3, Minus2, Minus1, Zero, Zero, Zero, Plus1> luminousEfficacy = dimensions().t().plus3().l().minus2().m().minus1().j().plus1();
        Dimension<Minus4, Plus1, Zero, Zero, Zero, Zero, Zero> jounce = dimensions().l().t().minus4();
        use(time1, time2, time3, frequency, velocity1, velocity2, velocity3, velocity4, velocity5);
        use(pressure1, pressure2, pressure3, pressure4, capacitance, thermalConductance, catalyticActivity);
        use(luminousEfficacy, jounce);
    }

    void use(Dimension<?, ?, ?, ?, ?, ?, ?>... dimensions);
}
