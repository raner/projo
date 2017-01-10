//                                                                          //
// Copyright 2017 Mirko Raner                                               //
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
package pro.projo.template.configuration;

import java.util.Map;
import java.util.stream.Stream;
import org.junit.Test;
import pro.projo.template.annotation.Configuration;
import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;

public class ProjoFactoryTemplateConfigurationTest
{
    @Test
    public void testSinglesFactoryClassName()
    {
        assertEquals("pro.projo.singles.Factory", new ProjoFactoryTemplateConfiguration().get(0).fullyQualifiedClassName());
    }

    @Test
    public void testDoublesFactoryClassName()
    {
        assertEquals("pro.projo.doubles.Factory", new ProjoFactoryTemplateConfiguration().get(1).fullyQualifiedClassName());
    }

    @Test
    public void testTriplesFactoryClassName()
    {
        assertEquals("pro.projo.triples.Factory", new ProjoFactoryTemplateConfiguration().get(2).fullyQualifiedClassName());
    }

    @Test
    public void testSinglesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(0);
        String[][] expected =
        {
            {"template", "singles"},
            {"n", "1"},
            {"s", ""},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"},
            {"argumentAndAdditionalParameters", "first"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    @Test
    public void testDoublesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(1);
        String[][] expected =
        {
            {"template", "doubles"},
            {"n", "2"},
            {"s", "s"},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_, _Second_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"
                + lineSeparator() + "* @param <_Second_> the type of the second field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"
                + lineSeparator() + "    * @param second the second parameter"},
            {"argumentAndAdditionalParameters", "first, _Second_ second"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    @Test
    public void testTriplesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(2);
        String[][] expected =
        {
            {"template", "triples"},
            {"n", "3"},
            {"s", "s"},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_, _Second_, _Third_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"
                + lineSeparator() + "* @param <_Second_> the type of the second field"
                + lineSeparator() + "* @param <_Third_> the type of the third field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"
                + lineSeparator() + "    * @param second the second parameter"
                + lineSeparator() + "    * @param third the third parameter"},    
            {"argumentAndAdditionalParameters", "first, _Second_ second, _Third_ third"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    @Test
    public void testTrigintuplesFactoryParameters()
    {
        Configuration configuration = new ProjoFactoryTemplateConfiguration().get(29);
        String[][] expected =
        {
            {"template", "trigintuples"},
            {"n", "30"},
            {"s", "s"},
            {"First", "_First_"},
            {"AdditionalTypeParameters", "_First_, _Second_, _Third_, _Fourth_, _Fifth_, _Sixth_, "
                + "_Seventh_, _Eighth_, _Ninth_, _Tenth_, _Eleventh_, _Twelfth_, "
                + "_Thirteenth_, _Fourteenth_, _Fifteenth_, _Sixteenth_, _Seventeenth_, _Eighteenth_, "
                + "_Nineteenth_, _Twentieth_, _TwentyFirst_, _TwentySecond_, _TwentyThird_, _TwentyFourth_, "
                + "_TwentyFifth_, _TwentySixth_, _TwentySeventh_, _TwentyEighth_, _TwentyNinth_, _Thirtieth_"},
            {"additionalTypeParameterDocumentation", "@param <_First_> the type of the first field"
                + lineSeparator() + "* @param <_Second_> the type of the second field"
                + lineSeparator() + "* @param <_Third_> the type of the third field"
                + lineSeparator() + "* @param <_Fourth_> the type of the fourth field"
                + lineSeparator() + "* @param <_Fifth_> the type of the fifth field"
                + lineSeparator() + "* @param <_Sixth_> the type of the sixth field"
                + lineSeparator() + "* @param <_Seventh_> the type of the seventh field"
                + lineSeparator() + "* @param <_Eighth_> the type of the eighth field"
                + lineSeparator() + "* @param <_Ninth_> the type of the ninth field"
                + lineSeparator() + "* @param <_Tenth_> the type of the tenth field"
                + lineSeparator() + "* @param <_Eleventh_> the type of the eleventh field"
                + lineSeparator() + "* @param <_Twelfth_> the type of the twelfth field"
                + lineSeparator() + "* @param <_Thirteenth_> the type of the thirteenth field"
                + lineSeparator() + "* @param <_Fourteenth_> the type of the fourteenth field"
                + lineSeparator() + "* @param <_Fifteenth_> the type of the fifteenth field"
                + lineSeparator() + "* @param <_Sixteenth_> the type of the sixteenth field"
                + lineSeparator() + "* @param <_Seventeenth_> the type of the seventeenth field"
                + lineSeparator() + "* @param <_Eighteenth_> the type of the eighteenth field"
                + lineSeparator() + "* @param <_Nineteenth_> the type of the nineteenth field"
                + lineSeparator() + "* @param <_Twentieth_> the type of the twentieth field"
                + lineSeparator() + "* @param <_TwentyFirst_> the type of the twenty-first field"
                + lineSeparator() + "* @param <_TwentySecond_> the type of the twenty-second field"
                + lineSeparator() + "* @param <_TwentyThird_> the type of the twenty-third field"
                + lineSeparator() + "* @param <_TwentyFourth_> the type of the twenty-fourth field"
                + lineSeparator() + "* @param <_TwentyFifth_> the type of the twenty-fifth field"
                + lineSeparator() + "* @param <_TwentySixth_> the type of the twenty-sixth field"
                + lineSeparator() + "* @param <_TwentySeventh_> the type of the twenty-seventh field"
                + lineSeparator() + "* @param <_TwentyEighth_> the type of the twenty-eighth field"
                + lineSeparator() + "* @param <_TwentyNinth_> the type of the twenty-ninth field"
                + lineSeparator() + "* @param <_Thirtieth_> the type of the thirtieth field"},
            {"additionalMethodParameterDocumentation", "@param first the first parameter"
                + lineSeparator() + "    * @param second the second parameter"
                + lineSeparator() + "    * @param third the third parameter"    
                + lineSeparator() + "    * @param fourth the fourth parameter"
                + lineSeparator() + "    * @param fifth the fifth parameter"
                + lineSeparator() + "    * @param sixth the sixth parameter"
                + lineSeparator() + "    * @param seventh the seventh parameter"
                + lineSeparator() + "    * @param eighth the eighth parameter"
                + lineSeparator() + "    * @param ninth the ninth parameter"
                + lineSeparator() + "    * @param tenth the tenth parameter"
                + lineSeparator() + "    * @param eleventh the eleventh parameter"
                + lineSeparator() + "    * @param twelfth the twelfth parameter"
                + lineSeparator() + "    * @param thirteenth the thirteenth parameter"
                + lineSeparator() + "    * @param fourteenth the fourteenth parameter"
                + lineSeparator() + "    * @param fifteenth the fifteenth parameter"
                + lineSeparator() + "    * @param sixteenth the sixteenth parameter"
                + lineSeparator() + "    * @param seventeenth the seventeenth parameter"
                + lineSeparator() + "    * @param eighteenth the eighteenth parameter"
                + lineSeparator() + "    * @param nineteenth the nineteenth parameter"
                + lineSeparator() + "    * @param twentieth the twentieth parameter"
                + lineSeparator() + "    * @param twentyFirst the twenty-first parameter"
                + lineSeparator() + "    * @param twentySecond the twenty-second parameter"
                + lineSeparator() + "    * @param twentyThird the twenty-third parameter"
                + lineSeparator() + "    * @param twentyFourth the twenty-fourth parameter"
                + lineSeparator() + "    * @param twentyFifth the twenty-fifth parameter"
                + lineSeparator() + "    * @param twentySixth the twenty-sixth parameter"
                + lineSeparator() + "    * @param twentySeventh the twenty-seventh parameter"
                + lineSeparator() + "    * @param twentyEighth the twenty-eighth parameter"
                + lineSeparator() + "    * @param twentyNinth the twenty-ninth parameter"
                + lineSeparator() + "    * @param thirtieth the thirtieth parameter"},
            {"argumentAndAdditionalParameters", "first, _Second_ second, _Third_ third, _Fourth_ fourth, "
                + "_Fifth_ fifth, _Sixth_ sixth, _Seventh_ seventh, _Eighth_ eighth, _Ninth_ ninth, _Tenth_ tenth, "
                + "_Eleventh_ eleventh, _Twelfth_ twelfth, _Thirteenth_ thirteenth, _Fourteenth_ fourteenth, "
                + "_Fifteenth_ fifteenth, _Sixteenth_ sixteenth, _Seventeenth_ seventeenth, _Eighteenth_ eighteenth, "
                + "_Nineteenth_ nineteenth, _Twentieth_ twentieth, _TwentyFirst_ twentyFirst, "
                + "_TwentySecond_ twentySecond, _TwentyThird_ twentyThird, _TwentyFourth_ twentyFourth, "
                + "_TwentyFifth_ twentyFifth, _TwentySixth_ twentySixth, _TwentySeventh_ twentySeventh, "
                + "_TwentyEighth_ twentyEighth, _TwentyNinth_ twentyNinth, _Thirtieth_ thirtieth"}
        };
        assertEquals(map(expected), configuration.parameters());
    }

    private Map<String, String> map(String[][] entries)
    {
        return Stream.of(entries).collect(toMap(key -> key[0], value -> value[1]));
    }
}
