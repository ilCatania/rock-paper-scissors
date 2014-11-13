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
package it.gcatania.rps.game.players

import it.gcatania.rps.game.Player
import it.gcatania.rps.game.PlayerChoiceCallback
import it.gcatania.rps.game.Sign


/**
 * convenience class for synchronous player implementations (typically AIs)
 * @author ilCatania
 */
abstract class SynchronousPlayer implements Player {

    @Override
    public String getName() {
        return getClass().simpleName
    }

    @Override
    public void play(int maxSigns, long timeoutMillis, PlayerChoiceCallback callback) {
        int signId = chooseSignId(maxSigns)
        Sign s = new Sign(signId)
        callback.choose(s)
    }

    protected abstract int chooseSignId(int maxSigns)
}
