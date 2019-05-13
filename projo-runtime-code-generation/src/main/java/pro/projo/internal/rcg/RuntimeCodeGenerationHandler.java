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
package pro.projo.internal.rcg;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.FieldAccessor;
import pro.projo.Projo;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.ProjoObject;
import pro.projo.internal.PropertyMatcher;
import pro.projo.internal.rcg.runtime.DefaultToStringObject;
import pro.projo.internal.rcg.runtime.ToStringObject;
import pro.projo.internal.rcg.runtime.ToStringValueObject;
import pro.projo.internal.rcg.runtime.ValueObject;
import static java.util.Arrays.asList;
import static java.util.function.UnaryOperator.identity;
import static net.bytebuddy.description.modifier.Visibility.PRIVATE;
import static net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.INJECTION;
import static net.bytebuddy.matcher.ElementMatchers.named;

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
        Method[] declaredMethods = type.getDeclaredMethods();
        Builder<_Artifact_> builder = create(type).name(implementationName(type));
        builder = Stream.of(declaredMethods).filter(this::getterOrSetter).reduce(builder, this::add, sequentialOnly());
        Unloaded<_Artifact_> unloaded = builder.make();
        return unloaded.load(type.getClassLoader(), INJECTION).getLoaded();
    }

    /**
    * TODO: this duplicates some of the predicate logic from the Predicates utility (but without the arguments).
    *    => Refactor!
    **/
    private boolean getterOrSetter(Method method)
    {
        Class<?> returnType = method.getReturnType();
        int parameterCount = method.getParameterCount();
        return (parameterCount == 1 && returnType.equals(void.class))
            || (parameterCount == 0 && !returnType.equals(void.class)
                && !"hashCode".equals(method.getName()) && !"toString".equals(method.getName()));
    }

    private Builder<_Artifact_> add(Builder<_Artifact_> builder, Method method)
    {
        String methodName = method.getName();
        String propertyName = matcher.propertyName(methodName);
        UnaryOperator<Builder<_Artifact_>> addFieldForGetter;
        boolean isGetter = Predicates.getter.test(method, new Object[method.getParameterCount()]);
        Class<?> type = isGetter? method.getReturnType():void.class;
        addFieldForGetter = isGetter? localBuilder -> localBuilder.defineField(propertyName, type, PRIVATE):identity();
        return addFieldForGetter.apply(builder).method(named(methodName)).intercept(FieldAccessor.ofField(propertyName));
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
        return type.getName() + SUFFIX;
    }

    private Builder<_Artifact_> create(Class<_Artifact_> type)
    {
        List<Boolean> features = asList(Projo.isValueObject(type), Projo.hasCustomToString(type));
        Class<?> baseclass = baseClasses.get(features);
        @SuppressWarnings("unchecked")
        Builder<_Artifact_> builder = (Builder<_Artifact_>)new ByteBuddy().subclass(baseclass).implement(type, ProjoObject.class);
        return builder;
    }
}
