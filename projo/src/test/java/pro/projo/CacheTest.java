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

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import pro.projo.annotations.Cached;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CacheTest
{
    static interface CachedValue
    {
        static AtomicInteger value = new AtomicInteger();

        @Cached
        default int value()
        {
            return value.incrementAndGet();
        }

        @Cached
        default String stringValue()
        {
            return String.valueOf(value.incrementAndGet());
        }

        @Cached(cacheSize = 2)
        default int valuePlus(int plus)
        {
            return value.incrementAndGet() + plus;
        }
    }

    @Test
    public void testCachedZeroArgumentMethodIsOnlyEvaluatedOnce()
    {
        CachedValue cached = Projo.create(CachedValue.class);
        int firstInvocation = cached.value();
        int secondInvocation = cached.value();
        assertEquals(firstInvocation, secondInvocation);
    }

    @Test
    public void testCachedZeroArgumentMethodReturningStringIsOnlyEvaluatedOnce()
    {
        CachedValue cached = Projo.create(CachedValue.class);
        String firstInvocation = cached.stringValue();
        String secondInvocation = cached.stringValue();
        assertEquals(firstInvocation, secondInvocation);
    }

    @Test
    public void testSeparateObjectsMaintainSeparateCaches()
    {
        CachedValue cached1 = Projo.create(CachedValue.class);
        CachedValue cached2 = Projo.create(CachedValue.class);
        assertEquals(cached1.value(), cached1.value());
        assertEquals(cached2.value(), cached2.value());
        assertNotEquals(cached1.value(), cached2.value());
    }

    @Test
    public void testCacheSizeLimitIsHonored()
    {
        CachedValue cached = Projo.create(CachedValue.class);
        int value1 = cached.valuePlus(1);
        assertEquals(value1, cached.valuePlus(1)); // First result should be cached
        int value2 = cached.valuePlus(2);
        assertEquals(value2, cached.valuePlus(2)); // Second result should be cached as well
        int value3 = cached.valuePlus(3);
        assertNotEquals(value3, cached.valuePlus(3)); // Third result is not cached; cache is full
    }
}
