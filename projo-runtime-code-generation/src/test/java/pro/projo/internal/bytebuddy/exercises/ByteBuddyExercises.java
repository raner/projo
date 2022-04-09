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

import java.lang.reflect.Field;
import java.util.Objects;
import org.junit.Test;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import static org.junit.Assert.assertEquals;
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
}
