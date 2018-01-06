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

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
* A simple JAX-RS application for testing Projo with RESTful web services.
*
* @author Mirko Raner
**/
@Path("/addressbook")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface AddressBook
{
    @PUT
    @Path("/address/{contact}")
    void createOrUpdate(@PathParam("contact") Contact contact, Address address);

    @GET
    @Path("/address/{contact}")
    Address getAddress(@PathParam("contact") Contact contact);

    @PUT
    @Path("/phone/{contact}")
    void createOrUpdatePhoneNumber(@PathParam("contact") Contact contact, PhoneNumber address);

    @GET
    @Path("/phone/{contact}")
    PhoneNumber getPhoneNumber(@PathParam("contact") Contact contact);
}
