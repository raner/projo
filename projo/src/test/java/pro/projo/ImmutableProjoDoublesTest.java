package pro.projo;

import org.junit.Test;
import pro.projo.doubles.Factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
}
