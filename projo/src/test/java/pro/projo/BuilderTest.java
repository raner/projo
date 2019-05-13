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
package pro.projo;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class BuilderTest
{
    static interface Complex
    {
        double real();
        double imaginary();
    }

    static interface ThreeD
    {
        double x();
        double y();
        double z();
    }

    @Test
    public void testBuilderWithTwoProperties()
    {
        Complex object = Projo.builder(Complex.class).with(Complex::real, 2.71).with(Complex::imaginary, 3.14).build();
        double[] expected = {2.71, 3.14};
        double[] actual = {object.real(), object.imaginary()};
        assertArrayEquals(expected, actual, 1E-6);
    }

    @Test
    public void testBuilderWithThreeProperties()
    {
        ThreeD object = Projo.builder(ThreeD.class).with(ThreeD::x, 1D).with(ThreeD::y, 2D).with(ThreeD::z, 3D).build();
        double[] expected = {1D, 2D, 3D};
        double[] actual = {object.x(), object.y(), object.z()};
        assertArrayEquals(expected, actual, 1E-6);
    }

    @Test
    public void testBuilderWithThreePropertiesInDifferentOrder()
    {
        ThreeD object = Projo.builder(ThreeD.class).with(ThreeD::z, 3D).with(ThreeD::x, 1D).with(ThreeD::y, 2D).build();
        double[] expected = {1D, 2D, 3D};
        double[] actual = {object.x(), object.y(), object.z()};
        assertArrayEquals(expected, actual, 1E-6);
    }

    @Test
    public void testBuilderWithThreePropertiesAndDuplicateInitialization()
    {
        ThreeD object = Projo.builder(ThreeD.class).with(ThreeD::z, 3D).with(ThreeD::x, 1D).with(ThreeD::z, 2D).build();
        double[] expected = {1D, 0D, 2D};
        double[] actual = {object.x(), object.y(), object.z()};
        assertArrayEquals(expected, actual, 1E-6);
    }

    @Test
    public void testBuilderWithThreePropertiesUsingNullValue()
    {
        ThreeD object = Projo.builder(ThreeD.class).with(ThreeD::x, 1D).with(ThreeD::y, 2D).with(ThreeD::z, null).build();
        double[] expected = {1D, 2D, 0D};
        double[] actual = {object.x(), object.y(), object.z()};
        assertArrayEquals(expected, actual, 1E-6);
    }
    
    @Test
    public void testBuilderWithThreePropertiesButOneMissing()
    {
        ThreeD object = Projo.builder(ThreeD.class).with(ThreeD::x, 1D).with(ThreeD::z, 3D).build();
        double[] expected = {1D, 0D, 3D};
        double[] actual = {object.x(), object.y(), object.z()};
        assertArrayEquals(expected, actual, 1E-6);
    }

    @Test
    public void testBuilderWithThreePropertiesButNoValuesSupplied()
    {
        ThreeD object = Projo.builder(ThreeD.class).build();
        double[] expected = {0D, 0D, 0D};
        double[] actual = {object.x(), object.y(), object.z()};
        assertArrayEquals(expected, actual, 1E-6);
    }
}
