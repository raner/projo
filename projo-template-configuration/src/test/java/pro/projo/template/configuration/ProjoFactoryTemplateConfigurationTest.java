//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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
package pro.projo.template.configuration;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.Test;
import pro.projo.template.annotation.Configuration;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

public class ProjoFactoryTemplateConfigurationTest
{
    @Test
    public void testSinglesFactoryClassName()
    {
        assertEquals("pro.projo.singles.Factory", new ProjoFactoryTemplateConfiguration().get(0).fullyQualifiedClassName());
    }

    @Test
    public void testDoublesFactoryClassName()
    {
        assertEquals("pro.projo.doubles.Factory", new ProjoFactoryTemplateConfiguration().get(1).fullyQualifiedClassName());
    }

    @Test
    public void testTriplesFactoryClassName()
    {
        assertEquals("pro.projo.triples.Factory", new ProjoFactoryTemplateConfiguration().get(2).fullyQualifiedClassName());
    }

    @Test
    public void testSinglesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(0);
        String[][] expected =
        {
            {"template", "singles"},
            {"n", "1"},
            {"s", ""},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"},
            {"argumentAndAdditionalParameters", "first"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    @Test
    public void testDoublesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(1);
        String[][] expected =
        {
            {"template", "doubles"},
            {"n", "2"},
            {"s", "s"},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_, _Second_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"
                + lineSeparator() + "* @param <_Second_> the type of the second field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"
                + lineSeparator() + "    * @param second the second parameter"},
            {"argumentAndAdditionalParameters", "first, _Second_ second"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    @Test
    public void testTriplesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(2);
        String[][] expected =
        {
            {"template", "triples"},
            {"n", "3"},
            {"s", "s"},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_, _Second_, _Third_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"
                + lineSeparator() + "* @param <_Second_> the type of the second field"
                + lineSeparator() + "* @param <_Third_> the type of the third field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"
                + lineSeparator() + "    * @param second the second parameter"
                + lineSeparator() + "    * @param third the third parameter"},    
            {"argumentAndAdditionalParameters", "first, _Second_ second, _Third_ third"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    private Map<String, String> map(String[][] entries)
    {
        return Stream.of(entries).collect(toMap(key -> key[0], value -> value[1]));
    }
}
