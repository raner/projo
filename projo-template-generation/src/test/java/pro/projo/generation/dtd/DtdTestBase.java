//                                                                          //
// Copyright 2022 - 2023 Mirko Raner                                        //
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
import java.util.Collection;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.AbstractTypeConverterTest;
import pro.projo.generation.utilities.Name;
import pro.projo.interfaces.annotation.Alias;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import pro.projo.interfaces.annotation.utilities.DefaultAttributeNameConverter;
import pro.projo.template.annotation.Configuration;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
* {@link DtdTestBase} serves as a base class for some of Projo's DTD-related unit tests.
* It mainly provides utility methods for accessing test DTDs.
*
* @author Mirko Raner
**/
public class DtdTestBase extends AbstractTypeConverterTest
{
    protected Collection<? extends Configuration> getConfigurations(String dtdPath, Class<?> emptyBase) throws Exception
    {
        return getConfigurations(dtdPath, emptyBase, "{0}");
    }

    protected Collection<? extends Configuration> getConfigurations(String dtdPath, Class<?> emptyBase, String elementNameFormat) throws Exception
    {
        return getConfigurations(dtdPath, emptyBase, Object.class, elementNameFormat);
    }

    protected Collection<? extends Configuration> getConfigurations(String dtdPath, Class<?> emptyBase, Class<?> mixedContent, String elementNameFormat) throws Exception
    {
        InterfaceTemplateProcessor processor = new InterfaceTemplateProcessor();
        ProcessingEnvironment environment = mock(ProcessingEnvironment.class);
        TypeMirror object = mock(TypeMirror.class);
        TypeMirror objectEmpty = mock(TypeMirror.class);
        TypeMirror mixedContentType = mock(TypeMirror.class);
        Messager messager = mock(Messager.class);
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
        when(objectEmpty.toString()).thenReturn(emptyBase.getName());
        when(mixedContentType.toString()).thenReturn(mixedContent.getName());
        when(elements.getTypeElement(any())).thenAnswer(call -> typeElement(call.getArgument(0, String.class)));
        when(environment.getFiler()).thenReturn(filer);
        when(environment.getMessager()).thenReturn(messager);
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
                return dtdPath.substring(1);
            }

            @Override
            public Class<?> baseInterface()
            {
                throw new MirroredTypeException(object);
            }

            @Override
            public Class<?> baseInterfaceEmpty()
            {
                throw new MirroredTypeException(objectEmpty);
            }

            @Override
            public Class<?> baseInterfaceText()
            {
                throw new MirroredTypeException(object);
            }

            @Override
            public Class<?> mixedContentInterface()
            {
                throw new MirroredTypeException(mixedContentType);
            }

            @Override
            public String elementNameFormat()
            {
                return elementNameFormat;
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
            
            @Override
            public Options options()
            {
                return DtdTestBase.this.createOptions(DtdTestBase.this.unmapped(true, true));
            }
            
            @Override
            public Alias[] aliases()
            {
                return new Alias[] {};
            }
        };
        return processor.getDtdConfiguration(packageElement, singletonList(dtd));
    }

    protected TypeElement typeElement(String fullyQualifiedName)
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
