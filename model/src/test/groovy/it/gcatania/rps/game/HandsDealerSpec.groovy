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

import it.gcatania.rps.game.exception.HandTimeoutException
import spock.lang.Specification
import spock.util.concurrent.AsyncConditions

/**
 * @author ilCatania
 */
class HandsDealerSpec extends Specification {

    def "test timely choices"() {
        setup:
        Player p0 = Stub(Player)
        Player p1 = Stub(Player)
        Player p2 = Stub(Player)
        GameSettings settings = new GameSettings(maxSigns: 3, handTimeoutMillis: 75L, scoreTreshold: 5)
        HandsDealer dealer = new HandsDealer(settings, [p0, p1, p2] as Player[])

        when:
        Sign[] choices = dealer.collectPlayerChoices()

        then:
        p0.play(3, 75L, _) >> { args -> args[2].choose(Sign.ROCK) }
        p1.play(3, 75L, _) >> { args -> args[2].choose(Sign.PAPER) }
        p2.play(3, 75L, _) >> { args -> args[2].choose(Sign.SCISSORS) }

        expect:
        choices.collect({it.id}) == [0, 1, 2]
    }

    def "test choice timeout"() {
        setup:
        Player p0 = Stub(Player)
        Player p1 = Stub(Player)
        Player p2 = Stub(Player)
        GameSettings settings = new GameSettings(maxSigns: 3, handTimeoutMillis: 75L, scoreTreshold: 5)
        HandsDealer dealer = new HandsDealer(settings, [p0, p1, p2] as Player[])
        AsyncConditions async = new AsyncConditions()

        when:
        Sign[] choices = dealer.collectPlayerChoices()

        then:
        p0.play(3, 75L, _) >> { args -> args[2].choose(Sign.ROCK) }
        p1.play(3, 75L, _) >> { args ->
            Thread.sleep(300)
            try {
                args[2].choose(Sign.PAPER)
            } catch(Exception e) {
                async.evaluate {
                    assert e instanceof HandTimeoutException
                    assert e.playerId == 1
                }
            }
        }
        p2.play(3, 75L, _) >> { args -> args[2].choose(Sign.SCISSORS) }
        async.await()

        //        expect:
        //        choices.collect({it?.id}) == [0, null, 2]
    }
}
