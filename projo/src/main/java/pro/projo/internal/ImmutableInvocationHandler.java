package pro.projo.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ImmutableInvocationHandler<_Artifact_> implements InvocationHandler
{
    Map<Method, Object> state = new HashMap<>();
    Stack<Object> initializationStack = new Stack<>();
    BiFunction<Method, Object[], Object> invoker = (method, arguments) -> state.put(method, initializationStack.pop());

    public class Initializer
    {
        _Artifact_ instance;

        Initializer(_Artifact_ object)
        {
            this.instance = object;
        }

        @SafeVarargs
        public final Members members(Function<_Artifact_, ?>... members)
        {
            return new Members(members);
        }
 
        public class Members
        {
            private Iterator<Function<_Artifact_, ?>> members;

            @SafeVarargs
            Members(Function<_Artifact_, ?>... members)
            {
                this.members = Arrays.asList(members).iterator();
            }

            public _Artifact_ with(Object... values)
            {
                for (Object value: values)
                {
                    initializationStack.push(value);
                    members.next().apply(instance);
                }
                if (members.hasNext() || !initializationStack.isEmpty())
                {
                    throw new IllegalStateException();
                }
                invoker = (method, arguments) -> state.get(method);
                initializationStack = null;
                return instance;
            }
        }
    }

    public Initializer initialize(_Artifact_ artifact)
    {
        return new Initializer(artifact);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable
    {
        return invoker.apply(method, arguments);
    }
}
