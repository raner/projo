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
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.sun.xml.dtdparser.DTDParser;
import pro.projo.generation.dtd.model.Attribute;
import pro.projo.generation.dtd.model.ChildElement;
import pro.projo.generation.dtd.model.ContentModel;
import pro.projo.generation.dtd.model.ContentModelType;
import pro.projo.generation.dtd.model.DtdElement;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.DefaultConfiguration;
import pro.projo.generation.utilities.DefaultNameComparator;
import pro.projo.generation.utilities.TypeMirrorUtilities;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.interfaces.annotation.utilities.AttributeNameConverter;
import pro.projo.template.annotation.Configuration;
import static javax.tools.Diagnostic.Kind.WARNING;

/**
* The {@link DtdElementCollector} is a mutable type that collects information from
* DTD parsing events and builds template {@link Configuration}s based on the
* collected data.
*
* @author Mirko Raner
**/
public class DtdElementCollector implements TypeMirrorUtilities
{
    private Map<String, Configuration> configurations = new HashMap<>();
    private Comparator<Name> importOrder = new DefaultNameComparator();
    private Name packageName;
    private Name generated;
    private UnaryOperator<String> typeNameTransformer;
    private AttributeNameConverter attributeNameConverter;
    private TypeElement baseInterface;
    private TypeElement baseInterfaceEmpty;
    private TypeElement baseInterfaceText;
    private Format elementTypeName;
    private Format contentTypeName;
    private Elements elements;
    private Messager messager;

    public DtdElementCollector(Name packageName, Dtd dtd, Elements elements, Messager messager)
    {
        this(packageName, UnaryOperator.identity());
        this.elements = elements;
        this.messager = messager;
        baseInterface = getTypeElement(dtd::baseInterface);
        baseInterfaceEmpty = getTypeElement(dtd::baseInterfaceEmpty);
        baseInterfaceText = getTypeElement(dtd::baseInterfaceText);
        elementTypeName = new MessageFormat(dtd.elementNameFormat());
        contentTypeName = new MessageFormat(dtd.contentNameFormat());
        generated = new pro.projo.generation.utilities.Name("javax.annotation.Generated");
        try
        {
            Constructor<?> constructor = getType(dtd::attributeNameConverter).getDeclaredConstructor();
            attributeNameConverter = (AttributeNameConverter)constructor.newInstance();
        }
        catch (Exception exception)
        {
            throw new Error(exception.toString());
        }
    }

    public DtdElementCollector(Name packageName, UnaryOperator<String> typeNameTransformer)
    {
        this.packageName = packageName;
        this.typeNameTransformer = typeNameTransformer;
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
        Optional<Configuration> contentConfiguration = getOrCreateContentType(contentModel);
        return Stream.concat
        (
            Stream.of(createElementInterface(contentModel)),
            contentConfiguration.isPresent()? Stream.of(contentConfiguration.get()):Stream.empty()
        );
    }

    public Configuration createElementInterface(ContentModel contentModel)
    {
        Map<String, Object> parameters = new HashMap<>();
        String elementName = contentModel.name();
        boolean currentContentModelHasChildren = contentModel.nonAttributes().count() > 0;
        String typeName = elementTypeName.format(new Object[] {typeNameTransformer.apply(typeName(elementName))});
        TypeElement superType = contentModel.type() == ContentModelType.EMPTY? baseInterfaceEmpty:
            currentContentModelHasChildren? baseInterface:baseInterfaceText;
        boolean isObject = superType.getQualifiedName().toString().equals(Object.class.getName());
        String extend = isObject? "":(" extends " + superType.getSimpleName());
        String contentType = contentTypeName.format(new Object[] {typeNameTransformer.apply(typeName(elementName))});
        String typeParameters = "<PARENT" + (currentContentModelHasChildren? ", " + contentType + ", " + contentType + ">":">");
        Name superPackageName = packageName(superType);
        boolean superTypeSamePackage = superPackageName.equals(packageName);
        Name superTypeName = superType.getQualifiedName();
        Stream<Name> imported = superTypeSamePackage? Stream.of(generated):Stream.of(generated, superTypeName);
        String[] imports = imported
            .sorted(importOrder)
            .map(Name::toString)
            .filter(name -> !name.startsWith("java.lang."))
            .toArray(String[]::new);
        // TODO: consolidate import code with code in InterfaceTemplateProcessor.getInterfaceConfiguration
        parameters.put("package", packageName.toString());
        parameters.put("imports", imports);
        parameters.put("javadoc", "THIS IS A GENERATED INTERFACE - DO NOT EDIT!");
        parameters.put("generatedBy", "@Generated(\"" + InterfaceTemplateProcessor.class.getName() + "\")");
        parameters.put("InterfaceTemplate", typeName + "<PARENT>" + extend + (isObject? "":typeParameters));
        parameters.put("methods", new String[] {});
        String fullyQualifiedClassName = packageName.toString() + "." + typeName;
        Configuration configuration = new DefaultConfiguration(fullyQualifiedClassName, parameters);
        configuration = contentModel.attributes().reduce(configuration, (conf, attr) -> attributeDecl(conf, contentModel, attr), (a, b) -> a);
        configurations.put(elementName, configuration);
        return configuration;
    }

    public Configuration attributeDecl(Configuration configuration, ContentModel contentModel, Attribute attribute)
    {
        String attributeName = attribute.name();
        String elementName = contentModel.name();
        if (configuration != null)
        {
            // TODO: next line duplicated from above
            String typeName = elementTypeName.format(new Object[] {typeNameTransformer.apply(typeName(elementName))});
            String methodName = attributeNameConverter.convertAttributeName(attributeName);
            String method = typeName + "<PARENT> " + methodName + "(String " + methodName + ")";
            String[] methods = (String[])configuration.parameters().get("methods");
            List<String> newMethods = new ArrayList<>(Arrays.asList(methods));
            newMethods.add(method);
            configuration.parameters().put("methods", newMethods.toArray(new String[] {}));
        }
        else
        {
            messager.printMessage
            (
                WARNING,
                "Encountered attribute '" + attributeName + "' for unknown element '" + elementName + "'"
            );
        }
        return configuration;
    }

    public Configuration childElement(Configuration configuration, ChildElement childElement)
    {
        Map<String, Object> parameters = configuration.parameters();
        String contentType = (String)parameters.get("InterfaceTemplate");
        @SuppressWarnings("unchecked")
        Set<String> methodNames = (Set<String>)parameters.getOrDefault("methodNames", new HashSet<>());
        // TODO: next line duplicated from above
        String methodName = childElement.name();
        if (!methodNames.contains(methodName))
        {
            String typeName = elementTypeName.format(new Object[] {typeNameTransformer.apply(typeName(childElement.name()))});
            String method = typeName + "<" + contentType + "> " + methodName + "()";
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

    private Optional<Configuration> getOrCreateContentType(ContentModel contentModel)
    {
        String contentType = contentTypeName.format(new Object[] {typeNameTransformer.apply(typeName(contentModel.name()))});
        Configuration configuration = configurations.get(contentType);
        if (configuration == null
        && contentModel.type() != ContentModelType.EMPTY
        && contentModel.nonAttributes().count() > 0)
        {
            Map<String, Object> parameters = new HashMap<>();
            // TODO: consolidate import code with code in InterfaceTemplateProcessor.getInterfaceConfiguration
            parameters.put("package", packageName.toString());
            parameters.put("imports", new String[] {generated.toString()});
            parameters.put("javadoc", "THIS IS A GENERATED INTERFACE - DO NOT EDIT!");
            parameters.put("generatedBy", "@Generated(\"" + InterfaceTemplateProcessor.class.getName() + "\")");
            parameters.put("InterfaceTemplate", contentType);
            parameters.put("methods", new String[] {});
            String fullyQualifiedClassName = packageName.toString() + "." + contentType;
            configuration = new DefaultConfiguration(fullyQualifiedClassName, parameters);
            configurations.put(contentType, configuration);
            BiFunction<Configuration, ? super DtdElement, Configuration> reducer =
                (conf, child) -> childElement(conf, (ChildElement)child);
            configuration = contentModel.nonAttributes()
                .flatMap(it -> Stream.concat(Stream.of(it), it.children().stream()))
                .filter(ChildElement.class::isInstance)
                .reduce(configuration, reducer, (a, b) -> a);
        }
        return Optional.ofNullable(configuration);
    }

    private String typeName(String elementName)
    {
        String firstLetter = elementName.substring(0, 1);
        String remainingLetters = elementName.substring(1);
        return firstLetter.toUpperCase() + remainingLetters.toLowerCase();
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
