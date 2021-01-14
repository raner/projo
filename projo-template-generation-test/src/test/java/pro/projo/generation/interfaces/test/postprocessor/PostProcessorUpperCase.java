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
package pro.projo.generation.interfaces.test.postprocessor;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.UnaryOperator;

/**
* {@link PostProcessorUpperCase} is a simple Projo template post-processor that converts
* all letters to upper case. This functionality is mainly provided for demo purposes.
*
* @author Mirko Raner
**/
public class PostProcessorUpperCase implements UnaryOperator<Writer>
{
    @Override
    public Writer apply(Writer writer)
    {
        return new FilterWriter(writer)
        {
            @Override
            public void write(int character) throws IOException
            {
                super.write(Character.toUpperCase(character));
            }

            @Override
            public void write(char[] characters, int offset, int length) throws IOException
            {
                super.write(new String(characters).toUpperCase().toCharArray(), offset, length);
            }

            @Override
            public void write(String string, int offset, int length) throws IOException
            {
                super.write(string.toUpperCase(), offset, length);
            }
        };
    }
}
