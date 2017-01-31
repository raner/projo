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
package pro.projo.internal;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PropertyMatcherTest
{
    private PropertyMatcher matcher = new PropertyMatcher();

    @Test
    public void testGetter()
    {
        assertEquals("property", matcher.propertyName("getProperty"));
    }

    @Test
    public void testSetter()
    {
        assertEquals("property", matcher.propertyName("setProperty"));
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

    @Test
    public void testGetterName()
    {
        assertEquals("getFirstName", matcher.getterName("FirstName"));
    }
}
