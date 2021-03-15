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
package pro.projo.internal;

import java.lang.reflect.Constructor;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static pro.projo.internal.NumbersWideningTest.Supported.can;
import static pro.projo.internal.NumbersWideningTest.Supported.cannot;

@RunWith(Parameterized.class)
public class NumbersWideningTest
{
    enum Supported {can, cannot}

    static Class<?>[][][] wideningPrimitiveConversions = // JLS section 5.1.2, without char
    {
        {{byte.class}, {short.class, int.class, long.class, float.class, double.class}},
        {{short.class}, {int.class, long.class, float.class, double.class}},
        {{int.class}, {long.class, float.class, double.class}},
        {{long.class}, {float.class, double.class}},
        {{float.class}, {double.class}},
        {{double.class}, {}}
    };

    private Numbers<?, ?> numbers = new Numbers<>();

    private static Supported conversionSupported(Class<?> from, Class<?> to)
    {
      return Stream.of(Stream.of(wideningPrimitiveConversions)
          .filter(item -> item[0][0].equals(from)).findFirst().get()[1])
          .anyMatch(to::equals)? can:cannot;
    }

    @Parameters(name="{0} {2} be widened to {1}")
    public static Collection<Object[]> testedConversions()
    {
        List<Class<?>> types = Stream.of(wideningPrimitiveConversions).map(item -> item[0][0]).collect(toList());
        return types.stream().flatMap(type -> types.stream().filter(isEqual(type).negate()).map(to -> new SimpleEntry<>(type, to)))
            .map(entry -> new Object[] {entry.getKey(), entry.getValue(), conversionSupported(entry.getKey(), entry.getValue())})
            .collect(toList());
    }

    @Parameter(0)
    public Class<?> from;

    @Parameter(1)
    public Class<?> to;

    @Parameter(2)
    public Supported supported;

    @Test
    public void test() throws Exception
    {
        @SuppressWarnings("unchecked")
        Class<? extends Number> target = (Class<? extends Number>)numbers.getWrapperClass(to);
        Callable<Number> result = () ->
        {
            Class<?> source = numbers.getWrapperClass(from);
            Class<?> parameter = Character.class.equals(source)? char.class:String.class;
            Object argument = Character.class.equals(source)? '0':"0";
            Constructor<?> constructor = source.getDeclaredConstructor(parameter);
            Number number = (Number)constructor.newInstance(argument);
            return numbers.cast(number).to(target);
        };
        if (supported == cannot)
        {
            assertThrows(IllegalArgumentException.class, result::call);
        }
        else
        {
            assertEquals(target, result.call().getClass());
        }
    }
}
