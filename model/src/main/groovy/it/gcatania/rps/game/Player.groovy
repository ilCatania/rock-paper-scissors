/**
 *
 */
package it.gcatania.rps.game

/**
 * @author ilCatania
 */
interface Player {

    /**
     * initiates a playing hand.
     * @param maxSigns the maximum number of signs allowed (e.g.
     * three for standard rock-paper-scissors)
     * @param callback the sign choice callback. Each player
     * should take care in calling it before the session time has
     * expired, otherwise he risks automatically losing the hand.
     */
    void play(int maxSigns, PlayerChoiceCallback callback)
}
