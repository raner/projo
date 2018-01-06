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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import pro.projo.Projo;
import pro.projo.triples.Factory;

/**
* The {@link PhoneNumber} interface defines mutable Projo objects that capture (US-centric) phone numbers.
* This interface is only used by integration tests. This is obviously a poor modeling of what constitutes a phone
* number (especially as strings are being used to represent the various parts of the number), but this interface
* was purely written for testing Projo's capability to serialize and deserialize immutable objects.
*
* @author Mirko Raner
**/
//@XmlAccessorType(XmlAccessType.NONE)
public interface PhoneNumber
{
    Factory<PhoneNumber, String, String, String> FACTORY = Projo.creates(PhoneNumber.class)
        .with(PhoneNumber::getAreaCode, PhoneNumber::getExchange, PhoneNumber::getSubscriberNumber);

//    @XmlElement String getAreaCode();
//    @XmlElement String getExchange();
//    @XmlElement String getSubscriberNumber();
    String getAreaCode();
    String getExchange();
    String getSubscriberNumber();
}
