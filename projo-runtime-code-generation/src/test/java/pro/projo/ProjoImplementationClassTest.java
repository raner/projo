//                                                                          //
// Copyright 2023 Mirko Raner                                               //
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
package pro.projo;

import java.net.URL;
import java.net.URLClassLoader;
import org.junit.Test;
import pro.projo.test.implementations.Injection;
import static org.junit.Assert.assertEquals;

/**
* {@link ProjoImplementationClassTest} verifies that {@link Projo#getImplementationClass(Class, ClassLoader)}
* indeed uses the specified {@link ClassLoader}.
*
* @author Mirko Raner
**/
public class ProjoImplementationClassTest
{
    static URL[] url = {ProjoImplementationClassTest.class.getClassLoader().getResource("versions-api-2.14.0.jar")};

    static ClassLoader classLoader = new URLClassLoader(url);

    Class<? extends Injection> loadedClass;

    /**
    * This is a test control that ensures that classes can be loaded from the custom class loader.
    **/
    @Test
    public void controlCustomClassLoader() throws Exception
    {
        Class<?> control = classLoader.loadClass("org.codehaus.mojo.versions.api.change.VersionChange");
        assertEquals("org.codehaus.mojo.versions.api.change.VersionChange", control.getName());
    }

    /**
    * This is a test control that ensures that the classes to be loaded from the custom class loader
    * are not already coincidentally on the regular class path.
    **/
    @Test(expected=ClassNotFoundException.class)
    public void controlDefaultClassLoader() throws Exception
    {
        Class.forName("org.codehaus.mojo.versions.api.change.VersionChange");
    }

    @Test
    public void getImplementationClassTestClassLoader()
    {
        loadedClass = Projo.getImplementationClass(Injection.class, classLoader);
        assertEquals(classLoader, loadedClass.getClassLoader());
    }

    @Test
    public void getImplementationClassTestMethodClassLoader() throws Exception
    {
        loadedClass = Projo.getImplementationClass(Injection.class, classLoader);
        Class<?> returnType = loadedClass.getDeclaredMethod("object").getReturnType();
        assertEquals(classLoader, returnType.getClassLoader());
    }
}
