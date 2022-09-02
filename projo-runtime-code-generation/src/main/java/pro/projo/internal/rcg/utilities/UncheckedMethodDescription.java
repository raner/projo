//                                                                          //
// Copyright 2021 - 2022 Mirko Raner                                        //
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
package pro.projo.internal.rcg.utilities;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import pro.projo.annotations.Delegate;
import pro.projo.annotations.Overrides;
import static pro.projo.annotations.Overrides.toString;

/**
* An {@link UncheckedMethodDescription} is a wrapper around a {@link MethodDescription}
* that will not verify the method's invocability on a certain type. The wrapper's
* {@link #isInvokableOn(TypeDescription)} method will always return {@code true}.
* All other methods will return the same result as the corresponding method of the
* wrapped method description.
*
* This class is a somewhat hacky work-around for problems when invoking methods
* that were implicitly inherited, as is the case when using
* {@link pro.projo.annotations.Implements @Implements} and
* {@link pro.projo.annotations.Returns @Returns} annotations.
* It is also an interesting example of bootstrapping
* in the Projo project: the interface uses Projo's own {@link Delegate} mechanism
* to easily modify the behavior of an existing Byte Buddy {@link MethodDescription}.
*
* @author Mirko Raner
**/
public interface UncheckedMethodDescription extends MethodDescription
{
  @Delegate
  MethodDescription delegated();

  /**
  * Determines if the described method can be invoked on the given type -
  * will always return {@code true}.
  *
  * @param type the type for which invocability is to be checked
  * @return always {@code true}
  **/
  @Override
  default boolean isInvokableOn(TypeDescription type)
  {
    return true;
  }

  @Overrides(toString)
  default String toStringOverride()
  {
    return "unchecked " + delegated();
  }
}