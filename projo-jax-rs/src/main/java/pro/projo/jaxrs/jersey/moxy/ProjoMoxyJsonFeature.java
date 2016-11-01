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

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
* The {@link ProjoMoxyJsonFeature} registers the custom {@link ProjoJaxbContextResolver} necessary for integrating
* Projo with MOXy.
*
* @author Mirko Raner
**/
public class ProjoMoxyJsonFeature implements Feature
{
    @Override
    public boolean configure(FeatureContext context)
    {
        context.register(ProjoJaxbContextResolver.class);
        return true;
    }
}
