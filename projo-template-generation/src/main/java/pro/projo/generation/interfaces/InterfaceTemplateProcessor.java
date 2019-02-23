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
import java.util.Objects;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
import pro.projo.generation.utilities.DefaultNameComparator;
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
        Function<String, String> typeMap = getTypeMap(packageName, interfaces);
        Function<Interface, Configuration> getConfiguration = annotation ->
        {
            Set<String> imports = new HashSet<>();
            imports.add(Generated.class.getName());
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
            imports.addAll(classesUsedBy(methods, typeMap));
            List<String> importNames = imports.stream().map(Object::toString)
                .filter(name -> !name.startsWith(packageName + ".") && !name.startsWith("java.lang."))
                .map(pro.projo.generation.utilities.Name::new)
                .sorted(new DefaultNameComparator())
                .map(Name::toString)
                .collect(toList());
            Function<VariableElement, String> toString = parameter ->
            {
                return shortenQualifiedName(packageName, importNames).apply(typeMap.apply(parameter.asType().toString())) + " " + parameter.getSimpleName();
            };
            Function<ExecutableElement, String> toDeclaration = method ->
            {
                StringBuffer declaration = new StringBuffer();

                // Add type parameters, if any:
                List<? extends TypeParameterElement> typeParameterList = method.getTypeParameters();
                if (!typeParameterList.isEmpty())
                {
                    declaration.append("<");
                    declaration.append(typeParameterList.stream().map(TypeParameterElement::getSimpleName).collect(joining(", ")));
                    declaration.append("> ");
                }

                // Add return type:
                declaration.append(shortenQualifiedName(packageName, importNames).apply(typeMap.apply(method.getReturnType().toString()))).append(' ');

                // Add parameters:
                declaration.append(method.getSimpleName()).append('(');
                List<? extends VariableElement> parameters = method.getParameters();
                declaration.append(parameters.stream().map(toString).collect(joining(", ")));
                return declaration.append(')').toString();
            };
            Stream<String> declarations = methods.stream().filter(this::realMethodsOnly).map(toDeclaration);
            return new Configuration()
            {
                @Override
                public Map<String, Object> parameters()
                {
                    Map<String, Object> parameters = new HashMap<>();
                    parameters.put("package", packageName);
                    parameters.put("imports", importNames);
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

    boolean realMethodsOnly(ExecutableElement method)
    {
        return method.getSimpleName().charAt(0) != '<';
    }

    Collection<String> classesUsedBy(List<ExecutableElement> methods, Function<String, String> typeMap)
    {
        return methods.stream().filter(this::realMethodsOnly).flatMap(method -> classesUsedBy(method, typeMap)).collect(toList());
    }

    Stream<String> classesUsedBy(ExecutableElement method, Function<String, String> typeMap)
    {
        TypeElement returnType = elements.getTypeElement(method.getReturnType().toString());
        Stream<? extends VariableElement> parameters = method.getParameters().stream();
        Stream<TypeElement> parameterTypes = parameters.map(Element::asType).map(this::typeElement);
        return Stream.concat(Stream.of(returnType), parameterTypes).filter(Objects::nonNull)
            .map(TypeElement::getQualifiedName).map(Name::toString).map(typeMap);
        // TODO: filtering non-null (i.e., non-matching) types conveniently takes care of type
        //       parameters, but will cause problems if there is a type in scope with the same
        //       name as the type parameter (i.e., there is a type parameter V and also a type V)
    }
    
    TypeElement typeElement(TypeMirror type)
    {
        return elements.getTypeElement(type.toString());
    }

    private UnaryOperator<String> shortenQualifiedName(Name packageName, List<String> imports)
    {
        // TODO: change signature back to ..., Set<String> for efficiency reasons
        return name ->
        {
            String prefix;
            if (name.startsWith(prefix = packageName + ".")
            || (name.startsWith(prefix = "java.lang.")))
            {
                return name.substring(prefix.length());
            }
            if (imports.contains(name))
            {
              return name.substring(name.lastIndexOf('.') + 1);
            }
            return name;
        };
    }

    /**
    * To generate the source code of the Projo interface we need to produce two things:
    * - a sorted list of all classes that need to be imported
    * - a map that allows to look up the short name based on the fully qualified name
    * A couple of additional rules apply:
    * - java.lang.* classes need to be part of the lookup map but not part of the list
    * - the generated class itself is not part of the import list and always resolves to
    *   an unqualified name
    *   
    * @param packageName
    * @param interfaces
    * @return
    **/
    private Function<String, String> getTypeMap(Name packageName, Interfaces interfaces)
    {
        Function<Interface, String> keyMapper = annotation -> getTypeMirror(annotation::from).toString();
        Function<Interface, String> valueMapper = annotation ->
        {
            return packageName + "." + getMap(annotation).getOrDefault(getTypeMirror(annotation::from), annotation.generate());
            // If the Interface contains a Map that overrides its own type source, use the Map's target type instead
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
