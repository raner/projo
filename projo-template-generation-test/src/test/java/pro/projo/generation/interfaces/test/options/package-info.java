//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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
@Options(fileExtension=".kava", outputLocation=CLASS_OUTPUT)
@Interface(generate="Runnable", from=Runnable.class)
package pro.projo.generation.interfaces.test.options;

import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Options;
import static javax.tools.StandardLocation.CLASS_OUTPUT;

/**
* This {@code package-info} class contains additional annotations that are tested by the
* {@link pro.projo.generation.interfaces.InterfaceTemplateProcessorTest} class.
*
* <b>NOTE:</b> generating files in the {@link StandardLocation#CLASS_OUTPUT} location
* (instead of {@link StandardLocation#SOURCE_OUTPUT}) is the only option that allows for
* verifying the generated files as resources (via {@link ClassLoader#getResourceAsStream(String)})
* in both Maven and Eclipse without necessitating additional configuration or build steps.
*
* @author Mirko Raner
**/
