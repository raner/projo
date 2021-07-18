//                                                                          //
// Copyright 2020 - 2021 Mirko Raner                                        //
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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import static java.util.Collections.unmodifiableMap;

/**
* A {@link Mapping} maintains a bidirectional map between synthetic Projo types and
* their delegate types. {@link Mapping}s are used only for delegates, not for simple
* getter/setter-style Projo objects.
* The {@link Mapping} class follows an <i>immutable builder</i> pattern.
*
* @param <TYPE> the last mapped type
*
* @author Mirko Raner
**/
public abstract class Mapping<TYPE>
{
    public static interface Adapter<TYPE, ADAPTER>
    {
        Class<ADAPTER> type();

        Function<TYPE, ADAPTER> from();

        Function<ADAPTER, TYPE> to();
    }

    public abstract class Source
    {
        abstract Class<?> source();
        
        <TARGET> Mapping<TARGET> to(Class<? extends TARGET> destination)
        {
            return new Mapping<TARGET>()
            {
                @Override
                Class<TARGET> current()
                {
                    @SuppressWarnings("unchecked")
                    Class<TARGET> target =  (Class<TARGET>)destination;
                    return target;
                }

                @Override
                Map<Class<?>, Class<?>> syntheticToDelegate()
                {
                    HashMap<Class<?>, Class<?>> mapping = new HashMap<>(Mapping.this.syntheticToDelegate());
                    mapping.put(source(), destination);
                    return unmodifiableMap(mapping);
                }

                @Override
                Map<Class<?>, Class<?>> delegateToSynthetic()
                {
                    HashMap<Class<?>, Class<?>> mapping = new HashMap<>(Mapping.this.delegateToSynthetic());
                    mapping.put(destination, source());
                    return unmodifiableMap(mapping);
                }

                @Override
                public Map<Class<?>, Adapter<?, ?>> adapters()
                {
                    return Mapping.this.adapters();
                }
            };
        }
    }

    abstract Class<TYPE> current();

    abstract Map<Class<?>, Class<?>> syntheticToDelegate();

    abstract Map<Class<?>, Class<?>> delegateToSynthetic();

    abstract public Map<Class<?>, Adapter<?, ?>> adapters();

    public Class<?> getAdaptedType(Class<?> type)
    {
        Adapter<?, ?> identity = new Adapter<Object, Object>()
        {
            @Override
            @SuppressWarnings("unchecked")
            public Class<Object> type()
            {
                return (Class<Object>)type;
            }

            @Override
            public Function<Object, Object> from()
            {
                return null;
            }

            @Override
            public Function<Object, Object> to()
            {
                return null;
            }
        };
        return adapters().getOrDefault(type, identity).type();
    }

    public Class<?> getDelegate(Class<?> type)
    {
        return syntheticToDelegate().getOrDefault(type, type);
    }

    public Class<?> getSynthetic(Class<?> type)
    {
        return delegateToSynthetic().getOrDefault(type, type);
    }

    public Source map(Class<?> source)
    {
        return new Source()
        {
            @Override
            Class<?> source()
            {
              return source;
            }
        };
    }

    <ADAPTER> Mapping<TYPE> withAdapter(Class<ADAPTER> type,
        Function<TYPE, ADAPTER> from, Function<ADAPTER, TYPE> to)
    {
        Mapping<TYPE> mappingWithAdapter = new Mapping<TYPE>()
        {
            @Override
            Class<TYPE> current()
            {
                return Mapping.this.current();
            }

            @Override
            Map<Class<?>, Class<?>> syntheticToDelegate()
            {
                return Mapping.this.syntheticToDelegate();
            }

            @Override
            Map<Class<?>, Class<?>> delegateToSynthetic()
            {
                return Mapping.this.delegateToSynthetic();
            }

            @Override
            public Map<Class<?>, Adapter<?, ?>> adapters()
            {
                HashMap<Class<?>, Adapter<?, ?>> mapping = new HashMap<>(Mapping.this.adapters());
                @SuppressWarnings("rawtypes")
                Adapter<?, ?> adapter = new Adapter()
                {
                    @Override
                    public Class<?> type()
                    {
                        return type;
                    }

                    @Override
                    public Function<?, ?> from()
                    {
                        return from;
                    }

                    @Override
                    public Function<?, ?> to()
                    {
                        return to;
                    }
                };
                mapping.put(current(), adapter);
                return unmodifiableMap(mapping);
            }
        };
        return mappingWithAdapter;
    }
}
