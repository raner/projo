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

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TryCatchUtilitiesTest implements TryCatchUtilities
{
    @Test
    public void testSuccessfulCompletionWithoutCatches()
    {
        boolean result = tryCatch(() -> true).andReturn();
        assertTrue(result);
    }

    @Test
    public void testSuccessfulCompletionWithRethrow()
    {
        boolean result = tryCatch(() -> true)
            .rethrow(IllegalArgumentException.class).as(Error.class)
            .andReturn();
        assertTrue(result);
    }

    @Test(expected=ClassFormatError.class)
    public void testMatchingExceptionCaughtWithRethrow()
    {
        tryCatch(() -> throwing(new ClassCastException()))
            .rethrow(ClassCastException.class).as(ClassFormatError.class)
            .andReturn();
    }

    @Test(expected=NullPointerException.class)
    public void testNonMatchingExceptionCaughtWithRethrow()
    {
        tryCatch(() -> throwing(new NullPointerException()))
            .rethrow(ClassCastException.class).as(ClassFormatError.class)
            .andReturn();
    }

    private Boolean throwing(RuntimeException exception)
    {
        throw exception;
    }
}
