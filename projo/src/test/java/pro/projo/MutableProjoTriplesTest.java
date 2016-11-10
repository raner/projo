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
import static org.junit.Assert.assertArrayEquals;
import static pro.projo.Projo.create;

public class MutableProjoTriplesTest
{
    static interface Coordinate3D
    {
        void setX(float x);
        float getX();
        void setY(float y);
        float getY();
        void setZ(float z);
        float getZ();
    }

    @Test
    public void testCoordinate3D()
    {
        Coordinate3D coordinate = create(Coordinate3D.class);
        coordinate.setX(4);
        coordinate.setY(5);
        coordinate.setZ(6);
        Float[] expected = {4F, 5F, 6F};
        Float[] actual = {coordinate.getX(), coordinate.getY(), coordinate.getZ()};
        assertArrayEquals(expected, actual);
    }
}
