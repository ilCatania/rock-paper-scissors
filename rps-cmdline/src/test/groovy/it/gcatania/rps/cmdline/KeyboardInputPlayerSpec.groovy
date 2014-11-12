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

import it.gcatania.rps.cmdline.io.CharProvider
import it.gcatania.rps.game.PlayerChoiceCallback
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author ilCatania
 *
 */
class KeyboardInputPlayerSpec extends Specification {

    def "keyboard player registers listeners"() {
        CharProvider mockProvider = Mock(CharProvider)
        PlayerChoiceCallback mockCallback = Mock(PlayerChoiceCallback)
        def player = new KeyboardInputPlayer('name', 'asd')
        player.provider = mockProvider


        when:
        player.play(3, 1000, mockCallback)

        then:
        1 * mockProvider.addListener(_)
        1 * mockProvider.removeListener(_)
    }

    @Unroll
    def "player name is #n, keyBindings are #k"() {
        def player = new KeyboardInputPlayer(n, k)

        expect:
        player.name == n
        player.keyBindings == k

        where:
        n | k
        'myname' | 'asdf'
        null | null
    }
}
