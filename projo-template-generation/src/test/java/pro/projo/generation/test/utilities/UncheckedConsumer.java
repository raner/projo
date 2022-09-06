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
package pro.projo.generation.test.utilities;

/**
* The {@link UncheckedConsumer} is a variation of the {@link java.util.function.Consumer}
* interface that permits the {@link #accept(Object)} method to throw exceptions.
*
* @param <TYPE> the type of item consumed
*
* @author Mirko Raner
**/
public interface UncheckedConsumer<TYPE>
{
    void accept(TYPE object) throws Exception;
}
