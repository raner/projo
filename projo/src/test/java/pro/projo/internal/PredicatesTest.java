//                                                                          //
// Copyright 2019 Mirko Raner                                               //
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

import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PredicatesTest
{
    static interface Complex
    {
        Number getReal();
        void setReal(Number real);
        Number getImaginary();
        void setImaginary(Number imaginary);

        default Number getZero()
        {
            return 0;
        }

        @Override
        int hashCode();
    }

    @Test
    public void testGetterPredicate() throws Exception
    {
        Method setReal = Complex.class.getDeclaredMethod("setReal", Number.class);
        assertFalse(Predicates.getter.test(setReal));
    }

    @Test
    public void testGetterPredicateIsGetter() throws Exception
    {
        Method getZero = Complex.class.getDeclaredMethod("getZero");
        assertFalse(Predicates.getter.test(getZero));
    }

    @Test
    public void testDefaultMethodIsNotAGetter() throws Exception
    {
      Method getReal = Complex.class.getDeclaredMethod("getReal");
      assertTrue(Predicates.getter.test(getReal));
    }

    @Test
    public void testGetterPredicateObjectHashCodeIsNotGetter() throws Exception
    {
        Method hashCode = Object.class.getDeclaredMethod("hashCode");
        assertFalse(Predicates.getter.test(hashCode));
    }

    @Test
    public void testGetterPredicateHashCodeIsNotGetter() throws Exception
    {
        Method hashCode = Complex.class.getDeclaredMethod("hashCode");
        assertFalse(Predicates.getter.test(hashCode));
    }

    @Test
    public void testGetterPredicateNotifyIsNotGetter() throws Exception
    {
        Method notify = Object.class.getDeclaredMethod("notify");
        assertFalse(Predicates.getter.test(notify));
    }

    @Test
    public void testSetterPredicate() throws Exception
    {
        Method setReal = Complex.class.getDeclaredMethod("setReal", Number.class);
        assertTrue(Predicates.setter.test(setReal));
    }

    @Test
    public void testSetterPredicateWaitIsNotSetter() throws Exception
    {
        Method wait = Object.class.getDeclaredMethod("wait", long.class);
        assertFalse(Predicates.setter.test(wait));
    }
}
