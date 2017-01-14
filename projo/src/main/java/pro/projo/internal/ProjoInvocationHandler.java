//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import static java.lang.System.identityHashCode;
import static pro.projo.Projo.isValueObject;

/**
* The {@link ProjoInvocationHandler} is the {@link InvocationHandler} for Projo objects that are
* implemented by Java proxies. It implements the functionality of getters and setters as well as
* the {@link Object#equals(Object) equals()} and {@link Object#hashCode() hashCode()} methods.
*
* @param <_Artifact_> the type of the Projo object
*
* @author Mirko Raner
**/
public class ProjoInvocationHandler<_Artifact_> implements InvocationHandler
{
    private static Map<Class<?>, Object> DEFAULTS = new HashMap<>();
    private static PropertyMatcher matcher = new PropertyMatcher();

    public static BiPredicate<Method, Object[]> equals = (method, arguments) -> method.getName().equals("equals")
        && method.getParameterCount() == 1
        && method.getParameterTypes()[0] == Object.class
        && method.getReturnType() == boolean.class;
    public static BiPredicate<Method, Object[]> hashCode = (method, arguments) -> method.getName().equals("hashCode")
        && method.getParameterCount() == 0;
    public static BiPredicate<Method, Object[]> getter = (method, arguments) -> (arguments == null || arguments.length == 0)
        && !hashCode.test(method, arguments);
    public static BiPredicate<Method, Object[]> setter = (method, arguments) -> (arguments != null && arguments.length == 1)
        && !equals.test(method, arguments);

    private Map<String, Object> state = new HashMap<>();
    private Class<_Artifact_> reifiedType;
    Function<_Artifact_, ?>[] getters;
    Stack<Object> initializationStack = new Stack<>();
    InvocationHandler invoker = (Object proxy, Method method, Object... arguments) ->
    {
        state.put(matcher.propertyName(method.getName()), initializationStack.pop());

        // Avoid NPEs during auto-unboxing of return values of methods that return a primitive type:
        //
        return DEFAULTS.get(method.getReturnType());
    };

    static
    {
        DEFAULTS.put(int.class, 0);
        DEFAULTS.put(long.class, 0L);
        DEFAULTS.put(float.class, 0F);
        DEFAULTS.put(double.class, 0D);
        DEFAULTS.put(byte.class, (byte)0);
        DEFAULTS.put(short.class, (short)0);
        DEFAULTS.put(boolean.class, false);
        DEFAULTS.put(char.class, '\0');
    }

    public class Initializer
    {
        _Artifact_ instance;

        Initializer(_Artifact_ object)
        {
            this.instance = object;
        }
 
        public class Members
        {
            @SafeVarargs
            public Members(Function<_Artifact_, ?>... members)
            {
                getters = members;
            }

            public _Artifact_ with(Object... values)
            {
                Iterator<Function<_Artifact_, ?>> members = Arrays.asList(getters).iterator();
                for (Object value: values)
                {
                    initializationStack.push(value);
                    members.next().apply(instance);
                }
                if (members.hasNext() || !initializationStack.isEmpty())
                {
                    throw new IllegalStateException();
                }
                return returnInstance();
            }

            public _Artifact_ returnInstance()
            {
                invoker = ProjoInvocationHandler.this.regularInvoker;
                initializationStack = null;
                return instance;
            }
        }
    }

    InvocationHandler regularInvoker = (Object proxy, Method method, Object... arguments) ->
    {
        if (setter.test(method, arguments))
        {
            return state.put(matcher.propertyName(method.getName()), arguments[0]);
        }
        if (getter.test(method, arguments))
        {
            return state.get(matcher.propertyName(method.getName()));
        }
        if (equals.test(method, arguments))
        {
            return isValueObject(reifiedType)? isEqual(artifact(proxy), artifact(arguments[0])):proxy == arguments[0];
        }
        if (hashCode.test(method, arguments))
        {
            @SuppressWarnings("unchecked")
            _Artifact_ artifact = (_Artifact_)proxy;
            return isValueObject(reifiedType)? hashCode(artifact):identityHashCode(artifact);
        }
        throw new NoSuchMethodError(String.valueOf(method));
    };

    public ProjoInvocationHandler(Class<_Artifact_> type)
    {
        reifiedType = type;
    }

    public Initializer initialize(_Artifact_ artifact)
    {
        return new Initializer(artifact);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
    {
        return invoker.invoke(proxy, method, arguments);
    }

    private int hashCode(_Artifact_ proxy)
    {
        return Objects.hash(Stream.of(getters).map(method -> method.apply(proxy)).toArray(Object[]::new));
    }

    private boolean isEqual(_Artifact_ proxy, _Artifact_ object)
    {
        Predicate<Function<_Artifact_, ?>> equal = member -> equalOrBothNull(member.apply(proxy), member.apply(object));
        return reifiedType.isInstance(object) && Stream.of(getters).allMatch(equal);
    }

    private boolean equalOrBothNull(Object one, Object two)
    {
        return one == null? two == null:one.equals(two);
    }

    @SuppressWarnings("unchecked")
    private _Artifact_ artifact(Object object)
    {
        return reifiedType.isInstance(object)? (_Artifact_)object:null;
    }
}
