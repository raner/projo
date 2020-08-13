//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.tools.FileObject;
import pro.projo.generation.ProjoProcessor;
import pro.projo.generation.ProjoTemplateFactoryGenerator;
import pro.projo.generation.utilities.DefaultNameComparator;
import pro.projo.generation.utilities.PackageShortener;
import pro.projo.generation.utilities.Reduction;
import pro.projo.generation.utilities.Source;
import pro.projo.generation.utilities.Source.EnumSource;
import pro.projo.generation.utilities.Source.InterfaceSource;
import pro.projo.generation.utilities.TypeConverter;
import pro.projo.interfaces.annotation.Enum;
import pro.projo.interfaces.annotation.Enums;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Options;
import pro.projo.template.annotation.Configuration;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableSet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.iterate;
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
    final static Set<String> primitives =
        unmodifiableSet(new HashSet<>(asList("byte", "short", "int", "long", "float", "double", "char", "boolean")));
    final static Predicate<String> notPrimitive = not(primitives::contains);

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
                    FileObject sourceFile = createFile(filer, configuration, className, typeElement);
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

    private FileObject createFile(Filer filer, Configuration configuration, String className, Element typeElement)
    throws IOException
    {
        if (configuration.isDefault(Options::fileExtension))
        {
            return filer.createSourceFile(className, typeElement);
        }
        Options options = configuration.options();
        String fileExtension = options.fileExtension();
        String sourceFileName = className.substring(className.lastIndexOf('.')+1) + fileExtension;
        String packageName = className.substring(0, className.lastIndexOf('.'));
        return filer.createResource(options.outputLocation(), packageName, sourceFileName, typeElement);
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
        Predicate<String> notSamePackage = name -> !name.substring(0, name.lastIndexOf('.')).equals(String.valueOf(packageName));
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
            InterfaceSource primary = new InterfaceSource(annotation);
            Stream<Source> enums = getAnnotations(element, Enum.class, Enums.class).stream().map(EnumSource::new);
            Stream<Source> sources = Stream.concat(interfaces.stream().map(InterfaceSource::new), enums);
            TypeConverter typeConverter = new TypeConverter(types, shortener, packageName, sources, primary);
            Function<ExecutableElement, String> toDeclaration = convertToDeclaration(typeConverter, typeParameters);
            Predicate<TypeMirror> validSuperclass = base -> base.getKind() != NONE && !base.toString().equals(object);
            TypeMirror[] superclass = Stream.of(type.getSuperclass()).filter(validSuperclass).toArray(TypeMirror[]::new);
            String supertypes = modifiers.contains(STATIC)?
                "":concat(type.getInterfaces(), superclass).map(typeConverter::convert).collect(joining(", "));
            String[] declarations = methods.stream().filter(this::realMethodsOnly).map(toDeclaration).toArray(String[]::new);
            imports.addAll(typeConverter.getImports());
            List<String> importNames = imports.stream().map(Object::toString)
                .filter(notPrimitive)
                .filter(notSamePackage)
                .filter(name -> !name.startsWith("java.lang."))
                .map(pro.projo.generation.utilities.Name::new)
                .sorted(new DefaultNameComparator())
                .map(Name::toString)
                .collect(toList());
            return new TemplateConfiguration(packageName, annotation.generate(), element, annotation.options())
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

                private String interfaceSignature()
                {
                    String signature = annotation.generate();
                    if (!typeParameters.isEmpty())
                    {
                        signature += "<" + typeParameters.stream().map(Object::toString).collect(joining(", ")) + ">";
                    }
                    if (!supertypes.isEmpty())
                    {
                        signature += " extends " + supertypes;
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
            return new TemplateConfiguration(packageName, annotation.generate(), packageElement, annotation.options())
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

    Function<ExecutableElement, String> convertToDeclaration(TypeConverter typeMap,
        List<? extends TypeParameterElement> typeParameters)
    {
        return method ->
        {
            StringBuffer declaration = new StringBuffer();
    
            // Add type parameters, if any:
            List<? extends TypeParameterElement> typeParameterList = method.getTypeParameters();
            Map<String, String> renamedTypeVariables = renameShadowedTypeVariables(typeParameters, typeParameterList);
            if (!typeParameterList.isEmpty())
            {
                String localTypeParameters =
                    typeParameterList.stream()
                        .map(TypeParameterElement::getSimpleName)
                        .map(Name::toString)
                        .map(name -> renamedTypeVariables.getOrDefault(name, name))
                        .collect(joining(", "));
                declaration.append("<").append(localTypeParameters).append("> ");
            }
    
            // Add return type:
            String returnType = typeMap.convert(method.getReturnType(), renamedTypeVariables);
            declaration.append(returnType).append(' ');

            // Add parameters:
            declaration.append(method.getSimpleName()).append('(');
            Stream<? extends VariableElement> parameters = method.getParameters().stream();
            Stream<String> convertedParameters = parameters.map(parameter -> typeMap.convert(parameter, renamedTypeVariables));
            declaration.append(convertedParameters.collect(joining(", ")));
            return declaration.append(')').toString();
        };
    }

    Map<String, String> renameShadowedTypeVariables(
        List<? extends TypeParameterElement> classLevelVariables,
        List<? extends TypeParameterElement> methodLevelVariables)
    {
    	Map<String, Integer> typeVariableOccurrences = Stream.of(classLevelVariables, methodLevelVariables)
            .flatMap(List::stream)
            .map(TypeParameterElement::getSimpleName)
            .map(Name::toString)
            .collect(groupingBy(identity())).entrySet().stream()
            .collect(toMap(Entry::getKey, entry -> entry.getValue().size()));
        Stream<String> duplicates = typeVariableOccurrences.entrySet().stream()
            .filter(entry -> entry.getValue() > 1)
            .map(Entry::getKey);
        Entry<Map<String, Integer>, Map<String, String>> typesAndRenames =
            new SimpleEntry<>(typeVariableOccurrences, new HashMap<>());
        Reduction<Entry<Map<String, Integer>, Map<String, String>>, String> renameDuplicates = (entry, type) ->
        {
        	Map<String, Integer> types = new HashMap<>(entry.getKey());
            int suffixIndex = iterate(0, next -> next+1).filter(index -> !types.containsKey(type+index)).findFirst().get();
            String rename = type + suffixIndex;
            types.put(rename, 1);
            Map<String, String> renames = new HashMap<>(entry.getValue());
            renames.put(type, rename);
            return new SimpleEntry<>(types, renames);
        };
        return duplicates.reduce(typesAndRenames, renameDuplicates, (a, b) -> b).getValue();
    }

    boolean realMethodsOnly(ExecutableElement method)
    {
        return method.getSimpleName().charAt(0) != '<';
    }
    
    TypeElement typeElement(TypeMirror type)
    {
        return elements.getTypeElement(type.toString());
    }

    private static <T> Predicate<T> not(Predicate<T> target)
    {
        return target.negate();
    }
}
