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
package pro.projo.jaxrs.jersey.moxy;

import org.eclipse.persistence.exceptions.DescriptorException;
import org.eclipse.persistence.internal.descriptors.InstantiationPolicy;
import pro.projo.Projo;

/**
* {@link ProjoInstantiationPolicy} is a custom {@link InstantiationPolicy} that will create a Projo object
* when the descriptor's target type is an interface.
*
* @author Mirko Raner
**/
public class ProjoInstantiationPolicy extends InstantiationPolicy
{
    private final static long serialVersionUID = -8661304064608845293L;

    @Override
    public Object buildNewInstance() throws DescriptorException
    {
        Class<?> javaClass = getDescriptor().getJavaClass();
        if (javaClass.isInterface())
        {
            return Projo.create(javaClass);
        }
        return super.buildNewInstance();
    }
}
