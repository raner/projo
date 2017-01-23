//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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
package pro.projo.internal.rcg;

import org.junit.Test;
import static java.lang.reflect.Proxy.isProxyClass;
import static org.junit.Assert.assertFalse;

public class RuntimeCodeGenerationHandlerTest
{
    public static interface Person
    {
        String getFirstName();
        void setFirstName(String name);
        String getLastName();
        void setLastName(String name);
    }

    static interface PackagePerson
    {
        String getFirstName();
        void setFirstName(String name);
        String getLastName();
        void setLastName(String name);
    }

    @Test
    public void testHandlerReturnsNonProxyClass()
    {
        RuntimeCodeGenerationHandler<Person> handler = new RuntimeCodeGenerationHandler<>();
        Class<? extends Person> result = handler.getImplementationOf(Person.class);
        assertFalse(isProxyClass(result));
    }

    @Test
    public void testHandlerCanImplementNonPublicClass()
    {
        RuntimeCodeGenerationHandler<PackagePerson> handler = new RuntimeCodeGenerationHandler<>();
        Class<? extends PackagePerson> result = handler.getImplementationOf(PackagePerson.class);
        assertFalse(isProxyClass(result));
    }
}
