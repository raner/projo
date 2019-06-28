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

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
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
