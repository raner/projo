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
package pro.projo.test.implementations;

import javax.inject.Inject;
import pro.projo.annotations.Implements;
import pro.projo.annotations.Returns;
import pro.projo.test.interfaces.Provider;

@Implements("pro.projo.test.interfaces.Decimals")
public interface Decimals extends Provider<Object>
{
    @Inject
    Utilities utilities();

    @Override
    @Returns("java.lang.Object") // TODO: why is this necessary??
    public default Object parse(Object literal)
    {
        return utilities().nullValue();
    }
}
