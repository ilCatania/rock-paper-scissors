/**
 *
 */
package it.gcatania.rps.game.players

import it.gcatania.rps.game.Player
import it.gcatania.rps.game.PlayerChoiceCallback
import it.gcatania.rps.game.Sign


/**
 * convenience class for synchronous player implementations (typically AIs)
 * @author ilCatania
 */
abstract class SynchronousPlayer implements Player {

    @Override
    public String getName() {
        return getClass().simpleName
    }

    @Override
    public void play(int maxSigns, long timeoutMillis, PlayerChoiceCallback callback) {
        int signId = chooseSignId(maxSigns)
        Sign s = new Sign(signId)
        callback.choose(s)
    }

    protected abstract int chooseSignId(int maxSigns)
}
