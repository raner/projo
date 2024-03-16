//                                                                          //
// Copyright 2022 - 2024 Mirko Raner                                        //
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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
* An {@link AnnotationList} provides easy access to the annotations of an
* {@link AnnotatedElement}. It also provides convenience methods for handling
* JSR-330 and Jakarta dependency injection annotations.
*
* @author Mirko Raner
**/
public class AnnotationList extends ArrayList<Annotation>
{
    private final static long serialVersionUID = 7598992303415745213L;

    private final static String javaxInject = "javax.inject.Inject";
    private final static String jakartaInject = "jakarta.inject.Inject";

    public AnnotationList(AnnotatedElement element)
    {
        this(element.getAnnotations());
    }

    public AnnotationList(Annotation... annotations)
    {
        super(Arrays.asList(annotations));
    }

    public boolean contains(Class<? extends Annotation> annotation)
    {
        return stream().anyMatch(it -> it.annotationType().equals(annotation));
    }

    public boolean contains(String annotationName)
    {
        return stream().anyMatch(it -> it.annotationType().getName().equals(annotationName));
    }

    public boolean containsInject()
    {
        return stream().anyMatch(this::isInject);
    }

    public Optional<Annotation> getInject()
    {
      return stream().filter(this::isInject).findFirst();
    }

    public boolean isInject(Annotation annotation)
    {
        String name = annotation.annotationType().getName();
        return name.equals(javaxInject) || name.equals(jakartaInject);
    }

    @SuppressWarnings("unchecked")
    public <ANNOTATION extends Annotation> Optional<ANNOTATION> get(Class<ANNOTATION> annotation)
    {
        return (Optional<ANNOTATION>)stream().filter(it -> it.annotationType().equals(annotation)).findAny();
    }
    
    @SuppressWarnings("unchecked")
    public <ANNOTATION extends Annotation> Optional<ANNOTATION> get(String annotationName)
    {
        return (Optional<ANNOTATION>)stream().filter(it -> it.annotationType().getName().equals(annotationName)).findAny();
    }
}
