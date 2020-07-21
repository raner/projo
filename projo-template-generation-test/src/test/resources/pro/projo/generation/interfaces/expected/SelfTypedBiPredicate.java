//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
package pro.projo.generation.interfaces.test;
/* */
import javax.annotation.Generated;
/* */
/**
*
* This interface was extracted from java.util.function.BiPredicate.
*
**/
/* */
@Generated("pro.projo.generation.interfaces.InterfaceTemplateProcessor")
/* */
public interface SelfTypedBiPredicate<$ extends SelfTypedBiPredicate<$, T, U>, T, U>
{
/* */
    boolean test(T arg0, U arg1);
    SelfTypedBiPredicate<$, T, U> and(SelfTypedBiPredicate<? super T, ? super U> arg0);
    SelfTypedBiPredicate<$, T, U> negate();
    SelfTypedBiPredicate<$, T, U> or(SelfTypedBiPredicate<? super T, ? super U> arg0);
/* */
}
