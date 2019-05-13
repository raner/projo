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

import static org.junit.Assert.assertEquals;
import java.lang.reflect.Method;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

@RunWith(Parameterized.class)
public class IntermediateTest
{
    @Parameters(name="with[{0}]")
    public static Collection<Object[]> oneToThirty()
    {
        return rangeClosed(1, 30).mapToObj(value -> new Object[] {value}).collect(toList());
    }

    @Parameter
    public int count;

    @Test
    public void testParameterCount()
    {
        Projo.Intermediate<Object> intermediate = new Projo.Intermediate<Object>()
        {
            @Override
            public Class<Object> type()
            {
                return Object.class;
            }
        };
        Method method = intermediate.methods()[count-1];
        assertEquals(count, method.getParameterCount());
    }
}
