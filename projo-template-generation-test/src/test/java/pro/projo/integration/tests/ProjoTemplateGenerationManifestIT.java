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
package pro.projo.integration.tests;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import pro.projo.test.utilities.ManifestTest;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ProjoTemplateGenerationManifestIT extends ManifestTest
{
    @Parameters(name="{0}")
    public static TestAttribute[] getTestAttributes()
    {
        return testAttributes.toArray(new TestAttribute[] {});
    }

    public ProjoTemplateGenerationManifestIT(TestAttribute testAttribute) throws Exception
    {
        super("projo-template-generation", testAttribute);
    }

    @Override
    public String expectedBundleName()
    {
        return "pro.projo.projo-template-generation";
    }

    @Override
    public String expectedBundleSymbolicName()
    {
        return "pro.projo.projo-template-generation; singleton:=true";
    }

    @Override
    public String expectedAutomaticModuleName()
    {
        return "pro.projo.projo-template-generation";
    }

    @Override
    public Set<String> expectedExportPackage()
    {
        List<String> exports = Arrays.asList
        (
            "pro.projo.generation",
            "pro.projo.generation.interfaces",
            "pro.projo.generation.utilities",
            "$package; x-internal:=true"
        );
        return new HashSet<String>(exports);
    }

    @Override
    public Set<String> expectedRequireBundle()
    {
        return Collections.singleton("pro.projo.projo; bundle-version=\"" + version + "\"");
    }

    @Test
    public void testManifestAttribute() throws Exception
    {
        assertEquals(expected(), actual());
    }
}
