//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.junit.Test;
import static java.lang.reflect.Modifier.PUBLIC;
import static java.lang.reflect.Modifier.STATIC;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testObjectFactoryClassIsGeneratedInCorrectPackage() throws Exception
    {
        Class.forName("pro.projo.generation.interfaces.test.ObjectFactory");
    }

    @Test
    public void testMethodWithTypeParameter() throws Exception
    {
        Class<?> classObjectFactory = Class.forName("pro.projo.generation.interfaces.test.ObjectFactory");
        Method createObjectMethod = classObjectFactory.getMethod("createObject", Class.class);
        TypeVariable<Method> typeVariable = createObjectMethod.getTypeParameters()[0];
        assertEquals("T", typeVariable.getName());
    }

    @Test
    public void testObservablesClassIsGeneratedInCorrectPackage() throws Exception
    {
        Class.forName("pro.projo.generation.interfaces.test.Observables");
    }

    @Test
    public void testGeneratedMethodReturnsCorrectType() throws Exception
    {
        Class<?> classObjectFactory = Class.forName("pro.projo.generation.interfaces.test.ObjectFactory");
        Method createObjectFactoryMethod = classObjectFactory.getMethod("createObjectFactory");
        Class<?> returnType = createObjectFactoryMethod.getReturnType();
        assertEquals(classObjectFactory, returnType);
    }

    @Test
    public void testGeneratedMethodReturnsCorrectTypeFromMapJavadocExample() throws Exception
    {
        Class<?> classType = Class.forName("pro.projo.generation.interfaces.test.NewType");
        Method selfMethod = classType.getMethod("self", classType);
        Class<?> returnType = selfMethod.getReturnType();
        assertEquals(classType, returnType);
    }

    @Test
    public void testNestedClassesAreExcluded() throws Exception
    {
        Class<?> classNested = Class.forName("pro.projo.generation.interfaces.test.Nested");
        Method[] expected = {classNested.getMethod("outer")};
        Method[] actual = classNested.getDeclaredMethods();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testEnumerationIsGenerated() throws Exception
    {
        Class<?> classEnumeration = Class.forName("pro.projo.generation.interfaces.test.Enumeration");
        assertTrue(classEnumeration.isEnum());
    }

    @Test
    public void testEnumerationOptionsAreGenerated() throws Exception
    {
        Class<?> classEnumeration = Class.forName("pro.projo.generation.interfaces.test.Enumeration");
        Set<String> expected = new HashSet<>(Arrays.asList("YES", "NO", "MAYBE"));
        Set<String> actual = Stream.of(classEnumeration.getEnumConstants()).map(Enum.class::cast).map(Enum::name).collect(toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testEnumerationsAreProperlyImported() throws Exception
    {
        Class<?> classUseEnum = Class.forName("pro.projo.generation.interfaces.test.UseEnum");
        Class<?> classEnumeration = Class.forName("pro.projo.generation.interfaces.test.Enumeration");
        classUseEnum.getMethod("use", classEnumeration);
        //String returnType = use.getReturnType().getName();
        //assertEquals("pro.projo.generation.interfaces.test.Enumeration", returnType);
    }

    @Test
    public void testShadowedTypeVariablesAreRenamed() throws Exception
    {
    	Class<?> classShadowed = Class.forName("pro.projo.generation.interfaces.test.ShadowedTypeVariable");
    	Method method = classShadowed.getDeclaredMethods()[0];
    	String classLevelTypeVariable = classShadowed.getTypeParameters()[0].getName();
    	String methodLevelTypeVariable = method.getTypeParameters()[0].getName();
    	List<String> actual = asList(classLevelTypeVariable, methodLevelTypeVariable);
    	List<String> expected = asList("T", "T0");
    	assertEquals(expected, actual);
    }
    
    @Test
    public void testShadowedTypeVariablesAreUsedInMethodSignature() throws Exception
    {
    	Class<?> classShadowed = Class.forName("pro.projo.generation.interfaces.test.ShadowedTypeVariable");
    	Method method = classShadowed.getDeclaredMethods()[0];
    	String methodLevelTypeVariable = method.getTypeParameters()[0].getName();
    	String returnType = method.getGenericReturnType().toString();
    	String parameterType = method.getGenericParameterTypes()[0].toString();
    	List<String> actual = asList(returnType, parameterType);
    	List<String> expected = asList(methodLevelTypeVariable, methodLevelTypeVariable);
    	assertEquals(expected, actual);
    }

    @Test
    public void resourcesWithCustomFileExtensionAreGenerated() throws Exception
    {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("pro/projo/generation/interfaces/test/options/Runnable.kava");
        assertNotNull(file);
    }

    @Test
    public void annotationLevelOptionsOverridePackageLevelOptions() throws Exception
    {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream file = classLoader.getResourceAsStream("pro/projo/generation/interfaces/test/options/AutoCloseable.lava");
        assertNotNull(file);
    }

    @Test
    public void annotationLevelOptionsOverridePackageLevelOptionsInMath() throws Exception
    {
        Class<?> classInteger = Class.forName("pro.projo.generation.interfaces.test.math.Integer");
        Stream<Method> methods = Stream.of(classInteger.getDeclaredMethods());
        Stream<String> methodSignatures = methods.map(Method::toString);
        String math = "pro.projo.generation.interfaces.test.math.";
        String[] expected =
        {
            "public abstract " + math + "Integer " + math + "Integer.valueOf(long)",
            "public abstract " + math + "Integer " + math + "Integer.probablePrime(" + math + "Integer," + math + "Random)"
        };
        assertEquals(asList(expected), methodSignatures.collect(toList()));
    }
}
