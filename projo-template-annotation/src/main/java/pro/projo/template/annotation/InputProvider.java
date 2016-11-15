//                                                                          //
// Copyright 2016 Mirko Raner                                               //
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
package pro.projo.template.annotation;

import java.util.Collection;
import java.util.Map;

/**
* An {@link InputProvider} supplies inputs to a template engine. In conjunction with the template
* itself, the {@link InputProvider} determines the final output of the template engine.
* Each {@link Map} in the provided {@link Collection} represents the parameters for an individual run of
* the template engine. For example, if the collection contains 10 {@link Map}s the template engine will
* generate 10 separate source files (the template should be set up in such a way that it produces different
* class names or puts classes into different packages).
*
* For example, consider the following template class:
* <pre>
* package pro.projo.generated;
*
* @Template(input=FirstAndLastNameInputProvider.class)
* public class $Name
* {
*     private String value;
*
*     public void set$Name(String value)
*     {
*          this.value = value;
*     }
*
*     public String get$Name()
*     {
*         return value;
*     }
* }
* </pre>
* Now, consider the following {@link InputProvider}:
* <pre>
* package pro.projo.generated;
*
* import static java.util.Arrays.asList;
* import static java.util.Collections.singletonMap;
*
* public class FirstAndLastNameInputProvider extends InputProvider
* {
*     @Override
*     public Collection<Map<String, String>> getInputs()
*     {
*         return asList(singletonMap("Name", "FirstName"), singletonMap("Name", "LastName"));
*     }
* }
* </pre>
* The template only contains a single variable ({@code $Name}), but the generator will produce two classes,
* {@code FirstName} and {@code LastName}.
*
* @author Mirko Raner
**/
public interface InputProvider
{
    /**
    * Returns the inputs for the template engine.
    *
    * @return a {@link Collection} of parameter {link Map}s
    **/
    Collection<Map<String, String>> getInputs();
}
