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
package pro.projo.generation.test.utilities;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.AssumptionViolatedException;

/**
* The {@link ExceptionMatcher} is a custom Hamcrest matcher that verifies if an
* {@link UncheckedConsumer} was able to consume a given item without throwing an
* exception.
*
* @param <TYPE> the type of item consumed
* 
* @author Mirko Raner
**/
public class ExceptionMatcher<TYPE> extends TypeSafeMatcher<UncheckedConsumer<TYPE>>
{
    /**
    * {@link Outcome} is an intermediate class whose main purpose is to enable a
    * fluent API.
    *
    * @param <CONSUMABLE> the type of item consumed
    **/
    public static class Outcome<CONSUMABLE>
    {
        private CONSUMABLE consumable;

        Outcome(CONSUMABLE consumable)
        {
            this.consumable = consumable;
        }

        public ExceptionMatcher<CONSUMABLE> withoutThrowingException()
        {
            return new ExceptionMatcher<>(consumable);
        }
    }

    /**
    * Creates an {@link Outcome} for the given consumable.
    *
    * @param <CONSUMABLE> the consumable type
    * @param consumable the consumable
    * @return an {@link Outcome}
    **/
    public static <CONSUMABLE> Outcome<CONSUMABLE> consumes(CONSUMABLE consumable)
    {
        return new Outcome<>(consumable);
    }

    private TYPE consumable;

    public ExceptionMatcher(TYPE consumable)
    {
        this.consumable = consumable;
    }

    @Override
    public void describeTo(Description description)
    {
        // No additional description
    }

    /**
    * Determines if the matcher matches.
    * This implementation will directly throw an {@link AssumptionViolatedException} if
    * the match failed, because this allows to provide a more succinct message (the
    * regular handling would just display the {@link UncheckedConsumer}'s lambda expression
    * and would not show the thrown exception).
    **/
    @Override
    protected boolean matchesSafely(UncheckedConsumer<TYPE> consumer)
    {
        try
        {
            consumer.accept(consumable);
            return true;
        }
        catch (Exception exception)
        {
            throw new AssumptionViolatedException("exception thrown:", exception);
        }
    }
}
