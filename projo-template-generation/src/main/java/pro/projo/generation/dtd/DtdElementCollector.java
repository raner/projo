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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import org.xml.sax.SAXException;
import com.sun.xml.dtdparser.DTDHandlerBase;
import pro.projo.generation.interfaces.InterfaceTemplateProcessor;
import pro.projo.generation.utilities.DefaultNameComparator;
import pro.projo.template.annotation.Configuration;

/**
* The {@link DtdElementCollector} is a mutable type that collects information from
* DTD parsing events and builds template {@link Configuration}s based on the
* collected data.
*
* @author Mirko Raner
**/
public class DtdElementCollector extends DTDHandlerBase
{
    private Map<String, Configuration> configurations = new HashMap<>();
    private Comparator<Name> importOrder = new DefaultNameComparator();
    private Name packageName;
    private UnaryOperator<String> typeNameTransformer;
    private String currentContentModel;
    private TypeElement baseInterface;
    private TypeElement baseInterfaceEmpty;

    public DtdElementCollector(Name packageName, TypeElement baseInterface, TypeElement baseInterfaceEmpty)
    {
        this(packageName, UnaryOperator.identity());
        this.baseInterface = baseInterface;
        this.baseInterfaceEmpty = baseInterfaceEmpty;
    }

    public DtdElementCollector(Name packageName, UnaryOperator<String> typeNameTransformer)
    {
        this.packageName = packageName;
        this.typeNameTransformer = typeNameTransformer;
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
        Map<String, Object> parameters = new HashMap<>();
        String typeName = typeNameTransformer.apply(typeName(elementName));
        TypeElement superType = contentModelType == CONTENT_MODEL_EMPTY? baseInterfaceEmpty:baseInterface;
        boolean isObject = superType.getQualifiedName().toString().equals(Object.class.getName());
        String extend = isObject? "":(" extends " + superType.getSimpleName());
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
        parameters.put("generatedBy", "@Generated(\"" + InterfaceTemplateProcessor.class.getName() + "\")");
        parameters.put("InterfaceTemplate", typeName + extend);
        parameters.put("methods", new String[] {});
        Configuration configuration = new Configuration()
        {
            @Override
            public String fullyQualifiedClassName()
            {
                return packageName.toString() + "." + typeName;
            }

            @Override
            public Map<String, Object> parameters()
            {
                return parameters;
            }
        };
        configurations.put(elementName, configuration);
    }

    @Override
    public void endContentModel(String elementName, short contentModelType) throws SAXException
    {
        if (currentContentModel ==  null)
        {
            throw new IllegalStateException("unexpected end of content model");
        }
        currentContentModel = null;
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
