//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import pro.projo.generation.utilities.MergeOptions;
import pro.projo.interfaces.annotation.Options;
import pro.projo.template.annotation.Configuration;

/**
* {@link TemplateConfiguration} is an abstract base class that encapsulates some of the
* commonalities between interface configurations and enum configurations.
*
* @author Mirko Raner
**/
public abstract class TemplateConfiguration extends MergeOptions implements Configuration
{
    private Name packageName;
    private String generatedName;

    public TemplateConfiguration(Name packageName, String generatedName, PackageElement element, Options options)
    {
        super(element.getAnnotation(Options.class), options);
        this.packageName = packageName;
        this.generatedName = generatedName;
    }

    @Override
    public String fullyQualifiedClassName()
    {
        return packageName + "." + generatedName;
    }
}
