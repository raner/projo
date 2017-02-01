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
package pro.projo.jaxrs.jersey.moxy;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.eclipse.persistence.internal.descriptors.InstantiationPolicy;
import org.eclipse.persistence.internal.oxm.mappings.Descriptor;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.oxm.XMLContext;

/**
* The {@link ProjoJaxbContextResolver} is a {@link ContextResolver} that provides modified {@link JAXBContext} objects
* whose descriptors are Projo-aware. The resolver will install an {@link InstantiationPolicy} that will create Projo
* objects when the target type is an interface.
*
* <P><B>NOTE:</B> this class leverages non-public MOXy/EclipseLink interfaces!</P>
*
* @author Mirko Raner
**/
@Provider
public class ProjoJaxbContextResolver implements ContextResolver<JAXBContext>
{
    @Override
    public JAXBContext getContext(Class<?> type)
    {
        try
        {
            Class<?>[] domainClasses = {type};
            JAXBContext context = JAXBContextFactory.createContext(domainClasses, null);
            org.eclipse.persistence.jaxb.JAXBContext moxyContext = (org.eclipse.persistence.jaxb.JAXBContext)context;
            XMLContext xmlContext = moxyContext.getXMLContext();
            InstantiationPolicy instantiationPolicy = new ProjoInstantiationPolicy();
            for (Descriptor<?, ?, ?, ?, InstantiationPolicy, ?, ?, ?, ?, ?> descriptor: xmlContext.getDescriptors())
            {
                descriptor.setInstantiationPolicy(instantiationPolicy);
            }
            return moxyContext;
        }
        catch (JAXBException jaxbException)
        {
            throw new IllegalStateException(jaxbException);
        }
    }
}
