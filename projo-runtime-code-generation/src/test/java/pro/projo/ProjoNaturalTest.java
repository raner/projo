//                                                                          //
// Copyright 2022 Mirko Raner                                               //
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

import org.junit.Test;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import pro.projo.test.implementations.Utilities;
import pro.projo.test.interfaces.Literals;
import pro.projo.test.interfaces.Natural;
import pro.projo.test.interfaces.Naturals;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pro.projo.test.implementations.Natural.factory;

/**
* {@link ProjoNaturalTest} verifies Projo's {@link pro.projo.annotations.Implements @Implements},
* {@link pro.projo.annotations.Returns @Returns} and {@link pro.projo.annotations.Expects @Expects}
* annotations in a more real-world example.
* {@link pro.projo.test.implementations.Natural} implements {@link pro.projo.test.interfaces.Natural}
* without making any direct reference to it. Return types and parameter types are adjusted based
* on their annotations.
*
* @author Mirko Raner
**/
public class ProjoNaturalTest
{
    @Test
    public void testSuccessor()
    {
        Natural<?> one = (Natural<?>)factory.create(ONE);
        assertEquals("2", one.successor().toString());
    }

    @Test
    public void testPlus() throws Exception
    {
        Natural<?> one = (Natural<?>)factory.create(ONE);
        Natural<?> ten = (Natural<?>)factory.create(TEN);
        assertEquals("11", one.plus(ten).toString());
    }

    @Test
    public void testTimes() throws Exception
    {
        Natural<?> two = (Natural<?>)factory.create(ONE).successor();
        Natural<?> ten = (Natural<?>)factory.create(TEN);
        assertEquals("20", two.times(ten).toString());
    }

    @Test
    public void allNaturalsMethodsImplemented()
    {
        Literals literals = new Literals() {};
        Injector injector = Guice.createInjector(module(literals));
        Naturals<?> naturals = injector.getInstance(Naturals.class);
        Literals result = naturals.literals();
        assertEquals(literals, result);
    }

    @org.junit.Ignore
    @Test
    public void methodsFromImplementedAnnotationAreActuallyImplemented()
    {
        Literals literals = new Literals() {};
        Injector injector = Guice.createInjector(module(literals));
        Naturals<?> naturals = injector.getInstance(Naturals.class);
        Utilities result = naturals.utilities();
        assertNotNull(result);
    }

    @org.junit.Ignore
    @Test
    public void parseMethodInheritsDefaultImplementationFromImplemented()
    {
        Injector injector = Guice.createInjector(module(new Literals() {}));
        Naturals<?> naturals = injector.getInstance(Naturals.class);
        Natural<?> result = (Natural<?>)naturals.parse("1");
        Natural<?> expected = (Natural<?>)factory.create(ONE);
        assertEquals(expected, result);
    }

    private Module module(Literals literals)
    {
        return new AbstractModule()
        {
            @SuppressWarnings("unchecked")
            protected void configure()
            {
                Class<?> classNaturals = (Class<?>)Naturals.class;
                TypeLiteral<Object> interfaceNaturals = TypeLiteral.get((Class<Object>)classNaturals);
                Class<?> implementationNaturals = Projo.getImplementationClass(pro.projo.test.implementations.Naturals.class);
                TypeLiteral<Object> implementation = TypeLiteral.get((Class<Object>)implementationNaturals);
                bind(interfaceNaturals).to(implementation).asEagerSingleton();
                bind(Literals.class).toInstance(literals);
            }
        };
    }
}
