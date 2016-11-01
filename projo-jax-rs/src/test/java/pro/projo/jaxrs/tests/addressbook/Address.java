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

/**
* The {@link Address} interface defines mutable Projo objects that capture (US-centric) address information.
* This interfaces is only used by integration tests.
*
* @author Mirko Raner
**/
public interface Address
{
    public String getStreet();
    public void setStreet(String streetAddress);
    public String getCity();
    public void setCity(String city);
    public String getState();
    public void setState(String state);
    public String getZipCode();
    public void setZipCode(String zipCode);
}
