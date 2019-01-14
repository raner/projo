//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Generated;
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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import javax.tools.JavaFileObject;
import pro.projo.generation.ProjoProcessor;
import pro.projo.generation.ProjoTemplateFactoryGenerator;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Interfaces;
import pro.projo.template.annotation.Configuration;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
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
public class InterfaceTemplateProcessor extends ProjoProcessor
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
                    String resourceName = "/" + templateClassName.replace('.', '/') + ".java";
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

    /**
    * Generates code generation configurations for declared {@link Interface @Interface} annotations.
    *
    * @param packageName the package name in which interfaces will be generated
    * @param interfaces the set of {@link Interface @Interface} annotations containing the configuration data
    * @return a collection of code generation configurations, one for each generated interface
    **/
    private Collection<? extends Configuration> getConfiguration(Name packageName, Interfaces interfaces)
    {
        final String className = getClass().getName();
        UnaryOperator<String> shorten = shortenQualifiedName(packageName);
        Function<String, String> typeMap = getTypeMap(packageName, interfaces);
        Function<VariableElement, String> toString = parameter ->
        {
            return shorten.apply(typeMap.apply(parameter.asType().toString())) + " " + parameter.getSimpleName();
        };
        Function<ExecutableElement, String> toDeclaration = method ->
        {
            StringBuffer declaration = new StringBuffer();

            // Add type parameters, if any:
            List<? extends TypeParameterElement> typeParameters = method.getTypeParameters();
            if (!typeParameters.isEmpty())
            {
                declaration.append("<");
                declaration.append(typeParameters.stream().map(TypeParameterElement::getSimpleName).collect(joining(", ")));
                declaration.append("> ");
            }

            // Add return type:
            declaration.append(shorten.apply(typeMap.apply(method.getReturnType().toString()))).append(' ');

            // Add parameters:
            declaration.append(method.getSimpleName()).append('(');
            List<? extends VariableElement> parameters = method.getParameters();
            declaration.append(parameters.stream().map(toString).collect(joining(", ")));
            return declaration.append(')').toString();
        };
        Predicate<ExecutableElement> realMethodsOnly = method -> method.getSimpleName().charAt(0) != '<';
        Function<Interface, Configuration> getConfiguration = annotation ->
        {
            Set<TypeElement> imports = new HashSet<>();
            imports.add(elements.getTypeElement(Generated.class.getName()));
            TypeMirror originalClass = getTypeMirror(annotation::from);
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

    private UnaryOperator<String> shortenQualifiedName(Name packageName)
    {
        return name ->
        {
            String prefix;
            if (name.startsWith(prefix = packageName + ".")
            || (name.startsWith(prefix = "java.lang.")))
            {
                return name.substring(prefix.length());
            }
            return name;
        };
    }

    private Function<String, String> getTypeMap(Name packageName, Interfaces interfaces)
    {
        Function<Interface, String> keyMapper = annotation -> getTypeMirror(annotation::from).toString();
        Function<Interface, String> valueMapper = annotation ->
        {
            return packageName + "." + getMap(annotation).getOrDefault(getTypeMirror(annotation::from), annotation.generate());
            // If the Interface contains a Map that overrides its own type source, use the Map's target type instead:
        };
        BinaryOperator<String> merger = (oldValue, newValue) ->
        {
            if (newValue.equals(oldValue))
            {
                return oldValue;
            }
            throw new IllegalStateException(String.format("Value mismatch: %s/%s", oldValue, newValue));
        };
        Map<String, String> map = Stream.of(interfaces.value()).collect(toMap(keyMapper, valueMapper, merger));
        return key ->
        {
            int typeParameterIndex = key.indexOf('<');
            typeParameterIndex = typeParameterIndex == -1? key.length():typeParameterIndex;
            String rootType = key.substring(0, typeParameterIndex);
            String typeParameter = key.substring(typeParameterIndex);
            return map.getOrDefault(rootType, rootType) + typeParameter;
        };
    }

    private Map<TypeMirror, String> getMap(Interface map)
    {
        return Stream.of(map.map()).collect(toMap(annotation -> getTypeMirror(annotation::type), pro.projo.interfaces.annotation.Map::to));
    }
}
