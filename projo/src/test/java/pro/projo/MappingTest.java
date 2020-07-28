//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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

import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MappingTest implements AbstractTypeMappingTest
{
    @Test
    public void createSimpleMapping()
    {
        Mapping mapping = Projo.mapping()
            .map(Integer.class).to(BigInteger.class)
            .map(String.class).to(java.lang.String.class);
        assertEquals(BigInteger.class, mapping.getDelegate(Integer.class));
        assertEquals(java.lang.String.class, mapping.getDelegate(String.class));
    }
}
