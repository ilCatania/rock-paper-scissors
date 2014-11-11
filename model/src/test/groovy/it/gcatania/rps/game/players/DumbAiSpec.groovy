/**
 *
 */
package it.gcatania.rps.game.players

import spock.lang.Specification

/**
 * @author ilCatania
 */
class DumbAiSpec extends Specification {


    def "dumb AI will always pick the same sign, or the closer one if outside maximum signs"() {
        setup:
        DumbAi ai = new DumbAi()
        int signId = ai.chooseSignId(99)

        expect:
        ai.chooseSignId(m) == Math.min(m-1, signId)

        where:
        m << [1, 3, 5, 10, 40, 100, 120]
    }
}
