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
package pro.projo.test.implementations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import pro.projo.Projo;
import pro.projo.annotations.Expects;
import pro.projo.annotations.Implements;
import pro.projo.annotations.Inherits;
import pro.projo.annotations.Overrides;
import pro.projo.annotations.Returns;
import pro.projo.singles.Factory;
import static pro.projo.annotations.Overrides.equals;
import static pro.projo.annotations.Overrides.toString;

@Implements("pro.projo.test.interfaces.Real<pro.projo.test.implementations.Real>")
public interface Real
{
    Factory<Real, BigDecimal> factory = Projo.creates(Real.class).with(Real::value);

    BigDecimal value();

    @Returns("pro.projo.test.interfaces.Real")
    default Real negated()
    {
        return factory.create(value().negate());
    }

    @Returns("pro.projo.test.interfaces.Real")
    default Real plus(@Expects("pro.projo.test.interfaces.Real") Real other)
    {
        return factory.create(value().add(other.value()));
    }

    @Returns("java.util.List<pro.projo.test.interfaces.Real>")
    default List<Real> plusMinus(@Expects("pro.projo.test.interfaces.Real") Real other)
    {
        return Arrays.asList(plus(other), minus(other));
    }

    default boolean equals(@Expects("pro.projo.test.interfaces.Real") Real other)
    {
        return value().equals(other.value());
    }

    @Inherits
    @Returns("pro.projo.test.interfaces.Real")
    Real minus(@Expects("pro.projo.test.interfaces.Real") Real other);

    /**
    * This is a convenience method so that the test cases can use the regular {@code assertEquals} and
    * {@link #equals(Object)} (instead of comparing by using {@link #equals(Real)}).
    **/
    @Overrides(equals)
    default boolean defaultEquals(Object other)
    {
        return other instanceof Real && value().equals(((Real)other).value());
    }

    /**
    * This is another convenience method to make JUnit comparison failures more meaningful.
    **/
    @Overrides(toString)
    default String defaultToString()
    {
        return value().toString();
    }
}
