package pro.projo;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static pro.projo.Projo.create;

public class MutableProjoDoublesTest
{
    static interface Person
    {
        void setFirstName(String firstName);
        String getFirstName();
        void setLastName(String lastName);
        String getLastName();
    }

    @Test
    public void testCreatePerson()
    {
        Person person = create(Person.class);
        assertNotNull(person);
    }
    
    @Test
    public void testPerson()
    {
        Person person = create(Person.class);
        person.setFirstName("First");
        person.setLastName("Last");
        String[] expected = {"First", "Last"};
        String[] actual = {person.getFirstName(), person.getLastName()};
        assertArrayEquals(expected, actual);
    }
}
