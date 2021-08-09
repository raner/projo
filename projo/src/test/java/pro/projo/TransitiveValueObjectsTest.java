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
package pro.projo;

import org.junit.Test;
import pro.projo.singles.Factory;
import static org.junit.Assert.assertEquals;
import static pro.projo.Projo.creates;

public class TransitiveValueObjectsTest
{
    static interface Series
    {
        Integer index();

        @Override
        public boolean equals(Object other);

        @Override
        public int hashCode();
    }

    static interface Row extends Series
    {
        Factory<Row, Integer> ROWS = creates(Row.class).with(Row::index);
    }

    @Test
    public void testTransitiveValueObjectEquals()
    {
        Row row1 = Row.ROWS.create(1);
        Row row2 = Row.ROWS.create(1);
        assertEquals(row1, row2);
    }
    
    @Test
    public void testTransitiveValueObjectHashCode()
    {
        Row row1 = Row.ROWS.create(1);
        Row row2 = Row.ROWS.create(1);
        assertEquals(row1.hashCode(), row2.hashCode());
    }
}
