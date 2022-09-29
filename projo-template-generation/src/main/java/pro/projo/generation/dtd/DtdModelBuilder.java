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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.xml.sax.SAXException;
import com.sun.xml.dtdparser.DTDHandlerBase;
import com.sun.xml.dtdparser.InputEntity;
import pro.projo.generation.dtd.model.Attribute;
import pro.projo.generation.dtd.model.AttributeType;
import pro.projo.generation.dtd.model.AttributeUse;
import pro.projo.generation.dtd.model.ContentModel;
import pro.projo.generation.dtd.model.ContentModelType;
import pro.projo.generation.dtd.model.Dtd;
import pro.projo.generation.dtd.model.DtdElement;
import pro.projo.generation.dtd.model.ModelGroup;
import pro.projo.generation.dtd.model.ModelGroupType;
import pro.projo.generation.dtd.model.Occurrence;

/**
* The {@link DtdModelBuilder} builds an in-memory model of a DTD.
*
* One big drawback with Sun's classic DTD parser is that many crucial pieces of information
* are not yet available when an element is processed. For example, it may be important to
* know if an element is part of a sequence or a choice, however that information will not
* be available until the first {@link #connector(short) connector} is encountered. For the
* first element in a model group this information will therefore only be available after
* the fact. Similarly, a model group's occurrence constraints are not known until the end
* of the group, and any sort of processing decisions for elements in the group that might
* depend on this information will have to be postponed.
*
* Rather than dealing with all kinds of delayed decisions when processing a DTD, it is often
* more convenient to build up a complete model ahead of time and so that any validation or
* code generation can occur with all necessary information already available.
*
* @author Mirko Raner
**/
public class DtdModelBuilder extends DTDHandlerBase
{
    private Dtd result;
    private Stack<DtdElement> stack = new Stack<>();

    public Dtd getDtd()
    {
        return result;
    }

    @Override
    public void startDTD(InputEntity entity) throws SAXException
    {
        List<DtdElement> children = new ArrayList<>();
        Dtd dtd = new Dtd()
        {
            @Override
            public String name()
            {
                return entity.getName();
            }

            @Override
            public List<DtdElement> children()
            {
                return children;
            }
            
        };
        stack.push(dtd);
    }

    @Override
    public void endDTD() throws SAXException
    {
        result = (Dtd)stack.pop();
    }

    @Override
    public void startContentModel(String elementName, short contentModelType) throws SAXException
    {
        List<DtdElement> children = new ArrayList<>();
        ContentModel contentModel = new ContentModel()
        {
            @Override
            public String name()
            {
                return elementName;
            }

            @Override
            public ContentModelType type()
            {
                return ContentModelType.values()[contentModelType];
            }

            @Override
            public List<DtdElement> children()
            {
                return children;
            }
        };
        stack.push(contentModel);
    }

    
    @Override
    public void endContentModel(String elementName, short contentModelType) throws SAXException
    {
        DtdElement element = stack.pop();
        stack.peek().children().add(element);
    }

    @Override
    public void startModelGroup() throws SAXException
    {
        List<DtdElement> children = new ArrayList<>();
        ModelGroup modelGroup = new ModelGroup()
        {
            @Override
            public List<DtdElement> children()
            {
                return children;
            }
        };
        stack.push(modelGroup);
    }

    @Override
    public void endModelGroup(short occurence) throws SAXException
    {
        ModelGroup modelGroup = (ModelGroup)stack.pop();

        // Fill in occurrence information and select correct subtype:
        //
        ModelGroup completedModelGroup = new ModelGroup()
        {
            @Override
            public ModelGroupType type()
            {
                return modelGroup.type();
            }

            @Override
            public Occurrence occurrence()
            {
                return Occurrence.values()[occurence];
            }

            @Override
            public List<DtdElement> children()
            {
                return modelGroup.children();
            }
        };
        stack.peek().children().add(completedModelGroup);
    }

    @Override
    public void connector(short connectorType) throws SAXException
    {
        ModelGroup currentModelGroup = (ModelGroup)stack.peek();
        ModelGroupType currentType = currentModelGroup.type();
        ModelGroupType newType = ModelGroupType.values()[connectorType];
        if (currentType == null)
        {
            stack.pop();
            ModelGroup updatedModelGroup = new ModelGroup()
            {
                @Override
                public List<DtdElement> children()
                {
                    return currentModelGroup.children();
                }

                @Override
                public ModelGroupType type()
                {
                    return newType;
                }
            };
            stack.push(updatedModelGroup);
        }
        else
        {
            if (currentType != newType)
            {
                throw new SAXException("mismatched connectors: " + currentType + "/" + newType);
            }
        }
    }

    @Override
    public void attributeDecl(String elementName, String attributeName, String attributeType,
        String[] enumeration, short attributeUse, String defaultValue) throws SAXException
    {
        Dtd dtd = (Dtd)stack.peek();
        // TODO: make this more efficient than linear search
        DtdElement element = dtd.children().stream().filter(it -> it.name().equals(elementName)).findAny().orElse(null);
        if (element instanceof ContentModel)
        {
            Attribute attribute = new Attribute()
            {
                @Override
                public String name()
                {
                    return attributeName;
                }

                @Override
                public AttributeType type()
                {
                    return AttributeType.valueOf(attributeType);
                }

                @Override
                public AttributeUse use()
                {
                    return AttributeUse.values()[attributeUse];
                }
            };
            element.children().add(attribute);
        }
        else
        {
            throw new SAXException("encoountered attribute '" + attributeName + "' for unknown element '" + elementName + "'");
        }
    }
}
