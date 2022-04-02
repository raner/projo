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
package pro.projo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pro.projo.annotations.Cached.EvictionStrategy.NONE;

/**
* The {@link Cached @Cached} annotation supplies a caching layer around a default method.
*
* @since 1.4
*
* @author Mirko Raner
**/
@Target(METHOD)
@Retention(RUNTIME)
public @interface Cached
{
	enum EvictionStrategy {NONE}

	/**
	* The maximum size of the cache. Defaults to 1.
	*
	* @return the maximum cache size
	**/
    int cacheSize() default 1;

    /**
    * The eviction strategy that determines which items are removed from the cache.
    * Defaults to {@code NONE}, i.e., once an item is in the cache it will never be removed.
    *
    * @return the cache eviction strategy
    **/
    EvictionStrategy evictionStrategy() default NONE;
}
