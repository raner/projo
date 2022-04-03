//                                                                          //
// Copyright 2020 - 2022 Mirko Raner                                        //
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
package pro.projo.internal.rcg.utilities;

import java.util.concurrent.Callable;
import java.util.function.Function;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.DefaultCall;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

/**
* {@link Interceptor} is a utility class for facilitating the creation
* of ByteBuddy interceptor methods.
*
* @author Mirko Raner
**/
public class Interceptor
{
    public static interface Invocation
    {
        Object instance();
        Object[] arguments();
        Callable<?> defaultMethod();
    }

    private Function<Invocation, Object> interceptor;

    public Interceptor(Function<Invocation, Object> interceptor)
    {
        this.interceptor = interceptor;
    }

    @RuntimeType
    public Object intercept(@DefaultCall Callable<?> defaultMethod, @This Object instance, @AllArguments Object[] arguments) throws Exception
    {
        Invocation invocation = new Invocation()
        {
            @Override
            public Object instance()
            {
                return instance;
            }

            @Override
            public Object[] arguments()
            {
                return arguments;
            }

            @Override
            public Callable<?> defaultMethod()
            {
                return defaultMethod;
            }
        };
        return interceptor.apply(invocation);
    }
}
