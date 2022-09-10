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

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import javax.tools.FileObject;
import org.junit.Test;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.Name;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.template.annotation.Configuration;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InterfaceTemplateProcessorDtdTest
{
    @Test
    public void testGeneratedClasses() throws Exception
    {
        String dtdPath = "/DTDs/Ashbridge/html5.dtd";
        InterfaceTemplateProcessor processor = new InterfaceTemplateProcessor();
        ProcessingEnvironment environment = mock(ProcessingEnvironment.class);
        Filer filer = mock(Filer.class);
        FileObject dtdFile = mock(FileObject.class);
        URI dtdUri = getClass().getResource(dtdPath).toURI();
        PackageElement packageElement = mock(PackageElement.class);
        when(packageElement.getQualifiedName()).thenReturn(new Name("html.api"));
        when(dtdFile.toUri()).thenReturn(dtdUri);
        when(dtdFile.openInputStream()).thenReturn(getClass().getResourceAsStream(dtdPath));
        when(filer.getResource(any(), any(), any())).thenReturn(dtdFile);
        when(environment.getFiler()).thenReturn(filer);
        processor.init(environment);
        Dtd dtd = new Dtd()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Dtd.class;
            }

            @Override
            public String path()
            {
                return "DTDs/Ashbridge/html5.dtd";
            }
        };
        Collection<? extends Configuration> configurations = processor.getDtdConfiguration(packageElement, singletonList(dtd));
        Set<String> types = configurations.stream().map(it -> (String)it.parameters().get("InterfaceTemplate")).collect(toSet());
        String[] expected =
        {
            "Html", "Head", "Title", "Base", "Link", "Meta", "Style", "Body", "Article", "Section",
            "Nav", "Aside", "H1", "H2", "H3", "H4", "H5", "H6", "Hgroup", "Header", "Footer",
            "Address", "P", "Hr", "Pre", "Blockquote", "Ol", "Ul", "Li", "Dl", "Dt", "Dd", "Figure",
            "Figcaption", "Div", "Main", "A", "Em", "Strong", "Small", "S", "Cite", "Q", "Dfn", "Abbr",
            "Data", "Time", "Code", "Var", "Samp", "Kbd", "Sub", "Sup", "I", "B", "U", "Mark", "Ruby",
            "Rb", "Rt", "Rtc", "Rp", "Bdi", "Bdo", "Span", "Br", "Wbr", "Ins", "Del", "Picture", "Img",
            "Iframe", "Embed", "Object", "Param", "Video", "Audio", "Source", "Track", "Map", "Area",
            "Math", "Svg", "Table", "Caption", "Colgroup", "Col", "Tbody", "Thead", "Tfoot", "Tr",
            "Td", "Th", "Form", "Label", "Input", "Button", "Select", "Datalist", "Optgroup", "Option",
            "Textarea", "Keygen", "Output", "Progress", "Meter", "Fieldset", "Legend", "Script",
            "Noscript", "Template", "Canvas"
        };
        assertEquals(new HashSet<>(Arrays.asList(expected)), types);
    }
}