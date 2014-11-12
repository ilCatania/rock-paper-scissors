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
package it.gcatania.rps.cmdline

import it.gcatania.rps.cmdline.io.CharProvider
import it.gcatania.rps.game.Player
import it.gcatania.rps.game.PlayerChoiceCallback
import it.gcatania.rps.game.Sign


/**
 * @author ilCatania
 */
class KeyboardInputPlayer implements Player{

    public final String keyBindings
    private final String name
    private CharProvider provider

    /**
     * @param name the player name
     * @param keyBindings the list of keys this player may use to choose signs (the chosen
     * sign id will be the character index in the string)
     */
    public KeyboardInputPlayer(String name, String keyBindings) {
        this.name = name
        this.keyBindings = keyBindings
    }

    @Override
    public String getName() {
        return name
    }

    @Override
    public void play(int maxSigns, long timeoutMillis, PlayerChoiceCallback callback) {
        def listener = { char input ->
            for(int i = 0; i < keyBindings.length() && i < maxSigns; i++) {
                if(keyBindings.charAt(i) == input) {
                    callback.choose(new Sign(i))
                    break
                }
            }
        }
        provider.addListener listener
        try {
            Thread.sleep(timeoutMillis)
        } finally {
            provider.removeListener listener
        }
    }

    public void setProvider(CharProvider provider) {
        this.provider = provider
    }
}
