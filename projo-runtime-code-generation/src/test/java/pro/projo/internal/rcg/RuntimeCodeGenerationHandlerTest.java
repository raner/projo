//                                                                          //
// Copyright 2017 - 2023 Mirko Raner                                        //
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
package pro.projo.internal.rcg;

import javax.inject.Inject;
import org.junit.Test;
import net.bytebuddy.description.type.TypeDescription.Generic;
import pro.projo.annotations.Cached;
import pro.projo.utilities.AnnotationList;
import static java.lang.reflect.Proxy.isProxyClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class RuntimeCodeGenerationHandlerTest
{
    private ClassLoader classLoader = getClass().getClassLoader();

    public static interface Person
    {
        String getFirstName();
        void setFirstName(String name);
        String getLastName();
        void setLastName(String name);
    }

    static interface PackagePerson
    {
        String getFirstName();
        void setFirstName(String name);
        String getLastName();
        void setLastName(String name);
    }

    @Test
    public void testHandlerReturnsNonProxyClass()
    {
        RuntimeCodeGenerationHandler<Person> handler = new RuntimeCodeGenerationHandler<>();
        Class<? extends Person> result = handler.getImplementationOf(Person.class, false, classLoader);
        assertFalse(isProxyClass(result));
    }

    @Test
    public void testHandlerCanImplementNonPublicClass()
    {
        RuntimeCodeGenerationHandler<PackagePerson> handler = new RuntimeCodeGenerationHandler<>();
        Class<? extends PackagePerson> result = handler.getImplementationOf(PackagePerson.class, false, classLoader);
        assertFalse(isProxyClass(result));
    }

    @Test
    public void testFieldTypeForRegularMethod()
    {
        RuntimeCodeGenerationHandler<?> handler = new RuntimeCodeGenerationHandler<>();
        Generic fieldType = handler.getFieldType(new AnnotationList(), String.class, classLoader);
        assertEquals("class java.lang.String", fieldType.toString());
    }

    @Test
    @Inject
    public void testFieldTypeForInjectedMethod() throws Exception
    {
        RuntimeCodeGenerationHandler<?> handler = new RuntimeCodeGenerationHandler<>();
        Inject inject = getClass().getDeclaredMethod("testFieldTypeForInjectedMethod").getAnnotation(Inject.class);
        Generic fieldType = handler.getFieldType(new AnnotationList(inject), String.class, classLoader);
        assertEquals("javax.inject.Provider<java.lang.String>", fieldType.toString());
    }

    @Test
    @Cached
    public void testFieldTypeForCachedMethod() throws Exception
    {
        RuntimeCodeGenerationHandler<?> handler = new RuntimeCodeGenerationHandler<>();
        Cached cached = getClass().getDeclaredMethod("testFieldTypeForCachedMethod").getAnnotation(Cached.class);
        Generic fieldType = handler.getFieldType(new AnnotationList(cached), String.class, classLoader);
        assertEquals("pro.projo.internal.rcg.runtime.Cache<java.lang.String>", fieldType.toString());
    }

    @Test
    @Cached
    public void testFieldTypeForCachedPrimitiveMethod() throws Exception
    {
        RuntimeCodeGenerationHandler<?> handler = new RuntimeCodeGenerationHandler<>();
        Cached cached = getClass().getDeclaredMethod("testFieldTypeForCachedMethod").getAnnotation(Cached.class);
        Generic fieldType = handler.getFieldType(new AnnotationList(cached), int.class, classLoader);
        assertEquals("pro.projo.internal.rcg.runtime.Cache<java.lang.Integer>", fieldType.toString());
    }
}
