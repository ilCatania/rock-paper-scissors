/**
 *
 */
package it.gcatania.rps.game

/**
 * @author ilCatania
 */
interface Player {

    /**
     * @return the player name (may be null)
     */
    String getName()

    /**
     * initiates a playing hand.
     * @param callback the sign choice callback. Each player
     * should take care in calling it before the session time has
     * expired, otherwise he risks automatically losing the hand.
     */
    void play(PlayerChoiceCallback callback)
}
