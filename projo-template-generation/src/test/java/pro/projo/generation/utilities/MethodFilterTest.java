//                                                                          //
// Copyright 2020 - 2021 Mirko Raner                                        //
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
package pro.projo.generation.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import org.junit.Test;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Ternary;
import pro.projo.interfaces.annotation.Visibility;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toSet;
import static javax.lang.model.element.Modifier.STATIC;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pro.projo.interfaces.annotation.Ternary.EITHER;
import static pro.projo.interfaces.annotation.Ternary.FALSE;
import static pro.projo.interfaces.annotation.Ternary.TRUE;
import static pro.projo.interfaces.annotation.Visibility.PACKAGE;
import static pro.projo.interfaces.annotation.Visibility.PRIVATE;
import static pro.projo.interfaces.annotation.Visibility.PROTECTED;
import static pro.projo.interfaces.annotation.Visibility.PUBLIC;

public class MethodFilterTest
{
    static abstract class Variations
    {
        public abstract Variations publicMethods();

        protected abstract Variations protectedMethods();

        @SuppressWarnings("unused")
        private Variations privateMethods() {return null;}

        abstract Variations packageMethods();

        public static Variations publicStaticMethods() {return null;}
        
        protected static Variations protectedStaticMethods() {return null;}
        
        @SuppressWarnings("unused")
        private static Variations privateStaticMethods() {return null;}
        
        static Variations packageStaticMethods() {return null;}
    }

    final static Visibility[] ALL = {PUBLIC, PRIVATE, PROTECTED, PACKAGE};
    final static Object[][] MODIFIER_MAP =
    {
        {java.lang.reflect.Modifier.PUBLIC, Modifier.PUBLIC},
        {java.lang.reflect.Modifier.PRIVATE, Modifier.PRIVATE},
        {java.lang.reflect.Modifier.PROTECTED, Modifier.PROTECTED},
        {java.lang.reflect.Modifier.STATIC, Modifier.STATIC}
    };

    final Method[] methods = Variations.class.getDeclaredMethods();
    final Stream<ExecutableElement> elements = Stream.of(methods).map(this::method);

    MethodFilter methodFilter;

    @Test
    public void allMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, ALL));
        Set<String> expected = set
        (
            "publicMethods",
            "privateMethods",
            "protectedMethods",
            "packageMethods",
            "publicStaticMethods",
            "privateStaticMethods",
            "protectedStaticMethods",
            "packageStaticMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyStaticMethods()
    {
        methodFilter = new MethodFilter(testInterface(TRUE, ALL));
        Set<String> expected = set
        (
            "publicStaticMethods",
            "privateStaticMethods",
            "protectedStaticMethods",
            "packageStaticMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyNonStaticMethods()
    {
        methodFilter = new MethodFilter(testInterface(FALSE, ALL));
        Set<String> expected = set
        (
            "publicMethods",
            "privateMethods",
            "protectedMethods",
            "packageMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPublicNonStaticMethods()
    {
        methodFilter = new MethodFilter(testInterface(FALSE, new Visibility[] {PUBLIC}));
        Set<String> expected = set("publicMethods");
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPublicMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, new Visibility[] {PUBLIC}));
        Set<String> expected = set("publicMethods", "publicStaticMethods");
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPrivateMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, new Visibility[] {PRIVATE}));
        Set<String> expected = set("privateMethods", "privateStaticMethods");
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPublicOrProtectedMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, new Visibility[] {PUBLIC, PROTECTED}));
        Set<String> expected = set
        (
            "publicMethods",
            "publicStaticMethods",
            "protectedMethods",
            "protectedStaticMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPackageMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, new Visibility[] {PACKAGE}));
        Set<String> expected = set("packageMethods", "packageStaticMethods");
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPackageOrProtectedMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, new Visibility[] {PACKAGE, PROTECTED}));
        Set<String> expected = set
        (
            "packageMethods",
            "packageStaticMethods",
            "protectedMethods",
            "protectedStaticMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPackageOrProtectedNonStaticMethods()
    {
        methodFilter = new MethodFilter(testInterface(FALSE, new Visibility[] {PACKAGE, PROTECTED}));
        Set<String> expected = set
        (
            "packageMethods",
            "protectedMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPublicOrProtectedStaticMethods()
    {
        methodFilter = new MethodFilter(testInterface(TRUE, new Visibility[] {PUBLIC, PROTECTED}));
        Set<String> expected = set
        (
            "publicStaticMethods",
            "protectedStaticMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void onlyPublicProtectedOrPackageNonStaticMethods()
    {
        methodFilter = new MethodFilter(testInterface(FALSE, new Visibility[] {PUBLIC, PACKAGE, PROTECTED}));
        Set<String> expected = set
        (
            "publicMethods",
            "packageMethods",
            "protectedMethods"
        );
        assertEquals(expected, actual());
    }

    @Test
    public void modifiersPublicStaticShouldNotIncludePackageVisibleMethods()
    {
        methodFilter = new MethodFilter(testInterface(EITHER, ALL, Modifier.PUBLIC, STATIC));
        Set<String> expected = set("publicStaticMethods");
        assertEquals(expected, actual());
    }

    @SafeVarargs
    private final <_Any_> Set<_Any_> set(_Any_... elements)
    {
        return new HashSet<>(asList(elements));
    }

    private Set<String> actual()
    {
        return elements
            .filter(methodFilter::matches)
            .map(ExecutableElement::getSimpleName)
            .map(Name::toString)
            .collect(toSet());
    }

    private ExecutableElement method(Method method)
    {
        String name = method.getName();
        Set<Modifier> modifiers = modifiers(method.getModifiers());
        ExecutableElement element = mock(ExecutableElement.class);
        when(element.getModifiers()).thenReturn(modifiers);
        when(element.getSimpleName()).thenReturn(new pro.projo.generation.utilities.Name(name));
        return element;
    }

    private Set<Modifier> modifiers(int modifiers)
    {
        return Stream.of(MODIFIER_MAP)
            .filter(modifier -> (modifiers & ((Integer)modifier[0])) != 0)
            .map(modifier -> (Modifier)modifier[1])
            .collect(toSet());
    }

    private Interface testInterface(Ternary isStatic, Visibility[] visibility, Modifier... modifiers)
    {
        return new Interface()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Interface.class;
            }

            @Override
            public String generate()
            {
                return null;
            }

            @Override
            public String[] extend()
            {
                return new String[] {};
            }

            @Override
            public Modifier[] modifiers()
            {
                return modifiers;
            }

            @Override
            public Class<?> from()
            {
                return null;
            }

            @Override
            public Map[] map()
            {
                return null;
            }

            @Override
            public Options options()
            {
                return null;
            }

            @Override
            public Ternary isStatic()
            {
                return isStatic;
            }

            @Override
            public Visibility[] visibility()
            {
                return visibility;
            }
        };
    }
}
