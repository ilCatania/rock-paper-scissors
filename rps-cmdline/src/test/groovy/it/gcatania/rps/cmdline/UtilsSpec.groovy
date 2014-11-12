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
 */
class UtilsSpec extends ConsoleMockingSpec {

    def 'table with header'() {
        def rows = [
            ['h1', 'h2', 'h3', 'h4'],
            ['a', 12, 'asd', 'asdf'],
            ['123', 'a', 'a', 'a'],
            [1, -1101, 0, 'aa']
        ]

        when:
        Utils.table(rows, true)

        then:
        1*mockOut.println('| h1  | h2    | h3  | h4   |')
        1*mockOut.println('============================')
        1*mockOut.println('| a   | 12    | asd | asdf |')
        1*mockOut.println('| 123 | a     | a   | a    |')
        1*mockOut.println('| 1   | -1101 | 0   | aa   |')
        0 * _
    }

    def 'table without header'() {
        def rows = [
            ['h1', 'h2', 'h3', 'h4'],
            ['a', 12, 'asd', 'asdf'],
            ['123', 'a', 'a', 'a'],
            [1, -1101, 0, 'aa']
        ]

        when:
        Utils.table(rows, false)

        then:
        1*mockOut.println('| h1  | h2    | h3  | h4   |')
        1*mockOut.println('| a   | 12    | asd | asdf |')
        1*mockOut.println('| 123 | a     | a   | a    |')
        1*mockOut.println('| 1   | -1101 | 0   | aa   |')
        0 * _
    }
}