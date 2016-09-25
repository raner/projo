package pro.projo;

import java.util.function.Function;
import pro.projo.internal.ProjoInvocationHandler;
import static java.lang.reflect.Proxy.newProxyInstance;

public class Projo
{
    public static class Intermediate<_Artifact_>
    {
        private Class<_Artifact_> type;

        public Intermediate(Class<_Artifact_> type)
        {
            this.type = type;
        }

        public <_First_, _Second_> pro.projo.doubles.Factory<_Artifact_, _First_, _Second_> with(Function<_Artifact_, _First_> first, Function<_Artifact_, _Second_> second)
        {
            return (argument1, argument2) ->
            {
                Class<?>[] interfaces = {type};
                ClassLoader loader = Projo.class.getClassLoader();
                ProjoInvocationHandler<_Artifact_> handler = new ProjoInvocationHandler<>();
                @SuppressWarnings("unchecked")
                _Artifact_ instance = (_Artifact_)newProxyInstance(loader, interfaces, handler);
                return handler.initialize(instance).members(first, second).with(argument1, argument2);
            };
        }
    }

    public static <_Artifact_> Intermediate<_Artifact_> creates(Class<_Artifact_> type)
    {
        return new Intermediate<>(type);
    }

    public static <_Artifact_> _Artifact_ create(Class<_Artifact_> type)
    {
        Class<?>[] interfaces = {type};
        ClassLoader loader = Projo.class.getClassLoader();
        ProjoInvocationHandler<_Artifact_> handler = new ProjoInvocationHandler<>();
        @SuppressWarnings("unchecked")
        _Artifact_ instance = (_Artifact_)newProxyInstance(loader, interfaces, handler);
        return handler.initialize(instance).members().with();
        // TODO: ^^^ code duplication
    }
}
