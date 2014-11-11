/**
 *
 */
package it.gcatania.rps.game.exception

/**
 * @author ilCatania
 */
abstract class SignChoiceException extends RuntimeException {
    final int playerId
    public SignChoiceException(int playerId) {
        this.playerId = playerId
    }
}