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
package pro.projo.generation;

import java.io.StringWriter;
import java.util.Map;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
* The {@link ProjoTemplateFactoryGenerator} uses the Apache Velocity template engine to generate a Projo factory interface
* from a template and a set of parameters.
*
* @author Mirko Raner
**/
public class ProjoTemplateFactoryGenerator
{
    public String generate(String templateName, Map<String, String> parameters)
    {
        VelocityEngine engine = new VelocityEngine();
        VelocityContext context = new VelocityContext();
        parameters.forEach(context::put);
        engine.init();
        Template template = engine.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
}
