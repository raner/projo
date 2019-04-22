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

import java.util.List;
import java.util.function.UnaryOperator;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.lang.model.util.Types;
import static java.util.stream.Collectors.joining;

/**
* The {@link TypeStringifier} converts {@link TypeMirror}s into {@link String}s while also applying
* an unary operation, such as shortening fully qualified package names.
*
* TODO: this class was added but then turned out to be unnecessary when its functionality was
*       integrated into the {@link TypeConverter}; for now, it is left in the code base as a
*       nice example for leveraging the {@link SimpleTypeVisitor8}, but it should be removed
*       if it turns out that will not be needed ever again
*
* @author Mirko Raner
**/
public class TypeStringifier extends SimpleTypeVisitor8<String, UnaryOperator<String>>
{
    private Types types;

    /**
    * Creates a new {@link TypeStringifier}.
    *
    * @param types type utilities, mainly used for creating raw types
    **/
    public TypeStringifier(Types types)
    {
        this.types = types;
    }

    @Override
    protected String defaultAction(TypeMirror type, UnaryOperator<String> parameter)
    {
        return parameter.apply(String.valueOf(type));
    }

    @Override
    public String visitDeclared(DeclaredType type, UnaryOperator<String> parameter)
    {
        DeclaredType rawType = getRawType(type);
        String raw = super.visitDeclared(rawType, parameter);
        List<? extends TypeMirror> typeArguments = type.getTypeArguments();
        boolean generic = !typeArguments.isEmpty();
        return raw + typeArguments.stream().map(argument -> visit(argument, parameter)).collect(joining(", ", generic? "<":"", generic? ">":""));
    }

    @Override
    public String visitWildcard(WildcardType wildcard, UnaryOperator<String> parameter)
    {
        TypeMirror extendsBound = wildcard.getExtendsBound();
        TypeMirror superBound = wildcard.getSuperBound();
        String string = "?";
        if (extendsBound != null)
        {
            string = string + " extends " + visit(extendsBound, parameter);
        }
        if (superBound != null)
        {
            string = string + " super " + visit(superBound, parameter);
        }
        return string;
    }

    /**
     * TODO: this code is duplicated from {@link TypeConverter}
     */
    private DeclaredType getRawType(TypeMirror type)
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
}
