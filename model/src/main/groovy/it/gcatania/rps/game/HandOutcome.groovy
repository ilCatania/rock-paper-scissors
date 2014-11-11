/**
 *
 */
package it.gcatania.rps.game

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


/**
 * @author ilCatania
 */
@EqualsAndHashCode @ToString
class HandOutcome {

    /**
     * the sign choices for all hand participants, some of which may be null if no choice was made
     */
    public final Sign[] playerChoices
    public final int[] scores

    /**
     * @param participantChoices the sign choices for all hand participants
     */
    public HandOutcome(Sign[] playerChoices, int[] scores) {
        this.playerChoices = playerChoices
        this.scores = scores
    }
}
