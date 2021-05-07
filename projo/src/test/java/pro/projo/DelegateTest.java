//                                                                          //
// Copyright 2020 - 2021 Mirko Raner                                        //
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

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Test;
import io.reactivex.rxjava3.core.Flowable;
import static org.junit.Assert.assertEquals;

public class DelegateTest implements AbstractTypeMappingTest
{
    // TODO: leverage Guice bindings for mappings; this requires some sort of type adapter
    //       to satisfy Guice's type requirements (can't bind A to B if there is no sub-typing
    //       relationship between A and B)

    static interface Predicate<$ extends Predicate<$, VALUE>, VALUE>
    {
        Boolean<?> test(VALUE value);
    }

    static interface Function<$ extends Function<$, INPUT, OUTPUT>, INPUT, OUTPUT>
    {
        OUTPUT apply(INPUT input);
    }

    static interface Tuple<$ extends Tuple<$, A, B>, A, B>
    {
        A getA();
        B getB();
    }

    static interface Flow<$ extends Flow<$, TYPE>, TYPE>
    {
        TYPE blockingFirst();
        Flow<?, TYPE> skip(Natural<?> count);
        Flow<?, TYPE> filter(Predicate<?, TYPE> filter);
        Flow<?, TYPE> takeWhile(Predicate<?, TYPE> condition);
        Flow<?, TYPE> concatWith(Flow<?, ? extends TYPE> other);
        <NEW> Flow<?, NEW> map(Function<?, TYPE, NEW> map);
        <NEW> Flow<?, NEW> scan(NEW initialValue, Function<?, Tuple<?, NEW, ? super TYPE>, NEW> accumulator);
        <OTHER, NEW> Flow<?, NEW> zipWith(Flow<?, ? extends OTHER> other, Function<?, Tuple<?, ? super TYPE, ? super OTHER>, ? extends NEW> zipper);
    }

    static interface Flows<$ extends Flows<$>>
    {
        <T> Flow<?, T> just(T element);
        <T> Flow<?, T> just(T element1, T element2);
        <T> Flow<?, T> fromIterable(Iterable<T> iterable);
        <T> Flow<?, T> concat(Flow<?, ? extends T> s1, Flow<?, ? extends T> s2);
        <T1, T2, T> Flow<?, T> zip(Flow<?, ? extends T1> s1, Flow<?, ? extends T2> s2, Function<?, Tuple<?, ? super T1, ? super T2>, ? extends T> zipper);
    }

    public static class ExtendedInteger extends BigInteger
    {
        private final static long serialVersionUID = 6122563045434251897L;

        public static ExtendedInteger ZERO = new ExtendedInteger("0");

        public static ExtendedInteger ONE = new ExtendedInteger("1");

        public static ExtendedInteger TEN = new ExtendedInteger("10");

        public ExtendedInteger(java.lang.String value)
        {
            super(value);
        }

        public static ExtendedInteger valueOf(long value)
        {
            return new ExtendedInteger(java.lang.String.valueOf(value));
        }

        public long longValue()
        {
            return super.longValue();
        }

        public ExtendedInteger add(ExtendedInteger value)
        {
            return new ExtendedInteger(java.lang.String.valueOf(((BigInteger)this).add(value)));
        }

        public ExtendedInteger subtract(ExtendedInteger value)
        {
            return new ExtendedInteger(java.lang.String.valueOf(((BigInteger)this).subtract(value)));
        }

        public boolean lessThan(ExtendedInteger other)
        {
            return compareTo(other) < 0;
        }
    }

    Mapping<?> mapping = Projo.mapping()
        .map(Flow.class).to(Flowable.class)
        .map(Flows.class).to(Flowable.class)
        .map(Natural.class).to(ExtendedInteger.class)
            .withAdapter(long.class, ExtendedInteger::longValue, ExtendedInteger::valueOf)
        .map(Integer.class).to(ExtendedInteger.class)
        .map(Predicate.class).to(io.reactivex.rxjava3.functions.Predicate.class);

    @Test
    public void integerAdd()
    {
        Mapping<?> mapping = Projo.mapping().map(IntegerImpl.class).to(ExtendedInteger.class);
        IntegerImpl one = Projo.delegate(IntegerImpl.class, ExtendedInteger.ONE, mapping);
        IntegerImpl ten = Projo.delegate(IntegerImpl.class, ExtendedInteger.TEN, mapping);
        IntegerImpl eleven = ten.add(one);
        BigInteger result = Projo.unwrap(eleven);
        assertEquals(new ExtendedInteger("11"), result);
    }

    @Test
    public void integerSubtract()
    {
        Integer<?> one = Projo.delegate(Integer.class, ExtendedInteger.ONE, mapping);
        Integer<?> ten = Projo.delegate(Integer.class, ExtendedInteger.TEN, mapping);
        Integer<?> nine = ten.subtract(one);
        ExtendedInteger result = Projo.unwrap(nine);
        assertEquals(new ExtendedInteger("9"), result);
    }

    @Test
    public void justFlows()
    {
        Integer<?> one = Projo.delegate(Integer.class, ExtendedInteger.ONE, mapping);
        Flows<?> flows = Projo.delegate(Flows.class, null, mapping);
        Flow<?, Integer<?>> flow = flows.just(one);
        Integer<?> first = flow.blockingFirst();
        ExtendedInteger result = Projo.unwrap(first);
        assertEquals(ExtendedInteger.ONE, result);
    }

    @Test
    public void skipFlow()
    {
        Natural<?> one = Projo.delegate(Natural.class, ExtendedInteger.ONE, mapping);
        Natural<?> ten = Projo.delegate(Natural.class, ExtendedInteger.TEN, mapping);
        Flows<?> flows = Projo.delegate(Flows.class, null, mapping);
        Flow<?, Natural<?>> flow = flows.just(one, ten);
        Flow<?, Natural<?>> skipped = flow.skip(one);
        Natural<?> first = skipped.blockingFirst();
        ExtendedInteger result = Projo.unwrap(first);
        assertEquals(ExtendedInteger.TEN, result);
    }
    
    @Test
    public void fromIterable()
    {
        Natural<?> zero = Projo.delegate(Natural.class, ExtendedInteger.ZERO, mapping);
        Natural<?> one = Projo.delegate(Natural.class, ExtendedInteger.ONE, mapping);
        Natural<?> ten = Projo.delegate(Natural.class, ExtendedInteger.TEN, mapping);
        Flows<?> flows = Projo.delegate(Flows.class, null, mapping);
        Iterable<Natural<?>> iterable = Arrays.asList(zero, one, ten);
        Flow<?, Natural<?>> flow = flows.fromIterable(iterable);
        Flow<?, Natural<?>> skipped = flow.skip(one);
        Natural<?> first = skipped.blockingFirst();
        ExtendedInteger result = Projo.unwrap(first);
        assertEquals(ExtendedInteger.ONE, result);
    }

    @Test
    public void filterFlow()
    {
        Natural<?> zero = Projo.delegate(Natural.class, ExtendedInteger.ZERO, mapping);
        Natural<?> one = Projo.delegate(Natural.class, ExtendedInteger.ONE, mapping);
        Natural<?> ten = Projo.delegate(Natural.class, ExtendedInteger.TEN, mapping);
        io.reactivex.rxjava3.functions.Predicate<Natural<?>> rxPredicate = natural ->
        {
            System.err.println("predicate***");
            @SuppressWarnings({"unchecked", "rawtypes"})
            Boolean<?> result = ((Natural)natural).lessThan(one);
            System.err.println("***result=" + Arrays.asList(result.getClass().getInterfaces()));
            return result instanceof AbstractTypeMappingTest.True;
        };
        Predicate<?, Natural<?>> filter = Projo.delegate(Predicate.class, rxPredicate, mapping);
        Flows<?> flows = Projo.delegate(Flows.class, null, mapping);
        Iterable<Natural<?>> iterable = Arrays.asList(ten, one, zero);
        Flow<?, Natural<?>> flow = flows.fromIterable(iterable);
        @SuppressWarnings({"unchecked", "rawtypes"})
        Predicate<?, Natural<?>> predicate = new Predicate()
        {
            @Override
            public Boolean<?> test(Object value)
            {
                Comparable natural = (Natural<?>)value;
                return natural.lessThan(one);
            }
        };
        Flow<?, Natural<?>> filtered = flow.filter(filter);//predicate);
        Natural<?> first = filtered.blockingFirst();
        ExtendedInteger result = Projo.unwrap(first);
        assertEquals(ExtendedInteger.ZERO, result);
    }
}
