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
package pro.projo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Expects @Expects} annotation is used for indicating that a method's parameter is of a
* different type than suggested by its signature. This is useful when overriding or implementing
* methods that have parameter types that are not on the compile-time classpath (but would be
* available on the runtime classpath).
*
* NOTE: When implementing a method that uses a type parameter for a parameter type in its original
* declaration, the {@code &#64;Expects} annotation needs to use the original type parameter's erasure
* for the method to be correctly implemented. For example:
* <pre>
*   package api;
*   interface Processor&lt;TYPE&gt;
*   {
*     void process(TYPE object);
*   }
* </pre>
* To implement this interface for the type {@code Integer}, one would use:
* <pre>
*   &#64;Implements("api.Processor&lt;java.lang.Integer&gt;")
*   interface IntegerProcessor
*   {
*     void process(&#64;Expects("java.lang.Object") Integer value) // !!!
*     {
*       // ...
*     }
*   }
* </pre>
* If the original type parameter was declared as a bounded parameter, e.g.,
* {@code interface Processor<TYPE extends Number>}, then the bounding type has to be used
* (e.g., {@code &#64;Expects("java.lang.Number")}).
*
* This annotation is only honored when using Projo's runtime code generation feature. It will have
* no effect (or may even cause errors) when used with proxy-based implementations.
*
* @author Mirko Raner
**/
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Expects
{
    /**
    * @return the fully qualified name of the parameter type.
    **/
    String value();
}
