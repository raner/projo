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
import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Generated;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
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
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;
import pro.projo.generation.ProjoProcessor;
import pro.projo.generation.ProjoTemplateFactoryGenerator;
import pro.projo.generation.utilities.DefaultNameComparator;
import pro.projo.generation.utilities.PackageShortener;
import pro.projo.generation.utilities.Source;
import pro.projo.generation.utilities.Source.EnumSource;
import pro.projo.generation.utilities.Source.InterfaceSource;
import pro.projo.generation.utilities.TypeConverter;
import pro.projo.interfaces.annotation.Enum;
import pro.projo.interfaces.annotation.Enums;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.template.annotation.Configuration;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.lang.model.type.TypeKind.NONE;
import static javax.tools.Diagnostic.Kind.ERROR;
import static javax.tools.Diagnostic.Kind.NOTE;
import static pro.projo.generation.interfaces.InterfaceTemplateProcessor.Enum;
import static pro.projo.generation.interfaces.InterfaceTemplateProcessor.Enums;
import static pro.projo.generation.interfaces.InterfaceTemplateProcessor.Interface;
import static pro.projo.generation.interfaces.InterfaceTemplateProcessor.Interfaces;

/**
* The {@link InterfaceTemplateProcessor} is an annotation processor that, at compile time, detects source files
* that have an {@link Interface @Interface}/{@link Enum @Enum} annotation and will use these sources for
* generating synthetic interfaces/enums.
*
* @author Mirko Raner
**/
@SupportedSourceVersion(RELEASE_8)
@SupportedAnnotationTypes({Enum, Enums, Interface, Interfaces})
public class InterfaceTemplateProcessor extends ProjoProcessor
{
    final static String Enum = "pro.projo.interfaces.annotation.Enum";
    final static String Enums = "pro.projo.interfaces.annotation.Enums";
    final static String Interface = "pro.projo.interfaces.annotation.Interface";
    final static String Interfaces = "pro.projo.interfaces.annotation.Interfaces";

    private Filer filer;
    private Types types;
    private Elements elements;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment environment)
    {
        super.init(environment);
        filer = environment.getFiler();
        types = environment.getTypeUtils();
        elements = environment.getElementUtils();
        messager = environment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment round)
    {
        messager.printMessage(NOTE, "Processing interfaces...");
        process(round, this::getInterfaceConfiguration, Interface.class, $package.$InterfaceTemplate.class);
        messager.printMessage(NOTE, "Processing enums...");
        process(round, this::getEnumConfiguration, Enum.class, $package.$EnumTemplate.class);
        messager.printMessage(NOTE, "Done processing...");
        return true;
    }

    private <_Annotation_ extends Annotation> void process(RoundEnvironment round,
        BiFunction<PackageElement, List<_Annotation_>, Collection<? extends Configuration>> configurationFactory,
        Class<_Annotation_> singleAnnotation,
        Class<?> templateClass)
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        Class<? extends Annotation> multiAnnotation = singleAnnotation.getAnnotation(Repeatable.class).value();
        Set<Element> annotatedElements = new HashSet<>();
        annotatedElements.addAll(round.getElementsAnnotatedWith(singleAnnotation));
        annotatedElements.addAll(round.getElementsAnnotatedWith(multiAnnotation));
        for (Element element: annotatedElements)
        {
            PackageElement packageElement = (PackageElement)element;
            List<_Annotation_> annotations = getAnnotations(packageElement, singleAnnotation, multiAnnotation);
            Collection<? extends Configuration> configurations = configurationFactory.apply(packageElement, annotations);
            messager.printMessage(NOTE, "Generating " + configurations.size() + " additional sources...");
            for (Configuration configuration: configurations)
            {
                String className = configuration.fullyQualifiedClassName();
                TypeElement typeElement = elements.getTypeElement(className);
                try
                {
                    String templateClassName = templateClass.getName();
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
                    messager.printMessage(ERROR, ioException.getClass().getName() + ": " + ioException.getMessage());
                }
            }
        }
    }

    private <_Annotation_ extends Annotation> List<_Annotation_> getAnnotations(Element packageElement,
        Class<_Annotation_> single, Class<? extends Annotation> repeated)
    {
        List<_Annotation_> annotations = new ArrayList<>();
        Annotation multiples = packageElement.getAnnotation(repeated);
        if (multiples != null)
        {
            try
            {
                Method value = multiples.getClass().getMethod("value");
                @SuppressWarnings("unchecked")
                _Annotation_[] repeatedAnnotations = (_Annotation_[])value.invoke(multiples);
                annotations = Arrays.asList(repeatedAnnotations);
            }
            catch (Exception exception)
            {
                messager.printMessage(ERROR, exception.getClass().getName() + ": " + exception.getMessage());
            }
        }
        Optional.ofNullable(packageElement.getAnnotation(single)).ifPresent(annotations::add);
        return annotations;
    }

    /**
    * Generates code generation configurations for declared {@link Interface @Interface} annotations.
    *
    * @param packageName the package name in which interfaces will be generated
    * @param interfaces the set of {@link Interface @Interface} annotations containing the configuration data
    * @return a collection of code generation configurations, one for each generated interface
    **/
    private Collection<? extends Configuration> getInterfaceConfiguration(PackageElement element, List<Interface> interfaces)
    {
        String object = Object.class.getName();
        Name packageName = element.getQualifiedName();
        Function<Interface, Configuration> getConfiguration = annotation ->
        {
            Set<String> imports = new HashSet<>();
            imports.add(Generated.class.getName());
            TypeMirror originalClass = getTypeMirror(annotation::from);
            Set<Modifier> modifiers = new HashSet<>(Arrays.asList(annotation.modifiers()));
            TypeElement type = elements.getTypeElement(originalClass.toString());
            List<ExecutableElement> methods = new ArrayList<>();
            List<? extends TypeParameterElement> typeParameters;
            typeParameters = modifiers.contains(STATIC)? emptyList():type.getTypeParameters();
            ElementScanner8<Void, List<ExecutableElement>> scanner = new ElementScanner8<Void, List<ExecutableElement>>()
            {
                @Override
                public Void visitExecutable(ExecutableElement method, List<ExecutableElement> executables)
                {
                    if (type.equals(method.getEnclosingElement())
                    && (method.getModifiers().containsAll(modifiers)))
                    {
                        executables.add(method);
                    }
                    return super.visitExecutable(method, executables);
                }
            };
            type.accept(scanner, methods);
            PackageShortener shortener = new PackageShortener();

            // Include both interfaces and enums in the TypeConverter, so that references to enums from
            // within interfaces are handled properly:
            //
            Stream<Source> enums = getAnnotations(element, Enum.class, Enums.class).stream().map(EnumSource::new);
            Stream<Source> sources = Stream.concat(interfaces.stream().map(InterfaceSource::new), enums);
            TypeConverter typeConverter = new TypeConverter(types, shortener, packageName, sources);
            Predicate<TypeMirror> validSuperclass = base -> base.getKind() != NONE && !base.toString().equals(object);
            TypeMirror[] superclass = Stream.of(type.getSuperclass()).filter(validSuperclass).toArray(TypeMirror[]::new);
            Stream<String> supertypes = concat(type.getInterfaces(), superclass).map(typeConverter::convert);
            Function<ExecutableElement, String> toDeclaration = convertToDeclaration(typeConverter);
            String[] declarations = methods.stream().filter(this::realMethodsOnly).map(toDeclaration).toArray(String[]::new);
            imports.addAll(typeConverter.getImports());
            List<String> importNames = imports.stream().map(Object::toString)
                .filter(name -> !name.startsWith(packageName + ".") && !name.startsWith("java.lang."))
                .map(pro.projo.generation.utilities.Name::new)
                .sorted(new DefaultNameComparator())
                .map(Name::toString)
                .collect(toList());
            return new Configuration()
            {
                @Override
                public Map<String, Object> parameters()
                {
                    Map<String, Object> parameters = getParameters(packageName, importNames);
                    parameters.put("javadoc", "This interface was extracted from " + originalClass + ".");
                    parameters.put("InterfaceTemplate", interfaceSignature());
                    parameters.put("methods", declarations);
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
                        signature += "<" + typeParameters.stream().map(Object::toString).collect(joining(", ")) + ">";
                    }
                    String superinterfaces = supertypes.collect(joining(", "));
                    if (!superinterfaces.isEmpty() && !modifiers.contains(STATIC))
                    {
                        signature += " extends " + superinterfaces;
                    }
                    return signature;
                }
            };
        };
        return interfaces.stream().map(getConfiguration).collect(toList());
    }

    @SafeVarargs
    private final <_Type_> Stream<_Type_> concat(Collection<? extends _Type_> initial, _Type_... additional)
    {
        return Stream.concat(initial.stream(), Stream.of(additional));
    }

    private Collection<? extends Configuration> getEnumConfiguration(PackageElement packageElement, List<Enum> enums)
    {
        Name packageName = packageElement.getQualifiedName();
        Function<pro.projo.interfaces.annotation.Enum, Configuration> getConfiguration = annotation ->
        {
            TypeMirror originalClass = getTypeMirror(annotation::from);
            TypeElement typeElement = elements.getTypeElement(originalClass.toString());
            List<Name> values = new ArrayList<>();
            ElementScanner8<Void, List<Name>> scanner = new ElementScanner8<Void, List<Name>>()
            {
                @Override
                public Void scan(Element element, List<Name> constants)
                {
                    if (element.getKind() == ElementKind.ENUM_CONSTANT
                    && (typeElement.equals(element.getEnclosingElement())))
                    {
                        constants.add(element.getSimpleName());
                    }
                    return super.scan(element, constants);
                }
            };
            typeElement.accept(scanner, values);
            return new Configuration()
            {
                @Override
                public Map<String, Object> parameters()
                {
                    Map<String, Object> parameters = getParameters(packageName, singleton(Generated.class.getName()));
                    parameters.put("javadoc", "This enum was extracted from " + originalClass + ".");
                    parameters.put("EnumTemplate", annotation.generate());
                    parameters.put("values", values);
                    return parameters;
                }
                
                @Override
                public String fullyQualifiedClassName()
                {
                    return packageName.toString() + '.' + annotation.generate();
                }
            };
        };
        return enums.stream().map(getConfiguration).collect(toList());
    }

    Map<String, Object> getParameters(Name packageName, Collection<String> imports)
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("package", packageName);
        parameters.put("imports", imports);
        parameters.put("generatedBy", getClass().getName());
        return parameters;
    }

    Function<ExecutableElement, String> convertToDeclaration(TypeConverter typeMap)
    {
        return method ->
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
            String returnType = typeMap.convert(method.getReturnType());
            declaration.append(returnType).append(' ');

            // Add parameters:
            declaration.append(method.getSimpleName()).append('(');
            Stream<? extends VariableElement> parameters = method.getParameters().stream();
            Stream<String> convertedParameters = parameters.map(typeMap::convert);
            declaration.append(convertedParameters.collect(joining(", ")));
            return declaration.append(')').toString();
        };
    }

    boolean realMethodsOnly(ExecutableElement method)
    {
        return method.getSimpleName().charAt(0) != '<';
    }
    
    TypeElement typeElement(TypeMirror type)
    {
        return elements.getTypeElement(type.toString());
    }
}
