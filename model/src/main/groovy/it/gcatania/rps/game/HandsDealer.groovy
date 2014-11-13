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

import groovy.util.logging.Slf4j
import it.gcatania.rps.game.exception.HandTimeoutException
import it.gcatania.rps.game.exception.InvalidSignChoiceException

import java.util.concurrent.Callable
import java.util.concurrent.Executors



/**
 * helper class to deal game hands
 * @author ilCatania
 */
@Slf4j
class HandsDealer {

    /**
     * cautionary timeout margin to ensure choices picked close to the timeout expiration are registered
     */
    private static final long CAUTIONARY_TIMEOUT_MARGIN_MILLIS = 25

    private final GameSettings settings
    private final Player[] players
    private final int playerCount


    HandsDealer(GameSettings settings, Player[] players) {
        this.settings = settings
        this.players = players
        playerCount = players.length
    }

    protected Sign[] collectPlayerChoices() {
        Sign[] playerChoices = new Sign[playerCount]

        def pool = Executors.newFixedThreadPool(playerCount)
        log.debug('starting collecting choices for {} players', playerCount)
        players.eachWithIndex { final Player p, final int playerId ->
            pool.submit({
                long startMillis = System.currentTimeMillis()
                log.trace('requesting choice from player {} at {}', playerId, startMillis)

                p.play(settings.maxSigns, settings.handTimeoutMillis, { Sign s->
                    long choiceTimeMillis = System.currentTimeMillis() - startMillis
                    log.trace('player {} answered within {} millis', playerId, choiceTimeMillis)

                    if(choiceTimeMillis > settings.handTimeoutMillis)  {
                        throw new HandTimeoutException(playerId, settings.handTimeoutMillis, choiceTimeMillis)
                    }

                    if(s == null) {
                        log.debug('player {} chose no sign', playerId)
                        return
                    }
                    if(s.id < 0 || s.id >= settings.maxSigns) {
                        throw new InvalidSignChoiceException(playerId, s.id, settings.maxSigns)
                    }

                    synchronized (playerChoices) {
                        playerChoices[playerId] = s
                    }
                    log.trace('registered choice for player {}: {}', playerId, s)
                })
            } as Callable)
            log.trace('choice request completed for player {}', playerId)
        }

        log.trace('started collecting choices, waiting for answers from all players')
        Thread.sleep(settings.handTimeoutMillis + CAUTIONARY_TIMEOUT_MARGIN_MILLIS * playerCount)
        log.trace('choice timeout expired, finalizing entered choices')

        Sign[] finalChoices
        synchronized(playerChoices) {
            finalChoices = playerChoices.clone()
        }
        pool.shutdown()
        return finalChoices
    }

    protected static int[] calculateScores(Sign[] choices) {
        int[] scores = [0]* choices.length
        for(int i = 0; i < choices.length-1; i++) {
            Sign first = choices[i]
            for(int j = i+1; j < choices.length; j++) {
                Sign second = choices[j]
                int outcome = Sign.outcome(first, second)
                if(outcome < 0 ) scores[i]++
                else if(outcome > 0 ) scores[j]++
            }
        }
        return scores
    }

    /**
     * @return the outcome of dealing a hand
     */
    HandOutcome deal() {
        Sign[] choices = collectPlayerChoices()
        int[] scores = calculateScores(choices)
        return new HandOutcome(choices, scores)
    }
}
