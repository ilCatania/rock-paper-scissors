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

/**
 * @author ilCatania
 */
interface Player {

    /**
     * @return the player name (may be null)
     */
    String getName()

    /**
     * initiates a playing hand.
     * @param maxSigns the maximum number of signs allowed (e.g.
     * three for standard rock-paper-scissors)
     * @param timeoutMillis the hand timeout, in millisecond
     * @param callback the sign choice callback. Each player
     * should take care in calling it before the hand timeout
     * expires, otherwise he risks automatically losing the hand.
     */
    void play(int maxSigns, long timeoutMillis, PlayerChoiceCallback callback)
}
