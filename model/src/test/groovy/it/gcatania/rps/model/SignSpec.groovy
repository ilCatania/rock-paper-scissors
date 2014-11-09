/**
 *
 */
package it.gcatania.rps.model

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author ilCatania
 *
 */
class SignSpec extends Specification {

    @Unroll
    def "name of sign number #id is #name"() {
        expect: new Sign(id).name == name

        where:
        id || name
        0 || 'Rock'
        1 || 'Paper'
        2 || 'Scissors'
        3 || 'Spock'
        4 || 'Lizard'
        16 || '16'
    }

    @Unroll
    def "outcome of sign #s1 vs sign #s2 among #n signs is #o"() {
        expect: Sign.outcome(new Sign(s1), new Sign(s2), n) == o

        where:
        s1 | s2 | n || o
        2 | 1 | 3 || -1
        2 | 1 | 5 || -1
        2 | 1 | 7 || -1
        3 | 0 | 5 || -1
        5 | 0 | 9 || -1
        0 | 0 | 3 || 0
        0 | 0 | 9 || 0
        1 | 2 | 3 || 1
        1 | 2 | 5 || 1
        1 | 2 | 7 || 1
        0 | 3 | 5 || 1
        0 | 5 | 9 || 1
    }

    @Unroll
    def "#winner wins over #loser means #msg"() {
        expect:
        Sign w = new Sign(winner)
        Sign l = new Sign(loser)
        String verb = Sign.getVictoryVerb(w, l)
        "${w.name} $verb ${l.name}" == msg

        where:
        winner | loser || msg
        2 | 1 || 'Scissors cut Paper'
        1 | 0 || 'Paper covers Rock'
        0 | 4 || 'Rock crushes Lizard'
        4 | 3 || 'Lizard poisons Spock'
        3 | 2 || 'Spock smashes Scissors'
        2 | 4 || 'Scissors decapitate Lizard'
        4 | 1 || 'Lizard eats Paper'
        1 | 3 || 'Paper disproves Spock'
        3 | 0 || 'Spock vaporizes Rock'
        0 | 2 || 'Rock crushes Scissors'
        // generic case
        9 | 11 || '9 wins over 11'
    }
}
