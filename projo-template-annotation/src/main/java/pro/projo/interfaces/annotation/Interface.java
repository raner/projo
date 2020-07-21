//                                                                          //
// Copyright 2018 - 2020 Mirko Raner                                        //
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
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
    * Indicates which modifiers a method in the original class must have so that it will be
    * included in the generated interface. By default, this list is empty, i.e. all methods
    * will be included, irrespective of their modifiers. If this list contains more than one
    * element, the modifiers are combined using an <i>and</i> operation; for example, if the
    * list is {@code PUBLIC}, {@code FINAL}, {@code STATIC} then only those methods will be
    * included that are public and final and static. To realize an <i>or</i> operation,
    * multiple {@code @Interface} annotations need to be used.
    *
    * @return the method modifiers to be used for filtering
    **/
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
    * Indicates that the generated type should be generated with a "self" type variable that can
    * be bound to the current type (independent of the inheritance hierarchy). If this attribute
    * is non-empty a type {@code Data} (without parameters) will be generated as
    * {@code Data&lt;$ extends Data&lt;$&gt;&gt;} (assuming that the self type variable was set to
    * {@code $}. A type that already has type parameter, for example, {@code Pair&lt;K, V&gt;},
    * will be generated with the self type variable inserted at the very first position, before
    * the type's existing type parameters: {@code Pair&lt;$ extends Pair&lt;$, K, V&gt;, K, V&gt;}.
    * The attribute's default value is the empty string, meaning that no self type variable will
    * be inserted during code generation. The attribute must not contain any characters that would
    * not be valid in a Java type variable name.
    *
    * @return the self type variable name, or the empty string if no self type variable should be
    * generated
    **/
    String selfTypeVariable() default "";
}
