/*
 * Copyright 2014 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.gcatania.rps.game

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
        if(first == null) {
            if(second == null) {
                return 0
            }
            return 1
        }
        if(second == null) return -1

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
            default: return "Sign ${id}"
        }
    }

    int hashCode() {
        return id
    }

    boolean equals(Object other) {
        return (other instanceof Sign) && id == other.id
    }

    String toString() {
        if(id > 4) return "(no name) ${id}"
        return "$name ($id)"
    }

    /**
     * @param winner the winning sign
     * @param loser the losign sign
     * @return the victory verb, e.g. {@code 'disproves'} for 'Paper disproves Spock' - never null
     */
    static String outcomeString(Sign s1, Sign s2) {
        int o = outcome(s1, s2)
        if(o == 0) return "Tie!"
        def (winner, loser) = o < 0 ? [s1, s2]: [s2, s1]
        String verb
        if(winner != null && loser != null) verb = VICTORY_VERBS[new SignCombination(winner.id, loser.id)]
        if(verb == null) verb = 'wins over'
        return "${winner?.name} $verb ${loser?.name}!"
    }
}
