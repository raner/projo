//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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
@Options(fileExtension=".kava", postProcessor=UpperCasePostProcessor.class, addAnnotations=false)
@Interface(generate="Runnable", from=Runnable.class, options=@Options(addAnnotations=true, postProcessor=LowerCasePostProcessor.class))
@Interface(generate="AutoCloseable", from=AutoCloseable.class)
package pro.projo.generation.interfaces.test.options.packagelevel;

import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.postprocessor.LowerCasePostProcessor;
import pro.projo.interfaces.annotation.postprocessor.UpperCasePostProcessor;

/**
* This {@code package-info} class contains additional annotations that are tested by the option-related tests
* in {@link pro.projo.generation.interfaces.GeneratedPostProcessorPackageLevelSourcesTest}.
*
* @author Mirko Raner
**/
