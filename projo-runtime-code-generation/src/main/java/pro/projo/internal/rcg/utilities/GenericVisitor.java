//                                                                          //
// Copyright 2024 Mirko Raner                                               //
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

import java.util.stream.Stream;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.description.type.TypeDescription.Generic.Visitor;
import net.bytebuddy.description.type.TypeList;
import pro.projo.annotations.RawInterfaces;
import static java.util.stream.Collectors.toList;

/**
* {@link GenericVisitor} implements a variation of {@link Visitor.Reifying#INITIATING}
* that will strip type arguments from all interfaces extended by an interface that is
* annotated with {@link RawInterfaces} (effectively causing the interface to extend the
* corresponding raw types).
*
* @author Mirko Raner
**/
public class GenericVisitor implements Visitor<Generic>
{
    private final Visitor<Generic> delegate = Generic.Visitor.Reifying.INITIATING;

    /**
    * Determines if use of the {@link GenericVisitor} is necessary.
    *
    * @param annotationNames the type names of the annotations present
    * @return {@code true} if the annotation names include {@link RawInterfaces},
    * otherwise {@code false}
    **/
    public static boolean isNecessaryFor(Stream<String> annotationNames)
    {
        return annotationNames.anyMatch(RawInterfaces.class.getName()::equals);
    }

    @Override
    public Generic onGenericArray(Generic genericArray)
    {
        return delegate.onGenericArray(genericArray);
    }

    @Override
    public Generic onWildcard(Generic wildcard)
    {
        return delegate.onWildcard(wildcard);
    }

    @Override
    public Generic onParameterizedType(Generic parameterizedType)
    {
        return delegate.onParameterizedType(parameterizedType);
    }

    @Override
    public Generic onTypeVariable(Generic typeVariable)
    {
        return delegate.onTypeVariable(typeVariable);
    }

    @Override
    public Generic onNonGenericType(Generic typeDescription)
    {
        // NOTE: typeDescription.getDeclaredAnnotations() will typically not return any
        //       annotations for Generics corresponding to loaded types because
        //       Generic.OfNonGenericType.ForLoadedType uses AnnotationReader.NoOp.INSTANCE.
        //       This method will only work with custom Generics that retain annotations.
        //
        AnnotationList annotations = typeDescription.getDeclaredAnnotations();
        if (typeDescription instanceof RawGenericLoadedType
        && (isNecessaryFor(annotations.asTypeNames().stream())))
        {
            // Even though the type name is known from typeDescription.getActualName()
            // simply regenerating the Class via Class.forName(...) is tricky and prone
            // to failure because the correct class loader is not known. Unless, the
            // current class loader was also used to originally load the Class in question
            // the class may not be found in the current class loader, or, even worse,
            // a duplicate class is loaded. Therefore, the only way to obtain the Class
            // reliably is when the Generic type provided is a RawGenericLoadedType that
            // provides access to the encapsulated Class object:
            //
            Class<?> type = ((RawGenericLoadedType)typeDescription).getType();
            return new Generic.OfNonGenericType.ForLoadedType(type)
            {
                @Override
                public TypeList.Generic getInterfaces()
                {
                    Stream<Class<?>> interfaces = Stream.of(type.getInterfaces());
                    Stream<TypeDescription.ForLoadedType> rawInterfaces = interfaces.map
                    (
                        it -> new TypeDescription.ForLoadedType(it)
                        {
                            private final static long serialVersionUID = 1L;

                            @Override
                            public TypeList.Generic getTypeVariables()
                            {
                                return new TypeList.Generic.Empty();
                            }

                            @Override
                            public boolean isGenerified()
                            {
                                return false;
                            }
                        }
                    );
                    return new TypeList.Generic.Explicit(rawInterfaces.collect(toList()));
                }
            };
        }
        return delegate.onNonGenericType(typeDescription);
    }
}
