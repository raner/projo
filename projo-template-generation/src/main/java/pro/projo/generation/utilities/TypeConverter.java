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

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import pro.projo.interfaces.annotation.Interface;

/**
* The {@link TypeConverter} class converts types according to the mappings specified
* in a set of {@link Interface}s.
*
* @author Mirko Raner
**/
public class TypeConverter implements TypeMirrorUtilities
{
    private Types types;
    private PackageShortener shortener;
    private Map<String, String> generates = new HashMap<>();
    private Set<String> imports = new HashSet<>();

    public TypeConverter(Types types, PackageShortener shortener, Name targetPackage, Stream<Source> interfaces)
    {
        this.types = types;
        this.shortener = shortener;
        Function<Source, String> keyMapper = type -> getDeclaredType(type::from).toString();
        Function<Source, String> valueMapper = type ->
        {
            String className = getMap(type).getOrDefault(getTypeMirror(type::from), type.generate());
            return targetPackage + "." + className;
        };
        BinaryOperator<String> merger = (oldValue, newValue) ->
        {
            if (oldValue.equals(newValue))
            {
                return oldValue;
            }
            throw new IllegalStateException("old=" + oldValue + ", new=" + newValue);
        };
        generates = interfaces.collect(toMap(keyMapper, valueMapper, merger , HashMap::new));
    }

    public String convert(TypeMirror element)
    {
        if (element == null)
        {
            return ""; // to deal with absent extends/super bounds
        }
        if (element instanceof DeclaredType)
        {
            DeclaredType declaredType = getRawType(element);
            List<? extends TypeMirror> typeArguments = ((DeclaredType)element).getTypeArguments();
            String string = generates.getOrDefault(declaredType.toString(), declaredType.toString());
            String[] arguments = typeArguments.stream().map(this::convert).toArray(String[]::new);
            boolean hasArguments = arguments.length > 0;
            return shorten(string) + Stream.of(arguments).collect(joining(", ", hasArguments? "<":"", hasArguments? ">":""));
        }
        if (element instanceof ArrayType)
        {
            ArrayType arrayType = (ArrayType)element;
            return convert(arrayType.getComponentType()) + "[]";
        }
        if (element instanceof WildcardType)
        {
            WildcardType wildcard = (WildcardType)element;
            TypeMirror extendsBound = wildcard.getExtendsBound();
            TypeMirror superBound = wildcard.getSuperBound();
            String bounds = "?";
            bounds += extendsBound != null? " extends " + convert(extendsBound) : "";
            bounds += superBound != null? " super " + convert(superBound) : "";
            return bounds;
        }
        if (element instanceof TypeVariable
        || (element instanceof NoType)
        || (element instanceof PrimitiveType))
        {
            return element.toString();
        }
        throw new UnsupportedOperationException(element.getClass().getName());
    }

    public String convert(VariableElement variable)
    {
        TypeMirror type = variable.asType();
        String result = convert(type) + " " + variable.getSimpleName();
        return result;
    }

    public DeclaredType getRawType(TypeMirror type)
    {
        if (type instanceof WildcardType)
        {
            WildcardType wildcard = (WildcardType)type;
            if (wildcard.getExtendsBound() != null)
            {
                return types.getDeclaredType((TypeElement)((DeclaredType)wildcard.getExtendsBound()).asElement());
            }
            return types.getDeclaredType((TypeElement)((DeclaredType)wildcard.getSuperBound()).asElement());
        }
        return types.getDeclaredType((TypeElement)((DeclaredType)type).asElement());
    }

    public PackageShortener getPackageShortener()
    {
        return shortener;
    }

    public Set<String> getImports()
    {
        return imports;
    }

    private DeclaredType getDeclaredType(Supplier<Class<?>> type)
    {
        return (DeclaredType)getTypeMirror(type);
    }

    private String shorten(String fqcn)
    {
        imports.add(fqcn);
        return shortener.shorten(fqcn);
    }
}
