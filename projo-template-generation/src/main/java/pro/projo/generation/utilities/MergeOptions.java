//                                                                          //
// Copyright 2020 - 2021 Mirko Raner                                        //
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
package pro.projo.generation.utilities;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import javax.tools.StandardLocation;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Ternary;
import pro.projo.interfaces.annotation.Unmapped;
import static pro.projo.template.annotation.Configuration.defaults;

/**
* {@link MergeOptions} is a utility class for merging package-level options with annotation-level
* options in such a way that annotation-level options will override the package-level ones.
*
* @author Mirko Raner
**/
public class MergeOptions implements TypeMirrorUtilities
{
    private Options packageLevelOptions;
    private Options annotationLevelOptions;

    public MergeOptions(Options packageLevelOptions, Options annotationLevelOptions)
    {
        this.packageLevelOptions = packageLevelOptions;
        this.annotationLevelOptions = annotationLevelOptions;
    }

    public Options packageLevelOptions()
    {
        return packageLevelOptions;
    }

    public Options annotationLevelOptions()
    {
        return annotationLevelOptions;
    }

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
                return option(Options::fileExtension);
            }

            @Override
            public StandardLocation outputLocation()
            {
                return option(Options::outputLocation);
            }

            @Override
            public Unmapped skip()
            {
                return option(Options::skip);
            }

            @Override
            public Class<? extends UnaryOperator<Writer>> postProcessor()
            {
                return option(options -> getType(options::postProcessor));
            }

            @Override
            public Ternary addAnnotations()
            {
                return option(Options::addAnnotations);
            }

            <_Option_> _Option_ option(Function<Options, _Option_> option)
            {
                return option.apply(isDefault(annotationLevelOptions, option)? packageLevelOptions:annotationLevelOptions);
            }

            boolean isDefault(Options options, Function<Options, ?> option)
            {
                return option.apply(defaults()).equals(option.apply(options));
            }
        };
    }
}
