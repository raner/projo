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
package pro.projo.generation;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.parser.ParseException;
import org.junit.Test;
import static java.lang.System.lineSeparator;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

/**
* The {@link ProjoTemplateFactoryGeneratorTest} verifies that the {@link ProjoTemplateFactoryGenerator} produces the
* correct output for the given parameters.
*
* @author Mirko Raner
**/
public class ProjoTemplateFactoryGeneratorTest
{
    @Test
    public void testSinglesFactoryTemplateWriter() throws IOException, ParseException
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        String[][] variables =
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
        Map<String, String> parameters = Stream.of(variables).collect(toMap(key -> key[0], value -> value[1]));
        String expected = FileUtils.readFileToString(new File("src/test/resources/pro/projo/singles/Factory.expected.java"), UTF_8);
        StringWriter actual = new StringWriter();
        StringReader reader = new StringReader(FileUtils.readFileToString(new File("src/test/resources/pro/projo/$template/Factory.java"), UTF_8));
        Template template = generator.getTemplate(reader, "Factory");
        generator.generate(template, parameters, actual);
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testSinglesFactoryWriter() throws IOException
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        String[][] variables =
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
        Map<String, String> parameters = Stream.of(variables).collect(toMap(key -> key[0], value -> value[1]));
        String expected = FileUtils.readFileToString(new File("src/test/resources/pro/projo/singles/Factory.expected.java"), UTF_8);
        StringWriter actual = new StringWriter();
        generator.generate("src/test/resources/pro/projo/$template/Factory.java", parameters, actual);
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testSinglesFactory() throws IOException
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        String[][] variables =
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
        Map<String, String> parameters = Stream.of(variables).collect(toMap(key -> key[0], value -> value[1]));
        String expected = FileUtils.readFileToString(new File("src/test/resources/pro/projo/singles/Factory.expected.java"), UTF_8);
        String actual = generator.generate("src/test/resources/pro/projo/$template/Factory.java", parameters);
        assertEquals(expected, actual);
    }

    @Test
    public void testDoublesFactory() throws IOException
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        String[][] variables =
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
        Map<String, String> parameters = Stream.of(variables).collect(toMap(key -> key[0], value -> value[1]));
        String expected = FileUtils.readFileToString(new File("src/test/resources/pro/projo/doubles/Factory.expected.java"), UTF_8);
        String actual = generator.generate("src/test/resources/pro/projo/$template/Factory.java", parameters);
        assertEquals(expected, actual);
    }

    @Test
    public void testTriplesFactory() throws IOException
    {
        ProjoTemplateFactoryGenerator generator = new ProjoTemplateFactoryGenerator();
        String[][] variables =
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
        Map<String, String> parameters = Stream.of(variables).collect(toMap(key -> key[0], value -> value[1]));
        String expected = FileUtils.readFileToString(new File("src/test/resources/pro/projo/triples/Factory.expected.java"), UTF_8);
        String actual = generator.generate("src/test/resources/pro/projo/$template/Factory.java", parameters);
        assertEquals(expected, actual);
    }
}
