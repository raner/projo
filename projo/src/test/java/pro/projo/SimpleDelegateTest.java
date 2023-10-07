//                                                                          //
// Copyright 2021 - 2023 Mirko Raner                                        //
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
package pro.projo;

import java.io.Closeable;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ExceptionDefinition;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import pro.projo.annotations.Overrides;
import pro.projo.annotations.Delegate;
import pro.projo.sextuples.Factory;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static net.bytebuddy.description.type.TypeDescription.Generic.Builder.typeVariable;
import static net.bytebuddy.implementation.FixedValue.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pro.projo.annotations.Overrides.toString;

public class SimpleDelegateTest
{
    // Test interfaces:

    public static interface UncheckedMethodDescription extends MethodDescription
    {
        @Delegate
        MethodDescription proxied();

        @Override
        default boolean isInvokableOn(TypeDescription type)
        {
          return true;
        }

        @Overrides(toString)
        default String toStringOverride()
        {
            return "unchecked " + proxied().toString();
        }
    }

    public static interface PreparameterizedType extends InstrumentedType
    {
        pro.projo.doubles.Factory<PreparameterizedType, InstrumentedType, TypeList.Generic> FACTORY =
            Projo.creates(PreparameterizedType.class).with
            (
                PreparameterizedType::type,
                PreparameterizedType::getTypeVariables
            );

        @Delegate
        InstrumentedType type();

        // This method is supposed to return a value that was passed in at
        // object creation time, not what the proxied InstrumentedType
        // would return.
        @Override
        TypeList.Generic getTypeVariables(); 

        @Override
        default boolean isGenerified()
        {
            return true;
        }
    }

    public static interface PreparameterizedTypeWithName extends InstrumentedType
    {
      pro.projo.doubles.Factory<PreparameterizedTypeWithName, InstrumentedType, String> FACTORY =
          Projo.creates(PreparameterizedTypeWithName.class).with
          (
              PreparameterizedTypeWithName::type,
              PreparameterizedTypeWithName::name
          );

      @Delegate
      InstrumentedType type();

      // This method adds a new attribute
      String name();
    }

    public static interface MergeableInitial<TYPE> extends Initial<TYPE>
    {
        // These are additional attributes:
        //
        Collection<? extends ModifierContributor.ForMethod> modifiers();
        List<? extends Annotation> annotations();
        DynamicType.Builder<?> originalBuilder();
        TypeDefinition returnType();
        Implementation implementation();
        String methodName();

        // This is the factory for creating new objects:
        //
        @SuppressWarnings("rawtypes")
        Factory
        <
            MergeableInitial,
            DynamicType.Builder<?>,
            String,
            TypeDefinition,
            Implementation,
            Collection<? extends ModifierContributor.ForMethod>,
            List<? extends Annotation>
        >
        FACTORY = Projo.creates(MergeableInitial.class).with
        (
            MergeableInitial::originalBuilder,
            MergeableInitial::methodName,
            MergeableInitial::returnType,
            MergeableInitial::implementation,
            MergeableInitial::modifiers,
            MergeableInitial::annotations
        );

        // This is the proxy method - instead of redirecting to a wrapped
        // Initial, all proxied methods redirect to the result of this method:
        //
        @Delegate
        @SuppressWarnings("unchecked")
        default Initial<TYPE> initial()
        {
            return (Initial<TYPE>)originalBuilder().defineMethod(methodName(), returnType(), modifiers());
        }

        // The following methods override a default behavior using the additional new attributes:
        //
        @Override
        default ReceiverTypeDefinition<TYPE> intercept(Implementation intercept)
        {
            Implementation implementation = implementation();
            Implementation interceptor = implementation != null? implementation:intercept;
            return (ReceiverTypeDefinition<TYPE>)initial().intercept(interceptor).annotateMethod(annotations());
        }

        @Override
        default ReceiverTypeDefinition<TYPE> withoutCode()
        {
            return (ReceiverTypeDefinition<TYPE>)initial().withoutCode().annotateMethod(annotations());
        }

        // The following are new methods that are not part of the original interface:
        //
        @SuppressWarnings("unchecked")
        default MergeableInitial<TYPE> merge(ModifierContributor.ForMethod... modifiers)
        {
            return FACTORY.create(originalBuilder(), methodName(), returnType(), implementation(), asList(modifiers), annotations());
        }

        @SuppressWarnings("unchecked")
        default MergeableInitial<TYPE> implement(Implementation implementation)
        {
            return FACTORY.create(originalBuilder(), methodName(), returnType(), implementation, modifiers(), annotations());
        }

        @SuppressWarnings("unchecked")
        default MergeableInitial<TYPE> annotateMethod(List<? extends Annotation> annotations)
        {
            return FACTORY.create(originalBuilder(), methodName(), returnType(), implementation(), modifiers(), annotations);
        }
    }

    public static interface Package
    {
        String getName();
    }

    // Test objects:
    //
    MethodDescription getName = latent(type(Package.class), type(String.class), "getName", emptyList());

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void simpleDelegateWithOverriddenMethodInvokesDefaultMethod()
    {
        MethodDescription proxied = Projo.delegateOverride(getName, UncheckedMethodDescription.class);
        assertTrue(proxied.isInvokableOn(null));
    }

    @Test
    public void simpleDelegateWithOverriddenMethodInvokesRegularMethodReturningInt()
    {
        MethodDescription proxied = Projo.delegateOverride(getName, UncheckedMethodDescription.class);
        assertEquals(1, proxied.getActualModifiers());
    }

    @Test
    public void simpleDelegateWithOverriddenMethodInvokesRegularMethodReturningObject()
    {
        MethodDescription proxied = Projo.delegateOverride(getName, UncheckedMethodDescription.class);
        assertEquals(type(Package.class), proxied.getDeclaringType());
    }

    @Test
    public void simpleDelegateWithOverriddenMethodInvokesRegularMethodWithParameter()
    {
        MethodDescription proxied = Projo.delegateOverride(getName, UncheckedMethodDescription.class);
        assertTrue(proxied.isVisibleTo(type(Package.class)));
    }

    @Test
    public void simpleDelegateWithOverriddenMethodMustSubtypeAnotherInterface()
    {
        Runnable runnable = () -> {};
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("interface must have a super-interface");
        Projo.delegateOverride(runnable, Runnable.class);
    }

    @Test
    public void delegateMethodWithOverridesAnnotationOverridesIndicatedMethod()
    {
        MethodDescription proxied = Projo.delegateOverride(getName, UncheckedMethodDescription.class);
        assertEquals("unchecked public java.lang.String pro.projo.SimpleDelegateTest$Package.getName()", proxied.toString());
    }

    @Test
    public void simpleDelegateWithProxiedMethodShouldReturnOriginalDelegate()
    {
        UncheckedMethodDescription proxied = Projo.delegateOverride(getName, UncheckedMethodDescription.class);
        assertEquals(getName, proxied.proxied());
    }

    @Test
    public void simpleDelegateWithOneInterface() throws Exception
    {
        boolean[] value = {false};
        Closeable setValue = () -> value[0] = true;
        Closeable proxy = Projo.delegate(setValue, Closeable.class);
        proxy.close();
        assertTrue(value[0]);
    }

    @Test
    public void simpleDelegateWithTwoInterfacesImplementsBoth()
    {
        boolean[] value = {false};
        Runnable setValue = () -> value[0] = true;
        Runnable proxy = Projo.delegate(setValue, Runnable.class, Serializable.class);
        proxy.run();
        assertTrue(value[0]);
        assertTrue(proxy instanceof Serializable);
    }

    @Test
    public void delegateObjectCreatedViaFactoryHasAllAttributes()
    {
        Builder<Object> builder = new ByteBuddy().subclass(Object.class);
        MergeableInitial<?> initial = MergeableInitial.FACTORY.create
        (
            builder,
            "getId",
            type(String.class),
            nullValue(),
            emptyList(),
            emptyList()
        );
        assertEquals(builder, initial.originalBuilder());
        assertEquals("getId", initial.methodName());
        assertEquals(type(String.class), initial.returnType());
        assertEquals(nullValue(), initial.implementation());
        assertEquals(emptyList(), initial.annotations());
        assertEquals(emptyList(), initial.modifiers());
    }

    @Test
    public void delegateObjectCreatedViaFactoryHasWorkingProxiedMethod() throws Exception
    {
        Builder<Object> builder = new ByteBuddy().subclass(Object.class).name("Test");
        MergeableInitial<?> initial = MergeableInitial.FACTORY.create
        (
            builder,
            "getId",
            type(String.class),
            nullValue(),
            asList(Visibility.PROTECTED),
            emptyList()
        );
        Initial<?> proxied = initial.initial();
        Class<?> testClass = proxied.intercept(nullValue()).make().load(getClass().getClassLoader()).getLoaded();
        Method getId = testClass.getDeclaredMethod("getId");
        assertEquals(String.class, getId.getReturnType());
        assertEquals(Modifier.PROTECTED, getId.getModifiers());
    }

    @Test
    public void delegateObjectCreatedViaFactoryDelegatesToProxiedMethod() throws Exception
    {
        Builder<Object> builder = new ByteBuddy().subclass(Object.class).name("Test");
        MergeableInitial<?> initial = MergeableInitial.FACTORY.create
        (
            builder,
            "getId",
            type(String.class),
            nullValue(),
            asList(Visibility.PROTECTED),
            emptyList()
        );
        ExceptionDefinition<?> proxied = initial.withParameters(String.class);
        Class<?> testClass = proxied.intercept(nullValue()).make().load(getClass().getClassLoader()).getLoaded();
        Method getId = testClass.getDeclaredMethod("getId", String.class);
        assertEquals(String.class, getId.getReturnType());
        assertEquals(Modifier.PROTECTED, getId.getModifiers());
    }

    @Test
    public void hybridDelegateObjectImplementsNewMethods() throws Exception
    {
        PreparameterizedType type = PreparameterizedType.FACTORY.create
        (
            (InstrumentedType)new ByteBuddy().subclass(Object.class).make().getTypeDescription(),
            new TypeList.Generic.Explicit(typeVariable("T").build())
        );
        assertTrue(type.isGenerified());
    }
    
    @Test
    public void hybridDelegateObjectReturnsCorrectProxyObject() throws Exception
    {
        InstrumentedType originalType = (InstrumentedType)new ByteBuddy().subclass(Object.class).make().getTypeDescription();
        PreparameterizedType type = PreparameterizedType.FACTORY.create
        (
            originalType,
            new TypeList.Generic.Explicit(typeVariable("T").build())
        );
        assertEquals(originalType, type.type());
    }

    @Test
    public void hybridDelegateObjectReturnsOverriddenAttributes() throws Exception
    {
        InstrumentedType originalType = (InstrumentedType)new ByteBuddy().subclass(Object.class).make().getTypeDescription();
        TypeList.Generic typeVariables = new TypeList.Generic.Explicit(typeVariable("T").build());
        PreparameterizedType type = PreparameterizedType.FACTORY.create(originalType, typeVariables);
        assertEquals(typeVariables, type.getTypeVariables());
    }

    @Test
    public void hybridDelegateObjectUsesCorrectDelegate() throws Exception
    {
        InstrumentedType originalType = (InstrumentedType)new ByteBuddy().subclass(Object.class).make().getTypeDescription();
        TypeList.Generic typeVariables = new TypeList.Generic.Explicit(typeVariable("T").build());
        PreparameterizedType type = PreparameterizedType.FACTORY.create(originalType, typeVariables);
        assertEquals(originalType.getTypeInitializer(), type.getTypeInitializer());
    }
    
    @Test
    public void hybridDelegateObjectUsesCorrectDelegateWithParameters() throws Exception
    {
        InstrumentedType originalType = (InstrumentedType)new ByteBuddy().subclass(Object.class).make().getTypeDescription();
        TypeList.Generic typeVariables = new TypeList.Generic.Explicit(typeVariable("T").build());
        PreparameterizedType type = PreparameterizedType.FACTORY.create(originalType, typeVariables);
        assertEquals(originalType.withEnclosingType(type(SimpleDelegateTest.class)), type.withEnclosingType(type(SimpleDelegateTest.class)));
    }

    @Test
    public void noIntermediateNeededForDelegate() throws Exception
    {
      InstrumentedType originalType = (InstrumentedType)new ByteBuddy().subclass(Object.class).make().getTypeDescription();
      PreparameterizedTypeWithName type = PreparameterizedTypeWithName.FACTORY.create(originalType, "ObjectAlternative");
      assertEquals(originalType.withEnclosingType(type(SimpleDelegateTest.class)), type.withEnclosingType(type(SimpleDelegateTest.class)));
      assertEquals("ObjectAlternative", type.name());
    }

    private MethodDescription.Latent latent(TypeDefinition declaringType, TypeDefinition returnType, String internalName,
            List<? extends ParameterDescription.Token> parameterTokens)
    {
        return new MethodDescription.Latent
        (
            declaringType instanceof TypeDescription? (TypeDescription)declaringType : declaringType.asErasure(),
            internalName,
            Modifier.PUBLIC,
            emptyList(),
            generic(returnType),
            parameterTokens,
            emptyList(),
            emptyList(),
            null,
            null
        );
    }

    private TypeDescription.Generic generic(TypeDefinition type)
    {
        if (type instanceof TypeDescription.Generic)
        {
            return (TypeDescription.Generic)type;
        }
        else if (type instanceof TypeDescription)
        {
            return TypeDescription.Generic.Builder.rawType((TypeDescription)type).build();
        }
        else
        {
            return null;
        }
    }

    private TypeDescription type(Class<?> type)
    {
        return new TypeDescription.ForLoadedType(type);
    }
}
