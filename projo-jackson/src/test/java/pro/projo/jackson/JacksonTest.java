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
package pro.projo.jackson;

import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class JacksonTest {

    private ObjectMapper mapper;

    @Before
    public void initializeObjectMapper()
    {
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
    }

    @Test
    public void ensureThatProjoJacksonModuleIsFound() throws Exception
    {
        Stream<Module> modules = ObjectMapper.findModules().stream();
        assertTrue(modules.anyMatch(ProjoJacksonModule.class::isInstance));
    }

    @Test
    public void testDeserializeToInterface() throws Exception
    {
        Complex value = mapper.readValue("{\"real\":3.14, \"imaginary\":2.71}", Complex.class);
        assertArrayEquals(new double[] {3.14, 2.71}, new double[] {value.real(), value.imaginary()}, 1E-6);
    }

    @Test
    public void testDeserializeToRegularObject() throws Exception
    {
        ComplexPojo value = mapper.readValue("{\"real\":3.14, \"imaginary\":2.71}", ComplexPojo.class);
        assertArrayEquals(new double[] {3.14, 2.71}, new double[] {value.getReal(), value.getImaginary()}, 1E-6);
    }
}
