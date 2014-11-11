/**
 *
 */
package it.gcatania.rps.game.exception


/**
 * @author ilCatania
 */
class HandTimeoutException extends SignChoiceException {

    final long handTimeoutMillis
    final long participantChoiceTimeMillis

    HandTimeoutException(int playerId, long handTimeoutMillis, long participantChoiceTimeMillis) {
        super(playerId)
        this.handTimeoutMillis = handTimeoutMillis
        this.participantChoiceTimeMillis = participantChoiceTimeMillis
    }

    @Override
    String toString() {
        return "Player $playerId took $participantChoiceTimeMillis millis to pick a sign, maximum allowed is $handTimeoutMillis millis"
    }
}
