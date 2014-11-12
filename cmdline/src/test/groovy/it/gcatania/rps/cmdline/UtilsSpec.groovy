/**
 *
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