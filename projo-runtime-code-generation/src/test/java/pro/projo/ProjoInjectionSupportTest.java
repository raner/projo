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
package pro.projo;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.inject.Provider;
import org.junit.Test;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import pro.projo.annotations.Property;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

public class ProjoInjectionSupportTest
{
    static interface True
    {
        //
    }

    static interface False
    {
        //
    }

    static interface Booleans
    {
        @Inject True _true();
        @Inject False _false();
    }

    static interface Pass
    {
        @Inject
        @Property
        default Booleans booleans()
        {
            throw new NoSuchMethodError();
        }

        default True evaluate()
        {
            return booleans()._true();
        }
    }

    @Test
    public void testInjectAnnotationCarriesOverToGeneratedFields() throws Exception
    {
        Class<? extends Booleans> booleans = Projo.getImplementationClass(Booleans.class);
        Field trueField = booleans.getDeclaredField("_true");
        Field falseField = booleans.getDeclaredField("_false");
        Inject[] trueInject = trueField.getAnnotationsByType(Inject.class);
        Inject[] falseInject = falseField.getAnnotationsByType(Inject.class);
        int[] expected = {1, 1};
        int[] actual = {trueInject.length, falseInject.length};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testThatInjectedFieldsAreAlwaysUsingAProvider() throws Exception
    {
        Class<? extends Booleans> booleans = Projo.getImplementationClass(Booleans.class);
        Field trueField = booleans.getDeclaredField("_true");
        Field falseField = booleans.getDeclaredField("_false");
        Type trueType = trueField.getGenericType();
        Type falseType = falseField.getGenericType();
        class Expected
        {
            @SuppressWarnings("unused") Provider<True> trueExpected;
            @SuppressWarnings("unused") Provider<False> falseExpected;
        }
        Type[] expected =
        {
            Expected.class.getDeclaredField("trueExpected").getGenericType(),
            Expected.class.getDeclaredField("falseExpected").getGenericType()
        };
        Type[] actual = {trueType, falseType};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testThatPropertyAnnotationWorksInConjunctionWithInject() throws Exception
    {
        Module module = new AbstractModule()
        {
            protected void configure()
            {
                bind(True.class).to(Projo.getImplementationClass(True.class)).asEagerSingleton();
                bind(False.class).to(Projo.getImplementationClass(False.class)).asEagerSingleton();
                bind(Booleans.class).to(Projo.getImplementationClass(Booleans.class)).asEagerSingleton();
                bind(Pass.class).to(Projo.getImplementationClass(Pass.class)).asEagerSingleton();
            }
        };
        Injector injector = Guice.createInjector(module);
        Booleans booleans = injector.getInstance(Booleans.class);
        Pass pass = injector.getInstance(Pass.class);
        assertSame(booleans._true(), pass.evaluate());
    }
}
