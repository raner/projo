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
package pro.projo.interfaces.annotation.utilities;

public class AttributeNameConverter
{
    public String convertAttributeName(String name)
    {
        int index;
        while ((index = name.indexOf('-')) != -1)
        {
            name = name.substring(0, index) +
                (index+1 >= name.length()? "":
                    (name.substring(index+1, index+2).toUpperCase() +
                        name.substring(index+2)));
        }
        return name;
    }
}
