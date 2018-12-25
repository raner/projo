//                                                                          //
// Copyright 2018 Mirko Raner                                               //
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

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
* The {@link Map @Map} annotation defines a non-default type mapping within an
* {@link Interface @Interface} annotation. By default, in an interface extracted from
* an existing type, the type occurrence of the existing type will be replaced with the
* generated interface.
* <br>
* For example, given:
* <pre>
* package existing;
* public class Type
* {
*     public static Type self(Type type)
*     {
*         return type;
*     }
* }
* </pre>
* and
* <pre>
* &#64;Interface(from=existing.Type.class, generate="NewType")
* package generated;
* </pre>
* the generated interface would look like this:
* <pre>
* public interface NewType
* {
*     NewType self(NewType type);
* }
* </pre>
* In other words, the original interface type is replaced by the generated type in all places
* where it occurs. Most of the time, this is exactly the desired behavior, but there are some
* scenarios where a different type needs to be used instead. This occurs, for example, when an
* existing type declares static and non-static methods that need to be extracted into separate
* interfaces but need to refer to some common types. The {@link Map @Map} annotation can be
* used to define these mappings.
* 
* @author Mirko Raner
**/
@Target(PACKAGE)
@Retention(RUNTIME)
public @interface Map
{
    /**
    * @return the original type within the context of the enclosing {@link Interface @Interface}
    * annotation that is to be mapped.
    **/
    Class<?> type();

    /**
    * @return the generated destination type by which the original type is to be replaced.
    **/
    String to();
}
