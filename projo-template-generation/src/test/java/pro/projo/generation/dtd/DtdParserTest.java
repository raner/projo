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
package pro.projo.generation.dtd;

import java.io.IOException;
import java.net.URL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.xml.sax.InputSource;
import com.sun.xml.dtdparser.DTDParser;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import static org.junit.Assume.assumeThat;
import static pro.projo.generation.test.utilities.ExceptionMatcher.consumes;

/**
* The {@link DtdParserTest} is a test bed for evaluating DTD parsers (and, to some extend, DTDs
* as well) for the purpose of automatic API generation.
*
* @author Mirko Raner
**/
@RunWith(Parameterized.class)
public class DtdParserTest
{
    @Parameters(name="{0}")
    public static InputSource[] dtdFiles() throws Exception
    {
        try (ScanResult result = new ClassGraph().acceptClasspathElementsContainingResourcePath("*/html5.dtd").scan())
        {
            return result.getResourcesWithLeafName("html5.dtd").getURLs().stream()
                .map(DtdParserTest::open).toArray(InputSource[]::new);
        }
    }

    @Parameter
    public InputSource dtd;

    private DTDParser parser = new DTDParser();

    @Test
    public void test() throws Exception
    {
        assumeThat(parser::parse, consumes(dtd).withoutThrowingException());
    }

    private static InputSource open(URL url)
    {
        try
        {
            InputSource source = new InputSource(url.openStream())
            {
              @Override
              public String toString()
              {
                  String path = url.getPath();
                  return path.substring(path.lastIndexOf('/', path.indexOf("/html5.dtd")-1)+1);
              }
            };
            source.setSystemId(url.toString());
            return source;
        }
        catch (IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }
}
