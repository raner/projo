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
package pro.projo.internal;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import static java.util.stream.Collectors.toMap;

/**
* The {@link Numbers} class provides utility methods for dealing with {@link Number} objects and
* primitive numbers in the context of Projo.
* While not exactly stateless, all instances of this class will have the identical immutable state.
* Therefore this class can be used and treated as singleton.
*
* @param <_From_> conversion source type (not used externally, can be bound to {@code ?})
* @param <_To_> conversion target type (not used externally, can be bound to {@code ?})
* 
* @author Mirko Raner
**/
public class Numbers<_From_ extends Number, _To_ extends Number> 
{
    private static class Conversion<_From_ extends Number, _To_ extends Number>
    extends SimpleEntry<Entry<Class<_From_>, Class<_To_>>, Function<_From_, _To_>>
    {
        private final static long serialVersionUID = -1773495179933153508L;

        Conversion(Class<_From_> from, Class<_To_> to, Function<_From_, _To_> conversion)
        {
            super(new SimpleEntry<>(from, to), conversion);
        }
    }

    private static class From<_Source_ extends Number>
    {
        class To<_Target_ extends Number>
        {
            class Extract<_Primitive_>
            {
                private Function<_Source_, _Primitive_> extract;

                Extract(Function<_Source_, _Primitive_> extract)
                {
                    this.extract = extract;
                }

                Conversion<_Source_, _Target_> and(Function<_Primitive_, _Target_> wrap)
                {
                    return new Conversion<>(source, target, extract.andThen(wrap));
                }
            }

            Class<_Target_> target;

            To(Class<_Target_> target)
            {
              this.target = target;
            }

            <_Primitive_> Extract<_Primitive_> use(Function<_Source_, _Primitive_> value)
            {
                return new Extract<>(value);
            }
        }

        Class<_Source_> source;

        From(Class<_Source_> source)
        {
            this.source = source;
        }

        <_Target_ extends Number> To<_Target_> to(Class<_Target_> target)
        {
            return new To<>(target);
        }
    }

    /**
     * The following list defines the 19 widening conversions defined in JLS section 5.1.2, for both
     * primitives and wrapper classes as target types. For performance reasons, instead of using
     * reflection, the conversion objects are built with lambda expressions so that at invocation time
     * no reflective method execution is used.
     * Also, after much ado, type safety had to be given up here. Due to Eclipse bug
     * <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=530235">#530235</a> declaring {@code conversions}
     * as {@code List<Conversion<_From_, _To_>>} sadly only works in Eclipse but fails in the Maven build
     * using javac.
     */
    private List<Conversion<? extends Number, ? extends Number>> conversions = Arrays.asList
    (
        from(Byte.class).to(Short.class).use(Byte::shortValue).and(Short::valueOf),
        from(Byte.class).to(short.class).use(Byte::shortValue).and(Short::valueOf),
        from(Byte.class).to(Integer.class).use(Byte::intValue).and(Integer::valueOf),
        from(Byte.class).to(int.class).use(Byte::intValue).and(Integer::valueOf),
        from(Byte.class).to(Long.class).use(Byte::longValue).and(Long::valueOf),
        from(Byte.class).to(long.class).use(Byte::longValue).and(Long::valueOf),
        from(Byte.class).to(Float.class).use(Byte::floatValue).and(Float::valueOf),
        from(Byte.class).to(float.class).use(Byte::floatValue).and(Float::valueOf),
        from(Byte.class).to(Double.class).use(Byte::doubleValue).and(Double::valueOf),
        from(Byte.class).to(double.class).use(Byte::doubleValue).and(Double::valueOf),
        from(Short.class).to(Integer.class).use(Short::intValue).and(Integer::valueOf),
        from(Short.class).to(int.class).use(Short::intValue).and(Integer::valueOf),
        from(Short.class).to(Long.class).use(Short::longValue).and(Long::valueOf),
        from(Short.class).to(long.class).use(Short::longValue).and(Long::valueOf),
        from(Short.class).to(Float.class).use(Short::floatValue).and(Float::valueOf),
        from(Short.class).to(float.class).use(Short::floatValue).and(Float::valueOf),
        from(Short.class).to(Double.class).use(Short::doubleValue).and(Double::valueOf),
        from(Short.class).to(double.class).use(Short::doubleValue).and(Double::valueOf),
        from(Integer.class).to(Float.class).use(Integer::floatValue).and(Float::valueOf),
        from(Integer.class).to(float.class).use(Integer::floatValue).and(Float::valueOf),
        from(Integer.class).to(Double.class).use(Integer::doubleValue).and(Double::valueOf),
        from(Integer.class).to(double.class).use(Integer::doubleValue).and(Double::valueOf),
        from(Integer.class).to(Long.class).use(Integer::longValue).and(Long::valueOf),
        from(Integer.class).to(long.class).use(Integer::longValue).and(Long::valueOf),
        from(Long.class).to(Float.class).use(Long::floatValue).and(Float::valueOf),
        from(Long.class).to(float.class).use(Long::floatValue).and(Float::valueOf),
        from(Long.class).to(Double.class).use(Long::doubleValue).and(Double::valueOf),
        from(Long.class).to(double.class).use(Long::doubleValue).and(Double::valueOf),
        from(Float.class).to(Double.class).use(Float::doubleValue).and(Double::valueOf),
        from(Float.class).to(double.class).use(Float::doubleValue).and(Double::valueOf)
    );

    private Map<Class<?>, Class<?>> wrappers = new HashMap<Class<?>, Class<?>>()
    {
        private final static long serialVersionUID = 6210604569569261687L;
        {
            put(byte.class, Byte.class);
            put(short.class, Short.class);
            put(int.class, Integer.class);
            put(long.class, Long.class);
            put(float.class, Float.class);
            put(double.class, Double.class);
            put(char.class, Character.class);
            put(boolean.class, Boolean.class);
        }
    };

    /**
    * NOTE: The actual correct key type of {@code Entry<Class<? extends Number>, Class<? extends Number>>}
    *       had to be omitted in favor of {@code ?} due to type inference problems during compilation.
    **/
    Map<?, Function<? extends Number, ? extends Number>> map = conversions.stream().collect(toMap(Entry::getKey, Entry::getValue));

    class Cast
    {
        private Number number;

        Cast(Number number)
        {
            this.number = number;
        }

        <_Numeric_ extends Number> _Numeric_ to(Class<_Numeric_> type)
        {
            Function<? extends Number, ? extends Number> function = map.get(new SimpleEntry<>(number.getClass(), type));
            if (function == null)
            {
                throw new IllegalArgumentException(number.getClass() + " cannot be cast to " + type);
            }
            @SuppressWarnings("unchecked")
            Function<Number, ? extends Number> mapper = (Function<Number, ? extends Number>)function;
            @SuppressWarnings("unchecked")
            _Numeric_ result = (_Numeric_)mapper.apply(number);
            return result;
        }
    }

    /**
    * Returns a cast from of the given {@link Number}.
    *
    * @param number the number to be cast
    * @param <_Numeric_> the type of the number to be cast
    * @return a cast object
    **/
    public <_Numeric_ extends Number> Cast cast(_Numeric_ number)
    {
        return new Cast(number);
    }

    /**
    * Returns the wrapper class corresponding to the given primitive class.
    *
    * @param primitive the primitive type
    * @return the corresponding wrapper type (or the same as the input type, if the input type was not
    * in fact a primitive type)
    **/
    public Class<?> getWrapperClass(Class<?> primitive)
    {
        return wrappers.getOrDefault(primitive, primitive);
    }

    private <_Source_ extends Number> From<_Source_> from(Class<_Source_> source)
    {
        return new From<>(source);
    }
}
