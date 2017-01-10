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
package pro.projo;

import org.junit.Test;
import pro.projo.trigintuples.Factory;
import static org.junit.Assert.assertArrayEquals;
import static pro.projo.Projo.creates;

import java.util.Objects;

/**
* The class {@link ImmutableProjoTrigintuplesTest} is a JUnit test class for verifying immutable
* Projo objects with 30 fields.
*
* @author Mirko Raner
**/
public class ImmutableProjoTrigintuplesTest
{
    static class N extends Number
    {
        private final static long serialVersionUID = 1448368728154359520L;

        private int value;

        N(int value)
        {
            this.value = value;
        }

        @Override
        public int intValue()
        {
            return value;
        }

        @Override
        public long longValue()
        {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public float floatValue()
        {
            return value;
        }

        @Override
        public double doubleValue()
        {
            return value;
        }

        @Override
        public boolean equals(Object other)
        {
            return other instanceof N && ((N)other).value == value;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(value);
        }
    }

    static interface T
    {
        N s01();
        N s02();
        N s03();
        N s04();
        N s05();
        N s06();
        N s07();
        N s08();
        N s09();
        N s10();
        N s11();
        N s12();
        N s13();
        N s14();
        N s15();
        N s16();
        N s17();
        N s18();
        N s19();
        N s20();
        N s21();
        N s22();
        N s23();
        N s24();
        N s25();
        N s26();
        N s27();
        N s28();
        N s29();
        N s30();
        Factory<T, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N, N> FACTORY =
            creates(T.class).with(T::s01, T::s02, T::s03, T::s04, T::s05, T::s06, T::s07, T::s08, T::s09, T::s10,
                T::s11, T::s12, T::s13, T::s14, T::s15, T::s16, T::s17, T::s18, T::s19, T::s20,
                T::s21, T::s22, T::s23, T::s24, T::s25, T::s26, T::s27, T::s28, T::s29, T::s30);
    }

    @Test
    public void testName()
    {
        T t = T.FACTORY.create(
            n(1), n(2), n(3), n(4), n(5), n(6), n(7), n(8), n(9), n(10),
            n(11), n(12), n(13), n(14), n(15), n(16), n(17), n(18), n(19), n(20),
            n(21), n(22), n(23), n(24), n(25), n(26), n(27), n(28), n(29), n(30));
        Number[] expected =
        {
            n(1), n(2), n(3), n(4), n(5), n(6), n(7), n(8), n(9), n(10),
            n(11), n(12), n(13), n(14), n(15), n(16), n(17), n(18), n(19), n(20),
            n(21), n(22), n(23), n(24), n(25), n(26), n(27), n(28), n(29), n(30)
        };
        Object[] actual =
        {
             t.s01(), t.s02(), t.s03(), t.s04(), t.s05(), t.s06(), t.s07(), t.s08(), t.s09(), t.s10(),
             t.s11(), t.s12(), t.s13(), t.s14(), t.s15(), t.s16(), t.s17(), t.s18(), t.s19(), t.s20(),
             t.s21(), t.s22(), t.s23(), t.s24(), t.s25(), t.s26(), t.s27(), t.s28(), t.s29(), t.s30()
        };
        assertArrayEquals(expected, actual);
    }

    private N n(int n)
    {
        return new N(n);
    }
}
