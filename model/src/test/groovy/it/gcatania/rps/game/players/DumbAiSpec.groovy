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
