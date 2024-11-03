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
package pro.projo.integration.tests;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import pro.projo.Projo;
import pro.projo.test.implementations.Ranges;
import pro.projo.test.implementations.Utilities;
import pro.projo.test.interfaces.Decimals;
import pro.projo.test.interfaces.Literals;
import pro.projo.test.interfaces.Provider;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProjoDecimalsIT
{
    @BeforeClass
    public static void deleteDecimalClass() throws URISyntaxException
    {
        ClassLoader classLoader = ProjoDecimalsIT.class.getClassLoader();
        URL url = classLoader.getResource("pro/projo/test/interfaces/Decimal.class");
        if (url != null)
        {
            new File(url.toURI()).delete();
        }
    }

    @Test
    public void implementationOfDecimalsIsProperlyBound() throws Exception
    {
        Literals literals = new Literals() {};
        Injector injector = Guice.createInjector(module(literals));
        Decimals<?> naturals = injector.getInstance(Decimals.class);
        Method literalsMethod = Provider.class.getMethod("literals");
        Literals result = (Literals)literalsMethod.invoke(naturals);
        assertEquals(literals, result);
    }

    @Test
    public void interfaceMethodsAreProperlyInvoked() throws Exception
    {
        Injector injector = Guice.createInjector(module(new Literals() {}));
        Decimals<?> decimals = injector.getInstance(Decimals.class);
        Method parseMethod = Provider.class.getMethod("parse", Object.class);
        parseMethod.invoke(decimals, "");
        assertTrue(Utilities.nullValueCalled);
    }

    private Module module(Literals literals)
    {
        return new AbstractModule()
        {
            @Override
            @SuppressWarnings("unchecked")
            protected void configure()
            {
                Class<?> classDecimals = (Class<?>)Decimals.class;
                TypeLiteral<Object> interfaceDecimals = TypeLiteral.get((Class<Object>)classDecimals);
                Class<?> implementationNaturals = Projo.getImplementationClass(pro.projo.test.implementations.Decimals.class);
                TypeLiteral<Object> implementation = TypeLiteral.get((Class<Object>)implementationNaturals);
                bind(interfaceDecimals).to(implementation).asEagerSingleton();
                bind(Literals.class).toInstance(literals);
                bind(Ranges.class).toInstance(new Ranges() {});
            }
        };
    }
}
