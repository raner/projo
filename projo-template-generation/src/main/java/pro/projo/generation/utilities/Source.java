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

import pro.projo.interfaces.annotation.Enum;
import pro.projo.interfaces.annotation.Interface;
import pro.projo.interfaces.annotation.Map;

public interface Source
{
    Class<?> from();
    
    Map[] map();

    String generate();

    public static class SourceInterface implements Source
    {
        private Interface source;

        public SourceInterface(Interface source)
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
    }

    public static class SourceEnum implements Source
    {
        private Enum source;

        public SourceEnum(Enum source)
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
    }
}
