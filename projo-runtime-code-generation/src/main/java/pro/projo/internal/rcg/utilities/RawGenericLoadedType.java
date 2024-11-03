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
package pro.projo.internal.rcg.utilities;

import net.bytebuddy.description.type.TypeDescription.Generic;

/**
* {@link RawGenericLoadedType} is a customization of
* {@link Generic.OfNonGenericType.ForLoadedType} that provides the following
* additional features:
* <ul>
*  <li>it preserves annotations declared by the wrapped {@link Class}</li>
*  <li>it provides access to the original wrapped {@link Class} object</li>
* </ul>
* @author Mirko Raner
**/
public class RawGenericLoadedType extends Generic.OfNonGenericType.ForLoadedType
{
    private final Class<?> type;

    public RawGenericLoadedType(Class<?> type)
    {
        super(type, new AnnotationReader.Delegator.Simple(type));
        this.type = type;
    }

    public Class<?> getType()
    {
        return type;
    }
}
