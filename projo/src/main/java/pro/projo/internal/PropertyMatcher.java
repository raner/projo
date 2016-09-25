package pro.projo.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyMatcher
{
    private final Pattern pattern = Pattern.compile("(?:[gs]et)??([A-Z][A-Za-z0-9]*)");
    public String propertyName(String methodName)
    {
        Matcher matcher = pattern.matcher(methodName);
        if (matcher.matches())
        {
            return matcher.group(1);
        }
        return methodName;
    }
}
