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
package pro.projo.generation.dtd;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.xml.sax.InputSource;
import com.sun.xml.dtdparser.DTDParser;
import pro.projo.generation.dtd.model.ContentModel;
import pro.projo.generation.dtd.model.Dtd;
import pro.projo.generation.dtd.model.DtdElement;
import pro.projo.generation.dtd.model.ModelGroup;
import pro.projo.generation.dtd.model.ModelGroupType;
import pro.projo.generation.dtd.model.Occurrence;
import static org.junit.Assert.assertEquals;

public class DtdBuilderTest
{
    @Test
    public void testSimpleTopLevelElements() throws Exception
    {
        Dtd dtd = parse("/DTDs/HtmlWithHeadAndBody.dtd");
        List<String> elements = dtd.children().stream().map(DtdElement::name).collect(Collectors.toList());
        assertEquals(Arrays.asList("html", "head", "body"), elements);
    }

    @Test
    public void testSimpleSequence() throws Exception
    {
        Dtd dtd = parse("/DTDs/HtmlWithHeadAndBody.dtd");
        ContentModel first = (ContentModel)dtd.children().iterator().next();
        ModelGroup html = (ModelGroup)first.children().iterator().next();
        assertEquals(Occurrence.ONCE, html.occurrence());
        assertEquals(ModelGroupType.SEQUENCE, html.type());
    }

    @Test
    public void testSimpleChoice() throws Exception
    {
        Dtd dtd = parse("/DTDs/HtmlWithHeadOrBody.dtd");
        ContentModel first = (ContentModel)dtd.children().iterator().next();
        ModelGroup html = (ModelGroup)first.children().iterator().next();
        assertEquals(Occurrence.ONCE, html.occurrence());
        assertEquals(ModelGroupType.CHOICE, html.type());
    }

    private Dtd parse(String dtdPath) throws Exception
    {
        InputSource source = open(getClass().getResource(dtdPath));
        DTDParser parser = new DTDParser();
        DtdModelBuilder builder = new DtdModelBuilder();
        parser.setDtdHandler(builder);
        parser.parse(source);
        return builder.getDtd();
    }

    private InputSource open(URL url)
    {
        try
        {
            InputSource source = new InputSource(url.openStream())
            {
              @Override
              public String toString()
              {
                  String path = url.getPath();
                  return path.substring(path.lastIndexOf('/', path.lastIndexOf("/")-1)+1);
              }
            };
            source.setSystemId(url.toString());
            return source;
        }
        catch (IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }
}
