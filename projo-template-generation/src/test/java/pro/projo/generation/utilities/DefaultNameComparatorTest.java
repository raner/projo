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
package pro.projo.generation.utilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultNameComparatorTest
{
    static class Name implements javax.lang.model.element.Name
    {
        private String name;

        Name(String name)
        {
            this.name = name;
        }

        @Override
        public int length()
        {
            return name.length();
        }

        @Override
        public char charAt(int index)
        {
            return name.charAt(index);
        }

        @Override
        public CharSequence subSequence(int start, int end)
        {
            return name.subSequence(start, end);
        }

        @Override
        public boolean contentEquals(CharSequence sequence)
        {
            return name.contentEquals(sequence);
        }

        @Override
        public int hashCode()
        {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object other)
        {
            if (!(other instanceof Name))
            {
                return false;
            }
            return name.equals(((Name)other).name);
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    DefaultNameComparator comparator = new DefaultNameComparator();

    @Test
    public void javaPackagesComeFirst()
    {
        assertTrue(comparator.compare(name("java.nio.channels.Channel"), name("io.netty.channel.Channel")) < 0);
    }

    @Test
    public void nonJavaPackagesComeLater()
    {
        assertTrue(comparator.compare(name("io.netty.channel.Channel"), name("java.nio.channels.Channel")) > 0);
    }

    @Test
    public void packagesNamedJavaAreSortedNormallyWhenTheyAreNotTheTopLevelPackage()
    {
      assertTrue(comparator.compare(name("com.apple.Class"), name("com.java.Class")) < 0);
    }

    @Test
    public void javaxPackagesComeAfterJavaPackages()
    {
        assertTrue(comparator.compare(name("java.util.Map"), name("javax.inject.Inject")) < 0);
    }

    @Test
    public void orgPackagesComeAfterJavaxPackages()
    {
      assertTrue(comparator.compare(name("javax.inject.Inject"), name("org.w3c.dom.Element")) < 0);
    }

    @Test
    public void orgPackagesComeBeforeComPackages()
    {
       assertTrue(comparator.compare(name("org.w3c.dom.Element"), name("com.sun.java.browser.dom.DOMAction")) < 0);
    }

    @Test
    public void twoNamesInSamePackageShouldCompareAlphabeticallyEqual()
    {
        assertTrue(comparator.compare(name("java.util.List"), name("java.util.List")) == 0);
    }

    @Test
    public void twoNamesInSamePackageShouldCompareAlphabeticallyGreater()
    {
        assertTrue(comparator.compare(name("java.util.Set"), name("java.util.Map")) > 0);
    }

    @Test
    public void twoNamesInSamePackageShouldCompareAlphabeticallyLess()
    {
        assertTrue(comparator.compare(name("java.util.Map"), name("java.util.Set")) < 0);
    }

    @Test
    public void nestedInnerClassShouldComeAfterOuterClass()
    {
        assertTrue(comparator.compare(name("java.util.Map"), name("java.util.Map.Entry")) < 0);
    }

    @Test
    public void nestedPackageComesAfterParentPackage()
    {
         assertTrue(comparator.compare(name("java.util.ConcurrentModificationException"), name("java.util.concurrent.Future")) < 0);
    }

    @Test
    public void twoNamesInSamePackageShouldBeSortedAlphabetically()
    {
        Set<Name> set = new HashSet<>(asList(name("java.util.Set"), name("java.util.Map")));
        List<Name> expected = asList(name("java.util.Map"), name("java.util.Set"));
        List<Name> actual = set.stream().sorted(comparator).collect(toList());
        assertEquals(expected, actual);
    }

    @Test
    public void threeNamesInSamePackageShouldBeSortedAlphabetically()
    {
      Set<Name> set = new HashSet<>(asList(name("java.util.Map"), name("java.util.Set"), name("java.util.List")));
      List<Name> expected = asList(name("java.util.List"), name("java.util.Map"), name("java.util.Set"));
      List<Name> actual = set.stream().sorted(comparator).collect(toList());
      assertEquals(expected, actual);
    }

    private Name name(String name)
    {
        return new Name(name);
    }
}
