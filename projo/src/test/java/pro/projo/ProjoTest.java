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
package pro.projo;

import org.junit.Test;
import pro.projo.doubles.Factory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pro.projo.Projo.creates;

public class ProjoTest
{
    static interface Interval
    {
        Factory<Interval, Double, Double> FACTORY = creates(Interval.class).with(Interval::begin, Interval::end);
        Double begin();
        Double end();
    }

    static interface NoFactory
    {
        void setInteger(int value);
        int getInteger();
    }

    @Test
    public void testGetFactory()
    {
        assertEquals(Interval.FACTORY, Projo.getFactory(Interval.class));
    }

    @Test
    public void testGetFactoryReturnsNull()
    {
        assertNull(Projo.getFactory(NoFactory.class));
    }
}
