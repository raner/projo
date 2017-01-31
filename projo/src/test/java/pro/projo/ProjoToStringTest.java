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
package pro.projo;

import java.util.regex.Pattern;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
* The {@link ProjoToStringTest} verifies that the {@link #toString()} method produces the expected results
* when called on Projo objects.
*
* @author Mirko Raner
**/
public class ProjoToStringTest
{
    static interface DefaultToString
    {
        int getInteger();
        void setInteger(int integer);
        String getString();
        void setString(String string);
    }

    static interface FieldByFieldToString
    {
        int getInteger();
        void setInteger(int integer);
        String getString();
        void setString(String string);
        @Override String toString();
    }

    @Test
    public void testDefaultToString()
    {
        DefaultToString projo = Projo.create(DefaultToString.class);
        String toString = projo.toString();
        String expectedPrefix = DefaultToString.class.getName() + '@';
        Pattern expectedSuffix = Pattern.compile("@[0-9a-f]{1,8}$");
        assertTrue(toString, toString.startsWith(expectedPrefix) && expectedSuffix.matcher(toString).find());
    }

    @Test
    public void testFieldByFieldToString()
    {
        FieldByFieldToString projo = Projo.create(FieldByFieldToString.class);
        projo.setInteger(42);
        projo.setString("Hello");
        String toString = projo.toString();
        String expected = FieldByFieldToString.class.getName() + "[integer=42, string=\"Hello\"]";
        assertEquals(expected, toString);
    }

    @Test
    public void testFieldByFieldToStringWithNulls()
    {
        FieldByFieldToString projo = Projo.create(FieldByFieldToString.class);
        String toString = projo.toString();
        String expected = FieldByFieldToString.class.getName() + "[integer=0, string=null]";
        assertEquals(expected, toString);
    }
}
