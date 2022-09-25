//                                                                          //
// Copyright 2022 Mirko Raner                                               //
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
package pro.projo.interfaces.annotations.utilities;

import org.junit.Test;
import pro.projo.interfaces.annotation.utilities.DefaultAttributeNameConverter;
import static org.junit.Assert.assertEquals;

public class DefaultAttributeNameConverterTest
{
    DefaultAttributeNameConverter converter = new DefaultAttributeNameConverter();

    @Test
    public void testSimpleName()
    {
        assertEquals("width", converter.convertAttributeName("width"));
    }

    @Test
    public void testKeywordName()
    {
        assertEquals("class_", converter.convertAttributeName("class"));
    }

    @Test
    public void testOnlyHyphenAtEnd()
    {
        assertEquals("default_", converter.convertAttributeName("default-"));
    }
}
