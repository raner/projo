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
package pro.projo.jackson;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.deser.CreatorProperty;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.impl.PropertyValueBuffer;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.TypeFactory;
import pro.projo.Builder;
import pro.projo.Projo;
import pro.projo.internal.PropertyMatcher;
import pro.projo.utilities.MethodFunctionConverter;
import pro.projo.utilities.TryCatchUtilities;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
* The {@link ProjoJacksonValueInstantiator} is the main class for handling deserialization of Projo objects
* in Jackson.
*
* @param <_Artifact_> the value type
*
* @author Mirko Raner
**/
public class ProjoJacksonValueInstantiator<_Artifact_> extends ValueInstantiator.Base implements TryCatchUtilities
{
    private final static long serialVersionUID = -1973890909956713842L;

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
    public SettableBeanProperty[] getFromObjectArguments(DeserializationConfig configuration)
    {
        Method[] properties = Projo.getGetterMethods(getValueClass());
        return IntStream.range(0, properties.length)
            .mapToObj(index -> new SimpleEntry<>(index, properties[index]))
            .map(entry -> getSettableBeanProperty(entry, configuration))
            .toArray(SettableBeanProperty[]::new);
    }

    @Override
    public Object createFromObjectWith(DeserializationContext context, SettableBeanProperty[] properties, PropertyValueBuffer values) throws IOException
    {
        @SuppressWarnings("unchecked")
        Class<_Artifact_> type = Projo.getInterfaceClass((Class<_Artifact_>)getValueClass());
        Builder<_Artifact_> builder = Projo.builder(type);
        Method[] getters = Projo.getGetterMethods(type);
        DeserializationConfig configuration = context.getConfig();
        Map<String, SettableBeanProperty> propertyMap = Stream.of(properties).collect(toMap(SettableBeanProperty::getName, identity()));
        for (Method getter: getters)
        {
            Function<_Artifact_, Object> getterFunction = converter.convert(getter);
            String propertyName = getPropertyName(getter, configuration).getSimpleName();
            SettableBeanProperty property = propertyMap.get(propertyName);
            builder = builder.with(getterFunction, values.getParameter(property));
        }
        return builder.build();
    }

    @SuppressWarnings("deprecation")
    private SettableBeanProperty getSettableBeanProperty(Entry<Integer, Method> method, DeserializationConfig configuration)
    {
        Method getter = method.getValue();
        PropertyName propertyName = getPropertyName(getter, configuration);
        JavaType returnType = TypeFactory.defaultInstance().constructType(getter.getReturnType());
        return new CreatorProperty(propertyName, returnType, null, null, null, null, method.getKey(), null, null);
    }

    private PropertyName getPropertyName(Method getter, DeserializationConfig configuration)
    {
        JavaType type = configuration.getTypeFactory().constructType(getter.getDeclaringClass());
        @SuppressWarnings("deprecation")
        AnnotatedClass annotatedClass = AnnotatedClass.construct(type, configuration);
        AnnotatedMethod annotatedMethod = annotatedClass.findMethod(getter.getName(), getter.getParameterTypes());
        AnnotationIntrospector introspector = configuration.getAnnotationIntrospector();
        PropertyName name = introspector.findNameForDeserialization(annotatedMethod);
        return name != null? name:new PropertyName(propertyMatcher.propertyName(getter.getName()));
    }
}
