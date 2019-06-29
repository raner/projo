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
package pro.projo.generation.interfaces.test;
/* */
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.annotation.Generated;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Notification;
import io.reactivex.ObservableConverter;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Timed;
/* */
/**
*
* This interface was extracted from io.reactivex.Observable.
*
**/
/* */
@Generated("pro.projo.generation.interfaces.InterfaceTemplateProcessor")
/* */
public interface Observable<T> extends ObservableSource<T>
{
/* */
    Single<Boolean> all(Predicate<? super T> predicate);
    Observable<T> ambWith(ObservableSource<? extends T> other);
    Single<Boolean> any(Predicate<? super T> predicate);
    <R> R as(ObservableConverter<T, ? extends R> arg0);
    T blockingFirst();
    T blockingFirst(T defaultItem);
    void blockingForEach(Consumer<? super T> onNext);
    Iterable<T> blockingIterable();
    Iterable<T> blockingIterable(int bufferSize);
    T blockingLast();
    T blockingLast(T defaultItem);
    Iterable<T> blockingLatest();
    Iterable<T> blockingMostRecent(T initialValue);
    Iterable<T> blockingNext();
    T blockingSingle();
    T blockingSingle(T defaultItem);
    Future<T> toFuture();
    void blockingSubscribe();
    void blockingSubscribe(Consumer<? super T> onNext);
    void blockingSubscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError);
    void blockingSubscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete);
    void blockingSubscribe(Observer<? super T> observer);
    Observable<List<T>> buffer(int count);
    Observable<List<T>> buffer(int count, int skip);
    <U> Observable<U> buffer(int count, int skip, Callable<U> bufferSupplier);
    <U> Observable<U> buffer(int count, Callable<U> bufferSupplier);
    Observable<List<T>> buffer(long timespan, long timeskip, TimeUnit unit);
    Observable<List<T>> buffer(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler);
    <U> Observable<U> buffer(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, Callable<U> bufferSupplier);
    Observable<List<T>> buffer(long timespan, TimeUnit unit);
    Observable<List<T>> buffer(long timespan, TimeUnit unit, int count);
    Observable<List<T>> buffer(long timespan, TimeUnit unit, Scheduler scheduler, int count);
    <U> Observable<U> buffer(long timespan, TimeUnit unit, Scheduler scheduler, int count, Callable<U> bufferSupplier, boolean restartTimerOnMaxSize);
    Observable<List<T>> buffer(long timespan, TimeUnit unit, Scheduler scheduler);
    <TOpening, TClosing> Observable<List<T>> buffer(ObservableSource<? extends TOpening> openingIndicator, Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> closingIndicator);
    <TOpening, TClosing, U> Observable<U> buffer(ObservableSource<? extends TOpening> openingIndicator, Function<? super TOpening, ? extends ObservableSource<? extends TClosing>> closingIndicator, Callable<U> bufferSupplier);
    <B> Observable<List<T>> buffer(ObservableSource<B> boundary);
    <B> Observable<List<T>> buffer(ObservableSource<B> boundary, int initialCapacity);
    <B, U> Observable<U> buffer(ObservableSource<B> boundary, Callable<U> bufferSupplier);
    <B> Observable<List<T>> buffer(Callable<? extends ObservableSource<B>> boundarySupplier);
    <B, U> Observable<U> buffer(Callable<? extends ObservableSource<B>> boundarySupplier, Callable<U> bufferSupplier);
    Observable<T> cache();
    Observable<T> cacheWithInitialCapacity(int initialCapacity);
    <U> Observable<U> cast(Class<U> clazz);
    <U> Single<U> collect(Callable<? extends U> initialValueSupplier, BiConsumer<? super U, ? super T> collector);
    <U> Single<U> collectInto(U initialValue, BiConsumer<? super U, ? super T> collector);
    <R> Observable<R> compose(ObservableTransformer<? super T, ? extends R> composer);
    <R> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper);
    <R> Observable<R> concatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int prefetch);
    <R> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper);
    <R> Observable<R> concatMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int prefetch, boolean tillTheEnd);
    <R> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> mapper);
    <R> Observable<R> concatMapEager(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency, int prefetch);
    <R> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean tillTheEnd);
    <R> Observable<R> concatMapEagerDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency, int prefetch, boolean tillTheEnd);
    Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> mapper);
    Completable concatMapCompletable(Function<? super T, ? extends CompletableSource> mapper, int capacityHint);
    Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> mapper);
    Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd);
    Completable concatMapCompletableDelayError(Function<? super T, ? extends CompletableSource> mapper, boolean tillTheEnd, int prefetch);
    <U> Observable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> mapper);
    <U> Observable<U> concatMapIterable(Function<? super T, ? extends Iterable<? extends U>> mapper, int prefetch);
    <R> Observable<R> concatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper);
    <R> Observable<R> concatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper, int prefetch);
    <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> mapper);
    <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd);
    <R> Observable<R> concatMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean tillTheEnd, int prefetch);
    <R> Observable<R> concatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper);
    <R> Observable<R> concatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper, int prefetch);
    <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> mapper);
    <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd);
    <R> Observable<R> concatMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean tillTheEnd, int prefetch);
    Observable<T> concatWith(ObservableSource<? extends T> other);
    Observable<T> concatWith(SingleSource<? extends T> arg0);
    Observable<T> concatWith(MaybeSource<? extends T> arg0);
    Observable<T> concatWith(CompletableSource arg0);
    Single<Boolean> contains(Object element);
    Single<Long> count();
    <U> Observable<T> debounce(Function<? super T, ? extends ObservableSource<U>> debounceSelector);
    Observable<T> debounce(long timeout, TimeUnit unit);
    Observable<T> debounce(long timeout, TimeUnit unit, Scheduler scheduler);
    Observable<T> defaultIfEmpty(T defaultItem);
    <U> Observable<T> delay(Function<? super T, ? extends ObservableSource<U>> itemDelay);
    Observable<T> delay(long delay, TimeUnit unit);
    Observable<T> delay(long delay, TimeUnit unit, boolean delayError);
    Observable<T> delay(long delay, TimeUnit unit, Scheduler scheduler);
    Observable<T> delay(long delay, TimeUnit unit, Scheduler scheduler, boolean delayError);
    <U, V> Observable<T> delay(ObservableSource<U> subscriptionDelay, Function<? super T, ? extends ObservableSource<V>> itemDelay);
    <U> Observable<T> delaySubscription(ObservableSource<U> other);
    Observable<T> delaySubscription(long delay, TimeUnit unit);
    Observable<T> delaySubscription(long delay, TimeUnit unit, Scheduler scheduler);
    <T2> Observable<T2> dematerialize();
    Observable<T> distinct();
    <K> Observable<T> distinct(Function<? super T, K> keySelector);
    <K> Observable<T> distinct(Function<? super T, K> keySelector, Callable<? extends Collection<? super K>> collectionSupplier);
    Observable<T> distinctUntilChanged();
    <K> Observable<T> distinctUntilChanged(Function<? super T, K> keySelector);
    Observable<T> distinctUntilChanged(BiPredicate<? super T, ? super T> comparer);
    Observable<T> doAfterNext(Consumer<? super T> onAfterNext);
    Observable<T> doAfterTerminate(Action onFinally);
    Observable<T> doFinally(Action onFinally);
    Observable<T> doOnDispose(Action onDispose);
    Observable<T> doOnComplete(Action onComplete);
    Observable<T> doOnEach(Consumer<? super Notification<T>> onNotification);
    Observable<T> doOnEach(Observer<? super T> observer);
    Observable<T> doOnError(Consumer<? super Throwable> onError);
    Observable<T> doOnLifecycle(Consumer<? super Disposable> onSubscribe, Action onDispose);
    Observable<T> doOnNext(Consumer<? super T> onNext);
    Observable<T> doOnSubscribe(Consumer<? super Disposable> onSubscribe);
    Observable<T> doOnTerminate(Action onTerminate);
    Maybe<T> elementAt(long index);
    Single<T> elementAt(long index, T defaultItem);
    Single<T> elementAtOrError(long index);
    Observable<T> filter(Predicate<? super T> predicate);
    Maybe<T> firstElement();
    Single<T> first(T defaultItem);
    Single<T> firstOrError();
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper);
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors);
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency);
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize);
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper, Function<? super Throwable, ? extends ObservableSource<? extends R>> onErrorMapper, Callable<? extends ObservableSource<? extends R>> onCompleteSupplier);
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> onNextMapper, Function<Throwable, ? extends ObservableSource<? extends R>> onErrorMapper, Callable<? extends ObservableSource<? extends R>> onCompleteSupplier, int maxConcurrency);
    <R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int maxConcurrency);
    <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> resultSelector);
    <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors);
    <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency);
    <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner, boolean delayErrors, int maxConcurrency, int bufferSize);
    <U, R> Observable<R> flatMap(Function<? super T, ? extends ObservableSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner, int maxConcurrency);
    Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> mapper);
    Completable flatMapCompletable(Function<? super T, ? extends CompletableSource> mapper, boolean delayErrors);
    <U> Observable<U> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> mapper);
    <U, V> Observable<V> flatMapIterable(Function<? super T, ? extends Iterable<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends V> resultSelector);
    <R> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper);
    <R> Observable<R> flatMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> mapper, boolean delayErrors);
    <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper);
    <R> Observable<R> flatMapSingle(Function<? super T, ? extends SingleSource<? extends R>> mapper, boolean delayErrors);
    Disposable forEach(Consumer<? super T> onNext);
    Disposable forEachWhile(Predicate<? super T> onNext);
    Disposable forEachWhile(Predicate<? super T> onNext, Consumer<? super Throwable> onError);
    Disposable forEachWhile(Predicate<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete);
    <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> keySelector);
    <K> Observable<GroupedObservable<K, T>> groupBy(Function<? super T, ? extends K> keySelector, boolean delayError);
    <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector);
    <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, boolean delayError);
    <K, V> Observable<GroupedObservable<K, V>> groupBy(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, boolean delayError, int bufferSize);
    <TRight, TLeftEnd, TRightEnd, R> Observable<R> groupJoin(ObservableSource<? extends TRight> other, Function<? super T, ? extends ObservableSource<TLeftEnd>> leftEnd, Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd, BiFunction<? super T, ? super Observable<TRight>, ? extends R> resultSelector);
    Observable<T> hide();
    Completable ignoreElements();
    Single<Boolean> isEmpty();
    <TRight, TLeftEnd, TRightEnd, R> Observable<R> join(ObservableSource<? extends TRight> other, Function<? super T, ? extends ObservableSource<TLeftEnd>> leftEnd, Function<? super TRight, ? extends ObservableSource<TRightEnd>> rightEnd, BiFunction<? super T, ? super TRight, ? extends R> resultSelector);
    Maybe<T> lastElement();
    Single<T> last(T defaultItem);
    Single<T> lastOrError();
    <R> Observable<R> lift(ObservableOperator<? extends R, ? super T> lifter);
    <R> Observable<R> map(Function<? super T, ? extends R> mapper);
    Observable<Notification<T>> materialize();
    Observable<T> mergeWith(ObservableSource<? extends T> other);
    Observable<T> mergeWith(SingleSource<? extends T> arg0);
    Observable<T> mergeWith(MaybeSource<? extends T> arg0);
    Observable<T> mergeWith(CompletableSource arg0);
    Observable<T> observeOn(Scheduler scheduler);
    Observable<T> observeOn(Scheduler scheduler, boolean delayError);
    Observable<T> observeOn(Scheduler scheduler, boolean delayError, int bufferSize);
    <U> Observable<U> ofType(Class<U> clazz);
    Observable<T> onErrorResumeNext(Function<? super Throwable, ? extends ObservableSource<? extends T>> resumeFunction);
    Observable<T> onErrorResumeNext(ObservableSource<? extends T> next);
    Observable<T> onErrorReturn(Function<? super Throwable, ? extends T> valueSupplier);
    Observable<T> onErrorReturnItem(T item);
    Observable<T> onExceptionResumeNext(ObservableSource<? extends T> next);
    Observable<T> onTerminateDetach();
    ConnectableObservable<T> publish();
    <R> Observable<R> publish(Function<? super Observable<T>, ? extends ObservableSource<R>> selector);
    Maybe<T> reduce(BiFunction<T, T, T> reducer);
    <R> Single<R> reduce(R seed, BiFunction<R, ? super T, R> reducer);
    <R> Single<R> reduceWith(Callable<R> seedSupplier, BiFunction<R, ? super T, R> reducer);
    Observable<T> repeat();
    Observable<T> repeat(long times);
    Observable<T> repeatUntil(BooleanSupplier stop);
    Observable<T> repeatWhen(Function<? super Observable<Object>, ? extends ObservableSource<?>> handler);
    ConnectableObservable<T> replay();
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, long time, TimeUnit unit);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, long time, TimeUnit unit, Scheduler scheduler);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, int bufferSize, Scheduler scheduler);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, long time, TimeUnit unit);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, long time, TimeUnit unit, Scheduler scheduler);
    <R> Observable<R> replay(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, Scheduler scheduler);
    ConnectableObservable<T> replay(int bufferSize);
    ConnectableObservable<T> replay(int bufferSize, long time, TimeUnit unit);
    ConnectableObservable<T> replay(int bufferSize, long time, TimeUnit unit, Scheduler scheduler);
    ConnectableObservable<T> replay(int bufferSize, Scheduler scheduler);
    ConnectableObservable<T> replay(long time, TimeUnit unit);
    ConnectableObservable<T> replay(long time, TimeUnit unit, Scheduler scheduler);
    ConnectableObservable<T> replay(Scheduler scheduler);
    Observable<T> retry();
    Observable<T> retry(BiPredicate<? super Integer, ? super Throwable> predicate);
    Observable<T> retry(long times);
    Observable<T> retry(long times, Predicate<? super Throwable> predicate);
    Observable<T> retry(Predicate<? super Throwable> predicate);
    Observable<T> retryUntil(BooleanSupplier stop);
    Observable<T> retryWhen(Function<? super Observable<Throwable>, ? extends ObservableSource<?>> handler);
    void safeSubscribe(Observer<? super T> s);
    Observable<T> sample(long period, TimeUnit unit);
    Observable<T> sample(long period, TimeUnit unit, boolean emitLast);
    Observable<T> sample(long period, TimeUnit unit, Scheduler scheduler);
    Observable<T> sample(long period, TimeUnit unit, Scheduler scheduler, boolean emitLast);
    <U> Observable<T> sample(ObservableSource<U> sampler);
    <U> Observable<T> sample(ObservableSource<U> sampler, boolean emitLast);
    Observable<T> scan(BiFunction<T, T, T> accumulator);
    <R> Observable<R> scan(R initialValue, BiFunction<R, ? super T, R> accumulator);
    <R> Observable<R> scanWith(Callable<R> seedSupplier, BiFunction<R, ? super T, R> accumulator);
    Observable<T> serialize();
    Observable<T> share();
    Maybe<T> singleElement();
    Single<T> single(T defaultItem);
    Single<T> singleOrError();
    Observable<T> skip(long count);
    Observable<T> skip(long time, TimeUnit unit);
    Observable<T> skip(long time, TimeUnit unit, Scheduler scheduler);
    Observable<T> skipLast(int count);
    Observable<T> skipLast(long time, TimeUnit unit);
    Observable<T> skipLast(long time, TimeUnit unit, boolean delayError);
    Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler);
    Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError);
    Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError, int bufferSize);
    <U> Observable<T> skipUntil(ObservableSource<U> other);
    Observable<T> skipWhile(Predicate<? super T> predicate);
    Observable<T> sorted();
    Observable<T> sorted(Comparator<? super T> sortFunction);
    Observable<T> startWith(Iterable<? extends T> items);
    Observable<T> startWith(ObservableSource<? extends T> other);
    Observable<T> startWith(T item);
    Observable<T> startWithArray(T[] items);
    Disposable subscribe();
    Disposable subscribe(Consumer<? super T> onNext);
    Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError);
    Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete);
    Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe);
    void subscribe(Observer<? super T> observer);
    <E> E subscribeWith(E observer);
    Observable<T> subscribeOn(Scheduler scheduler);
    Observable<T> switchIfEmpty(ObservableSource<? extends T> other);
    <R> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper);
    <R> Observable<R> switchMap(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize);
    Completable switchMapCompletable(Function<? super T, ? extends CompletableSource> arg0);
    Completable switchMapCompletableDelayError(Function<? super T, ? extends CompletableSource> arg0);
    <R> Observable<R> switchMapMaybe(Function<? super T, ? extends MaybeSource<? extends R>> arg0);
    <R> Observable<R> switchMapMaybeDelayError(Function<? super T, ? extends MaybeSource<? extends R>> arg0);
    <R> Observable<R> switchMapSingle(Function<? super T, ? extends SingleSource<? extends R>> arg0);
    <R> Observable<R> switchMapSingleDelayError(Function<? super T, ? extends SingleSource<? extends R>> arg0);
    <R> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper);
    <R> Observable<R> switchMapDelayError(Function<? super T, ? extends ObservableSource<? extends R>> mapper, int bufferSize);
    Observable<T> take(long count);
    Observable<T> take(long time, TimeUnit unit);
    Observable<T> take(long time, TimeUnit unit, Scheduler scheduler);
    Observable<T> takeLast(int count);
    Observable<T> takeLast(long count, long time, TimeUnit unit);
    Observable<T> takeLast(long count, long time, TimeUnit unit, Scheduler scheduler);
    Observable<T> takeLast(long count, long time, TimeUnit unit, Scheduler scheduler, boolean delayError, int bufferSize);
    Observable<T> takeLast(long time, TimeUnit unit);
    Observable<T> takeLast(long time, TimeUnit unit, boolean delayError);
    Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler);
    Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError);
    Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler, boolean delayError, int bufferSize);
    <U> Observable<T> takeUntil(ObservableSource<U> other);
    Observable<T> takeUntil(Predicate<? super T> stopPredicate);
    Observable<T> takeWhile(Predicate<? super T> predicate);
    Observable<T> throttleFirst(long windowDuration, TimeUnit unit);
    Observable<T> throttleFirst(long skipDuration, TimeUnit unit, Scheduler scheduler);
    Observable<T> throttleLast(long intervalDuration, TimeUnit unit);
    Observable<T> throttleLast(long intervalDuration, TimeUnit unit, Scheduler scheduler);
    Observable<T> throttleLatest(long timeout, TimeUnit unit);
    Observable<T> throttleLatest(long timeout, TimeUnit unit, boolean emitLast);
    Observable<T> throttleLatest(long timeout, TimeUnit unit, Scheduler scheduler);
    Observable<T> throttleLatest(long timeout, TimeUnit unit, Scheduler scheduler, boolean emitLast);
    Observable<T> throttleWithTimeout(long timeout, TimeUnit unit);
    Observable<T> throttleWithTimeout(long timeout, TimeUnit unit, Scheduler scheduler);
    Observable<Timed<T>> timeInterval();
    Observable<Timed<T>> timeInterval(Scheduler scheduler);
    Observable<Timed<T>> timeInterval(TimeUnit unit);
    Observable<Timed<T>> timeInterval(TimeUnit unit, Scheduler scheduler);
    <V> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator);
    <V> Observable<T> timeout(Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator, ObservableSource<? extends T> other);
    Observable<T> timeout(long timeout, TimeUnit timeUnit);
    Observable<T> timeout(long timeout, TimeUnit timeUnit, ObservableSource<? extends T> other);
    Observable<T> timeout(long timeout, TimeUnit timeUnit, Scheduler scheduler, ObservableSource<? extends T> other);
    Observable<T> timeout(long timeout, TimeUnit timeUnit, Scheduler scheduler);
    <U, V> Observable<T> timeout(ObservableSource<U> firstTimeoutIndicator, Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator);
    <U, V> Observable<T> timeout(ObservableSource<U> firstTimeoutIndicator, Function<? super T, ? extends ObservableSource<V>> itemTimeoutIndicator, ObservableSource<? extends T> other);
    Observable<Timed<T>> timestamp();
    Observable<Timed<T>> timestamp(Scheduler scheduler);
    Observable<Timed<T>> timestamp(TimeUnit unit);
    Observable<Timed<T>> timestamp(TimeUnit unit, Scheduler scheduler);
    <R> R to(Function<? super Observable<T>, R> converter);
    Single<List<T>> toList();
    Single<List<T>> toList(int capacityHint);
    <U> Single<U> toList(Callable<U> collectionSupplier);
    <K> Single<Map<K, T>> toMap(Function<? super T, ? extends K> keySelector);
    <K, V> Single<Map<K, V>> toMap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector);
    <K, V> Single<Map<K, V>> toMap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, Callable<? extends Map<K, V>> mapSupplier);
    <K> Single<Map<K, Collection<T>>> toMultimap(Function<? super T, ? extends K> keySelector);
    <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector);
    <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, Callable<? extends Map<K, Collection<V>>> mapSupplier, Function<? super K, ? extends Collection<? super V>> collectionFactory);
    <K, V> Single<Map<K, Collection<V>>> toMultimap(Function<? super T, ? extends K> keySelector, Function<? super T, ? extends V> valueSelector, Callable<Map<K, Collection<V>>> mapSupplier);
    Flowable<T> toFlowable(BackpressureStrategy strategy);
    Single<List<T>> toSortedList();
    Single<List<T>> toSortedList(Comparator<? super T> comparator);
    Single<List<T>> toSortedList(Comparator<? super T> comparator, int capacityHint);
    Single<List<T>> toSortedList(int capacityHint);
    Observable<T> unsubscribeOn(Scheduler scheduler);
    Observable<Observable<T>> window(long count);
    Observable<Observable<T>> window(long count, long skip);
    Observable<Observable<T>> window(long count, long skip, int bufferSize);
    Observable<Observable<T>> window(long timespan, long timeskip, TimeUnit unit);
    Observable<Observable<T>> window(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler);
    Observable<Observable<T>> window(long timespan, long timeskip, TimeUnit unit, Scheduler scheduler, int bufferSize);
    Observable<Observable<T>> window(long timespan, TimeUnit unit);
    Observable<Observable<T>> window(long timespan, TimeUnit unit, long count);
    Observable<Observable<T>> window(long timespan, TimeUnit unit, long count, boolean restart);
    Observable<Observable<T>> window(long timespan, TimeUnit unit, Scheduler scheduler);
    Observable<Observable<T>> window(long timespan, TimeUnit unit, Scheduler scheduler, long count);
    Observable<Observable<T>> window(long timespan, TimeUnit unit, Scheduler scheduler, long count, boolean restart);
    Observable<Observable<T>> window(long timespan, TimeUnit unit, Scheduler scheduler, long count, boolean restart, int bufferSize);
    <B> Observable<Observable<T>> window(ObservableSource<B> boundary);
    <B> Observable<Observable<T>> window(ObservableSource<B> boundary, int bufferSize);
    <U, V> Observable<Observable<T>> window(ObservableSource<U> openingIndicator, Function<? super U, ? extends ObservableSource<V>> closingIndicator);
    <U, V> Observable<Observable<T>> window(ObservableSource<U> openingIndicator, Function<? super U, ? extends ObservableSource<V>> closingIndicator, int bufferSize);
    <B> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> boundary);
    <B> Observable<Observable<T>> window(Callable<? extends ObservableSource<B>> boundary, int bufferSize);
    <U, R> Observable<R> withLatestFrom(ObservableSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> combiner);
    <T1, T2, R> Observable<R> withLatestFrom(ObservableSource<T1> o1, ObservableSource<T2> o2, Function3<? super T, ? super T1, ? super T2, R> combiner);
    <T1, T2, T3, R> Observable<R> withLatestFrom(ObservableSource<T1> o1, ObservableSource<T2> o2, ObservableSource<T3> o3, Function4<? super T, ? super T1, ? super T2, ? super T3, R> combiner);
    <T1, T2, T3, T4, R> Observable<R> withLatestFrom(ObservableSource<T1> o1, ObservableSource<T2> o2, ObservableSource<T3> o3, ObservableSource<T4> o4, Function5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> combiner);
    <R> Observable<R> withLatestFrom(ObservableSource<?>[] others, Function<? super Object[], R> combiner);
    <R> Observable<R> withLatestFrom(Iterable<? extends ObservableSource<?>> others, Function<? super Object[], R> combiner);
    <U, R> Observable<R> zipWith(Iterable<U> other, BiFunction<? super T, ? super U, ? extends R> zipper);
    <U, R> Observable<R> zipWith(ObservableSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper);
    <U, R> Observable<R> zipWith(ObservableSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError);
    <U, R> Observable<R> zipWith(ObservableSource<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper, boolean delayError, int bufferSize);
    TestObserver<T> test();
    TestObserver<T> test(boolean dispose);
/* */
}
