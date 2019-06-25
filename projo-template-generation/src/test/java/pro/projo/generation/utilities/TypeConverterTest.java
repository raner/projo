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
package pro.projo.generation.utilities;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.generation.test.utilities.Mutable;
import pro.projo.generation.utilities.expected.test.types.Pending;
import pro.projo.generation.utilities.expected.test.types.Walkable;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import static java.util.Collections.singleton;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeThat;

@RunWith(Parameterized.class)
public class TypeConverterTest
{
    public enum Naming {FQCN, SHORT}

    Name testPackage = new Name("pro.projo.generation.utilities.expected.test.types");
    ReflectionTypes types = ReflectionTypes.getInstance();
    Function<Class<?>, TypeElement> typeElementFactory = types::typeElement;
    Function<Type, TypeMirror> typeMirrorFactory = types::typeMirror;

    @Parameter
    public Naming naming;

    @Parameters(name="{0}")
    public static Collection<Object[]> parameters()
    {
        Object[][] parameters = {{Naming.FQCN}, {Naming.SHORT}};
        return Arrays.asList(parameters);
    }

    Elements elements = proxy(Elements.class, Elements::getTypeElement, name ->
    {
        try
        {
            return typeElementFactory.apply(Class.forName(name.toString()));
        }
        catch (ClassNotFoundException classNotFound)
        {
            throw new NoClassDefFoundError(classNotFound.getMessage());
        }
    });

    Interface[] interfaces =
    {
        createInterface(Runnable.class, "Walkable"),
        createInterface(Future.class, "Pending")
    };

    TypeConverter converter;

    @Before
    public void initializeTypeConverterUnderTest()
    {
        PackageShortener shortener = naming == Naming.SHORT? new PackageShortener() : new PackageShortener()
        {
            @Override
            public String shorten(String fullyQualifiedClassName) {
                return fullyQualifiedClassName;
            }
        };
        converter = new TypeConverter(types, shortener, testPackage, Arrays.asList(interfaces));
    }

    /**
    * Verify that {@link AutoCloseable} stays unchanged (as there is no mapping).
    **/
    @Test
    public void convertSimpleTypeWithoutTranslation()
    {
        TypeElement closeable = typeElementFactory.apply(AutoCloseable.class);
        String element = converter.convert(closeable.asType());
        String expected = shorten("java.lang.AutoCloseable");
        assertEquals(expected, element);
    }

    /**
    * Verify that {@link Runnable} gets converted to {@link Walkable}.
    **/
    @Test
    public void convertSimpleTypeWithTranslation()
    {
        TypeElement runnable = typeElementFactory.apply(Runnable.class);
        String expected = shorten(Walkable.class.getName());
        String element = converter.convert(runnable.asType());
        assertEquals(expected, element);
    }

    /**
    * Verify that {@link Callable Callable&lt;V&gt;} stays unchanged (as there is no mapping).
    **/
    @Test
    public void convertParameterizedTypeWithoutTranslation()
    {
        DeclaredType callable = (DeclaredType)typeElementFactory.apply(Callable.class).asType();
        String element = converter.convert(callable);
        String expected = shorten(Callable.class.getName()) + "<V>";
        assertEquals(expected, element);
    }

    /**
     * Verify that {@link Callable Callable&lt;?&gt;} stays unchanged (as there is no mapping).
     **/
    @Test
    public void convertUnboundedWildcardTypeWithoutTranslation() throws Exception
    {
        abstract class Class
        {
            abstract Callable<?> method();
        }
        TypeMirror type = typeMirrorFactory.apply(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String element = converter.convert(type);
        String expected = shorten(Callable.class.getName()) + "<?>";
        assertEquals(expected, element);
    }

    /**
    * Verify that {@link Future Future&lt;V&gt;} gets converted to {@link Pending Pending&lt;V&gt;}.
    **/
    @Test
    public void convertParameterizedTypeWithTranslation()
    {
        DeclaredType future = (DeclaredType)typeElementFactory.apply(Future.class).asType();
        String element = converter.convert(future);
        String expected = shorten(Pending.class.getName()) + "<V>";
        assertEquals(expected, element);
    }

    /**
    * Verify that base type of {@link Callable}&lt;{@link Runnable}&gt; gets translated to
    * {@link Callable}.
    **/
    @Test
    public void convertBaseTypeForTypeParameterWithTranslation()
    {
        TypeElement callable = typeElementFactory.apply(Callable.class);
        TypeMirror runnable = typeMirrorFactory.apply(Runnable.class);
        DeclaredType callableOfRunnable = types.getDeclaredType(callable, runnable);
        String element = converter.convert(callableOfRunnable);
        String expected = shorten(Callable.class.getName()) + "<" + shorten(Walkable.class.getName()) + ">";
        assertEquals(expected, element);
    }

    /**
    * Verify that {@link Future}&lt;{@link Callable}&lt;{@link Runnable}&gt;&gt; gets translated to
    * {@link Pending}&lt;{@link Callable}&lt;{@link Walkable}&gt;&gt;.
    **/
    @Test
    public void convertNestedParameterizedTypesWithTranslation()
    {
        TypeElement typeCallable = typeElementFactory.apply(Callable.class);
        TypeMirror runnable = typeMirrorFactory.apply(Runnable.class);
        DeclaredType typeCallableOfRunnable = types.getDeclaredType(typeCallable, runnable);
        TypeElement typeFuture = typeElementFactory.apply(Future.class);
        DeclaredType typeFutureOfCallableOfRunnable = types.getDeclaredType(typeFuture, typeCallableOfRunnable);
        String result = converter.convert(typeFutureOfCallableOfRunnable);
        String expected = shorten(Pending.class.getName()) + "<" + shorten(Callable.class.getName()) + "<" + shorten(Walkable.class.getName()) + ">>";
        assertEquals(expected, result);
    }

    /**
    * Verify that {@link Future}&lt;{@link Callable}&lt;{@code ? extends }{@link Runnable}&gt;&gt; gets translated to
    * {@link Pending}&lt;{@link Callable}&lt;{@code ? extends }{@link Walkable}&gt;&gt;.
    **/
    @Test
    public void convertExtendsWildcardTypesWithTranslation()
    {
        TypeElement typeCallable = typeElementFactory.apply(Callable.class);
        TypeMirror runnable = typeMirrorFactory.apply(Runnable.class);
        WildcardType extendsRunnable = types.getWildcardType(runnable, null);
        DeclaredType typeCallableOfExtendsRunnable = types.getDeclaredType(typeCallable, extendsRunnable);
        TypeElement typeFuture = typeElementFactory.apply(Future.class);
        DeclaredType typeFutureOfCallableOfExtendsRunnable = types.getDeclaredType(typeFuture, typeCallableOfExtendsRunnable);
        String result = converter.convert(typeFutureOfCallableOfExtendsRunnable);
        String expected = shorten(Pending.class.getName()) + "<" + shorten(Callable.class.getName()) + "<? extends " + shorten(Walkable.class.getName()) + ">>";
        assertEquals(expected, result);
    }

    /**
    * Verify that {@link Future}&lt;{@link Callable}&lt;{@code ? super }{@link Runnable}&gt;&gt; gets translated to
    * {@link Pending}&lt;{@link Callable}&lt;{@code ? super }{@link Walkable}&gt;&gt;.
    **/
    @Test
    public void convertSuperWildcardTypesWithTranslation()
    {
        TypeElement typeCallable = typeElementFactory.apply(Callable.class);
        TypeMirror runnable = typeMirrorFactory.apply(Runnable.class);
        WildcardType superRunnable = types.getWildcardType(null, runnable);
        DeclaredType typeCallableOfSuperRunnable = types.getDeclaredType(typeCallable, superRunnable);
        TypeElement typeFuture = typeElementFactory.apply(Future.class);
        DeclaredType typeFutureOfCallableOfExtendsRunnable = types.getDeclaredType(typeFuture, typeCallableOfSuperRunnable);
        String result = converter.convert(typeFutureOfCallableOfExtendsRunnable);
        String expected = shorten(Pending.class.getName()) + "<" + shorten(Callable.class.getName()) + "<? super " + shorten(Walkable.class.getName()) + ">>";
        assertEquals(expected, result);
    }

    /**
    * Verify that {@link Future}&lt;{@code ? extends }{@link Callable}&lt;{@link Runnable}&gt;&gt; gets translated to
    * {@link Pending}&lt;{@code ? extends }{@link Callable}&lt;{@link Walkable}&gt;&gt;.
    **/
    @Test
    public void convertExtendsWildcardTypesWithoutTranslation()
    {
        TypeElement typeCallable = typeElementFactory.apply(Callable.class);
        TypeMirror runnable = typeMirrorFactory.apply(Runnable.class);
        DeclaredType typeCallableOfRunnable = types.getDeclaredType(typeCallable, runnable);
        WildcardType typeExtendsCallableOfRunnable = types.getWildcardType(typeCallableOfRunnable, null);
        TypeElement typeFuture = typeElementFactory.apply(Future.class);
        DeclaredType typeFutureOfExtendsCallableOfRunnable = types.getDeclaredType(typeFuture, typeExtendsCallableOfRunnable);
        String result = converter.convert(typeFutureOfExtendsCallableOfRunnable).toString();
        String expected = shorten("pro.projo.generation.utilities.expected.test.types.Pending")
            + "<? extends " + shorten("java.util.concurrent.Callable")
            + "<" + shorten("pro.projo.generation.utilities.expected.test.types.Walkable") + ">>";
        assertEquals(expected, result); // use simplified string-based comparison
    }

    /**
    * Verify that {@link Callable}&lt;{@link Future}&lt;{@code ? extends T}&gt;&gt; gets translated to
    * {@link Callable}&lt;{@link Pending}&lt;{@code ? extends T}&gt;&gt;.
    **/
    @Test
    public void convertExtendsWildcardTypeVariablesWithTranslation() throws Exception
    {
        abstract class Class<T>
        {
            abstract Callable<Future<? extends T>> method();
        }
        TypeMirror type = typeMirrorFactory.apply(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String expected = shorten("java.util.concurrent.Callable")
            + "<" + shorten("pro.projo.generation.utilities.expected.test.types.Pending")
            + "<? extends T>>";
        String result = converter.convert(type);
        assertEquals(expected.toString(), result);
    }

    /**
    * Verify that array types get translated correctly.
    **/
    @Test
    public void convertPlainArrayTypeWithTranslation() throws Exception
    {
        abstract class Class
        {
            abstract Callable<Runnable[]> method();
        }
        TypeMirror type = typeMirrorFactory.apply(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String expected = shorten("java.util.concurrent.Callable")
            + "<" + shorten("pro.projo.generation.utilities.expected.test.types.Walkable")
            + "[]>";
        String result = converter.convert(type);
        assertEquals(expected.toString(), result);
    }

    /**
    * Verify that array types get translated correctly.
    **/
    @Test
    public void convertParameterizedArrayTypeWithTranslation() throws Exception
    {
        abstract class Class
        {
            abstract Callable<Future<?>[]> method();
        }
        TypeMirror type = typeMirrorFactory.apply(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String expected = shorten("java.util.concurrent.Callable")
            + "<" + shorten("pro.projo.generation.utilities.expected.test.types.Pending")
            + "<?>[]>";
        String result = converter.convert(type);
        assertEquals(expected.toString(), result);
    }

    /**
    * Verify that primitive types get mapped to themselves.
    **/
    @Test
    public void convertPrimitiveType() throws Exception
    {
        TypeMirror type = typeMirrorFactory.apply(int.class);
        TypeMirror expected = typeMirrorFactory.apply(int.class);
        String result = converter.convert(type);
        assertEquals(expected.toString(), result);
    }

    /**
    * Verify that the void type gets mapped to itself.
    **/
    @Test
    public void convertVoidType() throws Exception
    {
        TypeMirror type = types.getNoType(TypeKind.VOID);
        TypeMirror expected = types.getNoType(TypeKind.VOID);
        String result = converter.convert(type);
        assertEquals(expected.toString(), result);
    }

    @Test
    public void verifyImports()
    {
        assumeThat(naming, is(Naming.SHORT));
        DeclaredType future = (DeclaredType)typeElementFactory.apply(Future.class).asType();
        converter.convert(future);
        assertEquals(singleton("pro.projo.generation.utilities.expected.test.types.Pending"), converter.getImports());
    }

    @Test
    public void verifyTypeVariablesAreNotIncludedAsImports()
    {
        assumeThat(naming, is(Naming.SHORT));
        DeclaredType future = (DeclaredType)typeElementFactory.apply(Future.class).asType();
        converter.convert(future);
        assertEquals(singleton("pro.projo.generation.utilities.expected.test.types.Pending"), converter.getImports());
    }

    @Test
    public void verifyImportsForBoundedWildcards() throws Exception
    {
        assumeThat(naming, is(Naming.SHORT));
        abstract class Class<T>
        {
            abstract Callable<Future<? extends T>> method();
        }
        TypeMirror type = typeMirrorFactory.apply(Class.class.getDeclaredMethod("method").getGenericReturnType());
        converter.convert(type);
        String[] expected =
        {
            "pro.projo.generation.utilities.expected.test.types.Pending",
            "java.util.concurrent.Callable"
        };
        assertEquals(new HashSet<>(Arrays.asList(expected)), converter.getImports());
    }

    @Test
    public void verifyMultipleImports()
    {
        assumeThat(naming, is(Naming.SHORT));
        TypeElement typeCallable = typeElementFactory.apply(Callable.class);
        TypeMirror runnable = typeMirrorFactory.apply(Runnable.class);
        DeclaredType typeCallableOfRunnable = types.getDeclaredType(typeCallable, runnable);
        TypeElement typeFuture = typeElementFactory.apply(Future.class);
        DeclaredType typeFutureOfCallableOfRunnable = types.getDeclaredType(typeFuture, typeCallableOfRunnable);
        converter.convert(typeFutureOfCallableOfRunnable);
        String[] expected =
        {
            "pro.projo.generation.utilities.expected.test.types.Pending",
            "pro.projo.generation.utilities.expected.test.types.Walkable",
            "java.util.concurrent.Callable"
        };
        assertEquals(new HashSet<>(Arrays.asList(expected)), converter.getImports());
    }

    private String shorten(String string)
    {
        if (naming == Naming.SHORT)
        {
            return converter.getPackageShortener().shorten(string);
        }
        return string;
    }

    private Interface createInterface(Class<?> from, String generate, Modifier... modifiers)
    {
        return createInterface(from, generate, modifiers, new Map[] {});
    }

    private Interface createInterface(Class<?> from, String generate, Modifier[] modifiers, Map[] map)
    {
        return new Interface()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
              return Interface.class;
            }

            /**
            * Provides the source {@link Class} ("from" class) of the annotation.
            * Note that this method behaves exactly like annotations behave at compile time
            * (during compile-time annotation processing), i.e. it actually throws a
            * {@link MirroredTypeException}.
            *
            * @throws MirroredTypeException containing the class's {@link TypeMirror}
            **/
            @Override
            public Class<?> from()
            {
                throw new MirroredTypeException(typeMirrorFactory.apply(from));
            }
  
            @Override
            public String generate()
            {
                return generate;
            }

            @Override
            public Modifier[] modifiers()
            {
                return modifiers;
            }

            @Override
            public Map[] map()
            {
                return map;
            }
        };
    }

    private <_Type_, _Input_, _Output_> _Type_ proxy(Class<_Type_> functionalInterface, BiFunction<_Type_, _Input_, _Output_> method, Function<_Input_, _Output_> implementation)
    {
        Mutable<Method> called = new Mutable<>();
        Class<?>[] target = {functionalInterface};

        // Setup spy to determine which method was called:
        //
        @SuppressWarnings("unchecked")
        _Type_ spy = (_Type_)Proxy.newProxyInstance(getClass().getClassLoader(), target, (Object proxy, Method calledMethod, Object[] args) ->
        {
            called.set(calledMethod);
            return null;
        });
        method.apply(spy, null);

        // When the method is invoked call alternative implementation instead:
        //
        @SuppressWarnings("unchecked")
        _Type_ real = (_Type_)Proxy.newProxyInstance(getClass().getClassLoader(), target, (Object proxy, Method calledMethod, Object[] args) ->
        {
            if (calledMethod.equals(called.get()))
            {
                _Output_ result = implementation.apply((_Input_)args[0]);
                return result;
            }
            return null;
        });
        return real;
    }
}
