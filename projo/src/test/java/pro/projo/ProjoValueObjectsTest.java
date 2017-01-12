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

import java.lang.reflect.Method;
import org.junit.Test;
import pro.projo.annotations.ValueObject;
import pro.projo.doubles.Factory;
import pro.projo.internal.ProjoInvocationHandler;
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
    public void testGetterPredicate() throws Exception
    {
        Method setReal = Complex.class.getDeclaredMethod("setReal", Number.class);
        assertFalse(ProjoInvocationHandler.getter.test(setReal, new Object[] {null}));
    }

    @Test
    @org.junit.Ignore("hashCode is not yet implemented")
    public void testHashCode()
    {
        EqualPerson person1 = EqualPerson.FACTORY.create("John", "Doe");
        EqualPerson person2 = EqualPerson.FACTORY.create("John", "Doe");
        int[] expected = {identityHashCode(person1), identityHashCode(person2)};
        int[] actual = {person1.hashCode(), person2.hashCode()};
        assertArrayEquals(expected, actual);
    }
}
