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
package pro.projo.test.implementations;

import pro.projo.annotations.Expects;
import pro.projo.annotations.Implements;
import pro.projo.annotations.Returns;

@Implements("pro.projo.test.interfaces.Processor<java.lang.Integer>")
public interface IntegerProcessor
{
    @Returns("java.lang.String")
    default String process(@Expects("java.lang.Object") Integer item)
    {
        int value = item.intValue();
        return String.valueOf(value);
    }
}
