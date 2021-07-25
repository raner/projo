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
package pro.projo.annotations;

import org.junit.Test;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.scaffold.ClassWriterStrategy;
import net.bytebuddy.dynamic.scaffold.ClassWriterStrategy.FrameComputingClassWriter;
import net.bytebuddy.jar.asm.ClassReader;
import net.bytebuddy.jar.asm.ClassWriter;
import net.bytebuddy.pool.TypePool;
import static org.junit.Assert.assertArrayEquals;

/**
 * 
 * {@link PrivateFieldProxyTest} is more of an integration test, as it tests a specific use case
 * where private field proxies come in handy.
 *
 * @author mirko
 *
 */
public class PrivateFieldProxyTest
{
    static class TestClassWriter extends FrameComputingClassWriter
    {
        interface SymbolTable
        {
            void addConstantInteger(int value);
        }

        @PrivateFieldProxy SymbolTable symbolTable;

        TestClassWriter(int flags, TypePool typePool)
        {
            super(flags, typePool);
            new PrivateFieldProxy.Processor(this).process();
            symbolTable.addConstantInteger(0xBadDecaf);
        }
    }

    static class TestClassWriterStrategy implements ClassWriterStrategy
    {
        @Override
        public ClassWriter resolve(int flags, TypePool typePool)
        {
          return new TestClassWriter(flags, typePool);
        }

        @Override
        public ClassWriter resolve(int flags, TypePool typePool, ClassReader classReader)
        {
          throw new UnsupportedOperationException();
        }
    }

    @Test
    public void testSymbolTableFieldProxy()
    {
        ByteBuddy byteBuddy = new ByteBuddy().with(new TestClassWriterStrategy());
        byte[] bytes = byteBuddy.subclass(Object.class).make().getBytes();
        byte[] magic = new byte[4];
        System.arraycopy(bytes, 11, magic, 0, 4);
        byte[] expected = {(byte)0xBa, (byte)0xdD, (byte)0xec, (byte)0xaf};
        assertArrayEquals(expected, magic);
    }
}
