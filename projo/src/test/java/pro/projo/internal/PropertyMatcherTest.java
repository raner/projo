package pro.projo.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PropertyMatcherTest
{
    private PropertyMatcher matcher = new PropertyMatcher();

    @Test
    public void testGetter()
    {
        assertEquals("Property", matcher.propertyName("getProperty"));
    }

    @Test
    public void testSetter()
    {
        assertEquals("Property", matcher.propertyName("setProperty"));
    }

    @Test
    public void testImmutableGetter1()
    {
        assertEquals("property", matcher.propertyName("property"));
    }

    @Test
    public void testImmutableGetter2()
    {
        assertEquals("getter", matcher.propertyName("getter"));
    }

    @Test
    public void testBadlyNamedImmutableGetter()
    {
        assertEquals("setter", matcher.propertyName("setter"));
    }
}
