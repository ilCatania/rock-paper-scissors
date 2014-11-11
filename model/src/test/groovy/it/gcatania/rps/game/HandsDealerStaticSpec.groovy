/**
 *
 */
package it.gcatania.rps.game

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author ilCatania
 */
class HandsDealerStaticSpec extends Specification {

    @Unroll
    def "score for choices #choices is #score"() {
        expect:
        def signChoices = choices.collect { new Sign(it) }
        HandsDealer.calculateScores(signChoices as Sign[]) == score

        where:
        choices || score
        [0, 1]|| [0, 1]
        [0, 2]|| [1, 0]
        [1, 2]|| [0, 1]
        [1, 0]|| [1, 0]
        [2, 0]|| [0, 1]
        [2, 1]|| [1, 0]
    }
}
