//                                                                          //
// Copyright 2018 - 2020 Mirko Raner                                        //
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
package pro.projo.generation.interfaces.test.classes;

public class ObjectFactory
{
    public static <T> T createObject(Class<T> type)
    {
        try
        {
            return type.getDeclaredConstructor().newInstance();
        }
        catch (@SuppressWarnings("unused") Exception exception)
        {
            return null;
        }
    }

    public static ObjectFactory createObjectFactory()
    {
        return new ObjectFactory();
    }
}
