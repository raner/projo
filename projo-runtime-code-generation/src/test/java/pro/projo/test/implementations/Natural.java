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

import java.math.BigInteger;
import pro.projo.Projo;
import pro.projo.annotations.Expects;
import pro.projo.annotations.Implements;
import pro.projo.annotations.Overrides;
import pro.projo.annotations.Returns;
import pro.projo.singles.Factory;
import static pro.projo.annotations.Overrides.toString;

@Implements("pro.projo.test.interfaces.Natural<pro.projo.test.implementations.Natural>")
public interface Natural
{
    public static Factory<Natural, BigInteger> factory = Projo.creates(Natural.class).with(Natural::value);

    BigInteger value();

    @Returns("pro.projo.test.interfaces.Natural")
    default Natural successor()
    {
            return factory.create(value().add(BigInteger.ONE));
    }

    @Returns("pro.projo.test.interfaces.Natural")
    default Natural plus(@Expects("pro.projo.test.interfaces.Natural") Natural other)
    {
        return factory.create(value().add(other.value()));
    }

    @Returns("pro.projo.test.interfaces.Natural")
    default Natural times(@Expects("pro.projo.test.interfaces.Natural") Natural other)
    {
        return factory.create(value().multiply(other.value()));
    }

    @Overrides(toString)
    default String toNativeString()
    {
        return value().toString();
    }
}