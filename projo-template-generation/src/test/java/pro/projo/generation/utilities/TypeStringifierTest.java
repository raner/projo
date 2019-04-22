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
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import org.junit.Test;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import static org.junit.Assert.assertEquals;

/**
* {@link TypeStringifierTest} performs basic tests for validating the
* {@link TypeStringifier}.
*
* @author Mirko Raner
**/
public class TypeStringifierTest
{
    ReflectionTypes types = ReflectionTypes.getInstance();
    UnaryOperator<String> shorten = string -> string.substring(string.lastIndexOf('.') + 1);
    TypeStringifier stringifier = new TypeStringifier(types);

    @Test
    public void testSimpleType()
    {
        String result = types.typeMirror(String.class).accept(stringifier, shorten);
        assertEquals("String", result);
    }

    @Test
    public void testRawType()
    {
      String result = getRawType(types.typeMirror(List.class)).accept(stringifier, shorten);
      assertEquals("List", result);
    }

    @Test
    public void testUnboundedWildcard() throws Exception
    {
        abstract class Class
        {
            abstract Future<?> method();
        }
        TypeMirror type = types.typeMirror(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String result = type.accept(stringifier, shorten);
        assertEquals("Future<?>", result);
    }

    @Test
    public void testNestedWildcardWithTypeVariable() throws Exception
    {
        abstract class Class<T>
        {
            abstract Callable<Future<? extends T>> method();
        }
        TypeMirror type = types.typeMirror(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String result = type.accept(stringifier, shorten);
        String expected = "Callable<Future<? extends T>>";
        assertEquals(expected, result);
    }
    
    @Test
    public void testNestedWildcardWithConcreteType() throws Exception
    {
        abstract class Class
        {
          abstract Callable<Future<? extends Runnable>> method();
        }
        TypeMirror type = types.typeMirror(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String result = type.accept(stringifier, shorten);
        String expected = "Callable<Future<? extends Runnable>>";
        assertEquals(expected, result);
    }

    @Test
    public void testMultipleTypeParameters() throws Exception
    {
        abstract class Class<K, V>
        {
            abstract Map<? extends K, ? super V> method();
        }
        TypeMirror type = types.typeMirror(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String result = type.accept(stringifier, shorten);
        String expected = "Map<? extends K, ? super V>";
        assertEquals(expected, result);
    }

    @Test
    public void testTypeParameterByItself() throws Exception
    {
        abstract class Class<T>
        {
            abstract T method();
        }
        TypeMirror type = types.typeMirror(Class.class.getDeclaredMethod("method").getGenericReturnType());
        String result = type.accept(stringifier, shorten);
        String expected = "T";
        assertEquals(expected, result);
    }

    /**
     * TODO: this code is duplicated
     */
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
}
