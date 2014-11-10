/**
 *
 */
package it.gcatania.rps.model

import groovy.transform.EqualsAndHashCode

/**
 * a rock-paper-scissors sign, e.g. "Rock". Signs are uniquely identified by a
 * numeral to allow for more complex variations of the basic three-sign game
 * (e.g. rock-paper-scissors-lizard-spock)
 * @author ilCatania
 */
class Sign {

    /**
     * internal class to map victory combinations to the appropriate verb
     * (cosmetics)
     */
    @EqualsAndHashCode
    private static class SignCombination {
        int winner
        int loser

        SignCombination(int w, int l) {
            winner = w; loser = l
        }
        SignCombination(Sign w, Sign l) {
            winner = w.id; loser = l.id
        }
    }

    private static final Map<SignCombination, String> VICTORY_VERBS
    static {
        VICTORY_VERBS = [:]
        VICTORY_VERBS[new SignCombination(2, 1)] = 'cut'
        VICTORY_VERBS[new SignCombination(1, 0)] = 'covers'
        VICTORY_VERBS[new SignCombination(0, 4)] = 'crushes'
        VICTORY_VERBS[new SignCombination(4, 3)] = 'poisons'
        VICTORY_VERBS[new SignCombination(3, 2)] = 'smashes'
        VICTORY_VERBS[new SignCombination(2, 4)] = 'decapitate'
        VICTORY_VERBS[new SignCombination(4, 1)] = 'eats'
        VICTORY_VERBS[new SignCombination(1, 3)] = 'disproves'
        VICTORY_VERBS[new SignCombination(3, 0)] = 'vaporizes'
        VICTORY_VERBS[new SignCombination(0, 2)] = 'crushes'
    }

    static final Sign ROCK = new Sign(0)
    static final Sign PAPER = new Sign(1)
    static final Sign SCISSORS = new Sign(2)
    static final Sign SPOCK = new Sign(3)
    static final Sign LIZARD = new Sign(4)

    /**
     * the sign id, 0-based
     */
    final int id

    Sign(int id) {
        this.id = id
    }

    /**
     * the outcome of comparing two signs
     * @param first the first sign
     * @param second the second sign
     * @return -1 if the first sign wins, 0 for a tie, 1 if the second sign wins
     */
    static int outcome(Sign first, Sign second) {
        // each sign "i" wins on all next signs in the form "i+2*j"
        // this has the property of preserving existing win/lose relationships
        // across game sessions with different numbers of signs
        if(first.id == second.id) return 0

        int delta = second.id - first.id
        int deltaSign = Integer.signum(delta)
        return (delta % 2 == 0) ? -deltaSign : deltaSign
    }

    /**
     * @return the sign name, for cosmetic purposes
     */
    String getName() {
        switch(id) {
            case 0: return 'Rock'
            case 1: return 'Paper'
            case 2: return 'Scissors'
            case 3: return 'Spock'
            case 4: return 'Lizard'
            default: return String.valueOf(id)
        }
    }

    int hashCode() {
        return id
    }

    boolean equals(Object other) {
        return (other instanceof Sign) && id == other.id
    }

    String toString() {
        if(id > 4) return String.valueOf(id)
        return "$id ($name)"
    }

    /**
     * @param winner the winning sign
     * @param loser the losign sign
     * @return the victory verb, e.g. {@code 'disproves'} for 'Paper disproves Spock' - never null
     */
    static String getVictoryVerb(Sign winner, Sign loser) {
        return VICTORY_VERBS[new SignCombination(winner, loser)] ?: 'wins over'
    }
}
