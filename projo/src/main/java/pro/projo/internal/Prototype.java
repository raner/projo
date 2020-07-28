//                                                                          //
// Copyright 2017 - 2020 Mirko Raner                                        //
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
package pro.projo.internal;

import pro.projo.Projo;

/**
* The {@link Prototype} interface serves as the base interface of all generated intermediate
* interfaces. It declares an abstract method for determining the type of the objects that it should
* generate and implements an initialization method based on that abstract method.
*
* @param <_Artifact_> the object type
*
* @author Mirko Raner
**/
public interface Prototype<_Artifact_>
{
    /**
    * Returns the nominal interface type of the object.
    * @return the object type
    **/
    public Class<_Artifact_> type();

    /**
    * Creates a {@link ProjoHandler.ProjoInitializer}.
    * @param additionalInterfaces additional interfaces (if any) that should be implemented by the Projo object
    * @return an {@link ProjoHandler.ProjoInitializer Initializer} that creates a new Projo object
    **/
    public default ProjoHandler<_Artifact_>.ProjoInitializer initialize(Class<?>... additionalInterfaces)
    {
        return Projo.getImplementation().initializer(type(), additionalInterfaces);
    }
}
