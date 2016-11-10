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

public class ProjoInvocationHandler<_Artifact_> implements InvocationHandler
{
    private static Map<Class<?>, Object> DEFAULTS = new HashMap<>();
    private static PropertyMatcher matcher = new PropertyMatcher();

    private Map<String, Object> state = new HashMap<>();
    Stack<Object> initializationStack = new Stack<>();
    BiFunction<Method, Object[], Object> invoker = this::initializationInvoker;

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
                invoker = ProjoInvocationHandler.this::regularInvoker;
                initializationStack = null;
                return instance;
            }
        }
    }

    private Object initializationInvoker(Method method, @SuppressWarnings("unused") Object... arguments)
    {
        state.put(matcher.propertyName(method.getName()), initializationStack.pop());

        // Avoid NPEs during auto-unboxing of return values of methods that return a primitive type:
        //
        return DEFAULTS.get(method.getReturnType());
    }

    private Object regularInvoker(Method method, Object... arguments)
    {
        if (setter(method, arguments))
        {
            return state.put(matcher.propertyName(method.getName()), arguments[0]);
        }
        if (getter(method, arguments))
        {
            return state.get(matcher.propertyName(method.getName()));
        }
        throw new NoSuchMethodError(String.valueOf(method));
    }

    private boolean getter(@SuppressWarnings("unused") Method method, Object... arguments)
    {
        return arguments == null || arguments.length == 0;
    }

    private boolean setter(@SuppressWarnings("unused") Method method, Object... arguments)
    {
        return arguments != null && arguments.length == 1;
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
