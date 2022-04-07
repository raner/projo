//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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
package pro.projo.internal.bytebuddy.exercises;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.Test;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static net.bytebuddy.description.type.TypeDescription.VOID;
import static net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.INJECTION;
import static net.bytebuddy.matcher.ElementMatchers.named;

/**
* The class {@link ByteBuddyExercises} contains various exercises for getting more familiary with the {@link ByteBuddy} API.
* They are included as JUnit tests in the Projo sources, but they do not actually test any Projo functionality. Packaging
* these exercises as unit tests was merely done for reasons of convenience.
*
* @author Mirko Raner
**/
public class ByteBuddyExercises
{
    static interface Exercise
    {
        int getValue();
        void setValue(int value);
    }

    static class ReferenceImplementation implements Exercise
    {
        private int value;

        @Override
        public int getValue()
        {
            return value;
        }

        @Override
        public void setValue(int value)
        {
            this.value = value;
        }

        /**
         * This method produces the following bytecode:
         * <pre>
         *  0: iconst_1
         *  1: anewarray #3 // class java/lang/Object
         *  4: dup
         *  5: iconst_0
         *  6: aload_0
         *  7: getfield #20 // Field value:I
         * 10: invokestatic #25 // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
         * 13: aastore
         * 14: invokestatic #31 // Method java/util/Objects.hash:([Ljava/lang/Object;)I
         * 17: ireturn
         * </pre>
         */
        @Override
        public int hashCode()
        {
            return Objects.hash(value);
        }

        @Override
        public boolean equals(Object other)
        {
            return other instanceof ReferenceImplementation && ((ReferenceImplementation)other).value == value;
        }
    }

    public static interface SomeInterface {}

    public static class SomeBaseClass {}

    /**
    * This will produce the following bytecode:
    * <pre>
    * class pro.projo.internal.bytebuddy.exercises.ByteBuddyExercises$ReferenceClassWithHashMap
    * extends pro.projo.internal.bytebuddy.exercises.ByteBuddyExercises$SomeBaseClass
    * implements pro.projo.internal.bytebuddy.exercises.ByteBuddyExercises$SomeInterface
    * {
    *   private java.util.Map<java.lang.String, java.lang.Object> cache;
    *
    *   pro.projo.internal.bytebuddy.exercises.ByteBuddyExercises$ReferenceClassWithHashMap();
    *   Code:
    *    0: aload_0
    *    1: invokespecial #14                 // Method pro/projo/internal/bytebuddy/exercises/ByteBuddyExercises$SomeBaseClass."<init>":()V
    *    4: aload_0
    *    5: new           #16                 // class java/util/HashMap
    *    8: dup
    *    9: invokespecial #18                 // Method java/util/HashMap."<init>":()V
    *   12: putfield      #19                 // Field cache:Ljava/util/Map;
    *   15: return
    * }
    **/
    //static class ReferenceClassWithHashMap extends SomeBaseClass implements SomeInterface
    //{
    //    @SuppressWarnings("unused")
    //    private Map<String, Object> cache = new HashMap<>();
    //}

    @Test
    public void generateImplementationWithField() throws Exception
    {
        Builder<Exercise> builder;
        builder = new ByteBuddy().subclass(Exercise.class);
        builder = builder.defineField("value", int.class, Visibility.PRIVATE);
        Unloaded<Exercise> unloaded = builder.make();
        Exercise pojo = unloaded.load(Exercise.class.getClassLoader(), INJECTION).getLoaded().newInstance();
        Field value = pojo.getClass().getDeclaredField("value");
        value.setAccessible(true);
        value.set(pojo, 42);
        assertEquals(42, value.get(pojo));
    }
    
    @Test
    public void generateImplementationWithFieldAndGetter() throws Exception
    {
        Builder<Exercise> builder;
        builder = new ByteBuddy().subclass(Exercise.class);
        builder = builder.defineField("value", int.class, Visibility.PRIVATE);
        builder = builder.method(named("setValue")).intercept(FieldAccessor.ofField("value"));
        Unloaded<Exercise> unloaded = builder.make();
        Exercise pojo = unloaded.load(Exercise.class.getClassLoader(), INJECTION).getLoaded().newInstance();
        pojo.setValue(42);
        Field value = pojo.getClass().getDeclaredField("value");
        value.setAccessible(true);
        assertEquals(42, value.get(pojo));
    }

    @Test
    public void generateImplementationWithGetterAndSetter() throws Exception
    {
        Builder<Exercise> builder;
        builder = new ByteBuddy().subclass(Exercise.class);
        builder = builder.defineField("value", int.class, Visibility.PRIVATE);
        builder = builder.method(named("getValue")).intercept(FieldAccessor.ofField("value"));
        builder = builder.method(named("setValue")).intercept(FieldAccessor.ofField("value"));
        Unloaded<Exercise> unloaded = builder.make();
        Exercise pojo = unloaded.load(Exercise.class.getClassLoader(), INJECTION).getLoaded().newInstance();
        pojo.setValue(42);
        assertEquals(42, pojo.getValue());
    }

    /**
    * Interestingly, this test passes, so redeclaring methods is fine with ByteBuddy but redeclaring fields is not.
    * @throws Exception
    **/
    @Test
    public void generateImplementationWithDuplicateSetter() throws Exception
    {
        Builder<Exercise> builder;
        builder = new ByteBuddy().subclass(Exercise.class);
        builder = builder.defineField("value", int.class, Visibility.PRIVATE);
        builder = builder.method(named("setValue")).intercept(FieldAccessor.ofField("value"));
        builder = builder.method(named("getValue")).intercept(FieldAccessor.ofField("value"));
        builder = builder.method(named("setValue")).intercept(FieldAccessor.ofField("value"));
        Unloaded<Exercise> unloaded = builder.make();
        Exercise pojo = unloaded.load(Exercise.class.getClassLoader(), INJECTION).getLoaded().newInstance();
        pojo.setValue(42);
        assertEquals(42, pojo.getValue());
    }

    static class TrivialHashCode
    {
        public static int trivialHashCode()
        {
            ReferenceImplementation expected = new ReferenceImplementation();
            expected.setValue(42);
            return expected.hashCode();
        }
    }

    @Test
    public void generateTrivialHashCodeImplementation() throws Exception
    {
        Builder<Exercise> builder;
        builder = new ByteBuddy().subclass(Exercise.class);
        builder = builder.defineField("value", int.class, Visibility.PRIVATE);
        builder = builder.method(named("getValue")).intercept(FieldAccessor.ofField("value"));
        builder = builder.method(named("setValue")).intercept(FieldAccessor.ofField("value"));
        builder = builder.method(named("hashCode")).intercept(MethodDelegation.to(TrivialHashCode.class));
        Unloaded<Exercise> unloaded = builder.make();
        Exercise pojo = unloaded.load(Exercise.class.getClassLoader(), INJECTION).getLoaded().newInstance();
        pojo.setValue(42);
        ReferenceImplementation expected = new ReferenceImplementation();
        expected.setValue(42);
        assertEquals(expected.hashCode(), pojo.hashCode());
    }

    private Class<?> generatedClass() throws Exception
    {
        Builder<?> builder = new ByteBuddy()
                .subclass(SomeBaseClass.class)
                .implement(SomeInterface.class)
                .name("pro.projo.internal.bytebuddy.exercises.ByteBuddyExercises$ReferenceClassWithHashMap");
        //
        TypeDescription typeMap = new TypeDescription.ForLoadedType(Map.class);
        TypeDescription typeString = new TypeDescription.ForLoadedType(String.class);
        TypeDescription typeObject = new TypeDescription.ForLoadedType(Object.class);
        Generic typeMapStringObject = Generic.Builder.parameterizedType(typeMap, typeString, typeObject).build();
        //FieldDescription cacheField = new FieldDescription.Latent(builder.toTypeDescription(), "cache", Modifier.PRIVATE, typeMapStringObject, emptyList());
        builder = builder.defineField("cache", typeMapStringObject, Modifier.PRIVATE);
        MethodDescription superConstructor = new MethodDescription.Latent
        (
            builder.toTypeDescription().getSuperClass().asErasure(),
            "<init>",
            Modifier.PUBLIC,
            emptyList(),
            VOID.asGenericType(),
            emptyList(),
            emptyList(),
            emptyList(),
            null,
            null
        );
        Constructor<?> hashMapConstructor = HashMap.class.getDeclaredConstructor(int.class);
        MethodCall createHashMap = MethodCall.construct(hashMapConstructor).withArgument(0);
        Implementation implementation = MethodCall.invoke(superConstructor).onSuper()
            .andThen(createHashMap);
        builder = builder
            .defineConstructor(Modifier.PUBLIC)
            .withParameter(int.class)
            .intercept(implementation);
        builder.make().saveIn(new java.io.File("/tmp"));
        return builder.make().load(getClass().getClassLoader()).getLoaded();
    }

    @Test
    public void generatedClassHasCorrectName() throws Exception
    {
        String expectedName = "pro.projo.internal.bytebuddy.exercises.ByteBuddyExercises$ReferenceClassWithHashMap";
        assertEquals(expectedName, generatedClass().getName());
    }
    
    @Test
    public void generatedClassHasPrivateFieldCalledCache() throws Exception
    {
        Field cache = generatedClass().getDeclaredField("cache");
        assertEquals(Modifier.PRIVATE, cache.getModifiers() & Modifier.PRIVATE);
    }
    
    @Test
    public void generatedClassHasFieldCalledCacheWithProperSimpleType() throws Exception
    {
        Field cache = generatedClass().getDeclaredField("cache");
        assertEquals(Map.class, cache.getType());
    }
    
    @Test
    public void generatedClassHasFieldCalledCacheWithProperParameterizedType() throws Exception
    {
        Field cache = generatedClass().getDeclaredField("cache");
        assertEquals("java.util.Map<java.lang.String, java.lang.Object>", cache.getGenericType().toString());
    }
    
    @Test
    public void generatedClassIsInitializedCorrectly() throws Exception
    {
        Class<?> generatedClass = generatedClass();
        Constructor<?> constructor = generatedClass.getDeclaredConstructor();
        Object instance = constructor.newInstance();
        Field cache = generatedClass.getDeclaredField("cache");
        cache.setAccessible(true);
        Object object = cache.get(instance);
        assertEquals(HashMap.class, object.getClass());
    }
    
    @Test
    public void generatedClassExtendsProperBaseClass() throws Exception
    {
        assertEquals(SomeBaseClass.class, generatedClass().getSuperclass());
    }
    
    @Test
    public void generatedClassConstructorTakesIntArgument() throws Exception
    {
        generatedClass().getDeclaredConstructor(int.class);
    }
    
    @Test
    public void generatedClassHasExactlyOneConstructor() throws Exception
    {
        assertEquals(1, generatedClass().getDeclaredConstructors().length);
    }
    
    @Test
    public void generatedClassHasExactlyOneField() throws Exception
    {
        assertEquals(1, generatedClass().getDeclaredFields().length);
    }

    @Test
    public void generatedClassHasNoDeclaredMethods() throws Exception
    {
        assertEquals(0, generatedClass().getDeclaredMethods().length);
    }
}
