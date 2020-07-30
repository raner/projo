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
package pro.projo;

import java.lang.reflect.TypeVariable;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
* The {@link SelfTypeTest} verifies {@link Projo} methods that are related to the concept
* of "self" type variables, i.e. type variables that can only be bound to the declaring
* type itself. This is important for some of the envisioned downstream applications of
* Projo, for example, it ensures covariant return types across multiple class hierarchy
* levels:
* <pre>
*   interface Copyable<S extends Copyable<S>> // "S" is the self type variable
*   {
*     S copy();
*   }
*
*   interface Document<S extends Document<S>> extends Copyable<S> {}
*
*   interface Key<S extends Key<S>> extends Copyable<S> {}
*
*   interface SecureKey<S extends SecureKey<S>> extends Key<S> {}
*
*   class Test
*   {
*     void makeCopies(Document<?> document, Key<?> key, SecureKey<?> secureKey)
*     {     
*       Document<?> documentCopy = document.copy();
*       Key<?> keyCopy = key.copy();
*       SecureKey<?> secureKeyCopy = secureKey.copy();
*       //...
*     }
*   }
* </pre>
*
* @author Mirko Raner
**/
public class SelfTypeTest implements AbstractTypeMappingTest
{
    static interface Map1<X extends Map1<X, K, V>, K, V> {}

    static interface Map2<K, V, Y extends Map2<K, V, Y>> {}

    static interface Map3<K, Z extends Map3<K, Z, V>, V> {} // very unusual, but should work nonetheless

    static interface Map4<X extends Map4<?, K, V>, K, V> {} // not recursive, therefore not a "self" type

    static interface Map5<K extends CharSequence, V> {} // no parameter extends the type itself

    static interface Map6 {}

    @Test
    public void testSelfTypeIsTheOnlyTypeVariable()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Number.class);
        assertEquals("S", self.get().toString());
    }

    @Test
    public void testSelfTypeIsTheFirstTypeVariable()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Map1.class);
        assertEquals("X", self.get().toString());
    }

    @Test
    public void testSelfTypeIsTheLastTypeVariable()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Map2.class);
        assertEquals("Y", self.get().toString());
    }

    @Test
    public void testSelfTypeIsNeitherFirstNorLastTypeVariable()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Map3.class);
        assertEquals("Z", self.get().toString());
    }

    @Test
    public void testNonrecursiveTypeParametersAreNotConsideredSelfTypes()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Map4.class);
        assertTrue(self.isEmpty());
    }

    @Test
    public void testNoParameterExtendsTheTypeItselfNotConsideredSelfType()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Map5.class);
        assertTrue(self.isEmpty());
    }

    @Test
    public void testTypeWithoutParametersCannotHaveASelfTypeVariable()
    {
        Optional<TypeVariable<?>> self = Projo.getSelfType(Map6.class);
        assertTrue(self.isEmpty());
    }
}
