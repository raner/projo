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
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import pro.projo.quadruples.Factory;
import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static pro.projo.Projo.creates;

public class DelegateTest
{
    static interface Something<$ extends Something<$>>
    {
        $ self();
    }

    static interface Value<$ extends Value<$>> extends Something<$>
    {
        Boolean<?> equals($ other);
    }

    static interface Ordered<$ extends Ordered<$>> extends Something<$>
    {
        Boolean<?> lessOrEqual($ other);
    }

    static interface Boolean<$ extends Boolean<$>> extends Value<$>
    {
        Boolean<?> not();
        Boolean<?> and(Boolean<?> other);
    }

    static interface True<$ extends True<$>> extends Boolean<$>
    {
        //
    }

    static interface False<$ extends False<$>> extends Boolean<$>
    {
        //
    }

    static interface Comparable<$ extends Comparable<$>> extends Ordered<$>, Value<$>
    {
        default Boolean<?> lessThan($ other)
        {
            return lessOrEqual(other).and(equals(other).not());
        }
    }

    static interface Number<$ extends Number<$>> extends Value<$>
    {
        $ add($ other);
        $ multiply($ other);
    }

    static interface Natural<$ extends Natural<$>> extends Comparable<$>, Number<$>
    {
        
    }

    static interface Integer<$ extends Integer<$>> extends Comparable<$>, Number<$>
    {
    	Integer<?> negate();
    	Integer<?> absolute();
    	Integer<?> subtract(Integer<?> other);
    	Integer<?> remainder(Integer<?> other);
    }

    static interface Integer$ extends Integer<Integer$>
    {
        //
    }

    static interface String<$ extends String<$>> extends Value<$>
    {
        Natural<?> length();
        String<?> substring(Natural<?> begin);
        String<?> substring(Natural<?> begin, Natural<?> end);
        Natural<?> indexOf(String<?> string);
        Boolean<?> beginsWith(String<?> string);
        Boolean<?> endsWith(String<?> string);
    }

    
    @Test
    public void testIntegers()
    {
        Integer$ one = Projo.delegate(Integer$.class, BigInteger.ONE);
        Integer$ ten = Projo.delegate(Integer$.class, BigInteger.TEN);
        Integer$ eleven = one.add(ten);
        BigInteger result = Projo.unwrap(eleven);
        assertEquals(new BigInteger("11"), result);
    }
}
