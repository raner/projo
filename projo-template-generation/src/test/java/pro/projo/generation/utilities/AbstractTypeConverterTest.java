//                                                                          //
// Copyright 2020 - 2023 Mirko Raner                                        //
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
import java.lang.reflect.Type;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.StandardLocation;

import net.florianschoppmann.java.reflect.ReflectionTypes;
import pro.projo.generation.utilities.Source.InterfaceSource;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;
import pro.projo.interfaces.annotation.Ternary;
import pro.projo.interfaces.annotation.Unmapped;
import pro.projo.interfaces.annotation.Visibility;
import pro.projo.interfaces.annotation.postprocessor.IdentityPostProcessor;

import static pro.projo.template.annotation.Configuration.defaults;

/**
* {@link AbstractTypeConverterTest} is an abstract base class for test cases that
* verify the {@link TypeConverter}.
*
* @author Mirko Raner
**/
public abstract class AbstractTypeConverterTest
{
    protected Name testPackage = new Name("pro.projo.generation.utilities.expected.test.types");
    protected ReflectionTypes types = ReflectionTypes.getInstance();
    protected Function<Class<?>, TypeElement> typeElementFactory = types::typeElement;
    protected Function<Type, TypeMirror> typeMirrorFactory = types::typeMirror;

    protected Interface[] interfaces =
    {
        createInterface(Runnable.class, "Walkable"),
        createInterface(Future.class, "Pending")
    };

    protected TypeConverter converter;

    protected Stream<Source> sources()
    {
        return Stream.of(interfaces).map(InterfaceSource::new);
    }

    protected PackageShortener shortener()
    {
        return new PackageShortener();
    }

    protected Interface createInterface(Class<?> from, String generate, Modifier... modifiers)
    {
        return createInterface(from, generate, modifiers, new Map[] {});
    }

    protected Interface createInterface(Class<?> from, String generate, Modifier[] modifiers, Map[] map)
    {
        return createInterface(from, generate, modifiers, new Map[] {}, defaults());
    }

    protected Interface createInterface(Class<?> from, String generate, Modifier[] modifiers, Map[] map, Options options)
    {
        return new Interface()
        {
            @Override
            public Class<? extends Annotation> annotationType()
            {
              return Interface.class;
            }

            /**
            * Provides the source {@link Class} ("from" class) of the annotation.
            * Note that this method behaves exactly like annotations behave at compile time
            * (during compile-time annotation processing), i.e. it actually throws a
            * {@link MirroredTypeException}.
            *
            * @throws MirroredTypeException containing the class's {@link TypeMirror}
            **/
            @Override
            public Class<?> from()
            {
                throw new MirroredTypeException(typeMirrorFactory.apply(from));
            }

            @Override
            public String generate()
            {
                return generate;
            }

            @Override
            public String[] extend()
            {
                return new String[] {};
            }

            @Override
            public Modifier[] modifiers()
            {
                return modifiers;
            }

            @Override
            public Map[] map()
            {
                return map;
            }

            @Override
            public Options options()
            {
                return options;
            }

            @Override
            public Ternary isStatic()
            {
                return Ternary.EITHER;
            }

            @Override
            public Visibility[] visibility()
            {
                return new Visibility[] {};
            }
        };
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
    
            @Override
            public Class<? extends UnaryOperator<Writer>> typeVariableTransformer()
            {
                return IdentityPostProcessor.class;
            }
        };
    }

    protected Unmapped unmapped(boolean value, boolean includingPrimitives)
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
    
            @Override
            public boolean includingArrays()
            {
                return false;
            }
        };
    }
}
