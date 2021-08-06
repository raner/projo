//                                                                          //
// Copyright 2019 - 2021 Mirko Raner                                        //
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
package pro.projo.internal.rcg;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.ForLoadedType;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Valuable;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import pro.projo.Projo;
import pro.projo.annotations.Proxied;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.internal.PropertyMatcher;
import pro.projo.internal.rcg.runtime.DefaultToStringObject;
import pro.projo.internal.rcg.runtime.ToStringObject;
import pro.projo.internal.rcg.runtime.ToStringValueObject;
import pro.projo.internal.rcg.runtime.ValueObject;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.function.UnaryOperator.identity;
import static net.bytebuddy.ClassFileVersion.JAVA_V8;
import static net.bytebuddy.description.modifier.Visibility.PRIVATE;
import static net.bytebuddy.description.type.TypeDescription.OBJECT;
import static net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.INJECTION;
import static net.bytebuddy.implementation.bytecode.assign.Assigner.DEFAULT;
import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static pro.projo.internal.Predicates.getter;
import static pro.projo.internal.Predicates.setter;

/**
* The {@link RuntimeCodeGenerationHandler} is a {@link ProjoHandler} that generates implementation classes
* dynamically at runtime (using the {@link ByteBuddy} library). For each, object property the generated class
* will contain a field of the appropriate type, and the corresponding generated getter and setter will access
* that field directly, without using reflection. Generated implementation classes can be obtained by calling
* the {@link #getImplementationOf(Class)} method.
*
* @param <_Artifact_> the type of object being generated
*
* @author Mirko Raner
**/
public class RuntimeCodeGenerationHandler<_Artifact_> extends ProjoHandler<_Artifact_>
{
    private PropertyMatcher matcher = new PropertyMatcher();

    /**
    * {@code implementationClassCache} is the cache that maps interface classes to implementation classes.
    * This field does not need to be static because {@code RuntimeCodeGenerationHandler} is effectively a
    * singleton (because its enclosing class, {@code RuntimeCodeGenerationProjo}, is also a singleton).
    * Also, if it were static the {@code _Artifact_} type parameter would not be accessible.
    **/
    private Map<Class<_Artifact_>, Class<? extends _Artifact_>> implementationClassCache = new HashMap<>();

    private Predicate<Annotation> injected = annotation -> annotation.annotationType().getName().equals("javax.inject.Inject");

    private final static String SUFFIX = "$Projo";

    private static Map<List<Boolean>, Class<?>> baseClasses = new HashMap<>();

    static
    {
        @SuppressWarnings("unused")
        boolean valueObject, toString;
        baseClasses.put(asList(valueObject=false, toString=false), DefaultToStringObject.class);
        baseClasses.put(asList(valueObject=true, toString=false), ValueObject.class);
        baseClasses.put(asList(valueObject=false, toString=true), ToStringObject.class);
        baseClasses.put(asList(valueObject=true, toString=true), ToStringValueObject.class);
    }

    /**
    * Provides a class that implements the given type. All requests for the same type will return the same
    * implementation class.
    *
    * @param type the type (i.e., Projo interface)
    * @return the generated implementation class
    **/
    @Override
    public Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type)
    {
        return implementationClassCache.computeIfAbsent(type, this::generateImplementation);
    }

    public Class<? extends _Artifact_> getProxyImplementationOf(Class<_Artifact_> type, boolean override, Class<?>... additionalTypes)
    {
        return implementationClassCache.computeIfAbsent(type, it -> generateProxy(it, override, additionalTypes));
    }

    /**
    * Determines the Projo interface name (if any) of an implementation type.
    *
    * @param type the Projo implementation type
    * @return the interface name if the class was a Projo implementation class, otherwise the original type name
    **/
    public static String getInterfaceName(Class<?> type)
    {
        String name = type.getName();
        return name.endsWith(SUFFIX)? name.substring(0, name.length()-SUFFIX.length()):name;
    }

    private Class<? extends _Artifact_> generateImplementation(Class<_Artifact_> type)
    {
        Builder<_Artifact_> builder = create(type).name(implementationName(type));
        builder = Projo.getMethods(type, getter, setter).reduce(builder, this::add, sequentialOnly());
        return builder.make().load(type.getClassLoader(), INJECTION).getLoaded();
    }

    @SuppressWarnings("unchecked")
    private Class<? extends _Artifact_> generateProxy(Class<_Artifact_> type, boolean override, Class<?>... additionalTypes)
    {
        Builder<_Artifact_> builder = null;
        try
        {
            Type delegateType = override? type.getInterfaces()[0]:type;
            builder = (Builder<_Artifact_>)codeGenerator()
                .subclass(Object.class)
                .implement(type)
                .implement(additionalTypes)
                .name(implementationName(type))
                .defineField("delegate", delegateType)
                .defineConstructor(Modifier.PUBLIC)
                .withParameter(delegateType)
                .intercept(MethodCall.invoke(Object.class.getDeclaredConstructor())
                .andThen(FieldAccessor.ofField("delegate").setsArgumentAt(0)));
        }
        catch (NoSuchMethodException | SecurityException exception)
        {
            throw new Error(exception);
        }
        Predicate<Method> proxiable = method -> !method.isDefault();
        builder = Projo.getMethods(type, proxiable).reduce(builder, this::addProxy, sequentialOnly());
        return builder.make().load(type.getClassLoader()).getLoaded();
    }

    private Builder<_Artifact_> addProxy(Builder<_Artifact_> builder, Method method)
    {
        String methodName = method.getName();
        Type returnType = method.getReturnType();
        Type[] parameterTypes = method.getParameterTypes();
        Implementation methodCall;
        if (method.getAnnotation(Proxied.class) != null)
        {
            methodCall = FieldAccessor.ofField("delegate");
        }
        else
        {
            methodCall = MethodCall
                .invoke(method)
                .onField("delegate")
                .withAllArguments()
                .withAssigner(Assigner.DEFAULT, Assigner.Typing.DYNAMIC);
        }
        return builder
            .defineMethod(methodName, returnType)
            .withParameters(parameterTypes)
            .intercept(methodCall);
    }

    private Builder<_Artifact_> add(Builder<_Artifact_> builder, Method method)
    {
        boolean isGetter = getter.test(method);
        String methodName = method.getName();
        String propertyName = matcher.propertyName(methodName);
        UnaryOperator<Builder<_Artifact_>> addFieldForGetter;
        Optional<Annotation> inject = getInject(method);
        Type returnType = method.getReturnType();
        TypeDescription.Generic type = isGetter? getProcessedReturnType(inject, returnType):generic(ForLoadedType.of(void.class));
        addFieldForGetter = isGetter? localBuilder -> annotate(inject, localBuilder.defineField(propertyName, type, PRIVATE)):identity();
        Implementation implementation = inject.isPresent()? get(propertyName, returnType):FieldAccessor.ofField(propertyName);
        return addFieldForGetter.apply(builder).method(named(methodName)).intercept(implementation);
    }

    private Implementation get(String field, Type type)
    {
        Class<?> provider = getClass("javax.inject.Provider");
        Generic genericProvider = Generic.Builder.parameterizedType(provider, type).build();
        Generic typeParameter = genericProvider.getTypeArguments().get(0);
        MethodDescription get = latent(genericProvider.asErasure(), typeParameter, "get");
        return MethodCall.invoke(get).onField(field).withAssigner(DEFAULT, DYNAMIC);
    }

    private MethodDescription.Latent latent(TypeDescription declaringType, Generic returnType, String name)
    {
        return new MethodDescription.Latent(declaringType, name, PUBLIC, emptyList(), generic(OBJECT), emptyList(), emptyList(), emptyList(), null, null);
    }

    private TypeDescription.Generic generic(TypeDescription type)
    {
        return TypeDescription.Generic.Builder.rawType(type).build();
    }

    private ByteBuddy codeGenerator()
    {
        return new ByteBuddy(JAVA_V8);
    }

    private Generic getProcessedReturnType(Optional<Annotation> inject, Type originalReturnType)
    {
        boolean injected = inject.isPresent();
        Class<?> container = getClass(injected? "javax.inject.Provider":"java.util.Set");
        Generic generic = Generic.Builder.parameterizedType(container, originalReturnType).build();
        return injected? generic:generic.getTypeArguments().get(0);
    }

    private <_ValuableBuilder_ extends Valuable<_Artifact_> & Builder<_Artifact_>>
    Builder<_Artifact_> annotate(Optional<Annotation> annotation, _ValuableBuilder_ builder)
    {
        return annotation.isPresent()? builder.annotateField(annotation.get()):builder;
    }

    private Optional<Annotation> getInject(Method method)
    {
        return Stream.of(method.getAnnotations()).filter(injected).findFirst();
    }

    private Class<?> getClass(String name)
    {
        try
        {
            return Class.forName(name);
        }
        catch (ClassNotFoundException classNotFound)
        {
            throw new NoClassDefFoundError(classNotFound.getMessage());
        }
    }

    private <_Any_> BinaryOperator<_Any_> sequentialOnly()
    {
        return (operand1, operand2) ->
        {
            throw new UnsupportedOperationException("parallel stream processing not supported");
        };
    }

    private String implementationName(Class<_Artifact_> type)
    {
        String typeName = type.getName();
        if (typeName.startsWith("java."))
        {
            typeName = typeName.substring("java.".length());
        }
        return typeName + SUFFIX;
    }

    private Builder<_Artifact_> create(Class<_Artifact_> type)
    {
        List<Boolean> features = asList(Projo.isValueObject(type), Projo.hasCustomToString(type));
        Class<?> baseclass = baseClasses.get(features);
        @SuppressWarnings("unchecked")
        Builder<_Artifact_> builder = (Builder<_Artifact_>)codeGenerator().subclass(baseclass).implement(type, ProjoObject.class);
        return builder;
    }
}
