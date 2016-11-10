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
package pro.projo;

import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.internal.ProjoInvocationHandler;
import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * The {@link Projo} class provides static methods for creating Projo objects, as well as other
 * related utility methods.
 *
 * @author Mirko Raner
 */
public class Projo
{
    public static class Intermediate<_Artifact_>
    {
        private Class<_Artifact_> type;

        public Intermediate(Class<_Artifact_> type)
        {
            this.type = type;
        }

        public <_First_> pro.projo.singles.Factory<_Artifact_, _First_> with(Function<_Artifact_, _First_> first)
        {
            @SuppressWarnings("synthetic-access")
            pro.projo.singles.Factory<_Artifact_, _First_> factory = (argument1) ->
            {
                return initialize(type).members(first).with(argument1);
            };
            return factory;
        }

        public <_First_, _Second_> pro.projo.doubles.Factory<_Artifact_, _First_, _Second_> with(Function<_Artifact_, _First_> first, Function<_Artifact_, _Second_> second)
        {
            @SuppressWarnings("synthetic-access")
            pro.projo.doubles.Factory<_Artifact_, _First_, _Second_> factory = (argument1, argument2) ->
            {
                return initialize(type).members(first, second).with(argument1, argument2);
            };
            return factory;
        }

        public <_First_, _Second_, _Third_> pro.projo.triples.Factory<_Artifact_, _First_, _Second_, _Third_> with(Function<_Artifact_, _First_> first, Function<_Artifact_, _Second_> second, Function<_Artifact_, _Third_> third)
        {
            @SuppressWarnings("synthetic-access")
            pro.projo.triples.Factory<_Artifact_, _First_, _Second_, _Third_> factory = (argument1, argument2, argument3) ->
            {
                return initialize(type).members(first, second, third).with(argument1, argument2, argument3);
            };
            return factory;
        }
    }

    public static <_Artifact_> Intermediate<_Artifact_> creates(Class<_Artifact_> type)
    {
        return new Intermediate<>(type);
    }

    public static <_Artifact_> _Artifact_ create(Class<_Artifact_> type)
    {
        return initialize(type).members().with();
    }

    /**
     * Gets the factory (if any) of a Projo interface.
     *
     * @param projoInterface the Projo interface
     * @return the interface's {@link Factory}, or {@code null} if no factory was found
     */
    public static Factory getFactory(Class<?> projoInterface)
    {
        Stream<Field> fields = Stream.of(projoInterface.getDeclaredFields());
        Predicate<Field> isFactory = field -> Factory.class.isAssignableFrom(field.getType()) && isStatic(field.getModifiers());
        return fields.filter(isFactory).map(Projo::getFactory).findFirst().orElse(null);
    }

    private static Factory getFactory(Field field)
    {
        try
        {
            return (Factory)field.get(null);
        }
        catch (IllegalAccessException illegalAccess)
        {
            throw new IllegalArgumentException(illegalAccess.getMessage());
        }
    }

    private static <_Artifact_> ProjoInvocationHandler<_Artifact_>.Initializer initialize(Class<_Artifact_> type)
    {
        Class<?>[] interfaces = {type};
        ClassLoader loader = Projo.class.getClassLoader();
        ProjoInvocationHandler<_Artifact_> handler = new ProjoInvocationHandler<>();
        @SuppressWarnings("unchecked")
        _Artifact_ instance = (_Artifact_)newProxyInstance(loader, interfaces, handler);
        return handler.initialize(instance);
    }
}
