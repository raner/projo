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
public interface Flows
{
/* */
    <T> Flow<T> amb(Iterable<? extends FlowSource<? extends T>> sources);
    <T1, T2, R> Flow<R> combineLatest(FlowSource<? extends T1> source1, FlowSource<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends R> combiner);
    <T> Flow<T> concat(Iterable<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> concat(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> concat(FlowSource<? extends T> source1, FlowSource<? extends T> source2);
    <T> Flow<T> concat(FlowSource<? extends T> source1, FlowSource<? extends T> source2, FlowSource<? extends T> source3);
    <T> Flow<T> concat(FlowSource<? extends T> source1, FlowSource<? extends T> source2, FlowSource<? extends T> source3, FlowSource<? extends T> source4);
    <T> Flow<T> concatDelayError(Iterable<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> concatDelayError(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> concatEager(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> concatEager(Iterable<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> empty();
    <T> Flow<T> fromIterable(Iterable<? extends T> source);
    <T> Flow<T> fromPublisher(FlowSource<? extends T> source);
    <T> Flow<T> just(T item);
    <T> Flow<T> just(T item1, T item2);
    <T> Flow<T> just(T item1, T item2, T item3);
    <T> Flow<T> just(T item1, T item2, T item3, T item4);
    <T> Flow<T> just(T item1, T item2, T item3, T item4, T item5);
    <T> Flow<T> just(T item1, T item2, T item3, T item4, T item5, T item6);
    <T> Flow<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7);
    <T> Flow<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8);
    <T> Flow<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9);
    <T> Flow<T> just(T item1, T item2, T item3, T item4, T item5, T item6, T item7, T item8, T item9, T item10);
    <T> Flow<T> merge(Iterable<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> merge(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> merge(FlowSource<? extends T> source1, FlowSource<? extends T> source2);
    <T> Flow<T> merge(FlowSource<? extends T> source1, FlowSource<? extends T> source2, FlowSource<? extends T> source3);
    <T> Flow<T> merge(FlowSource<? extends T> source1, FlowSource<? extends T> source2, FlowSource<? extends T> source3, FlowSource<? extends T> source4);
    <T> Flow<T> mergeDelayError(Iterable<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> mergeDelayError(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> mergeDelayError(FlowSource<? extends T> source1, FlowSource<? extends T> source2);
    <T> Flow<T> mergeDelayError(FlowSource<? extends T> source1, FlowSource<? extends T> source2, FlowSource<? extends T> source3);
    <T> Flow<T> mergeDelayError(FlowSource<? extends T> source1, FlowSource<? extends T> source2, FlowSource<? extends T> source3, FlowSource<? extends T> source4);
    <T> Flow<T> never();
    <T> Flow<T> switchOnNext(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> switchOnNextDelayError(FlowSource<? extends FlowSource<? extends T>> sources);
    <T> Flow<T> unsafeCreate(FlowSource<T> onSubscribe);
    <T1, T2, R> Flow<R> zip(FlowSource<? extends T1> source1, FlowSource<? extends T2> source2, BiFunction<? super T1, ? super T2, ? extends R> zipper);
/* */
}
