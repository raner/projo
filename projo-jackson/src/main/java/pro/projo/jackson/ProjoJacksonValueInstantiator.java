//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
package pro.projo.jackson;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import pro.projo.Builder;
import pro.projo.Projo;
import pro.projo.internal.PropertyMatcher;
import pro.projo.utilities.MethodFunctionConverter;
import pro.projo.utilities.TryCatchUtilities;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class ProjoJacksonValueInstantiator<_Artifact_> extends ValueInstantiator.Base implements TryCatchUtilities
{
    private PropertyMatcher propertyMatcher = new PropertyMatcher();
    private MethodFunctionConverter converter = new MethodFunctionConverter();

    public ProjoJacksonValueInstantiator(Class<_Artifact_> type)
    {
        super(type);
    }

    @Override
    public boolean canCreateFromObjectWith()
    {
        return true;
    }

    @Override
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig config)
    {
        Method[] properties = Projo.getGetterMethods(getValueClass());
        IntFunction<Entry<Integer, Method>> indexedMethod = index -> new SimpleEntry<>(index, properties[index]);
        return IntStream.range(0, properties.length)
            .mapToObj(indexedMethod)
            .map(this::getSettableBeanProperty)
            .toArray(SettableBeanProperty[]::new);
    }

    @Override
    public Object createFromObjectWith(DeserializationContext context, SettableBeanProperty[] properties, PropertyValueBuffer values) throws IOException
    {
        @SuppressWarnings("unchecked")
        Class<_Artifact_> type = Projo.getInterfaceClass((Class<_Artifact_>)getValueClass());
        Builder<_Artifact_> builder = Projo.builder(type);
        Method[] getters = Projo.getGetterMethods(type);
        Map<String, SettableBeanProperty> propertyMap = Stream.of(properties).collect(toMap(SettableBeanProperty::getName, identity()));
        for (Method getter: getters)
        {
            Function<_Artifact_, Object> getterFunction = converter.convert(getter);
            String propertyName = propertyMatcher.propertyName(getter.getName());
            SettableBeanProperty property = propertyMap.get(propertyName);
            builder = builder.with(getterFunction, values.getParameter(property));
        }
        return builder.build();
    }

    private SettableBeanProperty getSettableBeanProperty(Entry<Integer, Method> method)
    {
        Method getter = method.getValue();
        PropertyName name = new PropertyName(propertyMatcher.propertyName(getter.getName()));
        JavaType type = TypeFactory.defaultInstance().constructType(getter.getReturnType());
        return new CreatorProperty(name, type, null, null, null, null, method.getKey(), null, null);
    }
}
