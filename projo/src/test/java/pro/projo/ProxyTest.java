//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial;
import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.Implementation;
import pro.projo.annotations.Proxy;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ProxyTest
{
    // Test interfaces:

    public static interface UncheckedMethodDescription extends MethodDescription
    {
        @Override
        default boolean isInvokableOn(TypeDescription type)
        {
          return true;
        }
    }

    public static interface PreparameterizedType extends InstrumentedType
    {
        @Override
        default boolean isGenerified()
        {
            return true;
        }

        // This method is supposed to return a value that was passed in at
        // object creation time, not what the proxied InstrumentedType
        // would return.
        @Override
        TypeList.Generic getTypeVariables();        
    }

    public static interface MergeableInitial<TYPE> extends Initial<TYPE>
    {
        // These are additional attributes:
        //
        Collection<? extends ModifierContributor.ForMethod> modifiers();
        List<? extends Annotation> annotations();
        DynamicType.Builder<TYPE> originalBuilder();
        TypeDefinition returnType();
        Implementation implementation();
        String methodName();

        // This is the proxy method - instead of redirecting to a wrapped
        // Initial, all proxied methods redirect to the result of this method:
        //
        @Proxy
        default Initial<TYPE> initial()
        {
          return originalBuilder().defineMethod(methodName(), returnType(), modifiers());
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
        default MergeableInitial<TYPE> merge(ModifierContributor.ForMethod... modifiers)
        {
            return null; //...(originalBuilder(), methodName(), returnType(), implementation(), modifiers, annotations());
        }

        default MergeableInitial<TYPE> implement(Implementation implementation)
        {
            return null; //...(originalBuilder(), methodName(), returnType(), implementation, modifiers(), annotations());
        }

        default MergeableInitial<TYPE> annotateMethod(List<? extends Annotation> annotations)
        {
            return null; //...(originalBuilder(), methodName(), returnType(), implementation(), modifiers(), annotations);
        }
    }

    // Test objects:
    //
    MethodDescription getName = latent(type(Package.class), type(String.class), "getName", emptyList());

    @Test
    public void simpleProxyWithOverriddenMethodInvokesDefaultMethod()
    {
        MethodDescription proxied = Projo.proxy(getName, UncheckedMethodDescription.class);
        assertTrue(proxied.isInvokableOn(null));
    }

    @Test
    public void simpleProxyWithOverriddenMethodInvokesRegularMethodReturningInt()
    {
        MethodDescription proxied = Projo.proxy(getName, UncheckedMethodDescription.class);
        assertEquals(1, proxied.getActualModifiers());
    }

    @Test
    public void simpleProxyWithOverriddenMethodInvokesRegularMethodReturningObject()
    {
        MethodDescription proxied = Projo.proxy(getName, UncheckedMethodDescription.class);
        assertEquals(type(Package.class), proxied.getDeclaringType());
    }

    @Test
    public void simpleProxyWithOverriddenMethodInvokesRegularMethodWithParameter()
    {
        MethodDescription proxied = Projo.proxy(getName, UncheckedMethodDescription.class);
        assertTrue(proxied.isVisibleTo(type(Package.class)));
    }
    
    @Test
    public void simpleProxyWithTwoInterfacesImplementsBoth()
    {
        boolean[] value = {false};
        Runnable setValue = () -> value[0] = true;
        Runnable proxy = Projo.proxy(setValue, Runnable.class, Serializable.class);
        proxy.run();
        assertTrue(value[0]);
        assertTrue(proxy instanceof Serializable);
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
