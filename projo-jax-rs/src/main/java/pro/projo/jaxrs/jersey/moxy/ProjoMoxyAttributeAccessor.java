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

import org.eclipse.persistence.exceptions.DescriptorException;
import org.eclipse.persistence.internal.descriptors.InstanceVariableAttributeAccessor;
import org.eclipse.persistence.oxm.mappings.XMLDirectMapping;

/**
* The ...
*
* <P><B>NOTE:</B> this class leverages non-public MOXy/EclipseLink interfaces!</P>
*
* @author Mirko Raner
**/
public class ProjoMoxyAttributeAccessor extends InstanceVariableAttributeAccessor
{
    private final static long serialVersionUID = -8833824266081037342L;

    ProjoMoxyAttributeAccessor()
    {
        super();
    }
    /**
    * Replaces an {@link XMLDirectMapping}'s {@link InstanceVariableAttributeAccessor} with a corresponding
    * {@link ProjoMoxyAttributeAccessor} that is capable of accessing Projo objects.
    *
    * @param mapping the {@link XMLDirectMapping} to be modified
    **/
    public static void update(XMLDirectMapping mapping)
    {
        InstanceVariableAttributeAccessor accessor = (InstanceVariableAttributeAccessor)mapping.getAttributeAccessor();
        ProjoMoxyAttributeAccessor projoAccessor = new ProjoMoxyAttributeAccessor();
        projoAccessor.setAttributeField(accessor.getAttributeField());
        projoAccessor.setAttributeName(accessor.getAttributeName());
        projoAccessor.setIsWriteOnly(accessor.isWriteOnly());
        projoAccessor.setIsReadOnly(accessor.isReadOnly());
        mapping.setAttributeAccessor(projoAccessor);
    }

    @Override
    public void setAttributeValueInObject(Object anObject, Object value) throws DescriptorException
    {
        // TODO Auto-generated method stub
        super.setAttributeValueInObject(anObject, value);
    }
}
