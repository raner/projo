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
package pro.projo.generation.interfaces.test;
/* */
import javax.annotation.Generated;
/* */
/**
*
* This interface was extracted from java.io.DataInput.
*
**/
/* */
@Generated("pro.projo.generation.interfaces.InterfaceTemplateProcessor")
/* */
public interface DataInput
{
/* */
    void readFully(byte[] arg0);
    void readFully(byte[] arg0, int arg1, int arg2);
    int skipBytes(int arg0);
    boolean readBoolean();
    byte readByte();
    int readUnsignedByte();
    short readShort();
    int readUnsignedShort();
    char readChar();
    int readInt();
    long readLong();
    float readFloat();
    double readDouble();
/* */
}
