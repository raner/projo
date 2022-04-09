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
package pro.projo.internal.rcg.runtime;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* The {@link Cache} class is Projo's internal implementation for caching the results
* of method invocations. The key type is hard-coded to {@code List<Object>}, representing
* the generic list of input arguments (or an empty list for zero-argument methods).
*
* TODO: currently this class does not support cache eviction of any kind; the cache
*       will just fill up to the maximum capacity, and at that point no additional
*       results will be cached, while all previously cached results will remain in the
*       cache indefinitely
*
* <h2><code>Method</code> vs <code>MethodHandle</code> vs <code>Function&lt;Object[], VALUE&gt;</code></h2>
* The cache needs a way to retrieve results from the original method in case there is a cache miss
* for the given parameter combination. In the first implementation of the cache this was achieved
* by passing a <code>Function&lt;Object[], VALUE&gt;</code>, which would be lambda expression that
* calls the original default method. From a type safety perspective, this is preferable, since it
* supports retention of the <code>VALUE</code> type parameter. It also does not require use of the
* Reflection API (which is notoriously slow and type-unsafe).<br>
* However, since this API has to be called from generated code, there were a few other considerations
* as well. Unfortunately, code generation for calling lambda expressions is much more involved than
* calling methods with "regular" arguments. Among other things, it requires creation of additional
* bootstrap methods and often takes extra effort for capturing the right variables from the enclosing
* scope. For these reasons, the "<code>Function&lt;Object[], VALUE&gt;</code> + lambda expression"
* approach was eventually ruled out. <br>
* The next thing that came to mind was good ol' Reflection, which would pass a
* {@link java.lang.reflect.Method} instead of a {@link java.util.function.Function}. However, as
* already outlined above this approach is not type-safe and has extremely poor performance. Moreover,
* the Reflection API offers no reliable way to invoke an original default method that has been
* overridden by a subclass. So, in addition to performance and type-safety issues, the
* reflection-based approach is not possible due to this practical limitation. <br>
* The only remaining possibility is to use {@link MethodHandle}s from the new Invocation API
* introduced in Java 8. {@link MethodHandle}s don't provide type safety (at least not when they're
* invoked via a non-polymorphic signature like {@link MethodHandle#invokeWithArguments(Object...)}),
* but they support invocation of overridden default methods, and their performance overhead is
* considerably smaller than invocation via the Reflection API. Also, method information can be
* directly stored as {@code REF_invokeSpecial} entries in the constant pool, without the need for
* generating bootstrap methods. Finally, Byte Buddy offers very convenient APIs for all necessary
* code generation steps, which is why {@link MethodHandle}s ended up the winning solution among
* the various choices.
*
* @author Mirko Raner
**/
public class Cache<VALUE>
{
    private Object owner;
    private int maximumCacheSize;
    private MethodHandle originalMethod;
    private Map<List<Object>, VALUE> cache;

    /**
    * Creates a new empty cache based on the given cache parameters.
    * This is a convenience method to work around some limitations in the ByteBuddy API.
    * Notably, {@code MethodCall.setsField(...)} does not work for constructors (due to the
    * constructor's return type always being {@code void}). However, calling a static method
    * works as expected.
    *
    * @param maximumCacheSize the maximum number of elements that the cache can hold
    * @param originalMethod the {@link MethodHandle} of the original default method that is being cached
    * @param owner the object to which this {@link Cache} object belongs
    * @param <VALUE> the value type of the cache
    * @return a new cache
    **/
    public static <VALUE> Cache<VALUE> create(int maximumCacheSize, MethodHandle originalMethod, Object owner)
    {
        return new Cache<VALUE>(maximumCacheSize, originalMethod, owner);
    }

    /**
    * Creates a new empty cache based on the given cache parameters.
    *
    * @param maximumCacheSize the maximum number of elements that the cache can hold
    * @param originalMethod the {@link MethodHandle} of the original default method that is being cached
    * @param owner the object to which this {@link Cache} object belongs
    **/
    public Cache(int maximumCacheSize, MethodHandle originalMethod, Object owner)
    {
        cache = new HashMap<>();
        this.owner = owner;
        this.originalMethod = originalMethod;
        this.maximumCacheSize = maximumCacheSize;
    }

    /**
    * Returns a cached value for the given input arguments. If the value is not in the cache it will
    * be retrieved by calling the original default method via its {@link MethodHandle}.
    *
    * @param arguments the input arguments
    * @return the value (from the cache or from calling the original method)
    * @throws Throwable any {@link Throwable} that might be thrown by the original method
    **/
    public VALUE get(Object[] arguments) throws Throwable
    {
        List<Object> key = Arrays.asList(arguments); // arrays only support identity comparison; must use list

        // Check if the result is already in the cache
        // (use contains() since the result could actually be null):
        //
        if (cache.containsKey(key))
        {
            // Return cached value:
            //
            VALUE hit = cache.get(key);
            return hit;
        }
        else
        {
            // Obtain result by calling default method:
            //
            Object[] objectAndArguments = new Object[arguments.length+1];
            System.arraycopy(arguments, 0, objectAndArguments, 1, arguments.length);
            objectAndArguments[0] = owner;
            @SuppressWarnings("unchecked")
            VALUE value = (VALUE)originalMethod.invokeWithArguments(objectAndArguments);

            // Store cached result, if there is space in the cache:
            // (currently, no eviction strategies are supported)
            //
            if (cache.size() < maximumCacheSize)
            {
                cache.put(key, value);
            }
            return value;
        }
    }
}
