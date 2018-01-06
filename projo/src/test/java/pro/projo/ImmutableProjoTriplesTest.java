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
import pro.projo.triples.Factory;
import static org.junit.Assert.assertArrayEquals;
import static pro.projo.Projo.creates;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ImmutableProjoTriplesTest
{
    static interface Name
    {
        Function<Name, String> x = Name::first;
        List<Function<Name, ?>> y = Arrays.asList(Name::first, Name::middle, Name::last);
        Factory<Name, String, Character, String> FACTORY = creates(Name.class).with(Name::first, Name::middle, Name::last);
        String first();
        char middle();
        String last();
    }

    @Test
    public void testName()
    {
        Name name = Name.FACTORY.create("Alfred", 'E', "Neumann");
        Object[] expected = {"Alfred", 'E', "Neumann"};
        Object[] actual = {name.first(), name.middle(), name.last()};
        assertArrayEquals(expected, actual);
    }
}
