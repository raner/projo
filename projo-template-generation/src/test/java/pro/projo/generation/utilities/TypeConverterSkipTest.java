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
package pro.projo.generation.utilities;

import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.function.UnaryOperator;
import javax.tools.StandardLocation;
import org.junit.Test;
import pro.projo.generation.utilities.Source.InterfaceSource;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Ternary;
import pro.projo.interfaces.annotation.Unmapped;
import pro.projo.interfaces.annotation.postprocessor.IdentityPostProcessor;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pro.projo.template.annotation.Configuration.defaults;

/**
* The {@link TypeConverterSkipTest} contains test cases specific to verifying the
* skipping of methods that use {@link Unmapped} types. It mainly tests the
* {@link TypeConverter#getOrDefault(String)} method.
*
* @author Mirko Raner
**/
public class TypeConverterSkipTest extends AbstractTypeConverterTest
{
    @Test
    public void skipUnmappedNonPrimitiveTypeWhenUnmapped()
    {
        converter = converter(unmapped(true, false));
        assertTrue(converter.getOrDefault("java.lang.String").unmapped());
    }

    @Test
    public void dontSkipUnmappedNonPrimitiveUnlessUnmappedOptionSpecified()
    {
        converter = converter(unmapped(false, false));
        assertFalse(converter.getOrDefault("java.lang.String").unmapped());
    }

    @Test
    public void skipUnmappedPrimitivesIfPrimitivesIncluded()
    {
        converter = converter(unmapped(true, true));
        assertTrue(converter.getOrDefault("char").unmapped());
    }

    @Test
    public void dontSkipUnmappedPrimitivesUnlessPrimitivesIncluded()
    {
        converter = converter(unmapped(true, false));
        assertFalse(converter.getOrDefault("char").unmapped());
    }

    @Test
    public void dontSkipMappedTypes()
    {
        converter = converter(unmapped(true, false));
        assertFalse(converter.getOrDefault("java.lang.Runnable").unmapped());
    }
    
    @Test
    public void dontSkipMappedTypesEvenIfPrimitivesIncluded()
    {
        converter = converter(unmapped(true, true));
        assertFalse(converter.getOrDefault("java.lang.Runnable").unmapped());
    }

    private TypeConverter converter(Unmapped unmapped)
    {
        return new TypeConverter(types, shortener(), testPackage, sources(), source(unmapped));
    }

    private InterfaceSource source(Unmapped unmapped)
    {
        Options options = createOptions(unmapped);
        Interface primary = createInterface(null, null, null, new Map[] {}, options);
        return new InterfaceSource(primary);
    }

    protected Options createOptions(Unmapped unmapped)
    {
        return new Options()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Options.class;
            }

            @Override
            public String fileExtension()
            {
                return defaults().fileExtension();
            }

            @Override
            public StandardLocation outputLocation()
            {
                return defaults().outputLocation();
            }

            @Override
            public Unmapped skip()
            {
                return unmapped;
            }

            @Override
            public Ternary addAnnotations()
            {
                return Ternary.EITHER;
            }

            @Override
            public Class<? extends UnaryOperator<Writer>> postProcessor()
            {
                return IdentityPostProcessor.class;
            }
        };
    }

    private Unmapped unmapped(boolean value, boolean includingPrimitives)
    {
        return new Unmapped()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Unmapped.class;
            }

            @Override
            public boolean value()
            {
                return value;
            }

            @Override
            public boolean includingPrimitives()
            {
                return includingPrimitives;
            }
        };
    }
}
