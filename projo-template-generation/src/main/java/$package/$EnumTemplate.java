//                                                                          //
// Copyright 2019 - 2024 Mirko Raner                                        //
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
package $package;
/* *#**#/
#foreach ($import in $imports)
import $import;
#end
/* */
/**
*#*
* The {@link $package.$EnumTemplate} interface provides the Velocity template for Projo's scraping mechanism
* for {@code enum}s. The class is both a completely valid Java class and a valid Apache Velocity template (which is
* why the specific definition of its template references may appear a little funky at first glance), though it is
* currently treated as a resource, not a Java source.
*#
* $javadoc
*#*
* @author Mirko Raner
*#
**/
/* *#**#/
$generatedBy
/* */
public enum $EnumTemplate
{
/* *#**#/
#foreach ($value in $values)
    $value#if ($foreach.hasNext), #end

#end
/* */
}
