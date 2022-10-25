/*
 * Copyright 2022 Gabriele Catania <gabriele.ctn@gmail.com>
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

/**
 * @author ilCatania
 */
class DumbAi extends SynchronousPlayer{

    int signId = -1

    @Override
    protected int chooseSignId(int maxSigns) {
        if(signId < 0) {
            signId = new Random().nextInt(maxSigns)
        }
        return Math.min(signId, maxSigns-1)
    }
}
