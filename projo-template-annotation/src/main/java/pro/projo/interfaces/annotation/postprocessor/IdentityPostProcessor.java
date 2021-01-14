//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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
package pro.projo.interfaces.annotation.postprocessor;

import java.io.Writer;
import java.util.function.UnaryOperator;

/**
* {@link IdentityPostProcessor} creates a post-processor that does nothing
* (by simply returning the original writer).
*
* @author Mirko Raner
**/
public class IdentityPostProcessor implements UnaryOperator<Writer>
{
    @Override
    public Writer apply(Writer writer)
    {
        return writer;
    }
}
