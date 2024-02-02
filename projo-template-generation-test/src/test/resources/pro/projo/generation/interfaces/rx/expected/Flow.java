//                                                                          //
// Copyright 2019 - 2024 Mirko Raner                                        //
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
package pro.projo.generation.interfaces.test.rx;
/* */
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Generated;
/* */
/**
*
* This interface was extracted from io.reactivex.Flowable.
*
**/
/* */
@Generated("pro.projo.generation.interfaces.InterfaceTemplateProcessor")
/* */
public interface Flow<T> extends FlowSource<T>
{
/* */
    Flow<T> ambWith(FlowSource<? extends T> other);
    T blockingFirst();
    T blockingFirst(T defaultItem);
    T blockingLast();
    T blockingLast(T defaultItem);
    T blockingSingle();
    T blockingSingle(T defaultItem);
    Flow<T> cache();
    Flow<T> cacheWithInitialCapacity(Natural initialCapacity);
    <R> Flow<R> concatMap(Function<? super T, ? extends FlowSource<? extends R>> mapper);
    <R> Flow<R> concatMap(Function<? super T, ? extends FlowSource<? extends R>> mapper, Natural prefetch);
    <R> Flow<R> concatMapDelayError(Function<? super T, ? extends FlowSource<? extends R>> mapper);
    <R> Flow<R> concatMapEager(Function<? super T, ? extends FlowSource<? extends R>> mapper);
    <R> Flow<R> concatMapEager(Function<? super T, ? extends FlowSource<? extends R>> mapper, Natural maxConcurrency, Natural prefetch);
    Flow<T> concatWith(FlowSource<? extends T> other);
    <U> Flow<T> debounce(Function<? super T, ? extends FlowSource<U>> debounceIndicator);
    Flow<T> defaultIfEmpty(T defaultItem);
    <U> Flow<T> delay(Function<? super T, ? extends FlowSource<U>> itemDelayIndicator);
    <U, V> Flow<T> delay(FlowSource<U> subscriptionIndicator, Function<? super T, ? extends FlowSource<V>> itemDelayIndicator);
    <U> Flow<T> delaySubscription(FlowSource<U> subscriptionIndicator);
    <T2> Flow<T2> dematerialize();
    Flow<T> distinct();
    <K> Flow<T> distinct(Function<? super T, K> keySelector);
    Flow<T> distinctUntilChanged();
    <K> Flow<T> distinctUntilChanged(Function<? super T, K> keySelector);
    Flow<T> filter(Predicate<? super T> predicate);
    <R> Flow<R> flatMap(Function<? super T, ? extends FlowSource<? extends R>> mapper);
    <R> Flow<R> flatMap(Function<? super T, ? extends FlowSource<? extends R>> mapper, Natural maxConcurrency);
    <U, R> Flow<R> flatMap(Function<? super T, ? extends FlowSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner);
    <U, R> Flow<R> flatMap(Function<? super T, ? extends FlowSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner, Natural maxConcurrency);
    <TRight, TLeftEnd, TRightEnd, R> Flow<R> groupJoin(FlowSource<? extends TRight> other, Function<? super T, ? extends FlowSource<TLeftEnd>> leftEnd, Function<? super TRight, ? extends FlowSource<TRightEnd>> rightEnd, BiFunction<? super T, ? super Flow<TRight>, ? extends R> resultSelector);
    Flow<T> hide();
    <TRight, TLeftEnd, TRightEnd, R> Flow<R> join(FlowSource<? extends TRight> other, Function<? super T, ? extends FlowSource<TLeftEnd>> leftEnd, Function<? super TRight, ? extends FlowSource<TRightEnd>> rightEnd, BiFunction<? super T, ? super TRight, ? extends R> resultSelector);
    Flow<T> limit(Natural count);
    <R> Flow<R> map(Function<? super T, ? extends R> mapper);
    Flow<T> mergeWith(FlowSource<? extends T> other);
    Flow<T> onBackpressureBuffer();
    Flow<T> onBackpressureBuffer(Natural capacity);
    Flow<T> onBackpressureDrop();
    Flow<T> onBackpressureLatest();
    Flow<T> onErrorResumeNext(FlowSource<? extends T> next);
    Flow<T> onErrorReturnItem(T item);
    Flow<T> onExceptionResumeNext(FlowSource<? extends T> next);
    Flow<T> onTerminateDetach();
    <R> Flow<R> publish(Function<? super Flow<T>, ? extends FlowSource<R>> selector);
    <R> Flow<R> publish(Function<? super Flow<T>, ? extends FlowSource<? extends R>> selector, Natural prefetch);
    Flow<T> rebatchRequests(Natural n);
    Flow<T> repeat();
    Flow<T> repeat(Natural times);
    <R> Flow<R> replay(Function<? super Flow<T>, ? extends FlowSource<R>> selector);
    <R> Flow<R> replay(Function<? super Flow<T>, ? extends FlowSource<R>> selector, Natural bufferSize);
    Flow<T> retry();
    Flow<T> retry(Natural count);
    <U> Flow<T> sample(FlowSource<U> sampler);
    Flow<T> scan(BiFunction<T, T, T> accumulator);
    <R> Flow<R> scan(R initialValue, BiFunction<R, ? super T, R> accumulator);
    Flow<T> serialize();
    Flow<T> share();
    Flow<T> skip(Natural count);
    Flow<T> skipLast(Natural count);
    <U> Flow<T> skipUntil(FlowSource<U> other);
    Flow<T> skipWhile(Predicate<? super T> predicate);
    Flow<T> sorted();
    Flow<T> startWith(FlowSource<? extends T> other);
    Flow<T> startWith(T value);
    <E> E subscribeWith(E subscriber);
    Flow<T> switchIfEmpty(FlowSource<? extends T> other);
    <R> Flow<R> switchMap(Function<? super T, ? extends FlowSource<? extends R>> mapper);
    <R> Flow<R> switchMap(Function<? super T, ? extends FlowSource<? extends R>> mapper, Natural bufferSize);
    <R> Flow<R> switchMapDelayError(Function<? super T, ? extends FlowSource<? extends R>> mapper);
    <R> Flow<R> switchMapDelayError(Function<? super T, ? extends FlowSource<? extends R>> mapper, Natural bufferSize);
    Flow<T> take(Natural count);
    Flow<T> takeLast(Natural count);
    Flow<T> takeUntil(Predicate<? super T> stopPredicate);
    <U> Flow<T> takeUntil(FlowSource<U> other);
    Flow<T> takeWhile(Predicate<? super T> predicate);
    <V> Flow<T> timeout(Function<? super T, ? extends FlowSource<V>> itemTimeoutIndicator);
    <V> Flow<T> timeout(Function<? super T, ? extends FlowSource<V>> itemTimeoutIndicator, Flow<? extends T> other);
    <U, V> Flow<T> timeout(FlowSource<U> firstTimeoutIndicator, Function<? super T, ? extends FlowSource<V>> itemTimeoutIndicator);
    <U, V> Flow<T> timeout(FlowSource<U> firstTimeoutIndicator, Function<? super T, ? extends FlowSource<V>> itemTimeoutIndicator, FlowSource<? extends T> other);
    <R> R to(Function<? super Flow<T>, R> converter);
    Flow<Flow<T>> window(Natural count);
    Flow<Flow<T>> window(Natural count, Natural skip);
    Flow<Flow<T>> window(Natural count, Natural skip, Natural bufferSize);
    <B> Flow<Flow<T>> window(FlowSource<B> boundaryIndicator);
    <B> Flow<Flow<T>> window(FlowSource<B> boundaryIndicator, Natural bufferSize);
    <U, V> Flow<Flow<T>> window(FlowSource<U> openingIndicator, Function<? super U, ? extends FlowSource<V>> closingIndicator);
    <U, V> Flow<Flow<T>> window(FlowSource<U> openingIndicator, Function<? super U, ? extends FlowSource<V>> closingIndicator, Natural bufferSize);
    <U, R> Flow<R> withLatestFrom(FlowSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> combiner);
    <U, R> Flow<R> zipWith(FlowSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper);
/* */
}
