//                                                                          //
// Copyright 2021 Mirko Raner                                               //
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
package pro.projo.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.stream.Stream.iterate;

/**
* The {@link PrivateFieldProxy} annotation provides access to inherited private fields by generating
* a proxy that delegates to the underlying private field.
* To initialize the proxy, {@link PrivateFieldProxy.Processor#process()} must be called first.
*
* @author Mirko Raner
**/
@Target(FIELD)
@Retention(RUNTIME)
@interface PrivateFieldProxy
{
  class Processor
  {
    private Object object;
    private List<Field> proxies;

    Processor(Object object)
    {
      this.object = object;
      proxies = Stream.of(object.getClass().getDeclaredFields())
        . filter(it -> Stream.of(it.getAnnotations()).anyMatch(PrivateFieldProxy.class::isInstance))
        . collect(Collectors.toList());
    }

    /**
    * Processes {@link PrivateFieldProxy} annotations on the current object. Annotated fields will
    * be initialized with proxies to inherited private fields of the same name.
    **/
    public void process()
    {
      proxies.stream().forEach((Field field) ->
      {
        field.setAccessible(true);
        Stream<Class<?>> supertypes = takeWhile(iterate(field.getDeclaringClass().getSuperclass(), (Class<?> it) -> it.getSuperclass()), it -> it != null);
        Optional<Class<?>> declaringType = supertypes
          . filter(it -> Stream.of(it.getDeclaredFields()).anyMatch(match -> match.getName().equals(field.getName()))).findFirst();
        declaringType.ifPresent((Class<?> type) ->
        {
          Field targetField = Stream.of(type.getDeclaredFields()).filter(it -> it.getName().equals(field.getName())).findFirst().get();
          targetField.setAccessible(true);
          try
          {
            Object privateFieldValue = targetField.get(object);
            InvocationHandler handler = (Object proxy, Method method, Object[] args) ->
            {
              Method targetMethod = findMethod(privateFieldValue.getClass(), method.getName(), method.getParameterTypes());
              targetMethod.setAccessible(true);
              return targetMethod.invoke(privateFieldValue, args);
            };
            Object proxy = Proxy.newProxyInstance(object.getClass().getClassLoader(), new Class<?>[] {field.getType()}, handler);
            field.set(object, proxy);
          }
          catch (IllegalAccessException illegalAccess)
          {
            throw new IllegalAccessError(illegalAccess.getMessage());
          }
        });
      });
    }

    Method findMethod(Class<?> type, String name, Class<?>... parameterTypes)
    {
      Stream<Class<?>> classHierarchy = takeWhile(iterate(type, (Class<?> it) -> it.getSuperclass()), it -> it != null);
      Stream<Optional<Method>> methodCandidates = classHierarchy.map(it -> findDeclaredMethod(it, name, parameterTypes));
      return methodCandidates.filter(Optional::isPresent).findFirst().get().get();
    }

    Optional<Method> findDeclaredMethod(Class<?> type, String name, Class<?>... parameterTypes)
    {
      try
      {
        return Optional.of(type.getDeclaredMethod(name, parameterTypes));
      }
      catch (NoSuchMethodException noSuchMethod)
      {
        return Optional.empty();
      }
    }

    /**
     * Java 8 implementation of {@code takeWhile} method copied from
     * <a href="https://stackoverflow.com/questions/20746429#answer-46446546">https://stackoverflow.com/questions/20746429#answer-46446546</a>.
     */
    static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> p)
    {
      class Taking extends Spliterators.AbstractSpliterator<T> implements Consumer<T>
      {
        private static final int CANCEL_CHECK_COUNT = 63;
        private final Spliterator<T> s;
        private int count;
        private T t;
        private final AtomicBoolean cancel = new AtomicBoolean();
        private boolean takeOrDrop = true;
  
        Taking(Spliterator<T> s)
        {
          super(s.estimateSize(), s.characteristics() & ~(Spliterator.SIZED | Spliterator.SUBSIZED));
          this.s = s;
        }
  
        @Override
        public boolean tryAdvance(Consumer<? super T> action)
        {
          boolean test = true;
          if (takeOrDrop &&         // If can take
              (count != 0 || !cancel.get()) && // and if not cancelled
              s.tryAdvance(this) &&   // and if advanced one element
              (test = p.test(t)))
          {   // and test on element passes
            action.accept(t);       // then accept element
            return true;
          }
          else
          {
            // Taking is finished
            takeOrDrop = false;
            // Cancel all further traversal and splitting operations
            // only if test of element failed (short-circuited)
            if (!test)
              cancel.set(true);
            return false;
          }
        }
  
        @Override
        public Comparator<? super T> getComparator()
        {
          return s.getComparator();
        }
  
        @Override
        public void accept(T t)
        {
          count = (count + 1) & CANCEL_CHECK_COUNT;
          this.t = t;
        }
  
        @Override
        public Spliterator<T> trySplit()
        {
          return null;
        }
      }
      return StreamSupport.stream(new Taking(stream.spliterator()), stream.isParallel()).onClose(stream::close);
    }
  }
}