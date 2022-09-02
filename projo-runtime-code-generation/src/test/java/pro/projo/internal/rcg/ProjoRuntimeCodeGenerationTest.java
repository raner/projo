//                                                                          //
// Copyright 2017 - 2022 Mirko Raner                                        //
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
package pro.projo.internal.rcg;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.function.Function;
import org.junit.Test;
import pro.projo.Projo;
import pro.projo.annotations.Implements;
import pro.projo.annotations.Returns;
import pro.projo.singles.Factory;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
* The {@link ProjoRuntimeCodeGenerationTest} is a JUnit test that verifies general aspects
* of Projo Runtime Code Generation (RCG).
*
* @author Mirko Raner
**/
public class ProjoRuntimeCodeGenerationTest
{
    static interface Getters
    {
        String getName();
        int getValue();
    }

    // A class can only be generated once, so to test generation in different
    // packages we need two different test interfaces:

    public static interface Named
    {
        Class<?> interfaceType();
    }

    public static interface Named1
    {
        String name();
    }
    
    public static interface Named2
    {
        String name();
    }

    @Implements("java.lang.Appendable")
    public static interface SpecialAppendable
    {
        @Returns("java.lang.Appendable")
        default Object append(CharSequence csq)
        {
            return System.out;
        }

        @Returns("java.lang.Appendable")
        default Object append(CharSequence csq, int start, int end)
        {
            return System.err;
        }

        @Returns("java.lang.Appendable")
        default Object append(char c)
        {
            return null;
        }
    }

    @Test
    public void testRuntimeCodeGenerationProjoImplementation()
    {
        assertEquals(RuntimeCodeGenerationProjo.class, Projo.getImplementation().getClass());
    }

    @Test
    public void testGetFields() throws Exception
    {
        Function<Getters, ?> getName = Getters::getName;
        Function<Getters, Object> getValue = Getters::getValue;
        @SuppressWarnings("unchecked")
        Function<Getters, Object>[] getters = (Function<Getters, Object>[])new Function<?, ?>[] {getValue, getName};
        String[] fieldNames = new RuntimeCodeGenerationProjo().getFieldNames(Getters.class, getters);
        String[] expected = {"value", "name"};
        assertArrayEquals(expected, fieldNames);
    }

    @Test
    public void testGenerationInSamePackage()
    {
        Named1 person = Projo.creates(Named1.class).with(Named1::name).create("John Doe");
        String[] nameAndPackageName = {person.name(), person.getClass().getPackage().getName()};
        String[] expected = {"John Doe", "pro.projo.internal.rcg"};
        assertArrayEquals(expected, nameAndPackageName);
    }
    
    @Test
    public void testGenerationInDefaultPackage()
    {
        Named2 person = Projo.creates(Named2.class).inDefaultPackage().with(Named2::name).create("John Doe");
        String[] nameAndFullClassName = {person.name(), person.getClass().getName()};
        String[] expected = {"John Doe", "Named2$Projo"};
        assertArrayEquals(expected, nameAndFullClassName);
    }

    @Test
    public void testReturnAnnotation() throws IOException
    {
        Appendable appendable = (Appendable)Projo.create(SpecialAppendable.class);
        assertEquals(System.out, appendable.append(""));
        assertEquals(System.err, appendable.append("", 0, 0));
        assertNull(appendable.append('\0'));
    }

    @Test
    public void testGeneratedCodeUsesInterfaceClassLoaderByDefault() throws Exception
    {
        // Ensure the integrity of the test:
        //
        ClassLoader interfaceClassLoader = Named.class.getClassLoader();
        assertNotNull(interfaceClassLoader); // should not be bootstrap class loader

        // By default, the interface's class loader should be used:
        //
        Factory<Named, Class<?>> factory = Projo.creates(Named.class).with(Named::interfaceType);
        Named object = factory.create(Named.class);
        assertEquals(interfaceClassLoader, object.getClass().getClassLoader());
    }

    @Test
    public void testDefaultPackageImplementationsUseContextClassLoader()
    {
        // JDK standard library classes should always be loaded by the bootstrap class loader:
        //
        ClassLoader bootstrapClassLoader = Override.class.getClassLoader(); // may be null

        // Make sure the expected CLs are different to begin with, to ensure the integrity of the test:
        //
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        assertNotEquals(contextClassLoader, bootstrapClassLoader);

        // The generated class should use the context class loader instead of the bootstrap class loader:
        // (which wouldn't work anyway, since it's not possible to inject into the bootstrap class loader)
        //
        Factory<Override, Class<? extends Annotation>> factory;
        factory = Projo.creates(Override.class).inDefaultPackage().with(Override::annotationType);
        Override override = factory.create(Override.class);
        assertEquals(contextClassLoader, override.getClass().getClassLoader());
    }
}
