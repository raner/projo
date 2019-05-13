//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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
package pro.projo.utilities;

import java.lang.reflect.Method;
import java.util.function.Function;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MethodFunctionConverterTest
{
    MethodFunctionConverter converter = new MethodFunctionConverter();

    @Test
    public void convertFunctionToMethod() throws NoSuchMethodException
    {
        Method expected = CharSequence.class.getDeclaredMethod("length");
        Method actual = converter.convert(CharSequence.class, CharSequence::length);
        assertEquals(expected, actual);
    }

    @Test
    public void convertMethodToFunction() throws NoSuchMethodException
    {
        Method method = CharSequence.class.getDeclaredMethod("length");
        Function<CharSequence, Integer> function = converter.convert(method);
        CharSequence test = "test";
        assertEquals(test.length(), function.apply(test).intValue());
    }
}
