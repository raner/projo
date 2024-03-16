//                                                                          //
// Copyright 2023 Mirko Raner                                               //
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
package pro.projo.generation.examples;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

import pro.projo.generation.examples.impl.HtmlContentHeadImpl;
import pro.projo.generation.interfaces.test.html.baseclasses.Body;
import pro.projo.generation.interfaces.test.html.baseclasses.Head;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContent;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContentBody;
import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContentHead;

/**
* The {@link HtmlExample} class illustrates how the generated HTML API can be
* used in Java. Due to Java's cumbersome syntax for lambda expressions, the
* result is pretty horrible, but it looks a lot better in more modern
* programming languages (e.g., Xtend).
*
* @author Mirko Raner
**/
public class HtmlExample
{
    public HtmlContent example(HtmlContentHead html)
    {
        return
        html.
            head().$($ -> $.title().$(() -> "Title")).
            body().$
            (
                $ -> $
                .
                img().src("image.jpg").alt("An image").$()
                .
                div().class_("content").id("content-id").$
                (
                    $1 -> $1
                    .
                    em().$
                    (
                        $2 -> $2.
                        img().src("image2.jpg").alt("Another image").$()
                    )
//                    .
//                    $("Hello").em().$($3 -> $3.$("emphasized text")).$("more text").
//                    img().src("image3.png").alt("Third image").$()
                )
//                .
//                ul().$
//                (
//                    $4 -> $4.
//                    li().$
//                    (
//                        $5 -> $5.
//                        $("First Item")
//                    ).
//                    li().$
//                    (
//                        $6 -> $6.
//                        $("Second Item")
//                    )
//                ).
//                noscript().$
//                (
//                    $7 -> $7.
//                    b().$($8 -> $8.$("No script installed"))
//                )
            );
    }

    public static void main(String[] arguments)
    {
      HtmlContentHead html = new HtmlContentHeadImpl();
      System.err.println(new HtmlExample().example(html));
    }
    public static void main0(String[] arguments)
    {
//        ClassLoader classLoader = HtmlExample.class.getClassLoader();
//        Class<?>[] interfaces =
//        {
//            Body.class,
//            Head.class,
//            HtmlContent.class,
//            HtmlContentBody.class,
//            HtmlContentHead.class
//        };
        StringBuilder content = new StringBuilder();
//        InvocationHandler handler = (Object proxy, Method method, Object[] args) ->
//        {
//          content.append("[" + method.getName() + "]");
//          return proxy;
//        };
        HtmlContentHead collector = create(HtmlContentHead.class, content);
        new HtmlExample().example(collector);
        System.err.println("Content: " + content);
    }
    
    static <TYPE> TYPE create(Class<TYPE> type, StringBuilder collector, Type... typeArguments)
    {
        ClassLoader classLoader = HtmlExample.class.getClassLoader();
        InvocationHandler handler = (Object proxy, Method method, Object[] args) ->
        {
            System.err.println("declaring type:" + method.getDeclaringClass());
            System.err.println("declaring type parameters:" + Arrays.asList(method.getDeclaringClass().getTypeParameters()));
            System.err.println("method:" + method);
            System.err.println("RT:" + method.getGenericReturnType());
            
            Type returnType = method.getGenericReturnType();
            if (returnType instanceof TypeVariable)
            {
                // Follow the interfaces from the nominal type until reaching the
                // declaring type, and carry type variables along:
                //
//                Type[] interfaces = type.getGenericInterfaces();
                // TODO: there can be multiple interfaces
//                if (interfaces.length > 0)
//                {
//                    Type firstInterface = interfaces[0];
//                    System.err.println("firstInterface:" + firstInterface);
//                }
                // 1. The variable we're trying to resolve is called PARENT
                String variableName = ((TypeVariable<?>)returnType).getName();
                
                // 2. The method that returns it is declared by type Element
                //
                Class<?> declaringType = method.getDeclaringClass();
                TypeVariable<?>[] declaringTypesTypeParameters = declaringType.getTypeParameters();
                //
                // 3. In Element's declaration this type variable is at index 0 (of 3)
                //
                int variableIndexInDeclaringType = -1;
                for (int index = 0; index < declaringTypesTypeParameters.length; index++)
                {
                    if (declaringTypesTypeParameters[index].getName().equals(variableName))
                    {
                        variableIndexInDeclaringType = index;
                        break;
                    }
                }
                // 4. The nominal type, Head, passes its own type variable,
                //    coincidentally also called PARENT and at index 0, into index 0
                //    of Element's type variables
                //    => which index in Head does variable index 0 of Element have?
                //
                System.err.println("nominal type:" + type + Arrays.asList(typeArguments));
                System.err.println("nominal type parameters:" + Arrays.asList(type.getTypeParameters()));
                Type[] interfaces = type.getGenericInterfaces();
                // TODO: there can be multiple interfaces
                if (interfaces.length > 0 && interfaces[0] instanceof ParameterizedType)
                {
                    ParameterizedType firstInterface = (ParameterizedType)interfaces[0];
                    System.err.println("firstInterface:" + firstInterface);
                    Type matchingType = firstInterface.getActualTypeArguments()[variableIndexInDeclaringType];
                    if (matchingType instanceof TypeVariable)
                    {
                        String typeVariableName = ((TypeVariable<?>)matchingType).getName();
                        
                        // At what index in the type's declaration is the type variable called
                        // `typeVariableName`?
                        //
                        int nominalIndex = -1;
                        TypeVariable<?>[] nominalTypeParameters = type.getTypeParameters();
                        for (int index = 0; index < nominalTypeParameters.length; index++)
                        {
                            if (nominalTypeParameters[index].getName().equals(typeVariableName))
                            {
                                nominalIndex = index;
                            }
                        }
                        //Type genericReturn = (Type)method.getGenericReturnType();
                        //System.err.println("Generic return type: " + genericReturn);
                        returnType = typeArguments[nominalIndex];
                        System.err.println("Resolved return type: " + returnType);
                    }
                    else
                    {
                        // Variable is already bound in the type's definition:
                        //
                    }
                }
                //
                // 5. The type variable at index 0 of Head is bound to HtmlContentBody
                //    in the declaration of the head() method of HtmlContentHead
                //
                // 
            }
            Type[] arguments = {};
            if (returnType instanceof ParameterizedType)
            {
                arguments = ((ParameterizedType)returnType).getActualTypeArguments();
            }
            collector.append("[" + method.getName() + "]");
            Type[] parameterTypes = method.getGenericParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].getTypeName().startsWith("java.util.function.Function"))
            {
                System.err.println("===> " + parameterTypes[0].getTypeName());
                Class<?> declaringClass = method.getDeclaringClass();
                System.err.println("===> declared by " + declaringClass);
                ParameterizedType parameterizedType = (ParameterizedType)parameterTypes[0];
                TypeVariable<?> inType = (TypeVariable<?>)parameterizedType.getActualTypeArguments()[0];
                System.err.println("===> inType " + inType);
                Type resolved = resolve(inType, type);
                Object object = create((Class<?>)resolved, collector);
                ((Function)args[0]).apply(object);
            }
            if (returnType instanceof ParameterizedType)
            {
                returnType = ((ParameterizedType)returnType).getRawType();
            }
            if (returnType instanceof TypeVariable)
            {
                System.err.println(">>> typeVariable=" + returnType);
            }
            return create((Class<?>)returnType, collector, arguments);
        };
        Class<?>[] interfaces = {type};
        @SuppressWarnings("unchecked")
        TYPE result = (TYPE)Proxy.newProxyInstance(classLoader, interfaces, handler);
        return result;
    }
    
    static Type resolve(TypeVariable<?> typeVariable, Class<?> context)
    {
        System.err.println("*** Resolving " + typeVariable + " in context " + context);
        GenericDeclaration declaration = typeVariable.getGenericDeclaration();
        System.err.println("*** declaration=" + declaration);
        int index = Arrays.asList(declaration.getTypeParameters()).indexOf(typeVariable);
        System.err.println("*** index=" + index);
        Stream<Type> genericInterfaces = Stream.of(context.getGenericInterfaces());
        Type matching = genericInterfaces.filter(type -> type.getTypeName().startsWith(((Type)declaration).getTypeName())).findFirst().get();
        System.err.println("*** matching=" + matching);
        Type resolvedType = ((ParameterizedType)matching).getActualTypeArguments()[index];
        System.err.println("*** resolvedType=" + resolvedType);
        return resolvedType;
    }
}
