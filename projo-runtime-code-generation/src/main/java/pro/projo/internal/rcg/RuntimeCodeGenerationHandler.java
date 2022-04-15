//                                                                          //
// Copyright 2017 - 2022 Mirko Raner                                        //
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
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Valuable;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.Implementation.Composable;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.utility.JavaConstant;
import pro.projo.Projo;
import pro.projo.annotations.Cached;
import pro.projo.annotations.Delegate;
import pro.projo.annotations.Overrides;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.internal.PropertyMatcher;
import pro.projo.internal.rcg.runtime.Cache;
import pro.projo.internal.rcg.runtime.DefaultToStringObject;
import pro.projo.internal.rcg.runtime.ToStringObject;
import pro.projo.internal.rcg.runtime.ToStringValueObject;
import pro.projo.internal.rcg.runtime.ValueObject;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Stream.empty;
import static net.bytebuddy.ClassFileVersion.JAVA_V8;
import static net.bytebuddy.description.modifier.Visibility.PRIVATE;
import static net.bytebuddy.description.type.TypeDescription.OBJECT;
import static net.bytebuddy.description.type.TypeDescription.VOID;
import static net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.INJECTION;
import static net.bytebuddy.implementation.bytecode.assign.Assigner.DEFAULT;
import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static pro.projo.internal.Predicates.cached;
import static pro.projo.internal.Predicates.getter;
import static pro.projo.internal.Predicates.setter;

/**
* The {@link RuntimeCodeGenerationHandler} is a {@link ProjoHandler} that generates implementation classes
* dynamically at runtime (using the {@link ByteBuddy} library). For each, object property the generated class
* will contain a field of the appropriate type, and the corresponding generated getter and setter will access
* that field directly, without using reflection. Generated implementation classes can be obtained by calling
* the {@link #getImplementationOf(Class, boolean)} method.
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
    public Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type, boolean defaultPackage)
    {
        return implementationClassCache.computeIfAbsent(type, it -> generateImplementation(it, defaultPackage));
    }

    public Class<? extends _Artifact_> getProxyImplementationOf(Class<_Artifact_> type, boolean override, boolean defaultPackage, Class<?>... additionalTypes)
    {
        return implementationClassCache.computeIfAbsent(type, it -> generateProxy(it, override, defaultPackage, additionalTypes));
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

    private Class<? extends _Artifact_> generateImplementation(Class<_Artifact_> type, boolean defaultPackage)
    {
        Stream<Method> cachedMethods = Projo.getMethods(type, cached);
        Builder<_Artifact_> builder = create(type).name(implementationName(type, defaultPackage));
        TypeDescription currentType = builder.make().getTypeDescription();
        builder = Projo.getMethods(type, getter, setter, cached).reduce(builder, this::add, sequentialOnly());
        builder = builder.defineConstructor(PUBLIC).intercept(constructor(currentType, cachedMethods));
        return builder.make().load(type.getClassLoader(), INJECTION).getLoaded();
    }

    @SuppressWarnings("unchecked")
    private Class<? extends _Artifact_> generateProxy(Class<_Artifact_> type, boolean override, boolean defaultPackage, Class<?>... additionalTypes)
    {
        Optional<Method> delegateMethod = getDelegateMethod(type.getDeclaredMethods());
        Builder<_Artifact_> builder = null;
        try
        {
            // Define the class and the delegate constructor:
            //
            Class<?> delegateType = override? type.getInterfaces()[0]:type;
            builder = (Builder<_Artifact_>)codeGenerator()
                .subclass(Object.class)
                .implement(type)
                .implement(additionalTypes)
                .name(implementationName(type, defaultPackage))
                .defineField("delegate", delegateType);
            builder = additionalAttributes(type, override)
                .reduce(builder, this::add, sequentialOnly())
                .defineConstructor(Modifier.PUBLIC)
                .withParameter(delegateType)
                .intercept(MethodCall.invoke(Object.class.getDeclaredConstructor())
                .andThen(FieldAccessor.ofField("delegate").setsArgumentAt(0)));
        }
        catch (NoSuchMethodException | SecurityException exception)
        {
            throw new Error(exception);
        }

        // Add proxy implementations for all non-default methods:
        //
        Predicate<Method> proxiable = method -> !method.isDefault()
                && (!method.getDeclaringClass().equals(type)
                    || !override || method.isAnnotationPresent(Delegate.class));
        builder = Projo.getMethods(type, proxiable)
            .reduce(builder, (it, method) -> addProxy(it, method, delegateMethod), sequentialOnly());

        // Add implementations that have @Overrides annotations:
        //
        builder = Stream.of(type.getDeclaredMethods())
            .map(method -> new SimpleEntry<>(method, method.getAnnotation(Overrides.class)))
            .filter(pair -> pair.getValue() != null)
            .map(pair -> new SimpleEntry<>(pair.getValue().value(), pair.getKey()))
            .reduce(builder, this::addOverrides, sequentialOnly());

        // Build and return the proxy class:
        //
        return builder.make().load(type.getClassLoader()).getLoaded();
    }

    private Stream<Method> additionalAttributes(Class<?> type, boolean override)
    {
        return override? Stream.of(type.getDeclaredMethods()).filter(Predicates.declaredAttribute):empty();
    }

    private Builder<_Artifact_> addOverrides(Builder<_Artifact_> builder, Entry<String, Method> method)
    {
        String methodName = method.getKey();
        Type returnType = method.getValue().getReturnType();
        Type[] parameterTypes = method.getValue().getParameterTypes();
        MethodCall methodCall = MethodCall.invoke(method.getValue()).withAllArguments();
        return builder
            .defineMethod(methodName, returnType)
            .withParameters(parameterTypes)
            .intercept(methodCall);
    }

    private Builder<_Artifact_> addProxy(Builder<_Artifact_> builder, Method method, Optional<Method> delegate)
    {
        String methodName = method.getName();
        Type returnType = method.getReturnType();
        Type[] parameterTypes = method.getParameterTypes();
        Implementation methodCall;
        if (method.isAnnotationPresent(Delegate.class))
        {
            methodCall = FieldAccessor.ofField("delegate");
        }
        else
        {
            if (delegate.isPresent())
            {
                MethodCall delegateMethodCall = MethodCall.invoke(delegate.get());
                methodCall = MethodCall.invoke(method).onMethodCall(delegateMethodCall).withAllArguments();
            }
            else
            {
                methodCall = MethodCall.invoke(method).onField("delegate").withAllArguments();
            }
        }
        return builder
            .defineMethod(methodName, returnType)
            .withParameters(parameterTypes)
            .intercept(methodCall);
    }

    /**
    * Adds method implementation and (if necessary) field definition for a method.
    *
    * @param builder the existing {@link Builder} to build upon
    * @param method a getter, setter, delegate or cached method
    * @return a new {@link Builder} with an additional method (and possibly an additional field)
    **/
    private Builder<_Artifact_> add(Builder<_Artifact_> builder, Method method)
    {
        Optional<Cached> cached = Optional.ofNullable(method.getAnnotation(Cached.class));
        boolean isGetter = getter.test(method) || method.isAnnotationPresent(Delegate.class) || cached.isPresent();
        String methodName = method.getName();
        String propertyName = matcher.propertyName(methodName);
        UnaryOperator<Builder<_Artifact_>> addFieldForGetter;
        Optional<Annotation> inject = getInject(method);
        Class<?> returnType = method.getReturnType();
        TypeDescription.Generic type = isGetter? getFieldType(inject, cached, returnType):VOID.asGenericType();
        addFieldForGetter = isGetter? localBuilder -> annotate(inject, localBuilder.defineField(propertyName, type, PRIVATE)):identity();
        Implementation implementation = getAccessor(inject, cached, returnType, propertyName);
        return addFieldForGetter.apply(builder).method(named(methodName)).intercept(implementation);
    }

    Implementation getAccessor(Optional<Annotation> inject, Optional<Cached> cached, Type returnType, String property)
    {
        if (inject.isPresent())
        {
            return get(property, returnType);
        }
        if (cached.isPresent())
        {
            return cached(cached.get(), property, returnType);
        }
        return FieldAccessor.ofField(property);
    }

    private Implementation constructor(TypeDescription currentType, Stream<Method> cachedMethods)
    {
        Method cacheCreator;
        try
        {
            cacheCreator = Cache.class.getDeclaredMethod("create", int.class, MethodHandle.class, Object.class);
        }
        catch (NoSuchMethodException exception)
        {
            throw new NoSuchMethodError(exception.getMessage());
        }
        Function<Method, Composable> initializer = method ->
        {
            TypeDescription returnType = new TypeDescription.ForLoadedType(method.getReturnType());
            TypeDescription genericCacheType = new TypeDescription.ForLoadedType(Cache.class);
            Generic cacheType = Generic.Builder.parameterizedType(genericCacheType, returnType).build();
            Cached annotation = method.getAnnotation(Cached.class);
            String fieldName = matcher.propertyName(method.getName());
            FieldDescription cacheField = new FieldDescription.Latent(currentType, fieldName, Modifier.PRIVATE, cacheType, emptyList());
            JavaConstant.MethodHandle originalMethod = JavaConstant.MethodHandle.ofSpecial(method, method.getDeclaringClass());
            return MethodCall
                .invoke(cacheCreator)
                .with(annotation.cacheSize())
                .with(originalMethod)
                .withThis()
                .setsField(cacheField);
        };
        Stream<Composable> initializers = cachedMethods.map(initializer);
        MethodDescription superConstructor = latent(currentType.getSuperClass().asErasure(), VOID.asGenericType(), "<init>");
        Composable invokeSuper = MethodCall.invoke(superConstructor).onSuper();
        return initializers.reduce(invokeSuper, Composable::andThen);
    }

    private Implementation cached(Cached cached, String field, Type type)
    {
        try
        {
            Method cacheAccessor = Cache.class.getDeclaredMethod("get", Object[].class);
            return MethodCall
                .invoke(cacheAccessor)
                .onField(field)
                .withArgumentArray()
                .withAssigner(DEFAULT, DYNAMIC);
        }
        catch (NoSuchMethodException exception)
        {
            throw new NoSuchMethodError(exception.getMessage());
        }
    }

    private Implementation get(String field, Type type)
    {
        Class<?> provider = getClass("javax.inject.Provider");
        Generic genericProvider = Generic.Builder.parameterizedType(provider, type).build();
        MethodDescription get = latent(genericProvider.asErasure(), OBJECT.asGenericType(), "get");
        return MethodCall.invoke(get).onField(field).withAssigner(DEFAULT, DYNAMIC);
    }

    private MethodDescription.Latent latent(TypeDescription declaringType, Generic returnType, String name, TypeDescription... parameterTypes)
    {
        return new MethodDescription.Latent(declaringType, name, PUBLIC, emptyList(), returnType, emptyList(), emptyList(), emptyList(), null, null);
    }

    private ByteBuddy codeGenerator()
    {
        return new ByteBuddy(JAVA_V8).with(TypeValidation.DISABLED);
    }

    /**
    * Determines the field type that is backing a method implementation. The field type is
    * not necessarily the same as the method's return type:
    * <ul>
    *  <li>for injected methods returning type {@code T}, the field type is {@code Provider<T>}</li>
    *  <li>for cached methods returning type {@code T}, the field type is
    *      {@code Cache<T>}</li>
    *  <li>for all other methods returning type {@code T}, the field type is {@code T}</li>
    * </ul>
    * @param inject an {@link Optional} {@link javax.inject.Inject} annotation
    * @param cached an {@link Optional} {@link pro.projo.annotations.Cached} annotation
    * @param originalReturnType the return type of the method
    * @return the appropriate field type for the method
    **/
    Generic getFieldType(Optional<Annotation> inject, Optional<Cached> cached, Class<?> originalReturnType)
    {
        if (!inject.isPresent() && !cached.isPresent())
        {
            return Generic.Builder.rawType(originalReturnType).build();
        }
        else
        {
            Class<?> container = inject.isPresent()? getClass("javax.inject.Provider"):Cache.class;
            Type wrappedType = MethodType.methodType(originalReturnType).wrap().returnType();
            return Generic.Builder.parameterizedType(container, wrappedType).build();
        }
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

    private String implementationName(Class<_Artifact_> type, boolean defaultPackage)
    {    
        String typeName = type.getName();
        if (defaultPackage)
        {
            typeName = type.getSimpleName();
        }
        else if (typeName.startsWith("java."))
        {
            typeName = typeName.substring("java.".length());
        }
        return typeName + SUFFIX;
    }

    private Class<?> baseclass(Class<_Artifact_> type)
    {
        List<Boolean> features = asList(Projo.isValueObject(type), Projo.hasCustomToString(type));
        return baseClasses.get(features);
    }

    private Builder<_Artifact_> create(Class<_Artifact_> type)
    {
        @SuppressWarnings("unchecked")
        Builder<_Artifact_> builder = (Builder<_Artifact_>)codeGenerator().subclass(baseclass(type)).implement(type, ProjoObject.class);
        return builder;
    }
}
