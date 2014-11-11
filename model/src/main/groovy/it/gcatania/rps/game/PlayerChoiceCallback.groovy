/**
 *
 */
package it.gcatania.rps.game

import it.gcatania.rps.game.exception.SignChoiceException

/**
 * @author ilCatania
 */
interface PlayerChoiceCallback {

    /**
     * @param s the sign chosen by the player. The player should make sure to provide a sign
     * within the hand's allowed time, otherwise he will risk automatically losing the hand.
     * @throws SignChoiceException if the performed choice is no longer allowed or invalid
     */
    void choose(Sign s) throws SignChoiceException
}
