//                                                                          //
// Copyright 2019 - 2022 Mirko Raner                                        //
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

import pro.projo.annotations.Property;
import pro.projo.doubles.Factory;
import static java.lang.System.identityHashCode;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pro.projo.Projo.creates;

public class ImmutableProjoDoublesTest
{
    static interface Person
    {
        Factory<Person, String, String> FACTORY = creates(Person.class).with(Person::firstName, Person::lastName);
        String firstName();
        String lastName();
    }

    static interface Complex
    {
        Factory<Complex, Number, Number> FACTORY = creates(Complex.class).with(Complex::real, Complex::imaginary);
        Number real();
        Number imaginary();
    }

    static interface ComplexPerson
    {
        Factory<ComplexPerson, Complex, Person> FACTORY = creates(ComplexPerson.class)
            .with(ComplexPerson::complex, ComplexPerson::person);
        Complex complex();
        Person person();
    }

    static interface Version
    {
        Factory<Version, Integer, Integer> FACTORY = creates(Version.class)
            .with(Version::major, Version::minor);

        int major();

        @Property
        default int minor()
        {
            // This method should be overridden by Projo
            throw new UnsupportedOperationException();
        }

        default int patch()
        {
        	// This method should be retained by Projo
        	return 99;
        }
    }

    @Test
    public void testCreateVersion()
    {
        Version version = Version.FACTORY.create(3, 14);
        assertEquals(3, version.major());
        assertEquals(14, version.minor());
        assertEquals(99, version.patch());
    }

    @Test
    public void testCreatePerson()
    {
        Person person = Person.FACTORY.create("John", "Doe");
        assertNotNull(person);
    }

    @Test
    public void testGetPersonFirstName()
    {
        Person person = Person.FACTORY.create("John", "Doe");
        assertEquals("John", person.firstName());
    }

    @Test
    public void testGetPersonLastName()
    {
        Person person = Person.FACTORY.create("John", "Doe");
        assertEquals("Doe", person.lastName());
    }

    @Test
    public void testCreateComplex()
    {
        Complex complex = Complex.FACTORY.create(Math.PI, Math.E);
        assertNotNull(complex);
    }

    @Test
    public void testComplexPerson()
    {
        Complex complex = Complex.FACTORY.create(Math.PI, Math.E);
        Person person = Person.FACTORY.create("John", "Doe");
        ComplexPerson complexPerson = ComplexPerson.FACTORY.create(complex, person);
        assertEquals(Math.PI, complexPerson.complex().real());
        assertEquals(Math.E, complexPerson.complex().imaginary());
        assertEquals("John", complexPerson.person().firstName());
        assertEquals("Doe", complexPerson.person().lastName());
    }

    @Test
    public void testEquals()
    {
        Person person = Person.FACTORY.create("John", "Doe");
        assertTrue(person.equals(person));
    }

    @Test
    public void testNotEquals()
    {
        Person person1 = Person.FACTORY.create("John", "Doe");
        Person person2 = Person.FACTORY.create("John", "Doe");
        assertFalse(person1.equals(person2));
    }

    @Test
    public void testHashCode()
    {
        Person person1 = Person.FACTORY.create("John", "Doe");
        Person person2 = Person.FACTORY.create("John", "Doe");
        int[] expected = {identityHashCode(person1), identityHashCode(person2)};
        int[] actual = {person1.hashCode(), person2.hashCode()};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testCreateWithBuilder()
    {
        Person person = Projo.builder(Person.class)
            .with(Person::firstName, "John")
            .with(Person::lastName, "Doe")
            .build();
        String[] expected = {"John", "Doe"};
        String[] actual = {person.firstName(), person.lastName()};
        assertArrayEquals(expected, actual);
    }
}
