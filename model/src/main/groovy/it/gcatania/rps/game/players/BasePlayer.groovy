/**
 *
 */
package it.gcatania.rps.game.players

import it.gcatania.rps.game.Player
import it.gcatania.rps.game.PlayerChoiceCallback
import it.gcatania.rps.game.Sign

/**
 * @author ilCatania
 */
abstract class BasePlayer implements Player {

    @Override
    public void play(int maxSigns, long timeoutMillis, PlayerChoiceCallback callback) {
        int signId = chooseSignId(maxSigns)
        Sign s = new Sign(signId)
        callback.choose(s)
    }

    protected abstract int chooseSignId(int maxSigns)
}
