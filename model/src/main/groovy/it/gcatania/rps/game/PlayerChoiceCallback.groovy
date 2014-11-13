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

import it.gcatania.rps.game.exception.SignChoiceException

/**
 * @author ilCatania
 */
interface PlayerChoiceCallback {

    /**
     * @param s the sign chosen by the player. The player should make sure to provide a sign
     * within the hand's allowed time, otherwise he will risk automatically losing the hand.
     * @throws SignChoiceException if the performed choice is no longer allowed or invalid
     */
    void choose(Sign s) throws SignChoiceException
}
