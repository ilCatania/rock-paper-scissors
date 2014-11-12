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
