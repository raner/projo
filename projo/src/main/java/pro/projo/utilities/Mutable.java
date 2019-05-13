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
package pro.projo.utilities;

/**
* The {@link Mutable} class provides a mutable wrapper for otherwise immutable objects.
*
* @param <_Type_> the type of the wrapped object
*
* @author Mirko Raner
**/
public class Mutable<_Type_>
{
    private _Type_ object;

    public void set(_Type_ object)
    {
        this.object = object;
    }

    public _Type_ get()
    {
        return object;
    }
}
