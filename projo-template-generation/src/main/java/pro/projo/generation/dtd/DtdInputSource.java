//                                                                          //
// Copyright 2022 Mirko Raner                                               //
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
package pro.projo.generation.dtd;

import java.io.InputStream;
import javax.lang.model.element.TypeElement;
import org.xml.sax.InputSource;

/**
* {@link DtdInputSource} is a SAX {@link InputSource} that carries some additional
* information.
*
* @author Mirko Raner
**/
public class DtdInputSource extends InputSource
{
    private TypeElement baseInterface;
    private TypeElement baseInterfaceEmpty;

    public DtdInputSource(InputStream input, TypeElement baseInterface, TypeElement baseInterfaceEmpty)
    {
        super(input);
        this.baseInterface = baseInterface;
        this.baseInterfaceEmpty = baseInterfaceEmpty;
    }

    public TypeElement getBaseInterface()
    {
        return baseInterface;
    }

    public TypeElement getBaseInterfaceEmpty()
    {
        return baseInterfaceEmpty;
    }
}
