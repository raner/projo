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
package pro.projo.jaxrs.tests.addressbook;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

/**
* {@link AddressBookImpl} is a simple in-memory implementation of the {@link AddressBook} JAX-RS interface.
* This class is only used for testing Projo's integrations with JAX-RS.
*
* @author Mirko Raner
**/
@Singleton
public class AddressBookImpl implements AddressBook
{
    private Map<Contact, Address> map = new HashMap<>();
    private Map<Contact, PhoneNumber> phoneNumbers = new HashMap<>();

    {
        Contact defaultContact = new Contact();
        defaultContact.setFirstName("Default");
        defaultContact.setLastName("Contact");
        PhoneNumber defaultNumber = PhoneNumber.FACTORY.create("123", "456", "7890");
        phoneNumbers.put(defaultContact, defaultNumber);
    }

    @Override
    public void createOrUpdate(Contact contact, Address address)
    {
        map.put(contact, address);
    }

    @Override
    public Address getAddress(Contact contact)
    {
        return map.get(contact);
    }

    @Override
    public void createOrUpdatePhoneNumber(Contact contact, PhoneNumber number)
    {
        phoneNumbers.put(contact, number);
    }

    @Override
    public PhoneNumber getPhoneNumber(Contact contact)
    {
        return phoneNumbers.get(contact);
    }
}
