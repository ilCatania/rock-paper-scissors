/*
 * Copyright 2014 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package it.gcatania.rps.cmdline

/**
 * @author ilCatania
 *
 */
final class Utils {

    static void table(def rows, boolean header) {
        def rowStrings = rows.collect({it.collect({String.valueOf(it)})})
        def columnWidths = rowStrings.transpose()*.collect({it.length()})*.max()

        rowStrings.eachWithIndex { cells, i ->
            StringBuilder rowOutput = new StringBuilder().append('|')
            cells.eachWithIndex { String cell, j ->
                rowOutput.append(' ').append(cell.padRight(columnWidths[j])).append(' |')
            }
            println rowOutput.toString()
            if(i == 0 && header) {
                println( '=' * (columnWidths.sum() + 3 * columnWidths.size() + 1))
            }
        }
    }
}
