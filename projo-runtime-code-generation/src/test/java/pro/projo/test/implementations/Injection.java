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

import pro.projo.ProjoImplementationClassTest;
import pro.projo.annotations.Returns;

/**
* The {@link Injection} interface is a test interface used by
* {@link ProjoImplementationClassTest}.
*
* @author Mirko Raner
**/
public interface Injection
{
    @Returns("org.codehaus.mojo.versions.api.change.VersionChange") default Object object()
    {
        return null;
    }
}
