//                                                                          //
// Copyright 2013 - 2024 Peter Walser, Mirko Raner                          //
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
package pro.projo.internal.rcg.utilities;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

/**
* The {@link GenericTypeResolver} is a utility class whose main purpose is to encapsulate
* Peter Walser's generic type argument resolution code as found in
* <a href="https://stackoverflow.com/questions/17297308#answer-17301917">https://stackoverflow.com/questions/17297308#answer-17301917</a>.
* Per Stack Overflow's <a href="https://stackoverflow.com/legal/terms-of-service/public#licensing">licensing rules</a>,
* section 6, subsection "Subscriber Content", source code snippets posted on the site
* are licensed under <a href="https://creativecommons.org/licenses/by-sa/4.0/">CC BY-SA 4.0</a>
* and are hereby further distributed under the Apache License, version 2.0, in a manner
* compatible with CC BY-SA 4.0, including full attribution of authorship. Author and
* copyright holder for the {@link #resolveActualTypeArgs(Class, Class, Type...)}
* method is <a href="https://stackoverflow.com/users/63293">Peter Walser</a>.
*
* @author Peter Walser
* @author Mirko Raner
**/
public class GenericTypeResolver
{
    /**
    * Gets the actual return type of a method with respect to an actual type, including
    * scenarios where the originally declared return type is a type variable.
    *
    * @param <T> the base type that contains the original method declaration
    * @param originalMethodDeclaration the original {@link Method} declaration
    * @param actualType the actual concrete type (must be a sub-type of {@code T})
    * @return the actual resolved return type (or the bounds type if there was no type variable binding)
    **/
    public static <T> Type getReturnType(Method originalMethodDeclaration, Class<? extends T> actualType)
    {
        Type genericReturnType = originalMethodDeclaration.getGenericReturnType();
        if (genericReturnType instanceof TypeVariable)
        {
            String variableName = ((TypeVariable<?>)genericReturnType).getName();
            @SuppressWarnings("unchecked")
            Class<T> baseClass = (Class<T>)originalMethodDeclaration.getDeclaringClass();
            TypeVariable<?>[] typeVariables = baseClass.getTypeParameters();
            List<String> variableNames = Stream.of(typeVariables).map(TypeVariable::getName).collect(toList());
            int variableIndex = variableNames.indexOf(variableName);
            Class<? extends T> offspring = actualType;
            Type[] resolvedTypeArguments = resolveActualTypeArgs(offspring, baseClass);
            Type resolvedType = resolvedTypeArguments[variableIndex];
            if (resolvedType instanceof TypeVariable)
            {
                return originalMethodDeclaration.getReturnType();
            }
            else
            {
                return resolvedType;
            }
        }
        else
        {
            return genericReturnType;
        }
    }

    /**
    * Resolves the actual generic type arguments for a base class, as viewed from a subclass or implementation.
    * 
    * @param <T> base type
    * @param offspring class or interface subclassing or extending the base type
    * @param base base class
    * @param actualArgs the actual type arguments passed to the offspring class
    * @return actual generic type arguments, must match the type parameters of the offspring class. If omitted, the
    * type parameters will be used instead.
    **/
    public static <T> Type[] resolveActualTypeArgs (Class<? extends T> offspring, Class<T> base, Type... actualArgs)
    {
        assert offspring != null;
        assert base != null;
        assert actualArgs.length == 0 || actualArgs.length == offspring.getTypeParameters().length;

        //  If actual types are omitted, the type parameters will be used instead.
        if (actualArgs.length == 0)
        {
            actualArgs = offspring.getTypeParameters();
        }
        // map type parameters into the actual types
        Map<String, Type> typeVariables = new HashMap<String, Type>();
        for (int i = 0; i < actualArgs.length; i++)
        {
            TypeVariable<?> typeVariable = (TypeVariable<?>) offspring.getTypeParameters()[i];
            typeVariables.put(typeVariable.getName(), actualArgs[i]);
        }

        // Find direct ancestors (superclass, interfaces)
        List<Type> ancestors = new LinkedList<Type>();
        if (offspring.getGenericSuperclass() != null)
        {
            ancestors.add(offspring.getGenericSuperclass());
        }
        for (Type t: offspring.getGenericInterfaces())
        {
            ancestors.add(t);
        }

        // Recurse into ancestors (superclass, interfaces)
        for (Type type: ancestors)
        {
            if (type instanceof Class<?>)
            {
                // ancestor is non-parameterized. Recurse only if it matches the base class.
                Class<?> ancestorClass = (Class<?>) type;
                if (base.isAssignableFrom(ancestorClass))
                {
                    @SuppressWarnings("unchecked")
                    Type[] result = resolveActualTypeArgs((Class<? extends T>) ancestorClass, base);
                    if (result != null)
                    {
                        return result;
                    }
                }
            }
            if (type instanceof ParameterizedType)
            {
                // ancestor is parameterized. Recurse only if the raw type matches the base class.
                ParameterizedType parameterizedType = (ParameterizedType)type;
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class<?>)
                {
                    Class<?> rawTypeClass = (Class<?>) rawType;
                    if (base.isAssignableFrom(rawTypeClass))
                    {
                        // loop through all type arguments and replace type variables with the actually known types
                        List<Type> resolvedTypes = new LinkedList<Type>();
                        for (Type t: parameterizedType.getActualTypeArguments())
                        {
                            if (t instanceof TypeVariable<?>)
                            {
                                Type resolvedType = typeVariables.get(((TypeVariable<?>)t).getName());
                                resolvedTypes.add(resolvedType != null? resolvedType:t);
                            } else
                            {
                                resolvedTypes.add(t);
                            }
                        }

                        @SuppressWarnings("unchecked")
                        Type[] result = resolveActualTypeArgs((Class<? extends T>) rawTypeClass, base, resolvedTypes.toArray(new Type[] {}));
                        if (result != null)
                        {
                            return result;
                        }
                    }
                }
            }
        }

        // we have a result if we reached the base class.
        return offspring.equals(base) ? actualArgs : null;
    }
}
