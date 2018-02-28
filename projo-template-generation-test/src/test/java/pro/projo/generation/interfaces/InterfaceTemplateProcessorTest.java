//                                                                          //
// Copyright 2018 Mirko Raner                                               //
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
package pro.projo.generation.interfaces;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.Test;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InterfaceTemplateProcessorTest
{
    @Test
    public void testServiceLoaderFileForProcessorIsOnTheClasspath()
    {
        assertNotNull(getClass().getResourceAsStream("/META-INF/services/javax.annotation.processing.Processor"));
    }

    @Test
    public void testCallableClassIsGeneratedInCorrectPackage() throws Exception
    {
        Class.forName("pro.projo.generation.interfaces.test.Callable");
    }
    
    @Test
    public void testCallableClassHasCallMethod() throws Exception
    {
        Class<?> callable = Class.forName("pro.projo.generation.interfaces.test.Callable");
        callable.getDeclaredMethod("call");
    }

    @Test
    public void testMathClassHasAllMethodsFromJavaLangMath() throws Exception
    {
        Predicate<Method> publicStatic = method -> (method.getModifiers() & (PUBLIC|STATIC)) == (PUBLIC|STATIC);
        Function<Method, String> toString = method -> method.getName() + asList(method.getParameterTypes());
        Class<?> math = Class.forName("pro.projo.generation.interfaces.test.Math");
        Set<String> actual = Stream.of(math.getDeclaredMethods()).map(toString).collect(toSet());
        Set<String> expected = Stream.of(Math.class.getDeclaredMethods()).filter(publicStatic).map(toString).collect(toSet());
        assertEquals(expected, actual);
    }
}
