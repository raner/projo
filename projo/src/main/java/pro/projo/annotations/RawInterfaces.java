//                                                                          //
// Copyright 2024 Mirko Raner                                               //
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
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link RawInterfaces} annotation prevents Projo from reifying type arguments of
* extended interfaces during runtime code generation.
* For example, consider the following interface:
* <pre>
*   interface Decimals extends Provider&lt;Decimal&gt;
*   {
*     //...
*   }
* </pre>
* When Projo creates an implementation of this interface (for example, due to an
* {@link Implements @Implements} annotation on some other type) this will trigger
* loading of the {@code Decimal} class, as the default behavior is to reify all
* type arguments. In 99% of all cases, this is exactly what should happen anyway.
* However, there are some edge cases where this is a problem, for example if the
* {@code Decimal} type does not exist (yet), or loading it at this point would
* cause some issue (after all, {@link Implements @Implements} is specifically
* geared towards unusual compile-time/runtime bootstrapping scenarios).
* By annotating the type with {@link RawInterfaces @RawInterfaces} the implemented
* interfaces will be treated as if they were raw types.
* This has a few implications, however. The most significant being that the type
* parameter of the extended interface will not be replaced with the actual type
* argument (as raw types will not convey what that type argument is). Therefore,
* parameterized methods can only be invoked through their generic interface.
*
* @author Mirko Raner
**/
@Target(TYPE)
@Retention(RUNTIME)
public @interface RawInterfaces
{
    // This annotation currently has no fields.
}
