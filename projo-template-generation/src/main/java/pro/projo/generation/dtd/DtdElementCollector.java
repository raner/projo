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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.Format;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.sun.xml.dtdparser.DTDParser;
import pro.projo.generation.dtd.model.Attribute;
import pro.projo.generation.dtd.model.AttributeUse;
import pro.projo.generation.dtd.model.ChildElement;
import pro.projo.generation.dtd.model.ContentModel;
import pro.projo.generation.dtd.model.ContentModelType;
import pro.projo.generation.dtd.model.DtdElement;
import pro.projo.generation.dtd.model.ModelGroup;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.DefaultConfiguration;
import pro.projo.generation.utilities.DefaultNameComparator;
import pro.projo.generation.utilities.Reduction;
import pro.projo.generation.utilities.TypeMirrorUtilities;
import pro.projo.interfaces.annotation.Alias;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import pro.projo.template.annotation.Configuration;
import static java.util.Arrays.asList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static javax.tools.Diagnostic.Kind.ERROR;
import static pro.projo.interfaces.annotation.Ternary.FALSE;

/**
* The {@link DtdElementCollector} is a type that collects information from
* DTD parsing events and builds template {@link Configuration}s based on the
* collected data.
*
* @author Mirko Raner
**/
public class DtdElementCollector implements TypeMirrorUtilities
{
    private final Comparator<Name> importOrder = new DefaultNameComparator();
    private final Name packageName;
    private final Name generated;
    private final AttributeNameConverter attributeNameConverter;
    private final TypeElement baseInterface;
    private final TypeElement baseInterfaceEmpty;
    private final TypeElement baseInterfaceText;
    private final TypeElement mixedContentInterface;
    private final Map<String, List<String>> aliases;
    private final Map<String, TypeElement> attributes;
    private final Options options;
    private final boolean addAnnotations;
    private final Format elementTypeName;
    private final Format contentTypeName;
    private final Elements elements;
    private final Messager messager;

    public DtdElementCollector(Name packageName, Dtd dtd, Elements elements, Messager messager)
    {
        this.packageName = packageName;
        this.elements = elements;
        this.messager = messager;
        baseInterface = getTypeElement(dtd::baseInterface);
        baseInterfaceEmpty = getTypeElement(dtd::baseInterfaceEmpty);
        baseInterfaceText = getTypeElement(dtd::baseInterfaceText);
        mixedContentInterface = getTypeElement(dtd::mixedContentInterface);
        elementTypeName = new MessageFormat(dtd.elementNameFormat());
        contentTypeName = new MessageFormat(dtd.contentNameFormat());
        aliases = Stream.of(dtd.aliases()).map(Alias::value).collect(toMap(key -> key[0], value -> asList(value)));
        attributes = Stream.of(dtd.attributes()).collect(toMap(key -> key.name(), value -> getTypeElement(value::type)));
        options = dtd.options();
        addAnnotations = options.addAnnotations() != FALSE; 
        generated = new pro.projo.generation.utilities.Name("javax.annotation.Generated");
        AttributeNameConverter attributeNameConverter = null;
        try
        {
            Constructor<?> constructor = getType(dtd::attributeNameConverter).getDeclaredConstructor();
            attributeNameConverter = (AttributeNameConverter)constructor.newInstance();
        }
        catch (Exception exception)
        {
            this.messager.printMessage(ERROR, "Could not instantiate attribute name converter");
        }
        this.attributeNameConverter = attributeNameConverter;
    }

    @Override
    public Elements elements()
    {
        return elements;
    }

    public Stream<Configuration> configurations(InputSource source) throws IOException, SAXException
    {
        DtdModelBuilder builder = new DtdModelBuilder();
        DTDParser parser = new DTDParser();
        parser.setDtdHandler(builder);
        parser.parse(source);
        Stream<ContentModel> contentModels = builder.getDtd().contentModels();
        return contentModels.flatMap(this::createElementAndContent);
    }

    public Stream<Configuration> createElementAndContent(ContentModel contentModel)
    {
        Stream<Configuration> contentConfiguration = createContentTypes(contentModel);
        return Stream.concat(createElementInterface(contentModel), contentConfiguration);
    }

    public Stream<Configuration> createElementInterface(ContentModel contentModel)
    {
        String elementName = contentModel.name();
        List<Attribute> requiredAttributes = contentModel.attributes()
            .filter(attribute -> attribute.use() == AttributeUse.REQUIRED)
            .collect(toList());
        boolean currentContentModelHasChildren = contentModel.nonAttributes().count() > 0;
        TypeElement superType = contentModel.type() == ContentModelType.EMPTY? baseInterfaceEmpty:
            currentContentModelHasChildren? baseInterface:baseInterfaceText;
        boolean isObject = superType.getQualifiedName().toString().equals(Object.class.getName());
        String extend = isObject? "":(" extends " + superType.getSimpleName());
        String contentType = contentTypeName.format(new Object[] {typeName(elementName)});
        String typeParameters = "<PARENT" + (currentContentModelHasChildren? ", " + contentType + ", " + contentType + ">":">");
        boolean superTypeSamePackage = packageName(superType).equals(packageName);
        Name superTypeName = superType.getQualifiedName();
        if (requiredAttributes.isEmpty())
        {
            Stream<Name> generated = addAnnotations? Stream.of(this.generated):Stream.empty();
            Stream<Name> imported = superTypeSamePackage? generated:Stream.concat(generated, Stream.of(superTypeName));
            String typeName = elementTypeName.format(new Object[] {typeName(elementName)});
            Configuration configuration = elementConfiguration(typeName, imported, extend + (isObject? "":typeParameters));
            configuration = contentModel.attributes().reduce(configuration, (conf, attr) -> attributeDecl(conf, contentModel, attr), (a, b) -> a);
            return Stream.of(configuration);
        }
        else
        {
            // For required attributes, multiple element interfaces need to be created
            // (specifically, 2^n interfaces, where n is the number of required attributes)
            //
            Stream<List<Attribute>> combinations = powerList(requiredAttributes);
            List<Attribute> optionalAttributes = contentModel.attributes()
                .filter(attribute -> attribute.use() != AttributeUse.REQUIRED)
                .collect(toList());
            return combinations.map(attributes ->
            {
                String presentAttributes = attributes.stream().map(Attribute::name).map(this::typeName).collect(joining());
                List<Attribute> missingRequired = new ArrayList<>(requiredAttributes);
                missingRequired.removeAll(attributes);
                boolean attributesComplete = attributes.size() == requiredAttributes.size();
                String typeName = elementTypeName.format(new Object[] {typeName(elementName) + presentAttributes});
                String extension = attributesComplete? extend + (isObject? "":typeParameters):"";
                Stream<Name> generated = addAnnotations? Stream.of(this.generated):Stream.empty();
                Stream<Name> imports = !attributesComplete || superTypeSamePackage? generated:Stream.concat(generated, Stream.of(superTypeName));
                Configuration configuration = elementConfiguration(typeName, imports, extension);
                ContentModel returnTypeContentModel = contentModel(typeName(elementName) + presentAttributes);
                return missingRequired.stream().reduce
                (
                    optionalAttributes.stream().reduce
                    (
                        configuration,
                        (conf, attr) -> attributeDecl(conf, returnTypeContentModel, attr),
                        (a, b) -> a
                    ),
                    (conf, attr) -> attributeDecl
                    (
                        conf,
                        missingAttributeReturnType(elementName, requiredAttributes, attributes, attr),
                        attr
                    ),
                    (a, b) -> a
                );
            });
        }
    }

    public ContentModel missingAttributeReturnType(String elementName, List<Attribute> all, List<Attribute> present, Attribute attribute)
    {
        // Unfortunately, just appending the new attribute to the list of already present attributes
        // will not produce the correct behavior because the attributes will possibly end up out of
        // order. To retain the original order, the attribute list is generated by starting with the
        // entire list of required attributes and then retaining the pre-existing attributes and the
        // additional new attribute. This guarantees the correct order of attributes.
        //
        List<Attribute> attributes = new ArrayList<>(all);
        List<Attribute> retain = new ArrayList<>(present);
        retain.add(attribute);
        attributes.retainAll(retain);
        String name = typeName(elementName) + attributes.stream().map(Attribute::name).map(this::typeName).collect(joining());
        return contentModel(name);
    }

    public ContentModel contentModel(String name)
    {
        return new ContentModel()
        {
            @Override
            public String name()
            {
                return name;
            }

            @Override
            public ContentModelType type()
            {
                return null;
            }
        };
    }

    public Configuration elementConfiguration(String typeName, Stream<Name> imported, String extendSpec)
    {
        String[] imports = imported
            .sorted(importOrder)
            .map(Name::toString)
            .filter(name -> !name.startsWith("java.lang."))
            .toArray(String[]::new);
        // TODO: consolidate import code with code in InterfaceTemplateProcessor.getInterfaceConfiguration
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("package", packageName.toString());
        parameters.put("imports", imports);
        parameters.put("javadoc", "THIS IS A GENERATED INTERFACE - DO NOT EDIT!");
        parameters.put("generatedBy", addAnnotations? "@Generated(\"" + InterfaceTemplateProcessor.class.getName() + "\")":"");
        parameters.put("InterfaceTemplate", typeName + "<PARENT>" + extendSpec);
        parameters.put("methods", new String[] {});
        String fullyQualifiedClassName = packageName.toString() + "." + typeName;
        return new DefaultConfiguration(fullyQualifiedClassName, parameters, options);
    }

    public Configuration attributeDecl(Configuration configuration, ContentModel contentModel, Attribute attribute)
    {
        String attributeName = attribute.name();
        String elementName = contentModel.name();
        // TODO: next line duplicated from above
        String typeName = elementTypeName.format(new Object[] {typeName(elementName)});
        String methodName = attributeNameConverter.convertAttributeName(attributeName);
        TypeElement parameterType = attributes.get(methodName);
        String parameterTypeName = parameterType != null? parameterType.getSimpleName().toString():"String";
        String method = typeName + "<PARENT> " + methodName + "(" + parameterTypeName + " " + methodName + ")";
        String[] methods = (String[])configuration.parameters().get("methods");
        List<String> newMethods = new ArrayList<>(Arrays.asList(methods));
        newMethods.add(method);
        configuration.parameters().put("methods", newMethods.toArray(new String[] {}));
        if (parameterType != null && !packageName(parameterType).equals(packageName))
        {
            String[] imported = (String[])configuration.parameters().get("imports");
            Stream<String> stream = Stream.of(imported != null? imported:new String[] {});
            Set<String> imports = new HashSet<>(stream.collect(toList()));
            imports.add(parameterType.getQualifiedName().toString());
            // TODO: this will sort the entire array after each new import
            // TODO: consolidate with code in elementConfiguration(...)
            configuration.parameters().put("imports",
                imports.stream()
                    .map(pro.projo.generation.utilities.Name::new)
                    .sorted(importOrder)
                    .map(Name::toString)
                    .toArray(String[]::new));
        }
        return configuration;
    }

    public Configuration childElement(Configuration configuration, TypedChildElement childElement, String contentTypeArgument)
    {
        Map<String, Object> parameters = configuration.parameters();
        String contentType = (String)parameters.get("InterfaceTemplate");
        @SuppressWarnings("unchecked")
        Set<String> methodNames = (Set<String>)parameters.getOrDefault("methodNames", new HashSet<>());
        // TODO: next line duplicated from above
        String methodName = childElement.name();
        if (!methodNames.contains(methodName))
        {
            String typeName = elementTypeName.format(new Object[] {typeName(childElement.typeName())});
            String method = typeName + "<" + (contentTypeArgument != null? contentTypeArgument:contentType) + "> " + methodName + "()";
            // TODO: next four lines also duplicated
            String[] methods = (String[])parameters.get("methods");
            List<String> newMethods = new ArrayList<>(Arrays.asList(methods));
            newMethods.add(method);
            parameters.put("methods", newMethods.toArray(new String[] {}));
            methodNames.add(methodName);
            parameters.put("methodNames", methodNames);
        }
        return configuration;
    }

    private Stream<Configuration> createContentTypes(ContentModel contentModel)
    {
        String contentType = contentTypeName.format(new Object[] {typeName(contentModel.name())});
        Stream<ChildElement> children = contentModel.nonAttributes()
            .flatMap(it -> Stream.concat(Stream.of(it), it.children().stream()))
            .filter(ChildElement.class::isInstance)
            .map(ChildElement.class::cast);
        List<ChildElement> childList = children.collect(toList());
        DtdElement modelGroup;
        if (contentModel.nonAttributes().count() == 1
        && (modelGroup = contentModel.nonAttributes().iterator().next()) instanceof ModelGroup
        && ((ModelGroup)modelGroup).isStrictSequence())
        {
            // To implement a strict sequence:
            //
            // - generate a content base interface (e.g., HtmlContent) without any methods
            // - generate a content<n> interface (n=0...) with the nth (0th, 1st, ...) method
            // - each interface's content method parameter points to the next interface
            // - the last interface's content method parameter points to the base interface
            // - the element interface uses content<0> as in type and the base interface as out type
            //
            Stream<Configuration> base = Stream.of(createContentType(contentType, null, Stream.empty(), ""));
            Stream<Configuration> sequence = IntStream.range(0, childList.size()).mapToObj
            (
                index -> createContentType
                (
                    contentType + typeName(childList.get(index).name()),
                    contentType + (index+1 < childList.size()? typeName(childList.get(index+1).name()):""),
                    Stream.of(childList.get(index)),
                    " extends " + contentType
                )
            );
            return Stream.concat(base, sequence);
        }
        else if (contentModel.type() != ContentModelType.EMPTY
        && (contentModel.nonAttributes().count() > 0))
        {
            String extend = "";
            String[] imports = {};
            if (contentModel.type() == ContentModelType.MIXED
            && !mixedContentInterface.getQualifiedName().toString().equals(Object.class.getName()))
            {
                extend = " extends " + mixedContentInterface.getSimpleName() + "<" + contentType + ">";
                String mixedContentTypeName = mixedContentInterface.getQualifiedName().toString();
                String mixedContentPackage = mixedContentTypeName.substring(0, mixedContentTypeName.lastIndexOf('.'));
                if (!mixedContentPackage.equals(packageName.toString()))
                {
                    imports = new String[] {mixedContentTypeName};
                }
            }
            return Stream.of(createContentType(contentType, null, childList.stream(), extend, imports));
        }
        return Stream.empty();
    }

    private Configuration createContentType(String contentType, String contentTypeArgument,
        Stream<ChildElement> children, String extend, String... imports)
    {
        List<String> importStatements = new ArrayList<>();
        if (addAnnotations)
        {
            importStatements.add(generated.toString());
        }
        importStatements.addAll(Arrays.asList(imports));
        Map<String, Object> parameters = new HashMap<>();
        // TODO: consolidate import code with code in InterfaceTemplateProcessor.getInterfaceConfiguration
        parameters.put("package", packageName.toString());
        parameters.put("imports", importStatements.toArray(new String[] {}));
        parameters.put("javadoc", "THIS IS A GENERATED INTERFACE - DO NOT EDIT!");
        parameters.put("generatedBy", addAnnotations? "@Generated(\"" + InterfaceTemplateProcessor.class.getName() + "\")":"");
        parameters.put("InterfaceTemplate", contentType + extend);
        parameters.put("methods", new String[] {});
        String fullyQualifiedClassName = packageName.toString() + "." + contentType;
        Function<String, Stream<String>> aliased = name -> aliases.getOrDefault(name, asList(name)).stream();
        Stream<TypedChildElement> typedChildren = children.flatMap
        (
            child -> aliased.apply(child.name()).map(it -> new TypedChildElement(child, it))
        );
        Configuration configuration = new DefaultConfiguration(fullyQualifiedClassName, parameters, options);
        String typeArgument = contentTypeArgument != null? contentTypeArgument:contentType;
        Reduction<Configuration, TypedChildElement> reducer = (conf, child) -> childElement(conf, child, typeArgument);
        return typedChildren.reduce(configuration, reducer, (a, b) -> a);
    }

    /**
    * This method produces a power set, but uses lists to preserve the order.
    **/
    private <TYPE> Stream<List<TYPE>> powerList(List<TYPE> list)
    {
        return range(0, 1 << list.size())
            .mapToObj(pattern -> range(0, list.size()).filter(bit -> (pattern & (1 << bit)) != 0).toArray())
            .map(indexArray -> IntStream.of(indexArray).mapToObj(list::get).collect(toList()));
    }

    private String typeName(String elementName)
    {
        String firstLetter = elementName.substring(0, 1);
        String remainingLetters = elementName.substring(1);
        return firstLetter.toUpperCase() + remainingLetters;
    }

    private Name packageName(TypeElement element)
    {
        String name = element.getQualifiedName().toString();
        int lastDot = name.lastIndexOf('.');
        if (lastDot == -1)
        {
            return new pro.projo.generation.utilities.Name("");
        }
        return new pro.projo.generation.utilities.Name(name.substring(0, lastDot));
    }
}
