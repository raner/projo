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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import org.junit.Test;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.generation.test.utilities.Mutable;
import pro.projo.generation.utilities.expected.test.types.Pending;
import pro.projo.generation.utilities.expected.test.types.Walkable;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;

public class TypeConverterTest
{
    Name testPackage = new Name("pro.projo.generation.utilities.expected.test.types");
    ReflectionTypes reflection = ReflectionTypes.getInstance();
    Elements elements = proxy(Elements.class, Elements::getTypeElement, name ->
    {
        try
        {
            return reflection.typeElement(Class.forName(name.toString()));
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
    TypeConverter converter = new TypeConverter(reflection, elements, testPackage, interfaces);

    /**
    * Verify that {@link AutoCloseable} stays unchanged (as there is no mapping).
    **/
    @Test
    public void convertSimpleTypeWithoutTranslation()
    {
        TypeElement closeable = reflection.typeElement(AutoCloseable.class);
        DeclaredType element = converter.convert((DeclaredType) closeable.asType());
        TypeMirror expected = closeable.asType();
        assertEquals(expected, element);
    }

    /**
    * Verify that {@link Runnable} gets converted to {@link Walkable}.
    **/
    @Test
    public void convertSimpleTypeWithTranslation()
    {
        TypeElement runnable = reflection.typeElement(Runnable.class);
        TypeMirror expected = reflection.typeElement(Walkable.class).asType();
        DeclaredType element = converter.convert((DeclaredType) runnable.asType());
        assertEquals(expected, element);
    }

    /**
    * Verify that {@link Callable Callable&lt;V&gt;} stays unchanged (as there is no mapping).
    **/
    @Test
    public void convertParameterizedTypeWithoutTranslation()
    {
        DeclaredType callable = (DeclaredType) reflection.typeElement(Callable.class).asType();
        DeclaredType element = converter.convert(callable);
        Object[] actual = {element, element.getTypeArguments().iterator().next().toString()};
        Object[] expected = {callable, "V"};
        assertArrayEquals(expected, actual);
    }

    /**
    * Verify that {@link Future Future&lt;V&gt;} gets converted to {@link Pending Pending&lt;V&gt;}.
    **/
    @Test
    public void convertParameterizedTypeWithTranslation()
    {
        DeclaredType future = (DeclaredType) reflection.typeElement(Future.class).asType();
        DeclaredType element = converter.convert(future);
        DeclaredType pending = (DeclaredType)reflection.typeElement(Pending.class).asType(); //reflection.typeElement(Pending.class);
        Object[] actual = {element.asElement(), element.getTypeArguments().iterator().next().toString()};
        Object[] expected = {pending.asElement(), "V"};
        assertArrayEquals(expected, actual);
    }

    /**
    * Verify that base type of {@link Callable}&lt;{@link Runnable}&gt; gets translated to
    * {@link Callable}.
    **/
    @Test
    public void convertBaseTypeForTypeParameterWithTranslation()
    {
        TypeElement callable = reflection.typeElement(Callable.class);
        TypeMirror runnable = reflection.typeMirror(Runnable.class);
        DeclaredType callableOfRunnable = reflection.getDeclaredType(callable, runnable);
        DeclaredType element = converter.convert(callableOfRunnable);
        assertEquals(callable, element.asElement());
    }

    /**
    * Verify that type argument of {@link Callable}&lt;{@link Runnable}&gt; gets translated to
    * {@link Walkable}.
    **/
    @Test
    public void convertTypeArgumentForTypeParameterWithTranslation()
    {
        TypeElement callable = reflection.typeElement(Callable.class);
        TypeMirror runnable = reflection.typeMirror(Runnable.class);
        DeclaredType declaredType = reflection.getDeclaredType(callable, runnable);
        DeclaredType element = converter.convert(declaredType);
        TypeMirror walkable = reflection.typeMirror(Walkable.class);
        TypeMirror typeArgument = element.getTypeArguments().get(0);
        assertEquals(walkable, typeArgument);
    }

    /**
    * Verify that {@link Future}&lt;{@link Callable}&lt;{@link Runnable}&gt;&gt; gets translated to
    * {@link Pending}&lt;{@link Callable}&lt;{@link Walkable}&gt;&gt;.
    **/
    @Test
    public void convertNestedParameterizedTypesWithTranslation()
    {
        TypeElement typeCallable = reflection.typeElement(Callable.class);
        TypeMirror runnable = reflection.typeMirror(Runnable.class);
        DeclaredType typeCallableOfRunnable = reflection.getDeclaredType(typeCallable, runnable);
        TypeElement typeFuture = reflection.typeElement(Future.class);
        DeclaredType typeFutureOfCallableOfRunnable = reflection.getDeclaredType(typeFuture, typeCallableOfRunnable);
        DeclaredType result = converter.convert(typeFutureOfCallableOfRunnable);
        DeclaredType outer = converter.getRawType(result);
        DeclaredType outerArgument = (DeclaredType) result.getTypeArguments().get(0);
        DeclaredType middle = converter.getRawType(outerArgument);
        DeclaredType inner = (DeclaredType) outerArgument.getTypeArguments().get(0);
        TypeMirror pending = converter.getRawType(reflection.typeMirror(Pending.class));
        TypeMirror callable = converter.getRawType(reflection.typeMirror(Callable.class));
        TypeMirror walkable = converter.getRawType(reflection.typeMirror(Walkable.class));
        TypeMirror[] expected = {pending, callable, walkable};
        TypeMirror[] actual = {outer, middle, inner};
        assertArrayEquals(expected, actual);
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
                throw new MirroredTypeException(reflection.typeMirror(from));
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

        // Setup spy to determined which method was called:
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
