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
public class ProjoManifestIT extends ManifestTest
{
    @Parameters
    public static TestAttribute[] getTestAttributes()
    {
        return testAttributes.toArray(new TestAttribute[] {});
    }

    public ProjoManifestIT(TestAttribute testAttribute) throws Exception
    {
        super("projo", testAttribute);
    }
    
    @Override
    public String expectedBundleName()
    {
        return "pro.projo.projo";
    }

    @Override
    public String expectedBundleSymbolicName()
    {
        return "pro.projo.projo; singleton:=true";
    }

    @Override
    public String expectedAutomaticModuleName()
    {
        return "pro.projo.projo";
    }

    @Override
    public Set<String> expectedExportPackage()
    {
        List<String> exports = Arrays.asList
        (
            "pro.projo",
            "pro.projo.$template",
            "pro.projo.annotations",
            "pro.projo.internal; x-internal:=true",
            "pro.projo.internal.proxy; x-internal:=true",
            "pro.projo.decuples",
            "pro.projo.doubles",
            "pro.projo.duodecuples",
            "pro.projo.duovigintuples",
            "pro.projo.nonuples",
            "pro.projo.novemdecuples",
            "pro.projo.novemvigintuples",
            "pro.projo.octodecuples",
            "pro.projo.octovigintuples",
            "pro.projo.octuples",
            "pro.projo.quadruples",
            "pro.projo.quattuordecuples",
            "pro.projo.quattuorvigintuples",
            "pro.projo.quindecuples",
            "pro.projo.quintuples",
            "pro.projo.quinvigintuples",
            "pro.projo.septendecuples",
            "pro.projo.septenvigintuples",
            "pro.projo.septuples",
            "pro.projo.sexdecuples",
            "pro.projo.sextuples",
            "pro.projo.sexvigintuples",
            "pro.projo.singles",
            "pro.projo.tredecuples",
            "pro.projo.trevigintuples",
            "pro.projo.trigintuples",
            "pro.projo.triples",
            "pro.projo.undecuples",
            "pro.projo.unvigintuples",
            "pro.projo.vigintuples",
            "pro.projo.utilities"
        );
        return new HashSet<String>(exports);
    }

    @Test
    public void testManifestAttribute() throws Exception
    {
        assertEquals(expected(), actual());
    }
}
