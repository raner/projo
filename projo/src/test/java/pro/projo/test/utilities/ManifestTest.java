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
package pro.projo.test.utilities;

import java.io.InputStream;
import java.net.URL;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class ManifestTest
{
    public static class TestAttribute
    {
        public String name;
        public Function<ManifestTest, Object> expected;
        
        public TestAttribute(String name, Function<ManifestTest, Object> expected)
        {
            this.name = name;
            this.expected = expected;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    public static List<TestAttribute> testAttributes = Arrays.asList
    (
        new TestAttribute("Manifest-Version", ManifestTest::expectedManifestVersion),
        new TestAttribute("Bundle-ManifestVersion", ManifestTest::expectedBundleManifestVersion),
        new TestAttribute("Bundle-Version", ManifestTest::expectedBundleVersion),
        new TestAttribute("Bundle-Name", ManifestTest::expectedBundleName),
        new TestAttribute("Bundle-SymbolicName", ManifestTest::expectedBundleSymbolicName),
        new TestAttribute("Bundle-Vendor", ManifestTest::expectedBundleVendor),
        new TestAttribute("Bundle-ActivationPolicy", ManifestTest::expectedBundleActivationPolicy),
        new TestAttribute("Bundle-RequiredExecutionEnvironment", ManifestTest::expectedBundleRequiredExecutionEnvironment),
        new TestAttribute("Automatic-Module-Name", ManifestTest::expectedAutomaticModuleName),
        new TestAttribute("Export-Package", ManifestTest::expectedExportPackage),
        new TestAttribute("Require-Bundle", ManifestTest::expectedRequireBundle),
        new TestAttribute("Fragment-Host", ManifestTest::expectedFragmentHost)
    );

    private static Format urlFormat = new MessageFormat("jar:file:/.*/{0}/target/{0}-.*\\.jar!/META-INF/MANIFEST.MF");

    private ClassLoader classLoader;
    private Pattern manifestPattern;
    private Attributes attributes;
    private TestAttribute testAttribute;
    protected final String version;

    protected ManifestTest(String moduleName, TestAttribute testAttribute) throws Exception
    {
        this.testAttribute = testAttribute;
        classLoader = getClass().getClassLoader();
        manifestPattern = Pattern.compile(urlFormat.format(new Object[] {moduleName}));
        try (InputStream versionProperties = classLoader.getResourceAsStream("version.properties"))
        {
            Properties properties = new Properties();
            properties.load(versionProperties);
            version = properties.getProperty("version");
        }
        List<URL> manifests = Collections.list(classLoader.getResources("META-INF/MANIFEST.MF"));
        Stream<URL> urls = manifests.stream();
        URL url = urls.filter(it -> manifestPattern.matcher(it.toString()).matches()).findFirst().get();
        try (InputStream input = url.openStream())
        {
            Manifest manifest = new Manifest(input);
            attributes = manifest.getMainAttributes();
        }
    }

    public String expectedBundleVersion()
    {
        return version;
    }

    public String expectedBundleManifestVersion()
    {
        return "2";
    }

    public String expectedManifestVersion()
    {
        return "1.0";
    }

    public String expectedBundleActivationPolicy()
    {
        return "lazy";
    }

    public String expectedBundleRequiredExecutionEnvironment()
    {
        return "JavaSE-1.8";
    }

    public String expectedBundleVendor()
    {
        return "Mirko Raner";
    }

    public String expectedFragmentHost()
    {
        return null;
    }

    public Set<String> expectedRequireBundle()
    {
        return null;
    }

    public abstract String expectedBundleName();

    public abstract String expectedBundleSymbolicName();

    public abstract String expectedAutomaticModuleName();

    public abstract Set<String> expectedExportPackage();

    public Object expected()
    {
        return testAttribute.expected.apply(this);
    }

    public Object actual()
    {
        if (expected() instanceof Set)
        {
            String string = attributes.getValue(testAttribute.name);
            List<String> elements = Arrays.asList(string.split(", *"));
            return new HashSet<String>(elements);
        }
        return attributes.getValue(testAttribute.name);
    }
}
