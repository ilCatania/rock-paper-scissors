/**
 *
 */
package it.gcatania.rps.game.players

import it.gcatania.rps.game.PlayerChoiceCallback

/**
 * @author ilCatania
 */
class UnpredictableAi extends BasePlayer {

    private final Random r = new Random()

    @Override
    protected int chooseSignId(int maxSigns) {
        return r.nextInt(maxSigns)
    }
}
