//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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

/**
* {@link AbstractTypeMappingTest} provides a set of test classes for testing
* type mapping and delegation.
*
* @author Mirko Raner
**/
public interface AbstractTypeMappingTest
{
    public static interface Something<S extends Something<S>>
    {
        S self();
    }

    public static interface Value<S extends Value<S>> extends Something<S>
    {
        Boolean<?> equals(S other);
    }

    public static interface Ordered<S extends Ordered<S>> extends Something<S>
    {
        Boolean<?> lessOrEqual(S other);
    }

    public static interface Boolean<S extends Boolean<S>> extends Value<S>
    {
        Boolean<?> not();
        Boolean<?> and(Boolean<?> other);
    }

    public static interface True<S extends True<S>> extends Boolean<S>
    {
        //
    }

    public static interface False<S extends False<S>> extends Boolean<S>
    {
        //
    }

    public static interface Comparable<S extends Comparable<S>> extends Ordered<S>, Value<S>
    {
        default Boolean<?> lessThan(S other)
        {
            return lessOrEqual(other).and(equals(other).not());
        }
    }

    public static interface Number<S extends Number<S>> extends Value<S>
    {
        S add(S other);
        S multiply(S other);
    }

    public static interface Natural<S extends Natural<S>> extends Comparable<S>, Number<S>
    {
        //
    }

    public static interface Integer<S extends Integer<S>> extends Comparable<S>, Number<S>
    {
        S negate();
        S absolute();
        S subtract(Integer<?> other);
        S remainder(Integer<?> other);
    }

    public static interface IntegerImpl extends Integer<IntegerImpl>
    {
        //
    }

    public static interface String<S extends String<S>> extends Value<S>
    {
        Natural<?> length();
        String<?> substring(Natural<?> begin);
        String<?> substring(Natural<?> begin, Natural<?> end);
        Natural<?> indexOf(String<?> string);
        Boolean<?> beginsWith(String<?> string);
        Boolean<?> endsWith(String<?> string);
    }
}
