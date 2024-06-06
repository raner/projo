//                                                                          //
// Copyright 2024 Mirko Raner                                               //
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

import java.util.ArrayList;
import javax.lang.model.type.TypeMirror;
import org.junit.Test;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.template.annotation.Configuration;

public class ProjoTemplateFactoryProcessorTest
{
    @Test(expected=RuntimeException.class)
    public void testGetConfiguration()
    {
        ProjoTemplateFactoryProcessor processor = new ProjoTemplateFactoryProcessor();
        TypeMirror type = ReflectionTypes.getInstance().typeMirror(ConstructorThrows.class);
        processor.getConfiguration(type);
    }

    @Test(expected=RuntimeException.class)
    public void testGetClass()
    {
        class Nested extends ArrayList<Configuration>
        {
            private static final long serialVersionUID = 3888598709112160435L;
        }
        ProjoTemplateFactoryProcessor processor = new ProjoTemplateFactoryProcessor();
        TypeMirror type = ReflectionTypes.getInstance().typeMirror(Nested.class);
        processor.getClass(type);
    }
}

class ConstructorThrows extends ArrayList<Configuration>
{
    private static final long serialVersionUID = 4201658057355742410L;

    @SuppressWarnings("unused")
    public ConstructorThrows()
    {
        throw new UnsupportedOperationException();
    }
}
