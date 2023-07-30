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

import java.lang.reflect.Method;
import java.util.stream.Stream;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.introspect.POJOPropertyBuilder;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.impl.AttributePropertyWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import pro.projo.Projo;
import pro.projo.internal.PropertyMatcher;
import static com.fasterxml.jackson.annotation.JsonInclude.Value.empty;

/**
* The {@link ProjoJacksonSerializer} class is an implementation of a Jackson
* {@link BeanSerializer} that is capable of serializing Projo objects.
*
* @author Mirko Raner
**/
public class ProjoJacksonSerializer extends BeanSerializer
{
    private final static long serialVersionUID = 7949066175006042890L;

    private static TypeFactory typeFactory = TypeFactory.defaultInstance();
    private static PropertyMatcher propertyMatcher = new PropertyMatcher();

    protected ProjoJacksonSerializer(SerializationConfig configuration, JavaType type, BeanDescription bean)
    {
        // TODO: check if handling filtered properties is necessary (currently always passing null)
        super(type, null, properties(configuration, type, bean), null);
    }

    private static BeanPropertyWriter[] properties(SerializationConfig configuration, JavaType type, BeanDescription bean)
    {
        Stream<Method> getters = Stream.of(Projo.getGetterMethods(type.getRawClass()));
        return getters.map(getter -> getBeanPropertyWriter(configuration, bean, getter)).toArray(BeanPropertyWriter[]::new);
    }

    private static BeanPropertyWriter getBeanPropertyWriter(SerializationConfig configuration, BeanDescription bean, Method method)
    {
        String propertyName = propertyMatcher.propertyName(method.getName());
        AnnotationIntrospector introspector = configuration.getAnnotationIntrospector();
        BeanPropertyDefinition propDef = new POJOPropertyBuilder(configuration, introspector, true, new PropertyName(propertyName))
        {
            @Override
            public AnnotatedMethod getGetter()
            {
                // TODO: check if null AnnotationMap is OK here
                return new AnnotatedMethod(bean.getClassInfo(), method, null, new AnnotationMap[] {});
            }
        };
        Annotations contextAnnotations = null; // TODO: handle context annotations
System.err.println("====== Property: " + method.getReturnType() + " " + propertyName);
        JavaType propertyType = typeFactory.constructType(method.getReturnType());
        return new AttributePropertyWriter(propertyName, propDef, contextAnnotations, propertyType, empty())
        {
            private final static long serialVersionUID = -8204427561857269317L;

            @Override
            protected Object value(Object object, JsonGenerator generator, SerializerProvider serializerProvider) throws Exception
            {
                // TODO: do we need to use the JsonGenerator and the SerializerProvider for anything?
                return method.invoke(object);
            }
        };
    }
}
