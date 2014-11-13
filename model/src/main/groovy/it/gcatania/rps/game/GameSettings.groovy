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
     * default settings to play a best-of-three rock/paper/scissors game
     */
    static final GameSettings DEFAULT_RPS = new GameSettings(maxSigns: 3, handTimeoutMillis: 3000L, scoreTreshold: 2)
    /**
     * recommended settings to play rock/paper/scissors/lizard/spock
     */
    static final GameSettings DEFAULT_RPSLS = new GameSettings(maxSigns: 5, handTimeoutMillis: 4000L, scoreTreshold: 10)

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
