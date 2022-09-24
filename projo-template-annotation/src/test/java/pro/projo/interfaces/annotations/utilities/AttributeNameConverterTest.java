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

import org.junit.Ignore;
import org.junit.Test;
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import static org.junit.Assert.assertEquals;

public class AttributeNameConverterTest
{
    AttributeNameConverter converter = new AttributeNameConverter();

    @Test
    public void testSimpleName()
    {
        assertEquals("width", converter.convertAttributeName("width"));
    }

    @Test
    public void testKeywordName()
    {
        assertEquals("class", converter.convertAttributeName("class"));
    }

    @Test
    public void testCompoundName()
    {
        assertEquals("onmousedown", converter.convertAttributeName("onmousedown"));
    }

    @Test
    public void testHyphenatedName()
    {
        assertEquals("ariaDisabled", converter.convertAttributeName("aria-disabled"));
    }

    @Test
    public void testDoubleHyphenatedName()
    {
        assertEquals("dataWidgetId", converter.convertAttributeName("data-widget-id"));
    }

    @Test
    public void testHyphenAtEnd()
    {
        assertEquals("dataWidgetId", converter.convertAttributeName("data-widget-id-"));
    }

    @Test
    public void testOnlyHyphenAtEnd()
    {
        assertEquals("data", converter.convertAttributeName("data-"));
    }

    @Test
    public void testMultipleHyphensAtEnd()
    {
        assertEquals("data", converter.convertAttributeName("data--"));
    }

    @Ignore("Not really valid - see https://stackoverflow.com/questions/925994")
    @Test
    public void testHyphenAtBeginning()
    {
        assertEquals("dataWidgetId", converter.convertAttributeName("-data-widget-id-"));
    }

    @Ignore("Not really valid - see https://stackoverflow.com/questions/925994")
    @Test
    public void testMultipleHyphensAtBeginning()
    {
        assertEquals("dataWidgetId", converter.convertAttributeName("--data-widget-id-"));
    }
}
