/**
 *
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
