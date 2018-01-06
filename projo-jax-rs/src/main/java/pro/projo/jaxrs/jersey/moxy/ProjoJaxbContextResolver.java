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

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBException;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.TypeMappingInfo;
import org.eclipse.persistence.oxm.XMLContext;
import org.eclipse.persistence.oxm.XMLDescriptor;
import pro.projo.Projo;
import static java.util.Collections.singletonMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
* The {@link ProjoJaxbContextResolver} is a {@link ContextResolver} that provides modified {@link JAXBContext} objects
* whose descriptors are Projo-aware. The resolver will install a {@link ProjoInstantiationPolicy} that will create Projo
* objects when the target type is an interface.
*
* <P><B>NOTE:</B> this class indirectly leverages non-public MOXy/EclipseLink interfaces!</P>
*
* @author Mirko Raner
**/
@Provider
public class ProjoJaxbContextResolver implements ContextResolver<javax.xml.bind.JAXBContext>
{
    @Override
    public javax.xml.bind.JAXBContext getContext(Class<?> type)
    {
        try
        {
            Class<?> implementation = Projo.getImplementation().getImplementationClass(type);
            Class<?>[] domainClasses = {type, implementation};
            JAXBContext jaxbContext = (JAXBContext)JAXBContextFactory.createContext(domainClasses, null);
            @SuppressWarnings("rawtypes")
            HashMap<String, Class> generatedClasses = new HashMap<>(singletonMap(type.getName(), implementation));
            jaxbContext.setClassToGeneratedClasses(generatedClasses);
            //jaxbContext.
            try {
                Method getTypeToTypeMappingInfo = JAXBContext.class.getDeclaredMethod("getTypeToTypeMappingInfo");
                getTypeToTypeMappingInfo.setAccessible(true);
                Method getTypeMappingInfoToGeneratedType = JAXBContext.class.getDeclaredMethod("getTypeMappingInfoToGeneratedType");
                getTypeMappingInfoToGeneratedType.setAccessible(true);
                Map<Type, TypeMappingInfo> map0 = (Map<Type, TypeMappingInfo>) getTypeToTypeMappingInfo.invoke(jaxbContext);
                Map<TypeMappingInfo, Class<?>> map = (Map<TypeMappingInfo, Class<?>>) getTypeMappingInfoToGeneratedType.invoke(jaxbContext);
                TypeMappingInfo typeMappingInfo = map0.get(type);
                map.put(typeMappingInfo, implementation);
            } catch (NoSuchMethodException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            XMLContext xmlContext = jaxbContext.getXMLContext();
            ProjoInstantiationPolicy instantiationPolicy = new ProjoInstantiationPolicy();
            xmlContext.getDescriptors().stream().map(XMLDescriptor.class::cast).forEach(instantiationPolicy);
            return jaxbContext;
        }
        catch (JAXBException jaxbException)
        {
            throw new IllegalStateException(jaxbException);
        }
    }
}
