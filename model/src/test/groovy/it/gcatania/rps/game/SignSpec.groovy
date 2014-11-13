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
package it.gcatania.rps.game

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
        16 || 'Sign 16'
    }

    @Unroll
    def "outcome of sign #s1 vs sign #s2 signs is #o"() {
        expect: Sign.outcome(new Sign(s1), new Sign(s2)) == o

        where:
        s1 | s2 || o
        2 | 1 || -1
        2 | 1 || -1
        2 | 1 || -1
        3 | 0 || -1
        5 | 0 || -1
        0 | 2 || -1
        0 | 0 || 0
        9 | 9 || 0
        1 | 2 || 1
        1 | 2 || 1
        1 | 2 || 1
        0 | 3 || 1
        0 | 5 || 1
        2 | 0 || 1
    }

    @Unroll
    def "#winner wins over #loser means #msg"() {
        expect:
        Sign w = new Sign(winner)
        Sign l = new Sign(loser)
        Sign.outcome(w, l) == -1 && Sign.outcomeString(w, l) == msg

        where:
        winner | loser || msg
        // from http://en.wikipedia.org/wiki/Rock-paper-scissors-lizard-Spock#Rules
        2 | 1 || 'Scissors cut Paper!'
        1 | 0 || 'Paper covers Rock!'
        0 | 4 || 'Rock crushes Lizard!'
        4 | 3 || 'Lizard poisons Spock!'
        3 | 2 || 'Spock smashes Scissors!'
        2 | 4 || 'Scissors decapitate Lizard!'
        4 | 1 || 'Lizard eats Paper!'
        1 | 3 || 'Paper disproves Spock!'
        3 | 0 || 'Spock vaporizes Rock!'
        0 | 2 || 'Rock crushes Scissors!'
        // generic case
        9 | 11 || 'Sign 9 wins over Sign 11!'
    }

    @Unroll
    def "outcomeString is simmetric for #n1 and #n2"() {
        expect:
        Sign s1 = new Sign(n1)
        Sign s2 = new Sign(n2)
        Sign.outcomeString(s1, s2) == Sign.outcomeString(s2, s1)

        where:
        n1 | n2
        0 | 1
        0 | 2
        0 | 3
        1 | 2
        1 | 3
        2 | 3
    }
}
