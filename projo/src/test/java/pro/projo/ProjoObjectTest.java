//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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

import java.lang.reflect.Method;
import org.junit.Test;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
* {@link ProjoObjectTest} is a JUnit test that makes sure that all methods of {@link java.lang.Object}
* (excluding {@link #equals(Object)}, {@link #hashCode()} and {@link #toString()}) can be invoked without
* problems on Projo objects.
*
* @author Mirko Raner
**/
public class ProjoObjectTest
{
    static interface ProjoObject extends Cloneable
    {
        int getValue();
    }

    private final static int DELAY = 500;

    private ProjoObject projo = Projo.create(ProjoObject.class);
    private Runnable notifyAll = () ->
    {
        synchronized (projo)
        {
            projo.notifyAll();
        }
    };

    /**
     * Tests the {@link #finalize()} method. This test has no assertions, it merely insures that invoking
     * the method will not throw an exception.
     *
     * @throws Exception if the test failed
     */
    @Test
    public void testFinalize() throws Exception
    {
        Method finalize = Object.class.getDeclaredMethod("finalize");
        finalize.setAccessible(true);
        finalize.invoke(projo);
    }

    @Test
    public void testClone() throws Exception
    {
        Method clone = Object.class.getDeclaredMethod("clone");
        clone.setAccessible(true);
        assertNotNull(clone.invoke(projo));
    }

    @Test
    public void testGetClass()
    {
        assertTrue(ProjoObject.class.isAssignableFrom(projo.getClass()));
    }

    @Test(timeout=DELAY*2)
    public void testWaitNotify() throws InterruptedException
    {
        Runnable notify = () ->
        {
            synchronized (projo)
            {
                projo.notify();
            }
        };
        newSingleThreadScheduledExecutor().schedule(notify, DELAY, MILLISECONDS);
        synchronized (projo)
        {
            projo.wait();
        }
    }

    @Test(timeout=DELAY*2)
    public void testWaitLongNotifyAll() throws InterruptedException
    {
        newSingleThreadScheduledExecutor().schedule(notifyAll, DELAY, MILLISECONDS);
        synchronized (projo)
        {
            projo.wait(DELAY*3);
        }
    }

    @Test(timeout=DELAY*2)
    public void testWaitLongIntNotifyAll() throws InterruptedException
    {
        newSingleThreadScheduledExecutor().schedule(notifyAll, DELAY, MILLISECONDS);
        synchronized (projo)
        {
            projo.wait(DELAY*3, 1);
        }
    }
}
