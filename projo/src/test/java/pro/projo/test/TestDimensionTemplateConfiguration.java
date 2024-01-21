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
package pro.projo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;
import pro.projo.interfaces.annotation.Options;
import pro.projo.template.annotation.Configuration;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;

/**
* The {@link TestDimensionTemplateConfiguration} is used for internal testing.
*
* @author Mirko Raner
**/
public class TestDimensionTemplateConfiguration extends ArrayList<Configuration>
{
    private final static long serialVersionUID = 5849315222475315976L;

    private final String[] dimensions = {"T", "L", "M", "I", "ϴ", "N", "J"};
    private final String[] exponents = {"Minus4", "Minus3", "Minus2", "Minus1", "Zero", "Plus1", "Plus2", "Plus3", "Plus4"};

    public TestDimensionTemplateConfiguration()
    {
        rangeClosed(0, 7).mapToObj(count -> new Configuration()
        {
            @Override
            public String fullyQualifiedClassName()
            {
                return "pro.projo.generation.template." + subpackage() + ".Dimension" + suffix();
            }

            @Override
            public Options options()
            {
                return TestDimensionTemplateConfiguration.this.options();
            }

            @Override
            public Map<String, Object> parameters()
            {
                return new HashMap<String, Object>()
                {
                    private static final long serialVersionUID = 1L;
                    {
                        put("Dimension", suffix()+typeParameters()+extend());
                        put("methods", methods());
                        put("test", subpackage());
                    }
                };
            }

            List<String> methods()
            {
                if (count == 0)
                {
                    return Stream.of(dimensions)
                        .map(dimension -> "Dimension" + dimension + Stream.of(dimensions).map(it -> it.equals(dimension)? "Plus1":it).collect(joined()) + " " + dimension.toLowerCase() + "();")
                        .collect(toList());
                }
                else
                {
                    return Stream.of(exponents)
                        .map(exponent -> "Dimension" + Stream.of(dimensions).map(it -> it.equals(suffix())? exponent:it).collect(joined()) + " " + exponent.toLowerCase() + "();")
                        .collect(toList());
                }
            }

            String suffix()
            {
                return count == 0? "":dimensions[count-1];
            }

            String extend()
            {
                return count == 0? "":" extends Dimension" + Stream.of(dimensions).collect(joined());
            }
        })
        .collect(toCollection(() -> this));
    }

    protected Options options()
    {
        return Configuration.defaults();
    }

    protected String subpackage()
    {
        return "test.dimensions";
    }

    String typeParameters()
    {
        return Stream.of(dimensions).map(it -> it + " extends Exponent").collect(joined());
    }

    Collector<CharSequence, ?, String> joined()
    {
        return joining(", ", "<", ">");
    }
}
