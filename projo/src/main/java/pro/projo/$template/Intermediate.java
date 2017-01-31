//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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

import java.util.function.Function;
import pro.projo.internal.Prototype;
/*#*/
import pro.projo.template.annotation.Template;
import pro.projo.template.configuration.ProjoIntermediateTemplateConfiguration;
/*#*/
/**
*#*
* The {@link pro.projo.$template.Intermediate} interface is the Velocity template for Projo's intermediate interfaces.
* This class is at the same time a completely valid Java class and a valid Apache Velocity template (which is why
* the specific definition of its template references may appear a little funky at first glance).
*#
* The {@link pro.projo.${template}.Intermediate} interface defines Projo's intermediate interface for objects with $n field$s.
*
* @param <_Artifact_> the artifact type generated by the factory
*
* @author Mirko Raner
**/
/*#*/@Template(input=ProjoIntermediateTemplateConfiguration.class)/*#*/
public interface Intermediate<_Artifact_> extends Prototype<_Artifact_>
{
    /*#*/ interface $First {/**/} /*#*/

    /**
    * Creates a new Projo factory.
    *#*
    * @param $argumentAndAdditionalParameters to be expanded by the template engine
    *#
    * $additionalMethodParameterDocumentation
    * $additionalTypeParameterDocumentation
    * @return a new Projo factory
    **/
    public default <$AdditionalTypeParameters> Factory<_Artifact_, $AdditionalTypeParameters> with($First $argumentAndAdditionalParameters)
    {
        @SuppressWarnings("unchecked")
        Factory<_Artifact_, $AdditionalTypeParameters> factory = ($arguments) ->
        {
            /*#*/ Function<_Artifact_, ?> $members = null; /*#*/
            return initialize().members($members).with($arguments);
        };
        return factory;
    }
}