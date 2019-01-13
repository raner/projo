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
package pro.projo.generation.interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

public class GeneratedSourcesTest
{
    @Test
    public void testNewType() throws Exception
    {
        File generated = new File("target/generated-test-sources/test-annotations/pro/projo/generation/interfaces/test/NewType.java");
        File comparison = new File("src/test/resources/pro/projo/generation/interfaces/expected/NewType.java");
        String expected = read(comparison);
        String actual = read(generated);
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
