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

import java.lang.annotation.Annotation;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.tools.StandardLocation;
import pro.projo.interfaces.annotation.Options;
import pro.projo.template.annotation.Configuration;

/**
* {@link TemplateConfiguration} is an abstract base class that encapsulates some of the
* commonalities between interface configurations and enum configurations.
*
* @author Mirko Raner
**/
public abstract class TemplateConfiguration implements Configuration
{
    private Name packageName;
    private String generatedName;
    private Options packageLevelOptions;
    private Options annotationLevelOptions;

    public TemplateConfiguration(Name packageName, String generatedName, PackageElement element, Options options)
    {
        this.packageName = packageName;
        this.generatedName = generatedName;
        packageLevelOptions = element.getAnnotation(Options.class);
        annotationLevelOptions = options;
    }

    @Override
    public String fullyQualifiedClassName()
    {
        return packageName + "." + generatedName;
    }

    @Override
    public Options options()
    {
        if (packageLevelOptions == null)
        {
            // No need to worry about merging package-level and annotation-level; simply return
            // annotation-level options (which may or may not be all default values):
            //
            return annotationLevelOptions;
        }

        // Both package-level and annotation-level options are present; merge option values from
        // both sources so that non-default annotation-level options override package-level options:
        //
        return new Options()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Options.class;
            }

            @Override
            public String fileExtension()
            {
                return isDefault(annotationLevelOptions, Options::fileExtension)?
                    packageLevelOptions.fileExtension():
                    annotationLevelOptions.fileExtension();
            }

            @Override
            public StandardLocation outputLocation()
            {
                return isDefault(annotationLevelOptions, Options::outputLocation)?
                    packageLevelOptions.outputLocation():
                    annotationLevelOptions.outputLocation();
            }
        };
    }
}
