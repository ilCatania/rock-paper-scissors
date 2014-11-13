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
