//                                                                          //
// Copyright 2020 Mirko Raner                                               //
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
package pro.projo.generation.interfaces;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

/**
* The {@link TypeVariableRenamingTest} reflectively verifies that shadowed type variables are
* renamed properly. Specifically, this test was introduced for situations where code generation
* is slightly different between different Java versions. For example, certain parameter names
* are always {@code arg0}, {@code arg1}, etc. on older runtimes like Java 8, whereas newer versions
* provide the correct parameter name. Due to this discrepancy, {@link GeneratedSourcesTest} would
* become runtime-dependent for those scenarios. {@link TypeVariableRenamingTest} only checks
* relevant type information and ignores parameter names.
*
* @author Mirko Raner
**/
public class TypeVariableRenamingTest
{
    @Test
    public void testFunctionTypeVariables() throws Exception
    {
        Class<?> classFunction = Class.forName("pro.projo.generation.interfaces.test.Function");
        Method identityMethod = classFunction.getDeclaredMethod("identity");
        ParameterizedType returnType = (ParameterizedType)identityMethod.getGenericReturnType();
        Stream<Type> typeArguments = Stream.of(returnType.getActualTypeArguments());
        List<String> typeArgumentNames = typeArguments.map(Type::toString).collect(toList());
        List<String> expected = asList("T0", "T0");
        assertEquals(expected, typeArgumentNames);
    }
}
