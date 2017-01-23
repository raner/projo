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
package pro.projo;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pro.projo.Projo.create;

/**
* {@link MutableProjoSinglesTest} is a JUnit test class that verifies the basic functionality of mutable
* Projos that have only a single field.
*
* @author Mirko Raner
**/
public class MutableProjoSinglesTest
{
    static interface UserId
    {
        void setId(String id);
        String getId();
    }

    static interface Account
    {
        void setNumber(long number);
        long getNumber();
    }

    @Test
    public void testCreateUserId()
    {
        UserId person = create(UserId.class);
        assertNotNull(person);
    }
    
    @Test
    public void testUserId()
    {
        UserId userId = create(UserId.class);
        userId.setId("12345678");
        assertEquals("12345678", userId.getId());
    }

    @Test
    public void testPrimitiveMember()
    {
        Account account = create(Account.class);
        account.setNumber(7815602394346543380L);
        assertEquals(7815602394346543380L, account.getNumber());
    }

    @Test
    public void testPrimitiveMemberNotInitialized()
    {
        Account account = create(Account.class);
        assertEquals(0L, account.getNumber());
    }
}
