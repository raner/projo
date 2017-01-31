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
package pro.projo.internal.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import pro.projo.Projo;
import pro.projo.internal.Default;
import pro.projo.internal.Predicates;
import pro.projo.internal.ProjoHandler;
import pro.projo.internal.PropertyMatcher;
import static java.lang.System.identityHashCode;
import static java.util.stream.Collectors.joining;
import static pro.projo.Projo.isValueObject;

/**
* The {@link ProxyProjoInvocationHandler} is the {@link InvocationHandler} for Projo objects that are
* implemented by Java proxies. It implements the functionality of getters and setters as well as
* the {@link Object#equals(Object) equals()} and {@link Object#hashCode() hashCode()} methods.
*
* @param <_Artifact_> the type of the Projo object
*
* @author Mirko Raner
**/
public class ProxyProjoInvocationHandler<_Artifact_> extends ProjoHandler<_Artifact_> implements Predicates, InvocationHandler
{
    private static PropertyMatcher matcher = new PropertyMatcher();

    private Map<String, Object> state = new HashMap<>();
    private Class<_Artifact_> reifiedType;
    Function<_Artifact_, ?>[] getters;
    Stack<Object> initializationStack = new Stack<>();
    LinkedHashMap<String, Class<?>> properties = new LinkedHashMap<>();

    InvocationHandler invoker = (Object proxy, Method method, Object... arguments) ->
    {
        String propertyName = matcher.propertyName(method.getName());
        state.put(propertyName, initializationStack.pop());
        properties.put(propertyName, method.getReturnType());

        // Avoid NPEs during auto-unboxing of return values of methods that return a primitive type:
        //
        return Default.VALUES.get(method.getReturnType());
    };

    class Initializer extends ProjoHandler<_Artifact_>.ProjoInitializer
    {
        _Artifact_ instance;

        Initializer(_Artifact_ object)
        {
            this.instance = object;
        }

        @Override
        @SafeVarargs
        public final ProjoMembers members(Function<_Artifact_, ?>... members)
        {
            return new Members(members);
        }

        class Members extends ProjoMembers
        {
            @SafeVarargs
            public Members(Function<_Artifact_, ?>... members)
            {
                getters = members;
            }

            @Override
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
                invoker = ProxyProjoInvocationHandler.this.regularInvoker;
                initializationStack = null;
                return instance;
            }

            @Override
            public _Artifact_ returnInstance()
            {
                return with(new Object[getters.length]);
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
            Object value = state.get(matcher.propertyName(method.getName()));
            return value != null? value:Default.VALUES.get(method.getReturnType());
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
        if (toString.test(method, arguments))
        {
            @SuppressWarnings("unchecked")
            _Artifact_ artifact = (_Artifact_)proxy;
            return toString(artifact);
        }
        throw new NoSuchMethodError(String.valueOf(method));
    };

    public ProxyProjoInvocationHandler(Class<_Artifact_> type)
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

    private String toString(_Artifact_ proxy)
    {
        if (Projo.hasCustomToString(reifiedType))
        {
            String fields = properties.entrySet().stream().map(this::propertyDescription).collect(joining(", "));
            return reifiedType.getName() + "[" + fields + "]";
        }
        return reifiedType.getName() + "@" + Integer.toHexString(proxy.hashCode());
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

    private String propertyDescription(Entry<String, Class<?>> description)
    {
        String property = description.getKey();
        Object rawValue = state.get(property);
        Object value = rawValue != null? rawValue:Default.VALUES.get(description.getValue());
        return property + "=" + (value instanceof String? "\"" + value + "\"":String.valueOf(value));
    }

    @SuppressWarnings("unchecked")
    private _Artifact_ artifact(Object object)
    {
        return reifiedType.isInstance(object)? (_Artifact_)object:null;
    }
}
