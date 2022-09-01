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
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
* The {@link Implements @Implements} annotation is used for indicating that an interface extends
* another interface in scenarios where the extended interface is only on the runtime classpath but
* not on the compile-time classpath. In other words, this annotation is a replacement for the
* {@code extends} keyword in an interface declaration where the extended interface cannot be named
* in the source code.
*
* @author Mirko Raner
**/
@Target(TYPE)
@Retention(RUNTIME)
public @interface Implements
{
    /**
    * The fully qualified names of the extended interfaces. For proxy-based implementation, the
    * type cannot contain type arguments for a parameterized type. For RCG-based implementation,
    * type arguments may be specified (e.g., {@code a.b.Cc<d.e.Ff, g.h.Ii>}) but only a
    * single level of type arguments is supported (i.e., {@code a.b.Cc<d.e.Ff<g.h.Ii>>} would not
    * work.
    **/
    String[] value();
}
