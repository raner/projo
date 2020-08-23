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
package pro.projo.generation.utilities;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Ternary;
import pro.projo.interfaces.annotation.Visibility;
import static java.util.Arrays.asList;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;
import static pro.projo.interfaces.annotation.Ternary.EITHER;
import static pro.projo.interfaces.annotation.Ternary.FALSE;

/**
* The {@link MethodFilter} checks whether the matching criteria specified by an
* {@link Interface @Interface} annotation apply to a given method.
*
* @author Mirko Raner
**/
public class MethodFilter
{
    private Predicate<ExecutableElement> predicate;

    /**
    * Creates a new {@link MethodFilter} with pre-compiled predicates for the given
    * {@link Interface} annotation.
    *
    * @param annotation the {@link Interface} annotation
    **/
    public MethodFilter(Interface annotation)
    {
        @SuppressWarnings("deprecation")
        Set<Modifier> modifiers = set(annotation.modifiers());
        Set<Visibility> visibility = set(annotation.visibility());
        Ternary isStatic = annotation.isStatic();

        // Predicate determining whether or not methods need to be static:
        //
        // +-----------+----------++----------------------------------+
        // | modifiers |          ||                                  |
        // | .contains | isStatic || resulting predicate              |
        // | (STATIC)  |          ||                                  |
        // +-----------+----------++----------------------------------+
        // | true      | TRUE     ||  getModifiers().contains(STATIC) |
        // | true      | FALSE    || !getModifiers().contains(STATIC) |
        // | true      | EITHER   ||  getModifiers().contains(STATIC) |
        // | false     | TRUE     ||  getModifiers().contains(STATIC) |
        // | false     | FALSE    || !getModifiers().contains(STATIC) |
        // | false     | EITHER   ||  always -> true                  |
        // +-----------+----------++----------------------------------+
        //
        Predicate<ExecutableElement> staticity = modifiers.contains(STATIC) || isStatic != EITHER?
            predicate(method -> method.getModifiers().contains(STATIC) ^ isStatic == FALSE):
                always(true);

        // Predicate determining whether or not methods need to be package-visible:
        //
        // +--------------------------------------------------------+
        // | visibility ||                                          |
        // | .contains  || resulting predicate                      |
        // | (PACKAGE)  ||                                          |
        // +------------++------------------------------------------+
        // |            || !getModifiers().contains(PUBLIC)         |
        // | true       ||   && !getModifiers().contains(PRIVATE)   |
        // |            ||   && !getModifiers().contains(PROTECTED) |
        // +------------++------------------------------------------+
        // | false      || always -> true                           |
        // +------------++------------------------------------------+
        //
        boolean packageVisibility = Stream.of(PUBLIC, PRIVATE, PROTECTED)
            .map(modifiers::contains).map(it -> !it)
            .reduce(visibility.contains(Visibility.PACKAGE), Boolean::logicalAnd);
        Predicate<ExecutableElement> isPackage = packageVisibility?
            not(method -> Stream.of(PUBLIC, PRIVATE, PROTECTED).map(method.getModifiers()::contains).reduce(false, Boolean::logicalOr)):
                always(false);

        // Compose overall predicate:
        //
        Stream<Predicate<ExecutableElement>> visibilities =
            Stream.of(Visibility.PUBLIC, Visibility.PRIVATE, Visibility.PROTECTED)
                .map(it -> visibility(annotation, it));
        predicate = staticity.and(visibilities.reduce(isPackage, Predicate::or));
    }

    Predicate<ExecutableElement> visibility(Interface annotation, Visibility visibility)
    {
        // Predicate determining whether or not methods need to be public/private/protected:
        //
        // +-----------+------------++----------------------------------+
        // | modifiers | visibility ||                                  |
        // | .contains | .contains  || resulting predicate              |
        // | (PUBLIC)  | (PUBLIC)   ||                                  |
        // +-----------+------------++----------------------------------+
        // | true      | true       ||  getModifiers().contains(PUBLIC) |
        // | true      | false      || !getModifiers().contains(PUBLIC) |
        // | false     | true       ||  getModifiers().contains(PUBLIC) |
        // | false     | false      || !getModifiers().contains(PUBLIC) |
        // +-----------+------------++----------------------------------+
        //
        @SuppressWarnings("deprecation")
        Modifier[] modifiers = annotation.modifiers();
        return (set(annotation.visibility()).contains(visibility))
            && (set(modifiers).contains(visibility.modifier()) || modifiers.length == 0)?
                predicate(method -> method.getModifiers().contains(visibility.modifier())):
                    always(false);
    }

    /**
    * Matches a method against the filter criteria.
    *
    * @param method the {@link ExecutableElement}
    * @return {@code true} if the method matches, {@code false} otherwise
    **/
    public boolean matches(ExecutableElement method)
    {
        return predicate.test(method);
    }

    private <_Any_> Set<_Any_> set(_Any_[] elements)
    {
        return new HashSet<_Any_>(asList(elements));
    }

    private <_Any_> Predicate<_Any_> always(boolean value)
    {
        return always -> value;
    }

    private Predicate<ExecutableElement> predicate(Predicate<ExecutableElement> predicate)
    {
        return predicate;
    }

    private Predicate<ExecutableElement> not(Predicate<ExecutableElement> predicate)
    {
        return predicate.negate();
    }
}
