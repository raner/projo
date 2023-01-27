//                                                                          //
// Copyright 2019 - 2023 Mirko Raner                                        //
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
package pro.projo.generation.interfaces.test;
/* */
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;
import org.reactivestreams.Publisher;
import io.reactivex.Emitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
/* */
/**
*
* This interface was extracted from io.reactivex.Observable.
*
**/
/* */
@Generated("pro.projo.generation.interfaces.InterfaceTemplateProcessor")
/* */
public interface Observables
{
/* */
    <T> Observable<T> amb(Iterable<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> ambArray(ObservableSource<? extends T>[] sources);
    int bufferSize();
    <T, R> Observable<R> combineLatest(Function<? super Object[], ? extends R> combiner, int bufferSize, ObservableSource<? extends T>[] sources);
    <T, R> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner);
    <T, R> Observable<R> combineLatest(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner, int bufferSize);
    <T, R> Observable<R> combineLatest(ObservableSource<? extends T>[] sources, Function<? super Object[], ? extends R> combiner);
    <T, R> Observable<R> combineLatest(ObservableSource<? extends T>[] sources, Function<? super Object[], ? extends R> combiner, int bufferSize);
    <T1, T2, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends R> combiner);
    <T1, T2, T3, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, Function3<? super T1, ? super T2, ? super T3, ? extends R> combiner);
    <T1, T2, T3, T4, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> combiner);
    <T1, T2, T3, T4, T5, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> combiner);
    <T1, T2, T3, T4, T5, T6, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> combiner);
    <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, ObservableSource<? extends T7> source7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> combiner);
    <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> combiner);
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> combineLatest(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8, ObservableSource<? extends T9> source9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> combiner);
    <T, R> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] sources, Function<? super Object[], ? extends R> combiner);
    <T, R> Observable<R> combineLatestDelayError(Function<? super Object[], ? extends R> combiner, int bufferSize, ObservableSource<? extends T>[] sources);
    <T, R> Observable<R> combineLatestDelayError(ObservableSource<? extends T>[] sources, Function<? super Object[], ? extends R> combiner, int bufferSize);
    <T, R> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner);
    <T, R> Observable<R> combineLatestDelayError(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> combiner, int bufferSize);
    <T> Observable<T> concat(Iterable<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> sources, int prefetch);
    <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3);
    <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3, ObservableSource<? extends T> source4);
    <T> Observable<T> concatArray(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatArrayDelayError(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatArrayEager(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatArrayEager(int maxConcurrency, int prefetch, ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatDelayError(Iterable<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources, int prefetch, boolean tillTheEnd);
    <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int prefetch);
    <T> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concatEager(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int prefetch);
    <T> Observable<T> create(ObservableOnSubscribe<T> source);
    <T> Observable<T> defer(Callable<? extends ObservableSource<? extends T>> supplier);
    <T> Observable<T> empty();
    <T> Observable<T> error(Callable<? extends Throwable> errorSupplier);
    <T> Observable<T> error(Throwable exception);
    <T> Observable<T> fromArray(T[] items);
    <T> Observable<T> fromCallable(Callable<? extends T> supplier);
    <T> Observable<T> fromFuture(Future<? extends T> future);
    <T> Observable<T> fromFuture(Future<? extends T> future, long timeout, TimeUnit unit);
    <T> Observable<T> fromFuture(Future<? extends T> future, long timeout, TimeUnit unit, Scheduler scheduler);
    <T> Observable<T> fromFuture(Future<? extends T> future, Scheduler scheduler);
    <T> Observable<T> fromIterable(Iterable<? extends T> source);
    <T> Observable<T> fromPublisher(Publisher<? extends T> publisher);
    <T> Observable<T> generate(Consumer<Emitter<T>> generator);
    <T, S> Observable<T> generate(Callable<S> initialState, BiConsumer<S, Emitter<T>> generator);
    <T, S> Observable<T> generate(Callable<S> initialState, BiConsumer<S, Emitter<T>> generator, Consumer<? super S> disposeState);
    <T, S> Observable<T> generate(Callable<S> initialState, BiFunction<S, Emitter<T>, S> generator);
    <T, S> Observable<T> generate(Callable<S> initialState, BiFunction<S, Emitter<T>, S> generator, Consumer<? super S> disposeState);
    Observable<Long> interval(long initialDelay, long period, TimeUnit unit);
    Observable<Long> interval(long initialDelay, long period, TimeUnit unit, Scheduler scheduler);
    Observable<Long> interval(long period, TimeUnit unit);
    Observable<Long> interval(long period, TimeUnit unit, Scheduler scheduler);
    Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit);
    Observable<Long> intervalRange(long start, long count, long initialDelay, long period, TimeUnit unit, Scheduler scheduler);
    <T> Observable<T> just(T item);
    <T> Observable<T> just(T item1, T item2);
    <T> Observable<T> just(T item1, T item2, T item3);
    <T> Observable<T> just(T item1, T item2, T item3, T item4);
    <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5);
    <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6);
    <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7);
    <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8);
    <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9);
    <T> Observable<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9, T item10);
    <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize);
    <T> Observable<T> mergeArray(int maxConcurrency, int bufferSize, ObservableSource<? extends T>[] sources);
    <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> merge(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency);
    <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency);
    <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3);
    <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3, ObservableSource<? extends T> source4);
    <T> Observable<T> mergeArray(ObservableSource<? extends T>[] sources);
    <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency, int bufferSize);
    <T> Observable<T> mergeArrayDelayError(int maxConcurrency, int bufferSize, ObservableSource<? extends T>[] sources);
    <T> Observable<T> mergeDelayError(Iterable<? extends ObservableSource<? extends T>> sources, int maxConcurrency);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources, int maxConcurrency);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3, ObservableSource<? extends T> source4);
    <T> Observable<T> mergeArrayDelayError(ObservableSource<? extends T>[] sources);
    <T> Observable<T> never();
    Observable<Integer> range(int start, int count);
    Observable<Long> rangeLong(long start, long count);
    <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, BiPredicate<? super T, ? super T> isEqual);
    <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, BiPredicate<? super T, ? super T> isEqual, int bufferSize);
    <T> Single<Boolean> sequenceEqual(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, int bufferSize);
    <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> sources, int bufferSize);
    <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources, int prefetch);
    Observable<Long> timer(long delay, TimeUnit unit);
    Observable<Long> timer(long delay, TimeUnit unit, Scheduler scheduler);
    <T> Observable<T> unsafeCreate(ObservableSource<T> onSubscribe);
    <T, D> Observable<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier, Consumer<? super D> disposer);
    <T, D> Observable<T> using(Callable<? extends D> resourceSupplier, Function<? super D, ? extends ObservableSource<? extends T>> sourceSupplier, Consumer<? super D> disposer, boolean eager);
    <T> Observable<T> wrap(ObservableSource<T> source);
    <T, R> Observable<R> zip(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper);
    <T, R> Observable<R> zip(ObservableSource<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper);
    <T1, T2, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends R> zipper);
    <T1, T2, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError);
    <T1, T2, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends R> zipper, boolean delayError, int bufferSize);
    <T1, T2, T3, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, Function3<? super T1, ? super T2, ? super T3, ? extends R> zipper);
    <T1, T2, T3, T4, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, Function4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipper);
    <T1, T2, T3, T4, T5, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipper);
    <T1, T2, T3, T4, T5, T6, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipper);
    <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, ObservableSource<? extends T7> source7, Function7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipper);
    <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8, Function8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipper);
    <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> zip(ObservableSource<? extends T1> source1, ObservableSource<? extends T2> source2, ObservableSource<? extends T3> source3, ObservableSource<? extends T4> source4, ObservableSource<? extends T5> source5, ObservableSource<? extends T6> source6, ObservableSource<? extends T7> source7, ObservableSource<? extends T8> source8, ObservableSource<? extends T9> source9, Function9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipper);
    <T, R> Observable<R> zipArray(Function<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize, ObservableSource<? extends T>[] sources);
    <T, R> Observable<R> zipIterable(Iterable<? extends ObservableSource<? extends T>> sources, Function<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize);
/* */
}
