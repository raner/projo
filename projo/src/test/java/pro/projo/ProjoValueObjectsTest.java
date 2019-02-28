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

import org.junit.Test;
import pro.projo.annotations.ValueObject;
import pro.projo.doubles.Factory;
import static java.lang.System.identityHashCode;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pro.projo.Projo.create;
import static pro.projo.Projo.creates;

public class ProjoValueObjectsTest
{
    static interface Stateless
    {
        // This interface has no methods
    }

    @ValueObject
    static interface StatelessValueObject
    {
        // This interface has no methods
    }

    static interface EqualPerson
    {
        Factory<EqualPerson, String, String> FACTORY = creates(EqualPerson.class).with(EqualPerson::firstName, EqualPerson::lastName);

        @Override
        boolean equals(Object other);
        String firstName();
        String lastName();
    }

    @ValueObject
    static interface Complex
    {
        Number getReal();
        void setReal(Number real);
        Number getImaginary();
        void setImaginary(Number imaginary);
    }

    static interface ComplexPrimitive
    {
        double getReal();
        void setReal(double real);
        double getImaginary();
        void setImaginary(double imaginary);
        @Override int hashCode();
        @Override boolean equals(Object other);
    }

    static interface ImmutableComplexPrimitive
    {
        Factory<ImmutableComplexPrimitive, Double, Double> FACTORY = creates(ImmutableComplexPrimitive.class)
            .with(ImmutableComplexPrimitive::getReal, ImmutableComplexPrimitive::getImaginary);
        double getReal();
        double getImaginary();
        @Override int hashCode();
        @Override boolean equals(Object other);
    }

    static interface ComplexPerson
    {
        Factory<ComplexPerson, Complex, EqualPerson> FACTORY = creates(ComplexPerson.class)
            .with(ComplexPerson::complex, ComplexPerson::person);
        Complex complex();
        EqualPerson person();
    }

    @Test
    public void testEquals()
    {
        EqualPerson person1 = EqualPerson.FACTORY.create("John", "Doe");
        EqualPerson person2 = EqualPerson.FACTORY.create("John", "Doe");
        assertTrue(person1.equals(person2));
    }

    @Test
    public void testNotEquals()
    {
        EqualPerson person1 = EqualPerson.FACTORY.create("John", "Doe");
        EqualPerson person2 = EqualPerson.FACTORY.create("Jane", "Doe");
        assertFalse(person1.equals(person2));
    }

    @Test
    public void testNotEqualsIncompleteState()
    {
        EqualPerson person1 = EqualPerson.FACTORY.create("John", null);
        EqualPerson person2 = EqualPerson.FACTORY.create("Jane", "Doe");
        assertFalse(person1.equals(person2));
    }

    @Test
    public void testNotEqualsNull()
    {
        EqualPerson person = EqualPerson.FACTORY.create("John", "Doe");
        assertFalse(person.equals(null));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testNotEqualsWrongType()
    {
        EqualPerson person = EqualPerson.FACTORY.create("John", "Doe");
        assertFalse(person.equals("John Doe"));
    }

    @Test
    public void testStatelessValueObjectsAreAlwaysEquals()
    {
        StatelessValueObject object1 = create(StatelessValueObject.class);
        StatelessValueObject object2 = create(StatelessValueObject.class);
        boolean[] expected = {true, false};
        boolean[] actual = {object1.equals(object2), object1 == object2};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testStatelessNonValueObjectsUseObjectIdentity()
    {
        Stateless object1 = create(Stateless.class);
        Stateless object2 = create(Stateless.class);
        boolean[] expected = {true, true, false, false};
        boolean[] actual = {object1.equals(object1), object2.equals(object2), object1.equals(object2), object1 == object2};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testMutableValueObjectsEquals()
    {
        Complex number1 = create(Complex.class);
        number1.setReal(Math.PI);
        number1.setImaginary(Math.E);
        Complex number2 = create(Complex.class);
        number2.setReal(Math.PI);
        number2.setImaginary(Math.E);
        assertTrue(number1.equals(number2));
    }

    @Test
    public void testMutableValueObjectsNotEqual()
    {
        Complex number1 = create(Complex.class);
        number1.setReal(Math.PI);
        number1.setImaginary(Math.E);
        Complex number2 = create(Complex.class);
        number2.setImaginary(Math.E);
        assertFalse(number1.equals(number2));
    }

    /**
    * Mutable objects may not have all of their properties set. We need to make sure that if the equals method is called
    * on an object with incomplete properties, it does not only compare the properties that are present, but all potential
    * properties of the object.
    **/
    @Test
    public void testMutableValueObjectsNotEqualOtherWayAround()
    {
        Complex number1 = create(Complex.class);
        number1.setReal(Math.PI);
        number1.setImaginary(Math.E);
        Complex number2 = create(Complex.class);
        number2.setImaginary(Math.E);
        assertFalse(number2.equals(number1));
    }

    @Test
    public void testMutablePrimitiveValueObjectsEquals()
    {
        ComplexPrimitive number1 = create(ComplexPrimitive.class);
        number1.setReal(Math.PI);
        number1.setImaginary(Math.E);
        ComplexPrimitive number2 = create(ComplexPrimitive.class);
        number2.setReal(Math.PI);
        number2.setImaginary(Math.E);
        assertTrue(number1.equals(number2));
    }

    @Test
    public void testMutableValueObjectsNullIsEqualToDefaultValue()
    {
        ComplexPrimitive number1 = create(ComplexPrimitive.class);
        number1.setReal(0D);
        number1.setImaginary(Math.E);
        ComplexPrimitive number2 = create(ComplexPrimitive.class);
        number2.setImaginary(Math.E);
        assertTrue(number1.equals(number2));
    }

    @Test
    public void testMutablePrimitiveValueObjectsNotEqual()
    {
        ComplexPrimitive number1 = create(ComplexPrimitive.class);
        number1.setReal(Math.PI);
        number1.setImaginary(Math.E);
        ComplexPrimitive number2 = create(ComplexPrimitive.class);
        number2.setReal(Math.PI);
        assertFalse(number1.equals(number2));
    }

    @Test
    public void testImmutablePrimitiveValueObjectsEquals()
    {
        ImmutableComplexPrimitive number1 = ImmutableComplexPrimitive.FACTORY.create(Math.PI, Math.E);
        ImmutableComplexPrimitive number2 = ImmutableComplexPrimitive.FACTORY.create(Math.PI, Math.E);
        assertTrue(number1.equals(number2));
    }

    @Test
    public void testImmutablePrimitiveValueObjectsNotEqual()
    {
        ImmutableComplexPrimitive number1 = ImmutableComplexPrimitive.FACTORY.create(Math.E, Math.PI);
        ImmutableComplexPrimitive number2 = ImmutableComplexPrimitive.FACTORY.create(Math.PI, Math.E);
        assertFalse(number1.equals(number2));
    }
    
    @Test
    public void testImmutablePrimitiveValueObjectsNullIsEqualToDefaultValue()
    {
        ImmutableComplexPrimitive number1 = ImmutableComplexPrimitive.FACTORY.create(0D, 0D);
        ImmutableComplexPrimitive number2 = ImmutableComplexPrimitive.FACTORY.create(null, null);
        assertTrue(number1.equals(number2));
    }

    @Test
    public void testImmutablePrimitiveValueObjectsHashCode()
    {
        ImmutableComplexPrimitive number1 = ImmutableComplexPrimitive.FACTORY.create(Math.PI, Math.E);
        ImmutableComplexPrimitive number2 = ImmutableComplexPrimitive.FACTORY.create(Math.PI, Math.E);
        assertTrue(number1.hashCode() == number2.hashCode());
    }

    @Test
    public void testImmutablePrimitiveValueObjectsHashCodeWithNullValues()
    {
        ImmutableComplexPrimitive number1 = ImmutableComplexPrimitive.FACTORY.create(0D, 0D);
        ImmutableComplexPrimitive number2 = ImmutableComplexPrimitive.FACTORY.create(null, null);
        assertTrue(number1.hashCode() == number2.hashCode());
    }

    /**
    * This test is technically not 100% correct, as the two test objects could indeed accidentally have the
    * same hash code. Despite this test's potential to fail unexpectedly, this particular test was added to
    * guard against common implementation flaws where, for example, the {@link #hashCode()} method would
    * always return the same value (which is still correct per the contract, though horribly inefficient).
    **/
    @Test
    public void testImmutablePrimitiveValueObjectsHashCodeNotEqual()
    {
        ImmutableComplexPrimitive number1 = ImmutableComplexPrimitive.FACTORY.create(Math.PI, Math.E);
        ImmutableComplexPrimitive number2 = ImmutableComplexPrimitive.FACTORY.create(0D, null);
        assertTrue(number1.hashCode() != number2.hashCode());
    }

    @Test
    public void testIdentityHashCode()
    {
        Complex number = create(Complex.class);
        EqualPerson person =  EqualPerson.FACTORY.create("John", "Doe");
        ComplexPerson person1 = ComplexPerson.FACTORY.create(number, person);
        ComplexPerson person2 = ComplexPerson.FACTORY.create(number, person);
        int[] expected = {identityHashCode(person1), identityHashCode(person2)};
        int[] actual = {person1.hashCode(), person2.hashCode()};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testEqualityHashCode()
    {
        EqualPerson person1 =  EqualPerson.FACTORY.create("John", "Doe");
        EqualPerson person2 =  EqualPerson.FACTORY.create("John", "Doe");
        assertTrue(person1.hashCode() == person2.hashCode());
    }

    @Test
    public void testEqualityHashCodeSwitchFirstAndLast()
    {
        EqualPerson person1 =  EqualPerson.FACTORY.create("Lewis", "Kelly");
        EqualPerson person2 =  EqualPerson.FACTORY.create("Kelly", "Lewis");
        assertFalse(person1.hashCode() == person2.hashCode());
    }

    @Test
    public void testEqualityHashCodeAllFieldsNull()
    {
        EqualPerson person1 =  EqualPerson.FACTORY.create(null, null);
        EqualPerson person2 =  EqualPerson.FACTORY.create(null, null);
        assertTrue(person1.hashCode() == person2.hashCode());
    }

    @Test
    public void testEqualityHashCodeAlwaysTheSame()
    {
        EqualPerson person =  EqualPerson.FACTORY.create("Taylor", "Bennett");
        int hashCode1 = person.hashCode();
        int hashCode2 = person.hashCode();
        assertTrue(hashCode1 == hashCode2);
    }
}
