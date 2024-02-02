//                                                                          //
// Copyright 2017 - 2024 Mirko Raner                                        //
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
package pro.projo.$template;
/*#*/
import pro.projo.template.annotation.Template;
import pro.projo.template.configuration.ProjoFactoryTemplateConfiguration;
/*#*/
/**
*#*
* The {@link pro.projo.$template.Factory} interface is the Velocity template for Projo's variadic factory interfaces.
* This class is at the same time a completely valid Java class and a valid Apache Velocity template (which is why
* the specific definition of its template references may appear a little funky at first glance).
*#
* This interface defines Projo's generic factory for objects with $n field$s.
*#*
* @param <$AdditionalTypeParameters> placeholder for type parameters 2 to n
*#
* @param <_Artifact_> the artifact type generated by the factory
* $additionalTypeParameterDocumentation
*
* @author Mirko Raner
**/
/*#*/@Template(input=ProjoFactoryTemplateConfiguration.class)/*#*/
public interface Factory<_Artifact_, $AdditionalTypeParameters> extends pro.projo.Factory
{
    /*#*/ interface $First {/**/} /*#*/
    /**
    * Creates a new Projo object, using the arguments to initialize the object's fields.
    *#*
    * @param $argumentAndAdditionalParameters to be expanded by the template engine
    *#
    * $additionalMethodParameterDocumentation
    * @return a new Projo object
    **/
    _Artifact_ create($First $argumentAndAdditionalParameters);
}
