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
package pro.projo;

import java.util.function.Function;

public interface Builder<_Artifact_>
{
    /**
    * Adds a new property to the build process.
    *
    * @param property the property's getter method
    * @param value the property value
    * @return a new {@link Builder} that includes new property
    **/
    <_Property_> Builder<_Artifact_> with(Function<_Artifact_, _Property_> property, _Property_ value);

    /**
    * Instantiates the object based on the {@link Builder}'s current description.
    *
    * @return the new object
    **/
    _Artifact_ build();
}
