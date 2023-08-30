//                                                                          //
// Copyright 2021 - 2023 Mirko Raner                                        //
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
import java.text.Format;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.stream.Stream;
import org.junit.runners.Parameterized.Parameters;
import static java.util.stream.Collectors.toList;

/**
* {@link GeneratedPostProcessorSourcesTest} is a parameterized test that checks that all files
* in {@code src/test/resources/pro/projo/generation/interfaces/postprocessor/expected} match their
* corresponding generated files.
*
* @author Mirko Raner
**/
public class GeneratedPostProcessorSourcesTest extends AbstractGeneratedSourcesTest
{
    static Format generated = new MessageFormat("target/generated-sources/annotations/pro/projo/generation/interfaces/test/postprocessor/{0}");
    static Format comparison = new MessageFormat("src/test/resources/pro/projo/generation/interfaces/postprocessor/expected/{0}");

    public GeneratedPostProcessorSourcesTest()
    {
        super(comparison, generated);
    }

    @Parameters(name="{0}")
    public static Collection<Object[]> testSources()
    {
        File expected = new File(comparison.format(new Object[] {""}));
        return Stream.of(expected.list()).map(file -> new Object[] {file}).collect(toList());
    }
}
