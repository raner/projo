//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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
package pro.projo.internal;

import java.util.function.Function;
import pro.projo.Mapping;

/**
* The {@link ProjoHandler} and its nested member classes {@link ProjoHandler.ProjoInitializer ProjoInitializer} and
* {@link ProjoHandler.ProjoInitializer.ProjoMembers ProjoMembers} provide Projo's main service provider interface (SPI).
* Currently, the {@link ProjoHandler}'s only function is to carry the type parameter and serve as an enclosure for the
* {@link ProjoHandler.ProjoInitializer ProjoInitializer} and {@link ProjoHandler.ProjoInitializer.ProjoMembers ProjoMembers}
* classes.
*
* @param <_Artifact_> the artifact type
*
* @author Mirko Raner
**/
public abstract class ProjoHandler<_Artifact_>
{
    /**
    * The {@link ProjoInitializer} generates the {@link ProjoMembers} from a series of getter {@link Function}s.
    **/
    public abstract class ProjoInitializer
    {
        /**
        * The {@link ProjoMembers} class is responsible for instantiating an initialized or uninitialized Projo object.
        **/
        public abstract class ProjoMembers
        {
            /**
            * Creates a new Projo artifact that is initialized with the given values.
            *
            * @param values the object's field values (in the order of its members)
            * @return a fully initialized object (typically immutable)
            **/
            public abstract _Artifact_ with(Object... values);

            /**
            * Creates a new Projo artifact that is uninitialized (i.e., all of its fields contain default values).
            *
            * @return an uninitialized object (typically mutable, to allow for later initialization)
            **/
            public abstract _Artifact_ returnInstance();
        }

        /**
        * Creates a {@link ProjoMembers} object from a series of getter functions.
        *
        * @param getters the getter {@link Function}s
        * @return the {@link ProjoMembers}
        **/
        public abstract ProjoMembers members(@SuppressWarnings("unchecked") Function<_Artifact_, ?>... getters);

        /**
        * Creates a {@link ProjoMembers} object based on a delegate object.
        *
        * @param delegate the delegate object
        * @param mapping the type mapping to be used for delegation
        * @return the {@link ProjoMembers}
        **/
        public abstract ProjoMembers delegate(Object delegate, Mapping mapping);
    }

    /**
    * Returns the concrete Projo implementation class for a given abstract type. The implementation class
    * can be a proxy class or some other class that was dynamically created at runtime.
    *
    * @param type the interface or abstract type to be implemented
    * @return the concrete implementation class
    **/
    public abstract Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type);
}
