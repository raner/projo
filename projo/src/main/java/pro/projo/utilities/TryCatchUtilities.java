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
package pro.projo.utilities;

import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
* {@link TryCatchUtilities} provides utility methods for simplified exception handling.
*
* TODO: address overlap/duplication between {@link TryCatch} and {@link Rethrow}
*
* @author Mirko Raner
**/
public interface TryCatchUtilities
{
    static class TryCatch<_Type_>
    {
        private _Type_ result;
        private Throwable throwable;

        /**
        * Creates a {@link TryCatch} that successfully produced a result.
        *
        * @param result the result
        **/
        public TryCatch(_Type_ result)
        {
            this.result = result;
        }

        /**
        * Creates a {@link TryCatch} that failed with a {@link Throwable}.
        *
        * @param failure the {@link Throwable}
        **/
        public TryCatch(Throwable failure)
        {
            this.throwable = failure;
        }

        @SafeVarargs
        public final Rethrow<_Type_> rethrow(Class<? extends Exception>... exceptions)
        {
            if (throwable == null)
            {
                // No exception was caught, just carry through the successful result:
                //
                return new Rethrow<>(result);
            }
            if (Stream.of(exceptions).anyMatch(exception -> exception.isInstance(throwable)))
            {
                // An exception was caught that matches a rethrow:
                //
                return new Rethrow<>(throwable);
            }

            // An exception was caught, but it is not in the list of exceptions to be thrown;
            // rethrow the actual exception:
            //
            return this.<RuntimeException>throwException(throwable);
        }

        public _Type_ andReturn()
        {
            return result;
        }

        private <_Throwable_ extends Throwable> Rethrow<_Type_> throwException(Throwable exception) throws _Throwable_
        {
            @SuppressWarnings("unchecked")
            _Throwable_ hiddenException = (_Throwable_)exception;
            throw hiddenException;
        }
    }

    static class Rethrow<_Type_>
    {
        private _Type_ result;
        private Throwable throwable;

        public Rethrow(_Type_ result)
        {
            this.result = result;
        }

        public Rethrow(Throwable throwable)
        {
            this.throwable = throwable;
        }

        public <_Throwable_ extends Throwable> TryCatch<_Type_> as(Class<? extends _Throwable_> wrapperClass) throws _Throwable_
        {
            if (throwable == null)
            {
                return new TryCatch<>(result);
            }
            _Throwable_ wrappedThrowable = null;
            try
            {
                wrappedThrowable = wrapperClass.newInstance();
            }
            catch (InstantiationException instantiationException)
            {
                throw new InstantiationError(instantiationException.getMessage());
            }
            catch (IllegalAccessException illegalAccessException)
            {
                throw new IllegalAccessError(illegalAccessException.getMessage());
            }
            // TODO: bootstrap TryCatchUtilities for the above exception handling
            wrappedThrowable.initCause(this.throwable);
            throw wrappedThrowable;
        }

        public TryCatch<_Type_> asRuntimeException()
        {
            return null;
        }
    }

    default <_ReturnType_> TryCatch<_ReturnType_> tryCatch(Callable<_ReturnType_> code)
    {
        try
        {
            return new TryCatch<>(code.call());
        }
        catch (Throwable throwable)
        {
            return new TryCatch<>(throwable);
        }
    }
}
