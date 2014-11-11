/**
 *
 */
package it.gcatania.rps.game.exception

/**
 * @author ilCatania
 *
 */
class InvalidSignChoiceException extends SignChoiceException {

    final int chosenSignId
    final int maxSigns


    public InvalidSignChoiceException(int playerId, int chosenSignId, int maxSigns) {
        super(playerId)
        this.chosenSignId = chosenSignId
        this.maxSigns = maxSigns
    }

    String toString() {
        return "Player $playerId chose sign $chosenSignId but maximum allowed sign is $maxSigns"
    }
}
