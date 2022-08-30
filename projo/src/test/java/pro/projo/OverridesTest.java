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
package pro.projo;

import org.junit.Test;
import pro.projo.annotations.Overrides;
import pro.projo.singles.Factory;
import static org.junit.Assert.assertEquals;
import static pro.projo.annotations.Overrides.toString;

public class OverridesTest
{
    // Test interfaces:

    public static interface Natural
    {
        public static Factory<Natural, Integer> factory = Projo.creates(Natural.class).with(Natural::value);

        Integer value();

        @Overrides(toString)
        default String toStringOverride()
        {
            return value().toString();
        }
    }

    @Test
    public void toStringMethodShouldPrintWrappedInteger()
    {
        Natural value = Natural.factory.create(42);
        assertEquals("42", value.toString());
    }
}
