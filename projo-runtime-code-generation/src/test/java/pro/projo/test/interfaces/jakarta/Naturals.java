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
package pro.projo.test.interfaces.jakarta;

import jakarta.inject.Inject;
import pro.projo.test.implementations.Natural;
import pro.projo.test.implementations.Utilities;

public interface Naturals<$ extends Naturals<$>> extends Provider<Natural>
{
    @Inject
    Utilities utilities();

    @Override
    public default Natural parse(Object literal)
    {
      return utilities().natural(String.valueOf(literal));
    }
}
