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

import java.text.Format;
import java.text.MessageFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import org.xml.sax.SAXException;
import com.sun.xml.dtdparser.DTDHandlerBase;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.DefaultConfiguration;
import pro.projo.generation.utilities.DefaultNameComparator;
import pro.projo.generation.utilities.TypeMirrorUtilities;
import pro.projo.interfaces.annotation.Dtd;
import pro.projo.template.annotation.Configuration;

/**
* The {@link DtdElementCollector} is a mutable type that collects information from
* DTD parsing events and builds template {@link Configuration}s based on the
* collected data.
*
* @author Mirko Raner
**/
public class DtdElementCollector extends DTDHandlerBase implements TypeMirrorUtilities
{
    private Map<String, Configuration> configurations = new HashMap<>();
    private Map<String, Integer> contentChildren = new HashMap<>();
    private Comparator<Name> importOrder = new DefaultNameComparator();
    private Name packageName;
    private UnaryOperator<String> typeNameTransformer;
    private String currentContentModel;
    private TypeElement baseInterface;
    private TypeElement baseInterfaceEmpty;
    private TypeElement baseInterfaceText;
    private Format elementTypeName;
    private Format contentTypeName;
    private Elements elements;

    public DtdElementCollector(Name packageName, Dtd dtd, Elements elements)
    {
        this(packageName, UnaryOperator.identity());
        this.elements = elements;
        baseInterface = getTypeElement(dtd::baseInterface);
        baseInterfaceEmpty = getTypeElement(dtd::baseInterfaceEmpty);
        baseInterfaceText = getTypeElement(dtd::baseInterfaceText);
        elementTypeName = new MessageFormat(dtd.elementNameFormat());
        contentTypeName = new MessageFormat(dtd.contentNameFormat());
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

    public Stream<Configuration> configurations()
    {
        return configurations.values().stream();
    }

    @Override
    public void startContentModel(String elementName, short contentModelType) throws SAXException
    {
        if (currentContentModel != null)
        {
            throw new IllegalStateException("content models cannot be nested");
        }
        if (configurations.containsKey(elementName))
        {
            throw new IllegalStateException("duplicate content model: " + elementName);
        }
        currentContentModel = elementName;
    }

    @Override
    public void endContentModel(String elementName, short contentModelType) throws SAXException
    {
        if (currentContentModel == null)
        {
            throw new IllegalStateException("unexpected end of content model");
        }

        // Add element interface:
        //
        Map<String, Object> parameters = new HashMap<>();
        boolean currentContentModelHasChildren = contentChildren.get(currentContentModel) != null;
        String typeName = elementTypeName.format(new Object[] {typeNameTransformer.apply(typeName(elementName))});
        TypeElement superType = contentModelType == CONTENT_MODEL_EMPTY? baseInterfaceEmpty:
            currentContentModelHasChildren? baseInterface:baseInterfaceText;
        boolean isObject = superType.getQualifiedName().toString().equals(Object.class.getName());
        String extend = isObject? "":(" extends " + superType.getSimpleName());
        String contentType = contentTypeName.format(new Object[] {typeNameTransformer.apply(typeName(elementName))});
        String typeParameters = "<PARENT" + (currentContentModelHasChildren? ", " + contentType + ">":">");
        Name superPackageName = packageName(superType);
        boolean superTypeSamePackage = superPackageName.equals(packageName);
        Name generated = new pro.projo.generation.utilities.Name("javax.annotation.Generated");
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
        configurations.put(elementName, configuration);

        // Add content interface, if applicable:
        //
        if (currentContentModelHasChildren)
        {
            parameters = new HashMap<>();
            // TODO: consolidate import code with code in InterfaceTemplateProcessor.getInterfaceConfiguration
            parameters.put("package", packageName.toString());
            parameters.put("imports", new String[] {generated.toString()});
            parameters.put("javadoc", "THIS IS A GENERATED INTERFACE - DO NOT EDIT!");
            parameters.put("generatedBy", "@Generated(\"" + InterfaceTemplateProcessor.class.getName() + "\")");
            parameters.put("InterfaceTemplate", contentType);
            parameters.put("methods", new String[] {});
            fullyQualifiedClassName = packageName.toString() + "." + contentType;
            configuration = new DefaultConfiguration(fullyQualifiedClassName, parameters);
            configurations.put(contentType, configuration);
        }

        // Reset content model:
        //
        currentContentModel = null;
    }

    @Override
    public void childElement(String elementName, short occurrence) throws SAXException
    {
        increaseChildCount();
    }

    @Override
    public void mixedElement(String elementName) throws SAXException
    {
        increaseChildCount();
    }

    @Override
    public void startModelGroup() throws SAXException
    {
        increaseChildCount();
    }

    private void increaseChildCount()
    {
        contentChildren.merge(currentContentModel, 1, Integer::sum);
    }

    public String typeName(String elementName)
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
