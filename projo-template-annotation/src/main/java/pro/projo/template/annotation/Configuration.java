//                                                                          //
// Copyright 2018 - 2021 Mirko Raner                                        //
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
package pro.projo.template.annotation;

import java.io.Writer;
import java.util.Map;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import pro.projo.interfaces.annotation.Options;

/**
* The {@link Configuration} interface describes the fully-qualified class name and the input
* parameters for an individual template invocation. A template can be invoke multiple times
* with different configurations.
*
* @author Mirko Raner
**/
public interface Configuration
{
    /**
    * @return the fully qualified name of the class to be generated
    **/
    String fullyQualifiedClassName();

    /**
    * @return additional parameters that need to be inserted into the code generation template
    **/
    Map<String, Object> parameters();

    /**
    * @return an operator that wraps the output writer into a filtering writer for post-processing
    **/
    default UnaryOperator<Writer> postProcessor()
    {
        return UnaryOperator.identity();
    }

    /**
    * Retrieves additional configuration options that were provided as an annotation parameter or
    * package annotation.
    *
    * @return the specified {@link Options} or a default {@link Options} object if not specified
    **/
    default Options options()
    {
        return defaults();
    }

    /**
    * Checks if the specified option is set to the default value. This method will also return true
    * if an option parameter was specified but happens to be identical to the default value
    * (e.g., {@code @Option(fileExtension=".java")}).
    *
    * @param option the option to check (typically as a method reference)
    * @return {@code true} if the option value is identical to the default value, {@code false} otherwise
    **/
    default boolean isDefault(Function<Options, ?> option)
    {
        return isDefault(options(), option);
    }

    default boolean isDefault(Options options, Function<Options, ?> option)
    {
        return option.apply(defaults()).equals(option.apply(options));
    }

    static Options defaults()
    {
        return Options.class.getPackage().getAnnotation(Options.class);
    }
}
