//                                                                          //
// Copyright 2023 Mirko Raner                                               //
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

import pro.projo.generation.dtd.model.ChildElement;
import pro.projo.generation.dtd.model.ContentModel;
import pro.projo.generation.dtd.model.Occurrence;

public class TypedChildElement implements ChildElement
{
    private String alias;
    private ChildElement element;

    public TypedChildElement(ChildElement element, String alias)
    {
        this.element = element;
        this.alias = alias;
    }

    public String typeName()
    {
        return element.name();
    }

    @Override
    public String name()
    {
        return alias;
    }

    @Override
    public ContentModel contentModel()
    {
        return element.contentModel();
    }

    @Override
    public Occurrence ocurrence()
    {
        return element.ocurrence();
    }
}
