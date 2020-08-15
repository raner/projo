//                                                                          //
// Copyright 2019 - 2020 Mirko Raner                                        //
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

import pro.projo.interfaces.annotation.Enum;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;
import pro.projo.interfaces.annotation.Options;

/**
* As annotations in Java do not support inheritance, the {@link Source} interface acts as a unifying
* wrapper around {@link Interface} and {@link Enum} objects. This allows both types of annotations
* to be easily passed to the {@link TypeConverter}.
*
* @author Mirko Raner
**/
public interface Source
{
    Class<?> from();
    
    Map[] map();

    String generate();

    Options options();

    public static class InterfaceSource implements Source
    {
        private Interface source;

        public InterfaceSource(Interface source)
        {
            this.source = source;
        }
      
        @Override
        public Class<?> from()
        {
            return source.from();
        }

        @Override
        public Map[] map()
        {
            return source.map();
        }

        @Override
        public String generate()
        {
            return source.generate();
        }

        @Override
        public Options options()
        {
            return source.options();
        }
    }

    public static class EnumSource implements Source
    {
        private Enum source;

        public EnumSource(Enum source)
        {
            this.source = source;
        }
      
        @Override
        public Class<?> from()
        {
            return source.from();
        }

        @Override
        public Map[] map()
        {
            return new Map[] {};
        }

        @Override
        public String generate()
        {
            return source.generate();
        }

        @Override
        public Options options()
        {
            return source.options();
        }
    }
}
