//                                                                          //
// Copyright 2024 Mirko Raner                                               //
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
package pro.projo.jakarta;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import jakarta.inject.Inject;
import pro.projo.Projo;
import static org.junit.Assert.assertEquals;

/**
* {@link ProjoBooleanInjectionTest} tests Projo's code generation for injected fields for a
* bootstrapped Boolean algebra that requires circular dependencies.
*
* @author Mirko Raner
**/
@RunWith(Parameterized.class)
public class ProjoBooleanInjectionTest
{
    static interface Boolean
    {
        Boolean or(Boolean other);
        Boolean xor(Boolean other);
        Boolean and(Boolean other);
        Boolean not();
    }

    static interface True extends Boolean, Booleans
    {
        @Override
        default Boolean or(Boolean other)
        {
            return TRUE();
        }

        @Override
        default Boolean xor(Boolean other)
        {
            return other.not();
        }

        @Override
        default Boolean and(Boolean other)
        {
            return other;
        }

        @Override
        default Boolean not()
        {
            return FALSE();
        }
    }

    static interface False extends Boolean, Booleans
    {
        @Override
        default Boolean or(Boolean other)
        {
            return other;
        }
  
        @Override
        default Boolean xor(Boolean other)
        {
            return other;
        }
  
        @Override
        default Boolean and(Boolean other)
        {
            return FALSE();
        }
  
        @Override
        default Boolean not()
        {
            return TRUE();
        }
    }

    static interface Booleans
    {
        @Inject True TRUE();
        @Inject False FALSE();
    }

    static abstract class TestData
    {
        boolean left;
        boolean expected;

        abstract void test(Function<java.lang.Boolean, Boolean> converter);
    }

    static class TestUnary extends TestData
    {
        UnaryOperator<Boolean> operator;

        TestUnary(UnaryOperator<Boolean> operator, boolean left, boolean expected)
        {
            this.operator = operator;
            this.left = left;
            this.expected = expected;
        }

        @Override
        void test(Function<java.lang.Boolean, Boolean> converter)
        {
            Boolean object = converter.apply(left);
            Boolean result = operator.apply(object);
            Boolean expected = converter.apply(this.expected);
            assertEquals(expected, result);
        }
    }

    static class TestBinary extends TestData 
    {
        BinaryOperator<Boolean> operator;
        boolean right;

        TestBinary(BinaryOperator<Boolean> operator, boolean left, boolean right, boolean expected)
        {
            this.operator = operator;
            this.left = left;
            this.right = right;
            this.expected = expected;
        }

        @Override
        void test(Function<java.lang.Boolean, Boolean> converter)
        {
            Boolean object = converter.apply(left);
            Boolean other = converter.apply(right);
            Boolean result = operator.apply(object, other);
            Boolean expected = converter.apply(this.expected);
            assertEquals(expected, result);
        }
    }

    @Parameters
    public static TestData[] getTestData()
    {
        return new TestData[]
        {
            new TestUnary(Boolean::not, false, true),
            new TestUnary(Boolean::not, true, false),
            new TestBinary(Boolean::and, false, false, false),
            new TestBinary(Boolean::and, true, false, false),
            new TestBinary(Boolean::and, false, true, false),
            new TestBinary(Boolean::and, true, true, true),
            new TestBinary(Boolean::xor, false, false, false),
            new TestBinary(Boolean::xor, true, false, true),
            new TestBinary(Boolean::xor, false, true, true),
            new TestBinary(Boolean::xor, true, true, false),
            new TestBinary(Boolean::or, false, false, false),
            new TestBinary(Boolean::or, true, false, true),
            new TestBinary(Boolean::or, false, true, true),
            new TestBinary(Boolean::or, true, true, true)
        };
    }

    @Parameter
    public TestData test;

    @Test
    public void test() throws Exception
    {
        Module module = new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(True.class).to(Projo.getImplementationClass(True.class)).asEagerSingleton();
                bind(False.class).to(Projo.getImplementationClass(False.class)).asEagerSingleton();
                bind(Booleans.class).to(Projo.getImplementationClass(Booleans.class)).asEagerSingleton();
            }
        };
        Injector injector = Guice.createInjector(module);
        Booleans booleans = injector.getInstance(Booleans.class);
        test.test(value -> value? booleans.TRUE():booleans.FALSE());
    }
}
