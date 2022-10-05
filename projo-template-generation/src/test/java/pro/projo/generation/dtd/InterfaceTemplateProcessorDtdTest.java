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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import pro.projo.template.annotation.Configuration;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;

public class InterfaceTemplateProcessorDtdTest extends DtdTestBase
{
    static interface EmptyElement<PARENT> {}

    @Test
    public void testGeneratedClasses() throws Exception
    {
        String dtdPath = "/DTDs/Ashbridge/html5.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, Object.class);
        Set<String> types = configurations.stream().map(it -> (String)it.parameters().get("InterfaceTemplate"))
            .map(it -> {int index = it.indexOf("<"); return index == -1? it:it.substring(0, index);})
            .collect(toSet());
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
            "Noscript", "Template", "Canvas",
            "HtmlContent", "HtmlContent0 extends HtmlContent", "HtmlContent1 extends HtmlContent",
            "HeadContent", "BodyContent", "ArticleContent", "SectionContent",
            "NavContent", "AsideContent", "H1Content", "H2Content", "H3Content", "H4Content",
            "H5Content", "H6Content", "HgroupContent", "HeaderContent", "FooterContent",
            "AddressContent", "PContent", "PreContent", "BlockquoteContent", "OlContent", "UlContent",
            "LiContent", "DlContent", "DtContent", "DdContent", "FigureContent", "FigcaptionContent",
            "DivContent", "MainContent", "AContent", "EmContent", "StrongContent", "SmallContent",
            "SContent", "CiteContent", "QContent", "DfnContent", "AbbrContent", "DataContent",
            "TimeContent", "CodeContent", "VarContent", "SampContent", "KbdContent", "SubContent",
            "SupContent", "IContent", "BContent", "UContent", "MarkContent", "RubyContent",
            "RbContent", "RtContent", "RtcContent", "RpContent", "BdiContent", "BdoContent",
            "SpanContent", "InsContent", "DelContent", "PictureContent", "IframeContent",
            "ObjectContent", "VideoContent", "AudioContent", "MapContent", "TableContent",
            "CaptionContent", "ColgroupContent", "TbodyContent", "TheadContent", "TfootContent",
            "TrContent", "TdContent", "ThContent", "FormContent", "LabelContent", "ButtonContent",
            "SelectContent", "DatalistContent", "OptgroupContent", "OutputContent", "ProgressContent",
            "MeterContent", "FieldsetContent", "LegendContent", "NoscriptContent", "TemplateContent",
            "BaseHref",
            "ImgAlt",
            "ImgSrc",
            "ImgAltSrc",
            "AreaAlt",
            "StyleType",
            "OptgroupLabel"
        };
        Set<String> expectedButNotFound = new HashSet<>(Arrays.asList(expected));
        expectedButNotFound.removeAll(types);
        Set<String> foundButNotExpected = types;
        foundButNotExpected.removeAll(Arrays.asList(expected));
        String message = "Expected but not found: " + expectedButNotFound +
            ", found but not expected: " + foundButNotExpected;
        assertTrue(message, expectedButNotFound.isEmpty() && foundButNotExpected.isEmpty());
    }

    @Test
    public void testGeneratedClassesForRequiredAttributes() throws Exception
    {
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        Set<String> types = configurations.stream().map(it -> (String)it.parameters().get("InterfaceTemplate"))
            .collect(toSet());
        String[] expected =
        {
            "Element<PARENT>",
            "ElementRequired1<PARENT>",
            "ElementRequired2<PARENT>",
            "ElementRequired3<PARENT>",
            "ElementRequired1Required2<PARENT>",
            "ElementRequired1Required3<PARENT>",
            "ElementRequired2Required3<PARENT>",
            "ElementRequired1Required2Required3<PARENT> extends EmptyElement<PARENT>"
        };
        Set<String> expectedButNotFound = new HashSet<>(Arrays.asList(expected));
        expectedButNotFound.removeAll(types);
        Set<String> foundButNotExpected = types;
        foundButNotExpected.removeAll(Arrays.asList(expected));
        String message = "Expected but not found: " + expectedButNotFound +
            ", found but not expected: " + foundButNotExpected;
        assertTrue(message, expectedButNotFound.isEmpty() && foundButNotExpected.isEmpty());
    }
}
