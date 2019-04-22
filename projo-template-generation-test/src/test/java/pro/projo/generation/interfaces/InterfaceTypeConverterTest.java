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
package pro.projo.generation.interfaces;

import java.lang.annotation.Annotation;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import org.junit.Before;
import org.junit.Test;
import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.generation.interfaces.test.classes.Thing;
import pro.projo.generation.utilities.Name;
import pro.projo.generation.utilities.PackageShortener;
import pro.projo.generation.utilities.TypeConverter;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Interfaces;
import pro.projo.interfaces.annotation.Map;
import static org.junit.Assert.assertEquals;

public class InterfaceTypeConverterTest
{
    ReflectionTypes types = ReflectionTypes.getInstance();
    TypeConverter converter;

    @Before
    public void initializeTypeConverterUnderTest() throws Exception
    {
        PackageShortener shortener = new PackageShortener();
        Name testPackage = new Name("pro.projo.generation.interfaces.test");
        Class<?> packageInfo = Class.forName(testPackage + ".package-info");
        Interfaces interfaces = packageInfo.getAnnotation(Interfaces.class);
        UnaryOperator<Map> testableMap = original -> new Map()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Map.class;
            }
  
            @Override
            public Class<?> type()
            {
                throw new MirroredTypeException(types.typeMirror(original.type()));
            }
  
            @Override
            public String to()
            {
                return original.to();
            }
        };
        UnaryOperator<Interface> testableInterface = original -> new Interface()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
                return Interface.class;
            }

            @Override
            public String generate()
            {
                return original.generate();
            }

            @Override
            public Modifier[] modifiers()
            {
                return original.modifiers();
            }
  
            @Override
            public Class<?> from()
            {
                throw new MirroredTypeException(types.typeMirror(original.from()));
            }
  
            @Override
            public Map[] map()
            {
                return Stream.of(original.map()).map(testableMap).toArray(Map[]::new);
            }
        };
        Interface[] testableInterfaces = Stream.of(interfaces.value()).map(testableInterface).toArray(Interface[]::new);
        converter = new TypeConverter(types, shortener, testPackage, testableInterfaces);
    }

    @Test
    public void test()
    {
        TypeMirror typeMirror = types.typeMirror(Thing.class);
        String result = converter.convert(typeMirror);
        assertEquals("NewThing", result);
    }
}
