//                                                                          //
// Copyright 2017 - 2022 Mirko Raner                                        //
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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
* The {@link ProjoSuiteTest} suite brings in various JUnit tests from the main Projo project and runs them again in the
* {@code projo-runtime-code-generation} project. This allows reusing the baseline tests that were created for the
* proxy-based Projo implementation to be reused for the Projo implementation that uses runtime code generation.
*
* @author Mirko Raner
**/
@RunWith(Suite.class)
@SuiteClasses
({
    CacheTest.class,
    BuilderTest.class,
    OverridesTest.class,
    ImplementsTest.class,
    SimpleDelegateTest.class,
    ProjoTest.class,
    ProjoObjectTest.class,
    ProjoToStringTest.class,
    ProjoValueObjectsTest.class,
    ProjoValueObjectsToStringTest.class,
    InheritanceTest.class,
    ImmutableProjoSinglesTest.class,
    ImmutableProjoDoublesTest.class,
    ImmutableProjoTriplesTest.class,
    ImmutableProjoTrigintuplesTest.class,
    MutableProjoSinglesTest.class,
    MutableProjoDoublesTest.class,
    MutableProjoTriplesTest.class,
    TransitiveValueObjectsTest.class
})
public class ProjoSuiteTest
{
    // No additional members...
}
