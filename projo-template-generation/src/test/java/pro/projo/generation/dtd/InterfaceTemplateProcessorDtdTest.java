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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import org.junit.Test;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.Name;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import pro.projo.interfaces.annotation.utilities.DefaultAttributeNameConverter;
import pro.projo.template.annotation.Configuration;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;
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
        TypeMirror object = mock(TypeMirror.class);
        Elements elements = mock(Elements.class);
        Filer filer = mock(Filer.class);
        FileObject dtdFile = mock(FileObject.class);
        URI dtdUri = getClass().getResource(dtdPath).toURI();
        PackageElement packageElement = mock(PackageElement.class);
        when(packageElement.getQualifiedName()).thenReturn(new Name("html.api"));
        when(dtdFile.toUri()).thenReturn(dtdUri);
        when(dtdFile.openInputStream()).thenReturn(getClass().getResourceAsStream(dtdPath));
        when(filer.getResource(any(), any(), any())).thenReturn(dtdFile);
        when(object.toString()).thenReturn(Object.class.getName());
        when(elements.getTypeElement(any())).thenAnswer(call -> typeElement(call.getArgument(0, String.class)));
        when(environment.getFiler()).thenReturn(filer);
        when(environment.getElementUtils()).thenReturn(elements);
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

            @Override
            public Class<?> baseInterface()
            {
                throw new MirroredTypeException(object);
            }

            @Override
            public Class<?> baseInterfaceEmpty()
            {
                throw new MirroredTypeException(object);
            }

            @Override
            public Class<?> baseInterfaceText()
            {
                throw new MirroredTypeException(object);
            }

            @Override
            public String elementNameFormat()
            {
                return "{0}";
            }

            @Override
            public String contentNameFormat()
            {
                return "{0}Content";
            }

            @Override
            public Class<? extends AttributeNameConverter> attributeNameConverter()
            {
                return DefaultAttributeNameConverter.class;
            }
        };
        Collection<? extends Configuration> configurations = processor.getDtdConfiguration(packageElement, singletonList(dtd));
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
            "MeterContent", "FieldsetContent", "LegendContent", "NoscriptContent", "TemplateContent"
        };
        Set<String> expectedButNotFound = new HashSet<>(Arrays.asList(expected));
        expectedButNotFound.removeAll(types);
        Set<String> foundButNotExpected = types;
        foundButNotExpected.removeAll(Arrays.asList(expected));
        String message = "Expected but not found: " + expectedButNotFound +
            ", found but not expected: " + foundButNotExpected;
        assertTrue(message, expectedButNotFound.isEmpty() && foundButNotExpected.isEmpty());
    }

    private TypeElement typeElement(String fullyQualifiedName)
    {
        try
        {
            Class<?> type = Class.forName(fullyQualifiedName);
            return ReflectionTypes.getInstance().typeElement(type);
        }
        catch (ClassNotFoundException exception)
        {
            throw new NoClassDefFoundError(exception.getMessage());
        }
    }
}
