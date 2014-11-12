/**
 *
 */
package it.gcatania.rps.cmdline

import it.gcatania.rps.cmdline.io.CharProvider
import it.gcatania.rps.game.Player
import it.gcatania.rps.game.PlayerChoiceCallback
import it.gcatania.rps.game.Sign


/**
 * @author ilCatania
 */
class KeyboardInputPlayer implements Player{

    public final String keyBindings
    private final String name
    private CharProvider provider

    /**
     * @param name the player name
     * @param keyBindings the list of keys this player may use to choose signs (the chosen
     * sign id will be the character index in the string)
     */
    public KeyboardInputPlayer(String name, String keyBindings) {
        this.name = name
        this.keyBindings = keyBindings
    }

    @Override
    public String getName() {
        return name
    }

    @Override
    public void play(int maxSigns, long timeoutMillis, PlayerChoiceCallback callback) {
        def listener = { char input ->
            for(int i = 0; i < keyBindings.length() && i < maxSigns; i++) {
                if(keyBindings.charAt(i) == input) {
                    callback.choose(new Sign(i))
                    break
                }
            }
        }
        provider.addListener listener
        try {
            Thread.sleep(timeoutMillis)
        } finally {
            provider.removeListener listener
        }
    }

    public void setProvider(CharProvider provider) {
        this.provider = provider
    }
}
