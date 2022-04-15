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

import java.util.function.Function;
import org.junit.Test;
import pro.projo.Projo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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

    public static interface Named1
    {
        String name();
    }
    
    public static interface Named2
    {
        String name();
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
}
