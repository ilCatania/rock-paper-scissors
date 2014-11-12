/**
 *
 */
package it.gcatania.rps.cmdline

import spock.lang.Specification

/**
 * @author ilCatania
 *
 */
abstract class ConsoleMockingSpec extends Specification {

    InputStream mockIn = Mock(InputStream)
    PrintStream mockOut = Mock(PrintStream)

    private InputStream originalIn
    private PrintStream originalOut

    def setup() {
        (originalIn, originalOut) = [System.in, System.out]
        System.in = mockIn ; System.out = mockOut
    }

    def cleanup() {
        System.in = originalIn ; System.out = originalOut
    }
}
