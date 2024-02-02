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
public interface Observables
{
/* */
    <T> Observable<T> ambArray(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concat(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3);
    <T> Observable<T> concat(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3, ObservableSource<? extends T> source4);
    <T> Observable<T> concatArray(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatArrayDelayError(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatArrayEager(ObservableSource<? extends T>[] sources);
    <T> Observable<T> concatDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> concatEager(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> empty();
    <T> Observable<T> fromArray(T[] items);
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
    <T> Observable<T> merge(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3);
    <T> Observable<T> merge(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3, ObservableSource<? extends T> source4);
    <T> Observable<T> mergeArray(ObservableSource<? extends T>[] sources);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3);
    <T> Observable<T> mergeDelayError(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2, ObservableSource<? extends T> source3, ObservableSource<? extends T> source4);
    <T> Observable<T> mergeArrayDelayError(ObservableSource<? extends T>[] sources);
    <T> Observable<T> never();
    <T> Observable<T> switchOnNext(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> switchOnNextDelayError(ObservableSource<? extends ObservableSource<? extends T>> sources);
    <T> Observable<T> unsafeCreate(ObservableSource<T> onSubscribe);
    <T> Observable<T> wrap(ObservableSource<T> source);
/* */
}
