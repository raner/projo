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
package pro.projo.generation.test.utilities;

/**
* The {@link Mutable} class provides a mutable wrapper around immutable objects like
* {@link String}s or {@link Integer}s. It provides a nice alternative to single-element
* arrays when passing values from nested classes back into their enclosing scope (as
* the variable needs to be final to be accessible in the nested class).
*
* @param <_Type_> the type of the wrapped object
*
* @author Mirko Raner
**/
public class Mutable<_Type_> {

    private _Type_ object;

    /**
    * Creates a new {@link Mutable} with a {@code null} payload.
    **/
    public Mutable()
    {
        super();
    }

    /**
    * Creates a new {@link Mutable} that wraps the specified object.
    **/
    public Mutable(_Type_ object)
    {
        this.object = object;
    }

    /**
    * Sets the object that is wrapped by this {@link Mutable}.
    *
    * @param object the wrapped object
    * @return a reference to the {@link Mutable} itself for purposes of method chaining
    **/
    public Mutable<_Type_> set(_Type_ object)
    {
        this.object = object;
        return this;
    }

    /**
    * Gets the object wrapped in this {@link Mutable}.
    * @return the wrapped object
    **/
    public _Type_ get()
    {
        return object;
    }
}
