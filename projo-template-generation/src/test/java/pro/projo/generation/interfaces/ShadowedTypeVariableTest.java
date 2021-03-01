//                                                                          //
// Copyright 2020 - 2021 Mirko Raner                                        //
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

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.TypeParameterElement;
import org.junit.Test;
import pro.projo.generation.utilities.Name;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static java.util.function.UnaryOperator.identity;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
* The {@link ShadowedTypeVariableTest} verifies the resolution of duplicate type variable names.
*
* @author Mirko Raner
**/
public class ShadowedTypeVariableTest
{
    InterfaceTemplateProcessor processor = new InterfaceTemplateProcessor();

    final TypeParameterElement S, T, U, V, W, X, X0, X1;

    public ShadowedTypeVariableTest()
    {
        S = mock(TypeParameterElement.class);
        T = mock(TypeParameterElement.class);
        U = mock(TypeParameterElement.class);
        V = mock(TypeParameterElement.class);
        W = mock(TypeParameterElement.class);
        X = mock(TypeParameterElement.class);
        X0 = mock(TypeParameterElement.class);
        X1 = mock(TypeParameterElement.class);
        when(S.getSimpleName()).thenReturn(new Name("S"));
        when(T.getSimpleName()).thenReturn(new Name("T"));
        when(U.getSimpleName()).thenReturn(new Name("U"));
        when(V.getSimpleName()).thenReturn(new Name("V"));
        when(W.getSimpleName()).thenReturn(new Name("W"));
        when(X.getSimpleName()).thenReturn(new Name("X"));
        when(X0.getSimpleName()).thenReturn(new Name("X0"));
        when(X1.getSimpleName()).thenReturn(new Name("X1"));
    }

    @Test
    public void noTypeVariablesAtAll()
    {
    	Map<String, String> result =
    	    processor.renameShadowedTypeVariables(emptyList(), emptyList(), identity());
    	assertEquals(emptyMap(), result);
    }

    @Test
    public void noShadowedTypeVariables()
    {
        Map<String, String> result =
            processor.renameShadowedTypeVariables(asList(S, T, U), asList(V, W), identity());
        assertEquals(emptyMap(), result);
    }

    @Test
    public void oneShadowedTypeVariable()
    {
        Map<String, String> result =
            processor.renameShadowedTypeVariables(asList(S, T), asList(T, U), identity());
        assertEquals(singletonMap("T", "T0"), result);
    }

    @Test
    public void oneShadowedTypeVariableRenameWouldCollideWithExistingTypeLevelVariable()
    {
    	Map<String, String> result =
    	    processor.renameShadowedTypeVariables(asList(W, X, X0), asList(T, U, X), identity());
    	assertEquals(singletonMap("X", "X1"), result);
    }

    @Test
    public void oneShadowedTypeVariableRenameWouldCollideWithExistingMethodLevelVariable()
    {
    	Map<String, String> result =
    	    processor.renameShadowedTypeVariables(asList(W, X), asList(T, U, X, X0), identity());
    	assertEquals(singletonMap("X", "X1"), result);
    }

    @Test
    public void oneShadowedTypeVariableRenameWouldCollideWithTwoExistingTypeLevelVariables()
    {
    	Map<String, String> result =
    	    processor.renameShadowedTypeVariables(asList(X, X0, X1), asList(U, X), identity());
    	assertEquals(singletonMap("X", "X2"), result);
    }

    @Test
    public void multipeShadowedTypeVariablesWithCollisions()
    {
    	Map<String, String> result =
    	    processor.renameShadowedTypeVariables(asList(S, T, U, X, X0), asList(U, V, W, X, X1), identity());
    	Map<String, String> expected = new HashMap<>();
    	expected.put("U", "U0");
    	expected.put("X", "X2");
    	assertEquals(expected, result);
    }

    @Test
    public void allMethodLevelsTypeVariablesAreShadowed()
    {
    	Map<String, String> result =
    	    processor.renameShadowedTypeVariables(asList(S, T, U, V, W), asList(T, U, V), identity());
    	Map<String, String> expected = new HashMap<>();
    	expected.put("T", "T0");
    	expected.put("U", "U0");
    	expected.put("V", "V0");
    	assertEquals(expected, result);
    }
}
