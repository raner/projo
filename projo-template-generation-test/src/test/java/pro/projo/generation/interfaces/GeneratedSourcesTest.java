//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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
package pro.projo.generation.interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.stream.Stream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
* {@link GeneratedSourcesTest} is a parameterized test that checks that all files
* in {@code src/test/resources/pro/projo/generation/interfaces/expected} match their
* corresponding generated files.
*
* @author Mirko Raner
**/
@RunWith(Parameterized.class)
public class GeneratedSourcesTest
{
    static Format generated = new MessageFormat("target/generated-test-sources/test-annotations/pro/projo/generation/interfaces/test/{0}");
    static Format comparison = new MessageFormat("src/test/resources/pro/projo/generation/interfaces/expected/{0}");

    @Parameters(name="{0}")
    public static Collection<Object[]> testSources()
    {
        File expected = new File(comparison.format(new Object[] {""}));
        return Stream.of(expected.list()).map(file -> new Object[] {file}).collect(toList());
    }

    @Parameter
    public String className;

    @Test
    public void test() throws Exception
    {
        Object[] file = {className};
        String expected = read(new File(comparison.format(file)));
        String actual = read(new File(generated.format(file)));
        assertEquals(expected, actual);
    }

    private String read(File file) throws IOException
    {
        try (InputStream stream = new FileInputStream(file))
        {
            return IOUtils.toString(stream, UTF_8);
        }
    }
}
