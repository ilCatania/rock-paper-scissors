/**
 *
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
