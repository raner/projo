//                                                                          //
// Copyright 2019 - 2023 Mirko Raner                                        //
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
package pro.projo.generation.utilities;

/**
* The {@link Name} class implements the {@link javax.lang.model.element.Name}
* interface by forwarding all methods to a wrapped string.
*
* @author Mirko Raner
**/
public class Name implements javax.lang.model.element.Name
{
    private String name;

    public Name(String name)
    {
        this.name = name;
    }

    @Override
    public int length()
    {
        return name.length();
    }

    @Override
    public char charAt(int index)
    {
        return name.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end)
    {
        return name.subSequence(start, end);
    }

    @Override
    public boolean contentEquals(CharSequence sequence)
    {
        return name.contentEquals(sequence);
    }

    @Override
    public int hashCode()
    {
        // TODO: this does not align with the hashCode() implementation of
        //       other types that implement Name and therefore causes inconsistencies
        //       (e.g., duplication of Names in a Set)
        //
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof javax.lang.model.element.Name))
        {
            return false;
        }
        return toString().equals(String.valueOf(other));
    }

    @Override
    public String toString()
    {
        return name;
    }
}
