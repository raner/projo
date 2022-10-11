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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InterfaceTemplateProcessorDtdTest extends DtdTestBase
{
    static interface EmptyElement<PARENT> {}

    static interface MixedContent<PARENT> {}

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

    @Test
    public void testAttributesWhenNoMandatoryAttributesAreBoundYet() throws Exception
    {
        // Expected: there should be a method for each attribute, the return type indicating
        //           the presence of just that attribute
        //
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        Configuration configuration = configurations.stream()
            .filter(it -> it.parameters().get("InterfaceTemplate").equals("Element<PARENT>"))
            .findFirst()
            .get();
        Set<String> methods = new HashSet<>(Arrays.asList((String[])configuration.parameters().get("methods")));
        assertTrue(methods.contains("ElementRequired1<PARENT> required1(String required1)"));
        assertTrue(methods.contains("ElementRequired2<PARENT> required2(String required2)"));
        assertTrue(methods.contains("ElementRequired3<PARENT> required3(String required3)"));
    }

    @Test
    public void testAttributesWhenOnlyFirstMandatoryAttributeIsBound() throws Exception
    {
        // Expected: there should be two methods; one for each combination of the bound attribute
        //           with one of the unbound attributes
        //
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        Configuration configuration = configurations.stream()
            .filter(it -> it.parameters().get("InterfaceTemplate").equals("ElementRequired1<PARENT>"))
            .findFirst()
            .get();
        Set<String> methods = new HashSet<>(Arrays.asList((String[])configuration.parameters().get("methods")));
        assertTrue(methods.contains("ElementRequired1Required2<PARENT> required2(String required2)"));
        assertTrue(methods.contains("ElementRequired1Required3<PARENT> required3(String required3)"));
    }

    @Test
    public void testAttributesWhenOnlySecondMandatoryAttributeIsBound() throws Exception
    {
        // Expected: there should be two methods; one for each combination of the bound attribute
        //           with one of the unbound attributes
        //
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        Configuration configuration = configurations.stream()
            .filter(it -> it.parameters().get("InterfaceTemplate").equals("ElementRequired2<PARENT>"))
            .findFirst()
            .get();
        Set<String> methods = new HashSet<>(Arrays.asList((String[])configuration.parameters().get("methods")));
        assertTrue(methods.contains("ElementRequired1Required2<PARENT> required1(String required1)"));
        assertTrue(methods.contains("ElementRequired2Required3<PARENT> required3(String required3)"));
    }

    @Test
    public void testAttributesWhenFirstAndSecondMandatoryAttributesAreBound() throws Exception
    {
        // Expected: there should be one method that provides the missing attribute
        //
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        Configuration configuration = configurations.stream()
            .filter(it -> it.parameters().get("InterfaceTemplate").equals("ElementRequired1Required2<PARENT>"))
            .findFirst()
            .get();
        Set<String> methods = new HashSet<>(Arrays.asList((String[])configuration.parameters().get("methods")));
        assertTrue(methods.contains("ElementRequired1Required2Required3<PARENT> required3(String required3)"));
    }

    @Test
    public void testAttributesWhenAllMandatoryAttributesAreBound() throws Exception
    {
        // Expected: only optional attributes are present
        //
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        Configuration configuration = configurations.stream()
            .filter(it -> it.parameters().get("InterfaceTemplate").equals("ElementRequired1Required2Required3<PARENT> extends EmptyElement<PARENT>"))
            .findFirst()
            .get();
        Set<String> methods = new HashSet<>(Arrays.asList((String[])configuration.parameters().get("methods")));
        String[] expectedMethods =
        {
            "ElementRequired1Required2Required3<PARENT> optional1(String optional1)",
            "ElementRequired1Required2Required3<PARENT> optional2(String optional2)",
        };
        Set<String> expected = new HashSet<>(Arrays.asList(expectedMethods));
        assertEquals(expected, methods);
    }

    @Test
    public void testMixedContentBaseInterface() throws Exception
    {
        String dtdPath = "/DTDs/Ashbridge/html5.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, Object.class, MixedContent.class, "{0}");
        Configuration bodyContent = configurations.stream()
            .filter(it -> ((String)it.parameters().get("InterfaceTemplate")).startsWith("BodyContent"))
            .findFirst()
            .get();
        Object type = bodyContent.parameters().get("InterfaceTemplate");
        assertEquals("BodyContent extends MixedContent<BodyContent>", type);
    }

    @Test
    public void testMixedContentChildElements() throws Exception
    {
        String dtdPath = "/DTDs/Ashbridge/html5.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, Object.class, MixedContent.class, "{0}");
        Configuration bodyContent = configurations.stream()
            .filter(it -> ((String)it.parameters().get("InterfaceTemplate")).startsWith("BodyContent"))
            .findFirst()
            .get();
        String[] methods = (String[])bodyContent.parameters().get("methods");
        String[] expected =
        {
            "Main<BodyContent> main()",
            "Header<BodyContent> header()",
            "Footer<BodyContent> footer()",
            "Form<BodyContent> form()",
            "Address<BodyContent> address()",
            "Table<BodyContent> table()",
            "Hgroup<BodyContent> hgroup()",
            "A<BodyContent> a()",
            "Abbr<BodyContent> abbr()",
            "Area<BodyContent> area()",
            "Audio<BodyContent> audio()",
            "B<BodyContent> b()",
            "Bdi<BodyContent> bdi()",
            "Bdo<BodyContent> bdo()",
            "Blockquote<BodyContent> blockquote()",
            "Br<BodyContent> br()",
            "Canvas<BodyContent> canvas()",
            "Cite<BodyContent> cite()",
            "Code<BodyContent> code()",
            "Data<BodyContent> data()",
            "Datalist<BodyContent> datalist()",
            "Del<BodyContent> del()",
            "Dfn<BodyContent> dfn()",
            "Div<BodyContent> div()",
            "Dl<BodyContent> dl()",
            "Em<BodyContent> em()",
            "Embed<BodyContent> embed()",
            "Fieldset<BodyContent> fieldset()",
            "Figure<BodyContent> figure()",
            "Hr<BodyContent> hr()",
            "I<BodyContent> i()",
            "Iframe<BodyContent> iframe()",
            "Img<BodyContent> img()",
            "Ins<BodyContent> ins()",
            "Kbd<BodyContent> kbd()",
            "Label<BodyContent> label()",
            "Map<BodyContent> map()",
            "Mark<BodyContent> mark()",
            "Math<BodyContent> math()",
            "Noscript<BodyContent> noscript()",
            "Object<BodyContent> object()",
            "Ol<BodyContent> ol()",
            "P<BodyContent> p()",
            "Pre<BodyContent> pre()",
            "Q<BodyContent> q()",
            "Ruby<BodyContent> ruby()",
            "S<BodyContent> s()",
            "Samp<BodyContent> samp()",
            "Script<BodyContent> script()",
            "Small<BodyContent> small()",
            "Span<BodyContent> span()",
            "Strong<BodyContent> strong()",
            "Sub<BodyContent> sub()",
            "Sup<BodyContent> sup()",
            "Svg<BodyContent> svg()",
            "Template<BodyContent> template()",
            "Time<BodyContent> time()",
            "U<BodyContent> u()",
            "Ul<BodyContent> ul()",
            "Var<BodyContent> var()",
            "Video<BodyContent> video()",
            "Wbr<BodyContent> wbr()",
            "H1<BodyContent> h1()",
            "H2<BodyContent> h2()",
            "H3<BodyContent> h3()",
            "H4<BodyContent> h4()",
            "H5<BodyContent> h5()",
            "H6<BodyContent> h6()",
            "Article<BodyContent> article()",
            "Aside<BodyContent> aside()",
            "Nav<BodyContent> nav()",
            "Section<BodyContent> section()",
            "Button<BodyContent> button()",
            "Input<BodyContent> input()",
            "Keygen<BodyContent> keygen()",
            "Meter<BodyContent> meter()",
            "Output<BodyContent> output()",
            "Progress<BodyContent> progress()",
            "Select<BodyContent> select()",
            "Textarea<BodyContent> textarea()"
        };
        assertArrayEquals(expected, methods);
    }
}
