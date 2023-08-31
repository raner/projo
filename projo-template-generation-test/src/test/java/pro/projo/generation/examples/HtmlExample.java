//                                                                          //
// Copyright 2023 Mirko Raner                                               //
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
package pro.projo.generation.examples;

import pro.projo.generation.interfaces.test.html.baseclasses.HtmlContentHead;

/**
* The {@link HtmlExample} class illustrates how the generated HTML API can be
* used in Java. Due to Java's cumbersome syntax for lambda expressions, the
* result is pretty horrible, but it looks a lot better in more modern
* programming languages (e.g., Xtend).
*
* @author Mirko Raner
**/
public class HtmlExample
{
    public void example(HtmlContentHead html)
    {
        html.
            head().$($ -> $.title().$(() -> "Title")).
            body().$
            (
                $ -> $.
                img().src("image.jpg").alt("An image").$().
                div().class_("content").id("content-id").$
                (
                    $1 -> $1.
                    em().$
                    (
                        $2 -> $2.
                        img().src("image2.jpg").alt("Another image").$()
                    ).
                    $("Hello").em().$($3 -> $3.$("emphasized text")).$("more text").
                    img().src("image3.png").alt("Third image").$()
                )
            );
    }
}
