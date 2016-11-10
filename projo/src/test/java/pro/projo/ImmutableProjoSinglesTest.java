//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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
import pro.projo.singles.Factory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pro.projo.Projo.creates;

public class ImmutableProjoSinglesTest
{
    static interface Constant
    {
        Factory<Constant, Double> FACTORY = creates(Constant.class).with(Constant::value);
        Double value();
    }

    static interface WrappedBoolean
    {
        Factory<WrappedBoolean, Boolean> FACTORY = creates(WrappedBoolean.class).with(WrappedBoolean::value);
        boolean value();
    }

    static interface WrappedCharacter
    {
        Factory<WrappedCharacter, Character> FACTORY = creates(WrappedCharacter.class).with(WrappedCharacter::value);
        char value();
    }

    static interface WrappedByte
    {
        Factory<WrappedByte, Byte> FACTORY = creates(WrappedByte.class).with(WrappedByte::value);
        byte value();
    }

    static interface WrappedShort
    {
        Factory<WrappedShort, Short> FACTORY = creates(WrappedShort.class).with(WrappedShort::value);
        short value();
    }

    static interface WrappedInteger
    {
        Factory<WrappedInteger, Integer> FACTORY = creates(WrappedInteger.class).with(WrappedInteger::value);
        int value();
    }

    static interface WrappedLong
    {
        Factory<WrappedLong, Long> FACTORY = creates(WrappedLong.class).with(WrappedLong::value);
        long value();
    }

    static interface WrappedFloat
    {
        Factory<WrappedFloat, Float> FACTORY = creates(WrappedFloat.class).with(WrappedFloat::value);
        float value();
    }

    static interface WrappedDouble
    {
        Factory<WrappedDouble, Double> FACTORY = creates(WrappedDouble.class).with(WrappedDouble::value);
        double value();
    }

    @Test
    public void testConstantE()
    {
        Constant e = Constant.FACTORY.create(Math.E);
        assertEquals(new Double(Math.E), e.value());
    }

    @Test
    public void testConstantPi()
    {
        Constant pi = Constant.FACTORY.create(Math.PI);
        assertEquals(new Double(Math.PI), pi.value());
    }

    // TODO: convert the following tests to a parameterized test

    @Test
    public void testWrappedBoolean()
    {
        WrappedBoolean wrapped = WrappedBoolean.FACTORY.create(true);
        assertTrue(wrapped.value());
    }

    @Test
    public void testWrappedCharacter()
    {
        WrappedCharacter wrapped = WrappedCharacter.FACTORY.create('*');
        assertEquals('*', wrapped.value());
    }

    @Test
    public void testWrappedByte()
    {
        WrappedByte wrapped = WrappedByte.FACTORY.create((byte)127);
        assertEquals((byte)127, wrapped.value());
    }

    @Test
    public void testWrappedShort()
    {
        WrappedShort wrapped = WrappedShort.FACTORY.create((short)32767);
        assertEquals((short)32767, wrapped.value());
    }

    @Test
    public void testWrappedInteger()
    {
        WrappedInteger wrapped = WrappedInteger.FACTORY.create(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, wrapped.value());
    }

    @Test
    public void testWrappedLong()
    {
        WrappedLong wrapped = WrappedLong.FACTORY.create(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, wrapped.value());
    }

    @Test
    public void testWrappedFloat()
    {
        WrappedFloat wrapped = WrappedFloat.FACTORY.create(42F);
        assertEquals(42F, wrapped.value(), 1E-8F);
    }

    @Test
    public void testWrappedDouble()
    {
        WrappedDouble wrapped = WrappedDouble.FACTORY.create(3D);
        assertEquals(3D, wrapped.value(), 1E-8D);
    }
}
