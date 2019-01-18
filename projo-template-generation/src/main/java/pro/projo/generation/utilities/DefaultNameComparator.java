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
package pro.projo.generation.utilities;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.lang.model.element.Name;

/**
* The {@link DefaultNameComparator} class is a default {@link Comparator} for {@link Name}s.
*
* @author Mirko Raner
**/
public class DefaultNameComparator implements Comparator<Name>
{
    private List<String> PRIORITY_NAMES = Arrays.asList("org", "javax", "java");

    @Override
    public int compare(Name type1, Name type2)
    {
        String[] sequence1 = type1.toString().split("\\.");
        String[] sequence2 = type2.toString().split("\\.");

        // Skip identical parts:
        int index = 0;
        while (index < sequence1.length && index < sequence2.length && sequence1[index].equals(sequence2[index]))
        {
            index++;
        }
        if (index == 0)
        {
            // Names differ at the very first segment; apply special rules for first segment:
            //
            int priority1 = PRIORITY_NAMES.indexOf(sequence1[index]);
            int priority2 = PRIORITY_NAMES.indexOf(sequence2[index]);
            if (priority1 > -1 || priority2 > -1)
            {
                return priority2 - priority1;
            }
        }
        if (index < sequence1.length && index < sequence2.length)
        {
            return sequence1[index].compareTo(sequence2[index]);
        }
        return sequence1.length - sequence2.length;
    }
}
