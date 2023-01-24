//                                                                          //
// Copyright 2023 Mirko Raner                                               //
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
package pro.projo.test.implementations;

import javax.inject.Inject;
import pro.projo.annotations.Returns;

/**
* {@link pro.projo.test.implementations.Provider} is a test interface that is used by
* {@link pro.projo.ProjoProviderTest}.
*
* @author Mirko Raner
**/
public interface Provider<TYPE>
{
  @Inject @Returns("pro.projo.test.interfaces.Literals") Object literals();
  @Inject @Returns("java.util.Comparator<pro.projo.test.interfaces.Literals>") Object comparator();
}
