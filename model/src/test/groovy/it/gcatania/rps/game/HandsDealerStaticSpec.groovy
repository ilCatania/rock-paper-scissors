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
