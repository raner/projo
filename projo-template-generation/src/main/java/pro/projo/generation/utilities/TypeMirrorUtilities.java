//                                                                          //
// Copyright 2019 - 2022 Mirko Raner                                        //
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

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import pro.projo.interfaces.annotation.Interface;
import static java.util.stream.Collectors.toMap;

/**
* {@link TypeMirrorUtilities} is a collection of useful methods for dealing with {@link TypeMirror}s.
*
* @author Mirko Raner
**/
public interface TypeMirrorUtilities
{
    /**
    * Provides access to the {@link Elements}.
    * This method must be implemented by the client type.
    *
    * @return the {@link Elements}
    **/
    Elements elements();

    /**
    * Converts a {@link Class} into a {@link TypeMirror}.
    *
    * @param supplier a method that returns the {@link Class} (as the {@link Class} itself will not
    * work during annotation processing)
    * @return the corresponding {@link TypeMirror}
    **/
    default TypeMirror getTypeMirror(Supplier<Class<?>> supplier)
    {
        try
        {
            supplier.get();
        }
        catch (MirroredTypeException mirroredTypeException)
        {
            return mirroredTypeException.getTypeMirror();
        }
        return null;
    }

    /**
     * Converts a {@link Class} into a {@link TypeElement}.
     *
     * @param supplier a method that returns the {@link Class} (as the {@link Class} itself will not
     * work during annotation processing)
     * @return the corresponding {@link TypeElement}
     **/
    default TypeElement getTypeElement(Supplier<Class<?>> supplier)
    {
        return elements().getTypeElement(getTypeMirror(supplier).toString());
    }

    /**
    * Resolves a possibly type-mirrored {@link Class}. If the class is indeed type-mirrored, this
    * method will first construct the {@link TypeMirror} and then use the type name to create the
    * corresponding {@link Class} object. If the class is not type-mirrored the method will simply
    * return the {@link Class} object provided by the {@link Supplier}.
    *
    * @param <_Type_> the type represented by the class
    * @param supplier a possibly type-mirrored {@link Class} {@link Supplier}
    * @return the {@link Class} (constructed directly, or via an intermediate {@link TypeMirror} if necessary)
    **/
    default <_Type_> Class<_Type_> getType(Supplier<Class<?>> supplier)
    {
        TypeMirror typeMirror = getTypeMirror(supplier);
        if (typeMirror != null)
        {
            try
            {
                @SuppressWarnings("unchecked")
                Class<_Type_> type = (Class<_Type_>)Class.forName(typeMirror.toString());
                return type;
            }
            catch (Exception exception)
            {
                throw new RuntimeException(exception);
            }
        }
        @SuppressWarnings("unchecked")
        Class<_Type_> type = (Class<_Type_>)supplier.get();
        return type;
    }

    /**
    * Extracts the {@link pro.projo.interfaces.annotation.Map Map} of {@link TypeMirror}s to class names as
    * a {@link java.util.Map}.
    *
    * @param map the {@link Interface} annotation instance
    * @return a map of {@link TypeMirror}s to class names
    **/
    default Map<TypeMirror, String> getMap(Source map)
    {
        return Stream.of(map.map()).collect(toMap(annotation -> getTypeMirror(annotation::type), pro.projo.interfaces.annotation.Map::to));
    }
}
