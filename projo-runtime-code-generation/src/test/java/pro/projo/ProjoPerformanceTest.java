//                                                                          //
// Copyright 2017 - 2022 Mirko Raner                                        //
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

import java.time.Duration;
import java.util.concurrent.Callable;
import org.junit.Test;
import pro.projo.triples.Factory;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
* {@link ProjoPerformanceTest} is a performance test that aims at revealing performance problems with Projo.
*
* @author Mirko Raner
**/
public class ProjoPerformanceTest
{
    static interface Color
    {
        Factory<Color, Byte, Byte, Byte> FACTORY = Projo.creates(Color.class).with(Color::red, Color::green, Color::blue);
        byte red();
        byte green();
        byte blue();
    }

    static class RealColor implements Color
    {
        private byte red, green, blue;

        public RealColor(byte red, byte green, byte blue)
        {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        @Override
        public byte red()
        {
            return red;
        }

        @Override
        public byte green()
        {
            return green;
        }

        @Override
        public byte blue()
        {
            return blue;
        }
    }

    /**
    * Proxy-based implementations have about 150 slower read performance that runtime-generated ones.
    *
    * @throws Exception
    **/
    @Test
    public void testReadOnlyPerformance() throws Exception
    {
        byte one = (byte)1;
        byte two = (byte)2;
        byte three = (byte)3;
        int repeat = 10000000;
        Callable<Color> testRealColors = () ->
        {
            byte reds = 0, greens = 0, blues = 0;
            Color color = new RealColor(one, two, three);
            for (int index = 0; index < repeat; index++)
            {
                reds += color.red();
                greens += color.green();
                blues += color.blue();
            }
            return new RealColor(reds, greens, blues);
        };
        TimedResult<Color> baseline = time(testRealColors);
        byte[] color = {baseline.value.red(), baseline.value.green(), baseline.value.blue()};
        byte[] expected = {-128, 0, -128};
        assertArrayEquals(expected, color);

        // Create at least one Projo object ahead of time to take the initial time for class
        // creation out of the equation:
        //
        Color.FACTORY.create(one, two, three);
        Callable<Color> testProjoColors = () ->
        {
            byte reds = 0, greens = 0, blues = 0;
            Color projo = Color.FACTORY.create(one, two, three);
            for (int index = 0; index < repeat; index++)
            {
                reds += projo.red();
                greens += projo.green();
                blues += projo.blue();
            }
            return new RealColor(reds, greens, blues);
        };
        TimedResult<Color> result = time(testProjoColors);
        color = new byte[] {result.value.red(), result.value.green(), result.value.blue()};
        assertArrayEquals(expected, color);
        long ratio = result.duration.toMillis()/baseline.duration.toMillis();
        assertTrue("Projo is " + ratio + " times slower", ratio <= 3);
    }

    @Test
    @org.junit.Ignore
    public void testReadWritePerformance() throws Exception
    {
        byte one = (byte)1;
        byte two = (byte)2;
        byte three = (byte)3;
        int repeat = 10000000;
        Callable<Color> testRealColors = () ->
        {
            byte reds = 0, greens = 0, blues = 0;
            for (int index = 0; index < repeat; index++)
            {
                Color color = new RealColor(one, two, three);
                reds += color.red();
                greens += color.green();
                blues += color.blue();
            }
            return new RealColor(reds, greens, blues);
        };
        TimedResult<Color> baseline = time(testRealColors);
        byte[] color = {baseline.value.red(), baseline.value.green(), baseline.value.blue()};
        byte[] expected = {-128, 0, -128};
        assertArrayEquals(expected, color);
        
        // Create at least one Projo object ahead of time to take the initial time for class
        // creation out of the equation:
        //
        Color.FACTORY.create(one, two, three);
        Callable<Color> testProjoColors = () ->
        {
            byte reds = 0, greens = 0, blues = 0;
            for (int index = 0; index < repeat; index++)
            {
                Color projo = Color.FACTORY.create(one, two, three);
                reds += projo.red();
                greens += projo.green();
                blues += projo.blue();
            }
            return new RealColor(reds, greens, blues);
        };
        TimedResult<Color> result = time(testProjoColors);
        color = new byte[] {result.value.red(), result.value.green(), result.value.blue()};
        assertArrayEquals(expected, color);
        long ratio = result.duration.toMillis()/baseline.duration.toMillis();
        assertTrue("Projo is " + ratio + " times slower", ratio <= 3);
    }

    static class TimedResult<_Any_>
    {
        _Any_ value;
        Duration duration;

        TimedResult(_Any_ value, long time)
        {
            this.value = value;
            duration = Duration.ofMillis(time);
        }
    }

    private <_Any_> TimedResult<_Any_> time(Callable<_Any_> callable) throws Exception
    {
        long time = System.currentTimeMillis();
        _Any_ value = callable.call();
        time = System.currentTimeMillis()-time;
        return new TimedResult<>(value, time);
    }
}
