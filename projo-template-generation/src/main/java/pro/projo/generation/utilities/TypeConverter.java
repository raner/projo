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

import static java.util.stream.Collectors.toMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import pro.projo.interfaces.annotation.Interface;

/**
* The {@link TypeConverter} class converts {@link DeclaredType}s according to the mappings specified
* in a set of {@link Interface}s.
*
* @author Mirko Raner
**/
public class TypeConverter implements TypeMirrorUtilities
{
    private Types types;
    private Elements elements;
    private Map<DeclaredType, String> generates = new HashMap<>();

    public TypeConverter(Types types, Elements elements, Name targetPackage, Interface... interfaces)
    {
        this.types = types;
        this.elements = elements;
        generates = Stream.of(interfaces).collect(toMap(type -> getDeclaredType(type::from), type -> targetPackage + "." + type.generate()));
    }

    public DeclaredType convert(DeclaredType element)
    {
        DeclaredType declaredType = getRawType(element);
        List<? extends TypeMirror> typeArguments = element.getTypeArguments();
        String string = generates.getOrDefault(declaredType, declaredType.toString());
        TypeElement typeElement = elements.getTypeElement(string);
        DeclaredType baseType = (DeclaredType) typeElement.asType();
        TypeMirror[] arguments = typeArguments.stream().map(this::map).toArray(TypeMirror[]::new);
        baseType = types.getDeclaredType(typeElement, arguments);
        // NOTE: this will actually make copies of the TypeArguments, hence the resulting
        //       DeclaredTypes will not be equal even if they have all the same attributes
        return baseType;
    }

    public DeclaredType getRawType(TypeMirror type)
    {
        return types.getDeclaredType((TypeElement)((DeclaredType)type).asElement());
    }

    private TypeMirror map(TypeMirror type)
    {
        if (type instanceof DeclaredType)
        {
            return convert((DeclaredType)type);
        }
        return type;
    }

    private DeclaredType getDeclaredType(Supplier<Class<?>> type)
    {
        return (DeclaredType)getTypeMirror(type);
    }
}
