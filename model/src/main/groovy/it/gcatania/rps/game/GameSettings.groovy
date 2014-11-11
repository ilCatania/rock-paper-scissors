
/**
 *
 */
package it.gcatania.rps.game

import groovy.transform.ToString
import it.gcatania.rps.game.exception.InvalidGameSettingsException

/**
 * @author ilCatania
 */
@ToString
class GameSettings {

    /**
     * the number of different signs allowed for this session (e.g. three for standard rock-paper-scissors)
     */
    int maxSigns

    /**
     * the time allotted for participants to pick their sign in each hand
     */
    long handTimeoutMillis

    /**
     * the score required to win the session
     */
    int scoreTreshold

    void validate() throws InvalidGameSettingsException {
        if(maxSigns < 3 || (maxSigns % 2) != 1) {
            throw new InvalidGameSettingsException("invalid maxSigns value: ${maxSigns} - must be odd and greater or equal to 3")
        }
        if(handTimeoutMillis < 1000) {
            throw new InvalidGameSettingsException("invalid handTimeoutMillis value: ${handTimeoutMillis} - must be at least 1000 milliseconds")
        }
        if(scoreTreshold < 1) {
            throw new InvalidGameSettingsException("invalid scoreTreshold value: ${scoreTreshold} - must be at least 1")
        }
    }
}
