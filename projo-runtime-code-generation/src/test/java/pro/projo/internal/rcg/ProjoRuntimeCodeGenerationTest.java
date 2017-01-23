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
package pro.projo.internal.rcg;

import java.util.function.Function;
import org.junit.Test;
import pro.projo.Projo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ProjoRuntimeCodeGenerationTest
{
    static interface Getters
    {
        String getName();
        int getValue();
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
        String[] expected = {"Value", "Name"};
        assertArrayEquals(expected, fieldNames);
    }
}
