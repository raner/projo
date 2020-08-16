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
package pro.projo.interfaces.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.tools.StandardLocation;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;

/**
* The {@link Options @Options} annotation captures various settings that affect the compile-time
* code generation triggered by the {@link Interface @Interface} and {@link Enum @Enum} annotations.
* An {@link Options @Options} annotation can be used stand-alone at package level, where it will
* affect code generation for the entire package, or as an argument to individual
* {@link Interface @Interface} or {@link Enum @Enum} annotations, where it will define (or override)
* the settings for just that annotation.
*
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
public @interface Options
{
    /**
    * The file extension to be used for generated files. Default value is {@code ".java"}.
    *
    * @return the file extension
    **/
    String fileExtension() default ".java";

    /**
    * The output location for generated files with a custom file extension.
    * <b>NOTE:</b> this setting only has an effect when {@link #fileExtension()} is set to
    * a non-default value; {@code .java} files will always be generated in the
    * {@link StandardLocation#SOURCE_OUTPUT} location (which is also the default value for
    * this option).
    *
    * @return the output location
    **/
    StandardLocation outputLocation() default SOURCE_OUTPUT;

    /**
    * The option for skipping generation of methods that use unmapped types. The default
    * value is {@code @Unmapped(false)}, i.e., no methods are skipped, even if they use
    * unmapped types.
    *
    * @return the option for skipping methods using {@link Unmapped} types
    **/
    Unmapped skip() default @Unmapped(false);
}
