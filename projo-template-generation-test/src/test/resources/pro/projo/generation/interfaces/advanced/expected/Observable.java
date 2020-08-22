//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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
package pro.projo.generation.interfaces.test.advanced;
/* */
import javax.annotation.Generated;
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
    Observable<T> ambWith(ObservableSource<? extends T> other);
    T blockingFirst();
    T blockingFirst(T defaultItem);
    T blockingLast();
    T blockingLast(T defaultItem);
    T blockingSingle();
    T blockingSingle(T defaultItem);
    Observable<T> cache();
    Observable<T> concatWith(ObservableSource<? extends T> other);
    Observable<T> defaultIfEmpty(T defaultItem);
    <U> Observable<T> delaySubscription(ObservableSource<U> other);
    <T2> Observable<T2> dematerialize();
    Observable<T> distinct();
    Observable<T> distinctUntilChanged();
    Observable<T> hide();
    Observable<T> mergeWith(ObservableSource<? extends T> other);
    Observable<T> onErrorResumeNext(ObservableSource<? extends T> next);
    Observable<T> onErrorReturnItem(T item);
    Observable<T> onExceptionResumeNext(ObservableSource<? extends T> next);
    Observable<T> onTerminateDetach();
    Observable<T> repeat();
    Observable<T> retry();
    <U> Observable<T> sample(ObservableSource<U> sampler);
    Observable<T> serialize();
    Observable<T> share();
    <U> Observable<T> skipUntil(ObservableSource<U> other);
    Observable<T> sorted();
    Observable<T> startWith(ObservableSource<? extends T> other);
    Observable<T> startWith(T item);
    Observable<T> startWithArray(T[] items);
    <E> E subscribeWith(E observer);
    Observable<T> switchIfEmpty(ObservableSource<? extends T> other);
    <U> Observable<T> takeUntil(ObservableSource<U> other);
    <B> Observable<Observable<T>> window(ObservableSource<B> boundary);
/* */
}
