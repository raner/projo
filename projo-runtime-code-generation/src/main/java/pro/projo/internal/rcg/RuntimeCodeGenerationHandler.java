//                                                                          //
// Copyright 2017 - 2023 Mirko Raner                                        //
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

import java.io.File;
import java.io.IOError;
import java.io.IOException;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Valuable;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ImplementationDefinition;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.DefaultMethodCall;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.Implementation.Composable;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.utility.JavaConstant;
import pro.projo.Projo;
import pro.projo.annotations.Cached;
import pro.projo.annotations.Delegate;
import pro.projo.annotations.Expects;
import pro.projo.annotations.Implements;
import pro.projo.annotations.Inherits;
import pro.projo.annotations.Overrides;
import pro.projo.annotations.Returns;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.internal.PropertyMatcher;
import pro.projo.internal.rcg.runtime.Cache;
import pro.projo.internal.rcg.runtime.DefaultToStringObject;
import pro.projo.internal.rcg.runtime.ToStringObject;
import pro.projo.internal.rcg.runtime.ToStringValueObject;
import pro.projo.internal.rcg.runtime.ValueObject;
import pro.projo.internal.rcg.utilities.UncheckedMethodDescription;
import pro.projo.utilities.AnnotationList;
import pro.projo.utilities.MethodInfo;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.empty;
import static net.bytebuddy.ClassFileVersion.JAVA_V8;
import static net.bytebuddy.description.modifier.Visibility.PRIVATE;
import static net.bytebuddy.description.type.TypeDescription.OBJECT;
import static net.bytebuddy.description.type.TypeDescription.VOID;
import static net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.INJECTION;
import static net.bytebuddy.implementation.bytecode.assign.Assigner.DEFAULT;
import static net.bytebuddy.implementation.bytecode.assign.Assigner.Typing.DYNAMIC;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static pro.projo.Projo.delegateOverride;
import static pro.projo.Projo.getMethods;
import static pro.projo.internal.Predicates.cached;
import static pro.projo.internal.Predicates.expects;
import static pro.projo.internal.Predicates.getter;
import static pro.projo.internal.Predicates.overrides;
import static pro.projo.internal.Predicates.returns;
import static pro.projo.internal.Predicates.setter;

/**
* The {@link RuntimeCodeGenerationHandler} is a {@link ProjoHandler} that generates implementation classes
* dynamically at runtime (using the {@link ByteBuddy} library). For each, object property the generated class
* will contain a field of the appropriate type, and the corresponding generated getter and setter will access
* that field directly, without using reflection. Generated implementation classes can be obtained by calling
* the {@link #getImplementationOf(Class, boolean, ClassLoader)} method.
*
* @param <_Artifact_> the type of object being generated
*
* @author Mirko Raner
**/
public class RuntimeCodeGenerationHandler<_Artifact_> extends ProjoHandler<_Artifact_>
{
    private final PropertyMatcher matcher = new PropertyMatcher();

    private final Pattern typeNamePattern = Pattern.compile("(?<base>[^<>]+)(<(?<arguments>[^>]+)>)?");

    /**
    * {@code implementationClassCache} is the cache that maps interface classes to implementation classes.
    * This field does not need to be static because {@code RuntimeCodeGenerationHandler} is effectively a
    * singleton (because its enclosing class, {@code RuntimeCodeGenerationProjo}, is also a singleton).
    * Also, if it were static the {@code _Artifact_} type parameter would not be accessible.
    **/
    private Map<Class<_Artifact_>, Class<? extends _Artifact_>> implementationClassCache =
        new ConcurrentHashMap<>();

    private final String injected = "javax.inject.Inject";

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
    * Pre-loads the implementation for {@link UncheckedMethodDescription} into the cache.
    * Without this, an {@code IllegalStateException: Recursive update} might be thrown when
    * {@link RuntimeCodeGenerationHandler} uses its own code generation for creating a modified
    * version of Byte Buddy's {@link MethodDescription} class.
    **/
    public void postInitialize()
    {
        try
        {
            // Just pick an arbitrary method:
            Method method = getClass().getDeclaredMethod("getInterfaceName", Class.class);
            MethodDescription description = new MethodDescription.ForLoadedMethod(method);
            delegateOverride(description, UncheckedMethodDescription.class);
        }
        catch (NoSuchMethodException noSuchMethod)
        {
            throw new NoSuchMethodError(noSuchMethod.getMessage());
        }
    }

    /**
    * Provides a class that implements the given type. All requests for the same type will return the same
    * implementation class.
    * <p>
    * <b>NOTE:</b> a {@link ClassLoader} will be passed only when this method is invoked from
    * {@link Projo#getImplementationClass(Class, ClassLoader)}; {@link RuntimeCodeGenerationProjo}
    * will always pass a {@code null} reference
    *
    * @param type the type (i.e., Projo interface)
    * @param defaultPackage flag indicating whether the generated class should be placed in the default package
    * @param classLoader the {@link ClassLoader} to be used
    * @return the generated implementation class
    **/
    @Override
    public Class<? extends _Artifact_> getImplementationOf(Class<_Artifact_> type, boolean defaultPackage, ClassLoader classLoader)
    {
        return implementationClassCache.computeIfAbsent(type, it -> generateImplementation(it, defaultPackage, classLoader));
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

    private Class<? extends _Artifact_> generateImplementation(Class<_Artifact_> type, boolean defaultPackage, ClassLoader classLoader)
    {
        Stream<Method> cachedMethods = Projo.getMethods(type, classLoader, cached);
        List<String> additionalImplements = getImplements(type);
        Builder<_Artifact_> builder = create(type, additionalImplements, classLoader).name(implementationName(type, defaultPackage));
        TypeDescription currentType = builder.make().getTypeDescription();
        return debug(getMethods(type, classLoader, additionalImplements, getter, setter, cached, overrides, returns, expects)
            .reduce(builder, (accumulator, method) -> add(accumulator, method, additionalImplements, classLoader), sequentialOnly())
            .defineConstructor(PUBLIC).intercept(constructor(type, currentType, cachedMethods))
            .make().load(classLoader(type, defaultPackage, classLoader), INJECTION)).getLoaded();
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
                .reduce(builder, (accumulator, method) -> add(accumulator, method, emptyList(), null), sequentialOnly())
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
        return debug(builder.make().load(classLoader(type, defaultPackage, null))).getLoaded();
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
    * @param additionalImplements additional interfaces implemented by means of {@link Implements} annotations
    * @return a new {@link Builder} with an additional method (and possibly an additional field)
    **/
    private Builder<_Artifact_> add(Builder<_Artifact_> builder, Method method, List<String> additionalImplements, ClassLoader classLoader)
    {
        AnnotationList annotations = new AnnotationList(method);
        boolean isGetter = getter.test(method) || annotations.contains(Delegate.class) || annotations.contains(Cached.class);
        String methodName = annotations.get(Overrides.class).map(Overrides::value).orElse(method.getName());
        String propertyName = matcher.propertyName(methodName);
        UnaryOperator<Builder<_Artifact_>> addFieldForGetter;
        Optional<Annotation> inject = annotations.get(injected);
        Class<?> returnType = method.getReturnType();
        TypeDescription.Generic type = isGetter? getFieldType(annotations, returnType, classLoader):VOID.asGenericType();
        addFieldForGetter = isGetter? localBuilder -> annotate(inject, localBuilder.defineField(propertyName, type, PRIVATE)):identity();
        Implementation implementation = getAccessor(method, annotations, returnType, propertyName, additionalImplements, classLoader);
        Optional<Returns> returns = annotations.get(Returns.class);
        Optional<Inherits> inherits = annotations.get(Inherits.class);
        List<Optional<Expects>> expects = Stream.of(method.getParameters())
            .map(it -> Optional.ofNullable(it.getAnnotation(Expects.class)))
            .collect(toList());
        if (!inherits.isPresent()
        && (returns.isPresent() || expects.stream().anyMatch(Optional::isPresent)))
        {
            Class<?> returnsType = returns
                .map(Returns::value)
                .map(it -> Projo.forName(it, classLoader))
                .map(Class.class::cast)
                .orElse(returnType);
            @SuppressWarnings("rawtypes")
            List<Class> parameterTypes = IntStream.range(0, expects.size())
                .mapToObj(index -> expects.get(index)
                    .map(Expects::value)
                    .map(it -> Projo.forName(it, classLoader))
                    .map(Class.class::cast)
                    .orElse(method.getParameterTypes()[index]))
                    .collect(toList());
            return addFieldForGetter.apply(builder)
                .defineMethod(methodName, returnsType, PUBLIC)
                .withParameters(parameterTypes.toArray(new Type[] {}))
                .intercept(implementation);
        }
        Function<Builder<_Artifact_>, ImplementationDefinition<_Artifact_>> createMethod = methodBuilder ->
        {
            if (inherits.isPresent())
            {
                return methodBuilder
                    .defineMethod(methodName, method.getReturnType(), PUBLIC)
                    .withParameters(method.getParameterTypes());
            }
            else
            {
                return methodBuilder.method(named(methodName));
            }
        };
        return createMethod.apply(addFieldForGetter.apply(builder)).intercept(implementation);
    }

    Implementation getAccessor(Method method, AnnotationList annotations, Type returnType, String property, List<String> additionalImplements, ClassLoader classLoader)
    {
        if (annotations.contains(injected))
        {
            return get(property, returnType, classLoader);
        }
        if (annotations.contains(Cached.class))
        {
            return cached(annotations.get(Cached.class).get(), property, returnType);
        }
        if (annotations.contains(Overrides.class))
        {
            return MethodCall.invoke(method).withAllArguments().withAssigner(DEFAULT, DYNAMIC);
        }
        if (annotations.contains(Inherits.class))
        {
            MethodInfo methodInfo = new MethodInfo(method, classLoader);
            TypeDescription declaring = new TypeDescription.ForLoadedType(Projo.forName(additionalImplements.get(0), classLoader));
            Generic returns = new TypeDescription.ForLoadedType(methodInfo.returnType()).asGenericType();
            TypeDescription[] parameters = methodInfo.parameterTypes().stream()
                .map(TypeDescription.ForLoadedType::new)
                .toArray(TypeDescription[]::new);
            MethodDescription inherited = latent(declaring, returns, method.getName(), parameters);
            return MethodCall.invoke(inherited).withAllArguments().withAssigner(DEFAULT, DYNAMIC);
        }
        if (annotations.contains(Returns.class) || expects.test(method))
        {
            MethodDescription description = new MethodDescription.ForLoadedMethod(method);
            MethodDescription unchecked = delegateOverride(description, UncheckedMethodDescription.class);
            return MethodCall.invoke(unchecked).onSuper().withAllArguments().withAssigner(DEFAULT, DYNAMIC);
        }
        Stream<Method> methods = additionalImplements.stream()
            .map(it -> Projo.forName(it, classLoader))
            .flatMap(type -> Stream.of(type.getDeclaredMethods()));
        Predicate<Method> sameSignature = match ->
            method.getName().equals(match.getName()) &&
            method.getReturnType().equals(match.getReturnType()) &&
            asList(method.getParameterTypes()).equals(asList(match.getParameterTypes()));
        List<Method> matchingMethod = methods.filter(sameSignature).collect(toList());
        if (matchingMethod.size() == 1)
        {
            return DefaultMethodCall.prioritize(matchingMethod.get(0).getDeclaringClass());
        }
        return FieldAccessor.ofField(property);
    }

    private Implementation constructor(Class<?> originalType, TypeDescription currentType, Stream<Method> cachedMethods)
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
            JavaConstant.MethodHandle originalMethod = JavaConstant.MethodHandle.ofSpecial(method, originalType);
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

    private Implementation get(String field, Type type, ClassLoader classLoader)
    {
        Class<?> provider = Projo.forName("javax.inject.Provider", classLoader);
        Generic genericProvider = Generic.Builder.parameterizedType(provider, type).build();
        MethodDescription get = latent(genericProvider.asErasure(), OBJECT.asGenericType(), "get");
        return MethodCall.invoke(get).onField(field).withAssigner(DEFAULT, DYNAMIC);
    }

    private MethodDescription.Latent latent(TypeDescription declaringType, Generic returnType, String name, TypeDescription... parameterTypes)
    {
        List<? extends ParameterDescription.Token> parameterTokens = Stream.of(parameterTypes)
            .map(TypeDescription::asGenericType)
            .map(ParameterDescription.Token::new)
            .collect(toList());
        return new MethodDescription.Latent(declaringType, name, PUBLIC, emptyList(), returnType, parameterTokens, emptyList(), emptyList(), null, null);
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
    Generic getFieldType(AnnotationList annotations, Class<?> originalReturnType, ClassLoader classLoader)
    {
        if (!annotations.contains(injected) && !annotations.contains(Cached.class))
        {
            return Generic.Builder.rawType(originalReturnType).build();
        }
        else
        {
            Class<?> container = annotations.contains(injected)? Projo.forName("javax.inject.Provider", classLoader):Cache.class;
            Type wrappedType = MethodType.methodType(originalReturnType).wrap().returnType();
            Optional<Returns> returns = annotations.get(Returns.class);
            if (returns.isPresent())
            {
                String returnType = returns.get().value();
                int index = returnType.indexOf('<');
                if (index == -1)
                {
                    return Generic.Builder.rawType(Projo.forName(returnType, classLoader)).build();
                }
                else
                {
                    Class<?> rawType = Projo.forName(returnType.substring(0, index), classLoader);
                    String[] parameters = returnType.substring(index+1, returnType.length()-1).split("[, ]");
                    Stream<Class<?>> typeParameters = Stream.of(parameters).map(it -> Projo.forName(it, classLoader));
                    Generic parameterizedType = Generic.Builder.parameterizedType(rawType, typeParameters.toArray(Type[]::new)).build();
                    TypeDescription containerType = Generic.Builder.rawType(container).build().asErasure();
                    return Generic.Builder.parameterizedType(containerType, parameterizedType).build();
                }
            }
            else
            {
                return Generic.Builder.parameterizedType(container, wrappedType).build();
            }
        }
    }

    private <_ValuableBuilder_ extends Valuable<_Artifact_> & Builder<_Artifact_>>
    Builder<_Artifact_> annotate(Optional<Annotation> annotation, _ValuableBuilder_ builder)
    {
        return annotation.isPresent()? builder.annotateField(annotation.get()):builder;
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

    private ClassLoader classLoader(Class<?> type, boolean defaultPackage, ClassLoader classLoader)
    {
        if (classLoader != null)
        {
            return classLoader;
        }
        return defaultPackage? Thread.currentThread().getContextClassLoader():type.getClassLoader();
    }

    private Class<?> baseclass(Class<_Artifact_> type)
    {
        List<Boolean> features = asList(Projo.isValueObject(type), Projo.hasCustomToString(type));
        return baseClasses.get(features);
    }

    private Builder<_Artifact_> create(Class<_Artifact_> type, List<String> additionalImplements, ClassLoader classLoader)
    {
        Stream<TypeDefinition> baseTypes = Stream.of(type(type), type(ProjoObject.class));
        TypeDefinition[] interfaces = Stream.concat(baseTypes, additionalImplements.stream().map(it -> type(it, classLoader))).toArray(TypeDefinition[]::new);
        @SuppressWarnings("unchecked")
        Builder<_Artifact_> builder = (Builder<_Artifact_>)codeGenerator().subclass(baseclass(type)).implement(interfaces);
        return builder;
    }

    private List<String> getImplements(Class<_Artifact_> type)
    {
        Implements annotation = type.getAnnotation(Implements.class);
        Stream<String> implement = annotation != null? Stream.of(annotation.value()):Stream.empty();
        return implement.collect(toList());
    }

    private TypeDefinition type(Class<?> type)
    {
        return new TypeDescription.ForLoadedType(type);
    }

    private TypeDefinition type(String typeName, ClassLoader classLoader)
    {
        Matcher matcher = typeNamePattern.matcher(typeName);
        matcher.matches();
        String baseType = matcher.group("base");
        String typeArguments = matcher.group("arguments");
        if (typeArguments == null)
        {
            return type(Projo.forName(baseType, classLoader));
        }
        else
        {
            Stream<String> arguments = Stream.of(typeArguments.split("[, ]+"));
            Type[] argumentTypes = arguments.map(it -> Projo.forName(it, classLoader)).toArray(Type[]::new);
            return Generic.Builder.parameterizedType(Projo.forName(baseType, classLoader), argumentTypes).build();
        }
    }

    private <_Any_> Loaded<_Any_> debug(Loaded<_Any_> loadedType)
    {
        String debugPath = System.getProperty("pro.projo.debug.path");
        if (debugPath != null)
        {
            String packageName = loadedType.getTypeDescription().getPackage().getName();
            String typeName = loadedType.getTypeDescription().getSimpleName();
            try
            {
                File dumpDirectory = new File(debugPath);
                System.err.println("Saving " + packageName + "." + typeName + " in " + dumpDirectory);
                loadedType.saveIn(dumpDirectory);
            }
            catch (IOException exception)
            {
                throw new IOError(exception);
            }
        }
        return loadedType;
    }
}
