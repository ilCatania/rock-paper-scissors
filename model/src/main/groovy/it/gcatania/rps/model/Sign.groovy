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

    private static Map<SignCombination, String> victoryVerbs
    static {
        victoryVerbs = [:]
        victoryVerbs[new SignCombination(2, 1)] = 'cut'
        victoryVerbs[new SignCombination(1, 0)] = 'covers'
        victoryVerbs[new SignCombination(0, 4)] = 'crushes'
        victoryVerbs[new SignCombination(4, 3)] = 'poisons'
        victoryVerbs[new SignCombination(3, 2)] = 'smashes'
        victoryVerbs[new SignCombination(2, 4)] = 'decapitate'
        victoryVerbs[new SignCombination(4, 1)] = 'eats'
        victoryVerbs[new SignCombination(1, 3)] = 'disproves'
        victoryVerbs[new SignCombination(3, 0)] = 'vaporizes'
        victoryVerbs[new SignCombination(0, 2)] = 'crushes'
    }

    private final int id

    Sign(int id) {
        this.id = id
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

    String toString() {
        if(id > 4) return String.valueOf(id)
        return "$id ($name)"
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
        // across different numbers of maximum signs
        if(first.id == second.id) return 0
        if(first.id < second.id) {
            int delta = second.id - first.id
            return (delta % 2 == 0) ? -1: 1
        }
        return -outcome(second, first)
    }

    /**
     * @param winner the winning sign
     * @param loser the losign sign
     * @return the victory verb, e.g. {@code 'disproves'} for 'Paper disproves Spock' - never null
     */
    static String getVictoryVerb(Sign winner, Sign loser) {
        return victoryVerbs[new SignCombination(winner, loser)] ?: 'wins over'
    }
}
