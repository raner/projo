//                                                                          //
// Copyright 2018 - 2024 Mirko Raner                                        //
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

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.lang.model.element.Modifier;
import pro.projo.interfaces.annotation.postprocessor.IdentityPostProcessor;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static javax.tools.StandardLocation.SOURCE_OUTPUT;
import static pro.projo.interfaces.annotation.Ternary.EITHER;
import static pro.projo.interfaces.annotation.Visibility.PUBLIC;
import static pro.projo.interfaces.annotation.Visibility.PRIVATE;
import static pro.projo.interfaces.annotation.Visibility.PROTECTED;

/**
* The {@link Interface} annotation captures the necessary information for generating an interface
* from an existing class (or existing interface) while filtering for certain modifiers. This also
* works for modifiers that are not compatible with interfaces, like e.g. {@code static}, which is
* useful for extracting factory interfaces and similar patterns. For example, the original class
* may have a number of {@code static} {@code final} methods for creating new objects; in the
* generated interface these methods will just appear as regular non-static, non-final methods.
*
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
@Repeatable(Interfaces.class)
public @interface Interface
{
    /**
    * @return the simple name of the interface to be generated
    **/
    String generate();

    /**
    * @return the (optional, hence possibly empty) list of base interfaces to extend
    **/
    String[] extend() default {};

    /**
    * Indicates which modifiers a method in the original class must have so that it will be
    * included in the generated interface. By default, this list is empty, i.e. all methods
    * will be included, irrespective of their modifiers. If this list contains more than one
    * element, the modifiers are combined using an <i>and</i> operation; for example, if the
    * list is {@code PUBLIC}, {@code FINAL}, {@code STATIC} then only those methods will be
    * included that are public and final and static. To realize an <i>or</i> operation,
    * multiple {@code @Interface} annotations need to be used.
    *
    * @return the method modifiers to be used for filtering
    * @deprecated use {@link #isStatic()} and {@link #visibility()} instead
    **/
    @Deprecated
    Modifier[] modifiers() default {};

    /**
    * @return the original class (or interface) from which the new interface will be generated
    **/
    Class<?> from();

    /**
    * @return optional type mappings, if any
    **/
    Map[] map() default {};

    /**
    * @return additional code generation options for this interface
    **/
    Options options() default @Options
    (
        fileExtension = ".java",
        outputLocation = SOURCE_OUTPUT,
        addAnnotations = EITHER,
        skip = @Unmapped(false),
        postProcessor = IdentityPostProcessor.class,
        typeVariableTransformer = IdentityPostProcessor.class
    );

    /**
    * @return indication whether only static ({@link Ternary#TRUE TRUE}), only non-static
    * ({@link Ternary#FALSE FALSE}), or both static and non-static methods
    * ({@link Ternary#EITHER EITHER}) should be included.
    **/
    Ternary isStatic() default EITHER;

    /**
    * @return the visibilities to be included for method generation (for example,
    * {@link Visibility#PUBLIC PUBLIC} will only include public methods,
    * {@link Visibility#PACKAGE &#123;PACKAGE, }{@link Visibility#PROTECTED PROTECTED&#125;}
    * will include protected and default/package visibility methods; there is no
    * direct way to express "not private", but as every method has to have one of
    * the four supported visibilities this can easily be expressed by listing all
    * visibilities that <i>should</i> be included, i.e.
    * {@link Visibility#PUBLIC &#123;PUBLIC, }{@link Visibility#PACKAGE PACKAGE, }{@link Visibility#PROTECTED PROTECTED&#125;})
    **/
    Visibility[] visibility() default {Visibility.PACKAGE, PUBLIC, PROTECTED, PRIVATE};
}
