//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
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
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
* The {@link TypeConverter} class converts types according to the mappings specified
* in a set of {@link Interface}s.
*
* @author Mirko Raner
**/
public class TypeConverter implements TypeMirrorUtilities
{
    private Types types;
    private Name targetPackage;
    private PackageShortener shortener;
    private Map<String, String> generates;
    private Set<String> imports;

    public TypeConverter(Types types, PackageShortener shortener, Name targetPackage, Stream<Source> sources,
    Source... primary)
    {
        this.types = types;
        this.shortener = shortener;
        this.targetPackage = targetPackage;
        Function<Source, String> keyMapper = type -> getTypeMirror(type::from).toString();
        Function<Source, String> valueMapper = type ->
            qualify(getMap(type).getOrDefault(getTypeMirror(type::from), type.generate()));
        generates = Stream.of(primary)
            .flatMap(source -> getMap(source).entrySet().stream())
            .collect(toMap(entry -> entry.getKey().toString(), entry -> qualify(entry.getValue())));
        imports = new HashSet<>(generates.values());
        sources.collect(toMap(keyMapper, valueMapper, this::rejectDuplicates, () -> generates));
    }

    public String convert(TypeMirror element)
    {
        return convert(element, Collections.emptyMap());
    }

    public String convert(TypeMirror element, Map<String, String> typeRenames)
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
        if (element instanceof TypeVariable)
        {
            String name = element.toString();
            return typeRenames.getOrDefault(name, name);
        }
        if (element instanceof PrimitiveType)
        {
            return shorten(generates.getOrDefault(element.toString(), element.toString()));
        }
        if (element instanceof NoType)
        {
            return element.toString();
        }
        throw new UnsupportedOperationException(element.getClass().getName());
    }

    public String convert(VariableElement variable, Map<String, String> typeRenames)
    {
        return convert(variable.asType(), typeRenames) + " " + variable.getSimpleName();
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

    private <_Type_> _Type_ rejectDuplicates(_Type_ oldValue, _Type_ newValue)
    {
        if (oldValue.equals(newValue))
        {
            return oldValue;
        }
        throw new IllegalStateException("old=" + oldValue + ", new=" + newValue);
    }

    private String qualify(String name)
    {
        return name.indexOf('.') != -1? name:targetPackage + "." + name;
    }

    private String shorten(String fqcn)
    {
        imports.add(fqcn);
        return shortener.shorten(fqcn);
    }
}
