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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import pro.projo.template.annotation.Configuration;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class DtdConfigurationTest extends DtdTestBase
{
    static interface EmptyElement<PARENT> {}

    @Parameters(name="{0}")
    public static Collection<Object[]> generatedClasses()
    {
        Object[][] generatedClasses =
        {
            {"Element"},
            {"ElementRequired1"},
            {"ElementRequired2"},
            {"ElementRequired3"},
            {"ElementRequired1Required2"},
            {"ElementRequired1Required3"},
            {"ElementRequired2Required3"},
            {"ElementRequired1Required2Required3"}
        };
        return Arrays.asList(generatedClasses);
    }

    @Parameter
    public String typeName;

    @Test
    public void testNonRequiredAttributesPresentOnAllClasses() throws Exception
    {
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class);
        List<Map<String, Object>> allParameters = configurations.stream().map(Configuration::parameters).collect(toList());
        Map<String, Object> parameters = allParameters.stream()
            .filter(it -> ((String)it.get("InterfaceTemplate")).startsWith(typeName + "<"))
            .findFirst().get();
        String interfaceName = (String)parameters.get("InterfaceTemplate");
        String baseTypeName = interfaceName.substring(0, interfaceName.indexOf('<'));
        Set<String> methods = new HashSet<>(Arrays.asList((String[])parameters.get("methods")));
        String optional1 = baseTypeName + "<PARENT> optional1(String optional1)";
        String optional2 = baseTypeName + "<PARENT> optional2(String optional2)";
        assertThat(methods + " does not contain " + optional1, methods.contains(optional1));
        assertThat(methods + " does not contain " + optional2, methods.contains(optional2));
    }

    @Test
    public void testNonRequiredAttributesPresentOnAllClassesWithCustomName() throws Exception
    {
        String dtdPath = "/DTDs/ElementWithRequiredAndOptionalAttributes.dtd";
        Collection<? extends Configuration> configurations = getConfigurations(dtdPath, EmptyElement.class, "{0}Attributes");
        List<Map<String, Object>> allParameters = configurations.stream().map(Configuration::parameters).collect(toList());
        Map<String, Object> parameters = allParameters.stream()
            .filter(it -> ((String)it.get("InterfaceTemplate")).startsWith(typeName + "Attributes<"))
            .findFirst().get();
        String interfaceName = (String)parameters.get("InterfaceTemplate");
        String baseTypeName = interfaceName.substring(0, interfaceName.indexOf('<'));
        Set<String> methods = new HashSet<>(Arrays.asList((String[])parameters.get("methods")));
        String optional1 = baseTypeName + "<PARENT> optional1(String optional1)";
        String optional2 = baseTypeName + "<PARENT> optional2(String optional2)";
        assertThat(methods + " does not contain " + optional1, methods.contains(optional1));
        assertThat(methods + " does not contain " + optional2, methods.contains(optional2));
    }
}
