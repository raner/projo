//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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
package pro.projo.interfaces.annotation;

import javax.lang.model.element.Modifier;

/**
* The {@link Visibility} enumeration defines constants for the four different
* visibility levels that Java elements can have. The {@link #PUBLIC},
* {@link #PRIVATE}, and {@link #PROTECTED} elements correspond to their
* respective {@link Modifier}s, whereas the {@link #PACKAGE} element corresponds
* to the absence of any visibility modifier.
*
* @author Mirko Raner
**/
public enum Visibility
{
    PUBLIC(Modifier.PUBLIC),
    PRIVATE(Modifier.PRIVATE),
    PROTECTED(Modifier.PROTECTED),
    PACKAGE(null);

    private Modifier modifier;

    private Visibility(Modifier modifier)
    {
        this.modifier = modifier;
    }

    /**
    * @return the corresponding {@link Modifier}, or {@code null} if none
    **/
    public Modifier modifier()
    {
        return modifier;
    }
}
