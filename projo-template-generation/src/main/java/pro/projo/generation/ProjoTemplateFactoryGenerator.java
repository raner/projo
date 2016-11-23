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

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.parser.ParseException;
import static org.apache.velocity.runtime.RuntimeSingleton.getRuntimeServices;

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
        StringWriter writer = new StringWriter();
        generate(templateName, parameters, writer);
        return writer.toString();
    }

    public void generate(String templateName, Map<String, String> parameters, Writer writer)
    {
        VelocityEngine engine = new VelocityEngine();
        Template template = engine.getTemplate(templateName);
        generate(template, parameters, writer);
    }

    public void generate(Template template, Map<String, String> parameters, Writer writer)
    {
        VelocityContext context = new VelocityContext();
        parameters.forEach(context::put);
        template.merge(context, writer);
    }

    public void generate(Reader reader, String name, Map<String, String> parameters, Writer writer)
    {
        try
        {
            generate(getTemplate(reader, name), parameters, writer);
        }
        catch (ParseException parseException)
        {
            // This is slightly unorthodox:
            //
            try (PrintWriter printWriter = new PrintWriter(writer, true))
            {
                parseException.printStackTrace(printWriter);
            }
        }
    }

    public Template getTemplate(Reader reader, String name) throws ParseException
    {
        Template template = new Template();
        template.setRuntimeServices(getRuntimeServices());
        template.setData(getRuntimeServices().parse(reader, name));
        template.initDocument();
        return template;
    }
}
