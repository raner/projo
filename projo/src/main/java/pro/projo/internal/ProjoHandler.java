//                                                                          //
// Copyright 2019 - 2023 Mirko Raner                                        //
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

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import pro.projo.Mapping;
import pro.projo.annotations.Delegate;

/**
* The {@link ProjoHandler} and its nested member classes {@link ProjoHandler.ProjoInitializer ProjoInitializer} and
* {@link ProjoHandler.ProjoInitializer.ProjoMembers ProjoMembers} provide Projo's main service provider interface (SPI).
* Currently, the {@link ProjoHandler}'s only function is to carry the type parameter and serve as an enclosure for the
* {@link ProjoHandler.ProjoInitializer ProjoInitializer} and {@link ProjoHandler.ProjoInitializer.ProjoMembers ProjoMembers}
* classes.
*
* Starting with version 1.5.1, {@link #getImplementationOf(Class, boolean, ClassLoader)} requires a {@link ClassLoader}
* object to be passed, and will therefore not be compatible with external Projo implementations for
* previous versions.
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

            protected boolean isProxiedInterface(Class<?> reifiedType)
            {
                return Stream.of(reifiedType.getDeclaredMethods())
                    .anyMatch(method -> method.getAnnotation(Delegate.class) != null);
            }
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
        public abstract ProjoMembers delegate(Object delegate, Mapping<?> mapping);

        /**
        * Creates a {@link ProjoMembers} object based on a proxy object.
        *
        * @param delegate the delegate object
        * @param proxyInterface the proxy interface
        * @param override {@code true} if this proxy overrides base interface functionality
        * @return the {@link ProjoMembers}
        **/
        public abstract ProjoMembers proxy(Object delegate, Class<?> proxyInterface, boolean override);
    }

    /**
    * Returns the concrete Projo implementation class for a given abstract type. The implementation class
    * can be a proxy class or some other class that was dynamically created at runtime.
    *
    * @param type the interface or abstract type to be implemented
    * @param defaultPackage {@code true} if generated code should be placed in the default package, {@code false} otherwise
    * @param classLoader the {@link ClassLoader} to be used
    * @return the concrete implementation class
    **/
    public abstract Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type, boolean defaultPackage, ClassLoader classLoader);

    protected Optional<Method> getDelegateMethod(Method[] declaredMethods)
    {
        return Stream.of(declaredMethods)
            .filter(method -> method.isAnnotationPresent(Delegate.class))
            .findFirst();
    }
}
