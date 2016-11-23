//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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
package pro.projo.generation;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import pro.projo.template.annotation.Configuration;
import pro.projo.template.annotation.Template;
import static java.io.File.separatorChar;
import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.tools.Diagnostic.Kind.NOTE;
import static javax.tools.StandardLocation.SOURCE_PATH;

/**
* The {@link ProjoTemplateFactoryProcessor} is an annotation processor that, at compile time, detects source files that
* have an {@link Template @Template} annotation and will use these sources for generating additional source files according
* to the template's {@link Template#input() input} {@link Configuration}.
*
* @author Mirko Raner
**/
@SupportedSourceVersion(RELEASE_8)
@SupportedAnnotationTypes("pro.projo.template.annotation.Template")
public class ProjoTemplateFactoryProcessor extends AbstractProcessor
{
    private Filer filer;
    private Elements elements;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment environment)
    {
        super.init(environment);
        filer = environment.getFiler();
        elements = environment.getElementUtils();
        messager = environment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round)
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        for (Element element: round.getElementsAnnotatedWith(Template.class))
        {
            Template template = element.getAnnotation(Template.class);
            messager.printMessage(NOTE, "@Template annotation found in " + element);
            FileObject templateFile = getTemplateFile(element);
            Collection<? extends Configuration> configurations = getConfiguration(getInputType(template));
            messager.printMessage(NOTE, "Generating " + configurations.size() + " additional sources...");
            for (Configuration configuration: configurations)
            {
                String className = configuration.fullyQualifiedClassName();
                TypeElement typeElement = elements.getTypeElement(className);
                try
                {
                    JavaFileObject sourceFile = filer.createSourceFile(className, typeElement);
                    try (PrintWriter writer = new PrintWriter(sourceFile.openWriter(), true))
                    {
                        try (Reader reader = templateFile.openReader(true))
                        {
                            generator.generate(reader, templateFile.getName(), configuration.parameters(), writer);
                        }
                    }
                    messager.printMessage(NOTE, "Generated " + className);
                }
                catch (IOException ioException)
                {
                    // Carry on for now; throwing an exception here would bring the compiler to a halt...
                    //
                    ioException.printStackTrace();
                }
            }
        }
        return true;
    }

    private FileObject getTemplateFile(Element element)
    {
        String simpleName = element.getSimpleName().toString();
        String packageName = elements.getPackageOf(element).getQualifiedName().toString();
        String sourceName = packageName.replace('.', separatorChar) + separatorChar + simpleName + ".java";
        try
        {
            return filer.getResource(SOURCE_PATH, "", sourceName);
        }
        catch (@SuppressWarnings("unused") IOException ioException)
        {
            return null;
        }
    }

    private Collection<? extends Configuration> getConfiguration(TypeMirror type)
    {
        try
        {
            Collection<? extends Configuration> configuration = getClass(type).newInstance();
            return configuration;
        }
        catch (@SuppressWarnings("unused") InstantiationException instantiationException)
        {
            return null;
        }
        catch (@SuppressWarnings("unused") IllegalAccessException illegalAccessException)
        {
            return null;
        }
    }

    private Class<? extends Collection<? extends Configuration>> getClass(TypeMirror type)
    {
        try
        {
            @SuppressWarnings("unchecked")
            Class<? extends Collection<? extends Configuration>> configurationClass = (Class<? extends Collection<? extends Configuration>>)Class.forName(type.toString());
            return configurationClass;
        }
        catch (@SuppressWarnings("unused") ClassNotFoundException classNotFound)
        {
            return null;
        }
    }

    private TypeMirror getInputType(Template template)
    {
        try
        {
            template.input();
        }
        catch (MirroredTypeException mirroredTypeException)
        {
            return mirroredTypeException.getTypeMirror();
        }
        return null;
    }
}
