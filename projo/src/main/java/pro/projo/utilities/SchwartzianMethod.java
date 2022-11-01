//                                                                          //
// Copyright 2022 Mirko Raner                                               //
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
package pro.projo.utilities;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import static java.lang.reflect.Modifier.ABSTRACT;

/**
* {@link SchwartzianMethod} implements a
* <a href="https://en.wikipedia.org/wiki/Schwartzian_transform">Schwartzian transform</a>
* for {@link Method}s that only takes into account the method name, return type, parameter types
* and whether or not the method is abstract. Methods that are equal within these four attributes
* are considered equal under the Schwartzian transform.
*
* @author Mirko Raner
**/
public class SchwartzianMethod
{
    private Method method;

    public SchwartzianMethod(Method method)
    {
        this.method = method;
    }

    public Method unwrap()
    {
        return method;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof SchwartzianMethod)
        {
            Method otherMethod = ((SchwartzianMethod)other).method;
            return method.getName().equals(otherMethod.getName())
                && method.getReturnType().equals(otherMethod.getReturnType())
                && (method.getModifiers() & ABSTRACT) == (otherMethod.getModifiers() & ABSTRACT)
                && Arrays.asList(method.getParameterTypes()).equals(Arrays.asList(otherMethod.getParameterTypes()));
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash
        (
            method.getName(),
            method.getReturnType(),
            method.getModifiers() & ABSTRACT,
            Arrays.hashCode(method.getParameterTypes())
        );
    }
}
