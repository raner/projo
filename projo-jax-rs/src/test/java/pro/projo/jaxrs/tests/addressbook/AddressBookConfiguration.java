//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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

import org.glassfish.jersey.server.ResourceConfig;

/**
* A simple Jersey JAX-RS configuration for testing Projo with RESTful web services.
*
* @author Mirko Raner
**/
public class AddressBookConfiguration extends ResourceConfig
{
    public AddressBookConfiguration()
    {
        register(AddressBookImpl.class);
        register(ContactParameterConverterProvider.class);
    }
}
