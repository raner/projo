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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Unmapped;
import pro.projo.template.annotation.Configuration;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

/**
* The {@link TypeConverter} class converts types according to the mappings specified
* in a set of {@link pro.projo.interfaces.annotation.Interface}s. An individual {@link TypeConverter}
* will be created for each interface being processed, but the mappings for all other interfaces in the
* same package are passed along as well.
*
* @author Mirko Raner
**/
public class TypeConverter implements TypeMirrorUtilities
{
    public static class Type
    {
        private final String signature;
        private final boolean unmapped;

        Type(String signature)
        {
            this(signature, false);
        }

        Type(String signature, boolean unmapped)
        {
            this.signature = signature;
            this.unmapped = unmapped;
        }

        public String signature()
        {
            return signature;
        }

        public boolean unmapped()
        {
            return unmapped;
        }
    }

    private static class DummyMessager implements Messager
    {
        @Override public void printMessage(Kind kind, CharSequence msg) {}
        @Override public void printMessage(Kind kind, CharSequence msg, Element e) {}
        @Override public void printMessage(Kind kind, CharSequence msg, Element e, AnnotationMirror a) {}
        @Override public void printMessage(Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {}
    }

    public final static Set<String> primitives =
        unmodifiableSet(new HashSet<>(asList("byte", "short", "int", "long", "float", "double", "char", "boolean", "void")));

    private Types types;
    private Messager debug;
    private Options options;
    private Name targetPackage;
    private PackageShortener shortener;
    private Map<String, String> generates;
    private Set<String> imports;

    public TypeConverter(Types types, PackageShortener shortener, Name targetPackage, Stream<Source> sources)
    {
        this(types, shortener, targetPackage, sources, null);
    }

    public TypeConverter(Types types, PackageShortener shortener, Name targetPackage, Stream<Source> sources,
    Source primary)
    {
        this(types, shortener, targetPackage, sources, primary, new DummyMessager());
    }

    public TypeConverter(Types types, PackageShortener shortener, Name targetPackage, Stream<Source> sources,
    Source primary, Messager debug)
    {
        this.types = types;
        this.debug = debug;
        this.shortener = shortener;
        this.targetPackage = targetPackage;
        options = primary != null? primary.options():Configuration.defaults(); // TODO: merge with package-wide options
        Function<Source, String> keyMapper = type -> getTypeMirror(type::from).toString();
        Function<Source, String> valueMapper = type ->
            qualify(getMap(type).getOrDefault(getTypeMirror(type::from), type.generate()));
        generates = primary == null? new HashMap<>():Stream.of(primary)
            .flatMap(source -> getMap(source).entrySet().stream())
            .collect(toMap(entry -> entry.getKey().toString(), entry -> qualify(entry.getValue())));
        imports = new HashSet<>(generates.values());
        sources.collect(toMap(keyMapper, valueMapper, this::rejectDuplicates, () -> generates));
    }

    @Override
    public Elements elements()
    {
        return null;
    }

    public Type convert(TypeMirror element)
    {
        return convert(element, Collections.emptyMap(), false, true);
    }

    public Type convert(TypeMirror element, Map<String, String> typeRenames, final boolean unmapped)
    {
        return convert(element, typeRenames, unmapped, false);
    }

    public Type convert(TypeMirror element, Map<String, String> typeRenames, final boolean unmapped, boolean supertype)
    {
        if (element == null)
        {
            return new Type("", unmapped); // to deal with absent extends/super bounds
        }
        if (element instanceof DeclaredType)
        {
            DeclaredType declaredType = getRawType(element);
            List<? extends TypeMirror> typeArguments = ((DeclaredType)element).getTypeArguments();
            Type mainType = getOrDefault(declaredType.toString());
            debug.printMessage(Kind.NOTE, "declared type " + declaredType + " unmapped? " + mainType.unmapped);
            Type[] arguments = typeArguments.stream().map(type -> convert(type, typeRenames, unmapped)).toArray(Type[]::new);
            boolean hasArguments = arguments.length > 0;
            String signature = shorten(new Type(mainType.signature, mainType.unmapped & !supertype))
                + Stream.of(arguments).map(Type::signature).collect(joining(", ", hasArguments? "<":"", hasArguments? ">":""));
            return new Type(signature, unmapped || mainType.unmapped 
                || Stream.of(arguments).map(Type::unmapped).reduce(false, Boolean::logicalOr));
        }
        if (element instanceof ArrayType)
        {
            Unmapped skip = options.skip();
            Type arrayType = convert(((ArrayType)element).getComponentType(), typeRenames, unmapped);
            return new Type(arrayType.signature + "[]", unmapped || arrayType.unmapped || skip.includingArrays());
        }
        if (element instanceof WildcardType)
        {
            WildcardType wildcard = (WildcardType)element;
            TypeMirror extendsBound = wildcard.getExtendsBound();
            TypeMirror superBound = wildcard.getSuperBound();
            String bounds = "?";
            boolean unmappedWildcard = unmapped;
            if (extendsBound != null)
            {
                Type extendsType = convert(extendsBound, typeRenames, unmapped);
                bounds += " extends " + extendsType.signature;
                unmappedWildcard |= extendsType.unmapped;
            }
            if (superBound != null)
            {
                Type superType = convert(superBound, typeRenames, unmapped);
                bounds += " super " + superType.signature;
                unmappedWildcard |= superType.unmapped;
            }
            return new Type(bounds, unmappedWildcard);
        }
        if (element instanceof TypeVariable)
        {
            String name = element.toString();
            return new Type(typeRenames.getOrDefault(name, name), unmapped);
        }
        if (element instanceof PrimitiveType)
        {
            Type type = getOrDefault(element.toString());
            return new Type(shorten(type), type.unmapped);
        }
        if (element instanceof NoType)
        {
            return getOrDefault(element.toString()); // handle mapped/unmapped void
        }
        throw new UnsupportedOperationException(element.getClass().getName());
    }

    public Type convert(VariableElement variable, Map<String, String> typeRenames)
    {
        return convert(variable.asType(), typeRenames, false);
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

    public Unmapped getUnmapped()
    {
        return options.skip();
    }

    Type getOrDefault(String element)
    {
        Unmapped unmapped = options.skip();
        boolean skip = unmapped.value() && (!primitives.contains(element) || unmapped.includingPrimitives());
        String result = generates.get(element);
        return result != null? new Type(result, false):new Type(element, skip);
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

    private String shorten(Type type)
    {
        if (!type.unmapped)
        {
            imports.add(type.signature);
        }
        return shortener.shorten(type.signature);
    }
}
