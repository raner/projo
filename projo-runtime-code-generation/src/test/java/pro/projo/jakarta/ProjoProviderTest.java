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
package pro.projo.jakarta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.junit.Test;
import pro.projo.Projo;
import pro.projo.test.implementations.jakarta.Provider;
import pro.projo.test.implementations.jakarta.Strings;
import static org.junit.Assert.assertEquals;

public class ProjoProviderTest
{
    Class<?> provider = Projo.getImplementationClass(Provider.class);
    Class<?> strings = Projo.getImplementationClass(Strings.class);

    @Test
    public void testLiteralsFieldType() throws Exception
    {
        Field literals = provider.getDeclaredField("literals");
        ParameterizedType literalsType = (ParameterizedType)literals.getGenericType();
        assertEquals("jakarta.inject.Provider", literalsType.getRawType().getTypeName());
        assertEquals("pro.projo.test.interfaces.Literals", literalsType.getActualTypeArguments()[0].getTypeName());
    }

    @Test
    public void testLiteralsReturnType() throws Exception
    {
        Method literals = provider.getDeclaredMethod("literals");
        Type literalsType = literals.getGenericReturnType();
        assertEquals("pro.projo.test.interfaces.Literals", literalsType.getTypeName());
    }

    @Test
    public void testComparatorFieldType() throws Exception
    {
        Field literals = provider.getDeclaredField("comparator");
        ParameterizedType literalsType = (ParameterizedType)literals.getGenericType();
        assertEquals("jakarta.inject.Provider<java.util.Comparator<pro.projo.test.interfaces.Literals>>", literalsType.getTypeName());
    }

    @Test
    public void testInheritedLiteralsFieldType() throws Exception
    {
        Field literals = strings.getDeclaredField("literals");
        ParameterizedType literalsType = (ParameterizedType)literals.getGenericType();
        assertEquals("jakarta.inject.Provider", literalsType.getRawType().getTypeName());
        assertEquals("pro.projo.test.interfaces.Literals", literalsType.getActualTypeArguments()[0].getTypeName());
    }

    @Test
    public void testInheritedLiteralsReturnType() throws Exception
    {
        Method literals = strings.getDeclaredMethod("literals");
        Type literalsType = literals.getGenericReturnType();
        assertEquals("pro.projo.test.interfaces.Literals", literalsType.getTypeName());
    }
}
