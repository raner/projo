//                                                                          //
// Copyright 2018 Mirko Raner                                               //
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
package pro.projo.generation.interfaces;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Generated;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;
import pro.projo.generation.ProjoTemplateFactoryGenerator;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Interfaces;
import pro.projo.template.annotation.Configuration;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.tools.Diagnostic.Kind.NOTE;

/**
* The {@link InterfaceTemplateProcessor} is an annotation processor that, at compile time, detects source files
* that have an {@link Interface @Interface} annotation and will use these sources for generating synthetic
* interfaces.
*
* @author Mirko Raner
**/
@SupportedSourceVersion(RELEASE_8)
@SupportedAnnotationTypes("pro.projo.interfaces.annotation.Interfaces")
public class InterfaceTemplateProcessor extends AbstractProcessor
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
        for (Element element: round.getElementsAnnotatedWith(Interfaces.class))
        {
            PackageElement packageElement = (PackageElement)element;
            Interfaces interfaces = packageElement.getAnnotation(Interfaces.class);
            messager.printMessage(NOTE, "@Interface annotation found in " + element);
            Name packageName = packageElement.getQualifiedName();
            Collection<? extends Configuration> configurations = getConfiguration(packageName, interfaces);
            messager.printMessage(NOTE, "Generating " + configurations.size() + " additional sources...");
            for (Configuration configuration: configurations)
            {
                String className = configuration.fullyQualifiedClassName();
                TypeElement typeElement = elements.getTypeElement(className);
                try
                {
                    String templateClassName = $package.$InterfaceTemplate.class.getName();
                    JavaFileObject sourceFile = filer.createSourceFile(className, typeElement);
                    String resourceName = "/" + templateClassName.replace('.', '/')+ ".java";
                    try (PrintWriter writer = new PrintWriter(sourceFile.openWriter(), true))
                    {
                        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(resourceName)))
                        {
                            generator.generate(reader, resourceName, configuration.parameters(), writer);
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

    private Collection<? extends Configuration> getConfiguration(Name packageName, Interfaces interfaces)
    {
        final String className = getClass().getName();
        Function<VariableElement, String> toString = parameter ->
        {
            return parameter.asType() + " " + parameter.getSimpleName();
        };
        Function<ExecutableElement, String> toDeclaration = method ->
        {
            StringBuffer declaration = new StringBuffer();
            declaration.append(method.getReturnType()).append(' ');
            declaration.append(method.getSimpleName()).append(' ');
            List<? extends VariableElement> parameters = method.getParameters();
            declaration.append("(" + parameters.stream().map(toString).collect(Collectors.joining(", "))+ ")"); //TODO
            return declaration.toString();
        };
        Predicate<ExecutableElement> realMethodsOnly = method -> method.getSimpleName().charAt(0) != '<';
        Function<Interface, Configuration> getConfiguration = annotation ->
        {
            Set<TypeElement> imports = new HashSet<>();
            imports.add(elements.getTypeElement(Generated.class.getName()));
            TypeMirror originalClass = getInputType(annotation::from);
            Set<Modifier> modifiers = new HashSet<>(Arrays.asList(annotation.modifiers()));
            TypeElement typeElement = elements.getTypeElement(originalClass.toString());
            List<ExecutableElement> methods = new ArrayList<>();
            final List<? extends TypeParameterElement> typeParameters = typeElement.getTypeParameters();
            ElementScanner8<Void, List<ExecutableElement>> scanner = new ElementScanner8<Void, List<ExecutableElement>>()
            {
                @Override
                public Void visitExecutable(ExecutableElement method, List<ExecutableElement> executables)
                {
                    if (method.getModifiers().containsAll(modifiers))
                    {
                        executables.add(method);
                    }
                    return super.visitExecutable(method, executables);
                }
            };
            typeElement.accept(scanner, methods);
            Stream<String> declarations = methods.stream().filter(realMethodsOnly).map(toDeclaration);
            return new Configuration()
            {
                @Override
                public Map<String, Object> parameters()
                {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("package", packageName);
                    parameters.put("imports", imports);
                    // TODO: imports.stream().map(Class::getName).toArray());
                    parameters.put("InterfaceTemplate", interfaceSignature());
                    parameters.put("javadoc", "This interface was extracted from " + originalClass + ".");
                    parameters.put("generatedBy", className);
                    parameters.put("methods", declarations.toArray());
                    return parameters;
                }
                
                @Override
                public String fullyQualifiedClassName()
                {
                    return packageName.toString() + '.' + annotation.generate();
                }

                private String interfaceSignature()
                {
                    String signature = annotation.generate();
                    if (!typeParameters.isEmpty())
                    {
                        signature += "<" + typeParameters.stream().map(Object::toString).collect(Collectors.joining(",")) + ">";
                    }
                    return signature;
                }
            };
        };
        return Stream.of(interfaces.value()).map(getConfiguration).collect(toList());
    }

    // TODO: reunite with getInputType(Template)
    private TypeMirror getInputType(Supplier<Class<?>> supplier)
    {
        try
        {
            supplier.get();
        }
        catch (MirroredTypeException mirroredTypeException)
        {
            return mirroredTypeException.getTypeMirror();
        }
        return null;
    }
}
