//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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

import java.util.stream.Stream;
import org.junit.Test;
import pro.projo.annotations.ValueObject;
import pro.projo.doubles.Factory;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pro.projo.Projo.creates;

/**
* The {@link ProjoTest} class verifies the public static methods of the {@link Projo} class.
*
* @author Mirko Raner
**/
public class ProjoTest
{
    static interface Interval
    {
        Factory<Interval, Double, Double> FACTORY = creates(Interval.class).with(Interval::begin, Interval::end);
        Double begin();
        Double end();
    }

    static interface NoFactory
    {
        void setInteger(int value);
        int getInteger();
    }

    static interface ValueObject1
    {
        @Override
        boolean equals(Object other);

        Object field();
    }

    static interface ValueObject2
    {
        @Override
        int hashCode();

        Object field();
    }

    static interface ValueObject3
    {
        @Override
        int hashCode();

        @Override
        boolean equals(Object other);

        Object field();
    }

    @ValueObject
    static interface ValueObject4
    {
        Object field();
    }

    @ValueObject
    static interface ValueObject5
    {
        @Override
        int hashCode();

        @Override
        boolean equals(Object other);

        Object field();
    }

    @Test
    public void testGetFactory()
    {
        assertEquals(Interval.FACTORY, Projo.getFactory(Interval.class));
    }

    @Test
    public void testGetFactoryReturnsNull()
    {
        assertNull(Projo.getFactory(NoFactory.class));
    }

    @Test
    public void testIsValueObject()
    {
        Class<?>[] projos =
        {
            Interval.class,
            NoFactory.class,
            ValueObject1.class,
            ValueObject2.class,
            ValueObject3.class,
            ValueObject4.class,
            ValueObject5.class
        };
        Object[] expected = {false, false, true, true, true, true, true};
        Object[] actual = Stream.of(projos).map(Projo::isValueObject).collect(toList()).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testIsImplementationClass()
    {
        Class<? extends Interval> type = Interval.FACTORY.create(1D, 2D).getClass();
        assertTrue(Projo.isProjoClass(type));
    }

    @Test
    public void testIsNotImplementationClass()
    {
        assertFalse(Projo.isProjoClass(String.class));
    }

    @Test
    public void testInterfaceIsNotImplementationClass()
    {
        assertFalse(Projo.isProjoClass(Interval.class));
    }

    @Test
    public void testGetProjoInterface()
    {
        Class<? extends Interval> type = Interval.FACTORY.create(1D, 2D).getClass();
        Class<Interval> projoInterface = Projo.getInterfaceClass(type);
        assertEquals(Interval.class, projoInterface);
    }
}
