/**
 *
 */
package it.gcatania.rps.game

import groovy.util.logging.Slf4j
import it.gcatania.rps.game.exception.GameCompletedException

/**
 * @author ilCatania
 */
@Slf4j
class Game {

    private final GameSettings settings
    private final Player[] players
    private final HandsDealer dealer

    private GameStatus status = GameStatus.NEW
    private int handCount = 1
    private int[] scores
    private List winnerIds = []

    Game(GameSettings settings, Player[] players) {
        this.settings = settings
        this.players = players
        dealer = new HandsDealer(settings, players)
        scores = [0]* players.length
    }

    /**
     * @return the outcome of dealing a single hand in this game
     * @throws GameCompletedException if this game is already completed
     */
    HandOutcome dealHand() throws GameCompletedException {
        if(status == GameStatus.COMPLETED) throw new GameCompletedException()
        handCount++
        status = GameStatus.ONGOING
        log.debug('starting new hand: {}', handCount)
        HandOutcome outcome = dealer.deal()

        // add hand scores to global game scores
        scores = [scores, outcome.scores].transpose()*.sum()

        def localWinnerIds = []
        scores.eachWithIndex { score, playerId ->
            if(score >= settings.scoreTreshold) {
                localWinnerIds.add(playerId)
            }
        }
        winnerIds.addAll(localWinnerIds)
        if(!winnerIds.isEmpty()) {
            log.info('game completed! winners: {}', winnerIds)
            status = GameStatus.COMPLETED
        }
        return outcome
    }

    /**
     * restarts this game with the same settings and players
     */
    void restart() {
        log.info('restarting game (previous status: {})', status)
        scores = [0]* players.length
        handCount = 1
        status = GameStatus.NEW
        winnerIds.clear()
    }

    /**
     * @return the game status. Once a game is completed,
     * no more hands can be dealt until it is restarted
     */
    GameStatus getStatus() {
        return status
    }

    /**
     * @return the number of initiated hands in this game
     */
    int getHandCount() {
        return handCount
    }

    /**
     * @return the player scores
     */
    int[] getScores() {
        return scores.clone()
    }
    /**
     * @return the identifier (ordinals) of winning players, and their respective scores
     */
    List<Integer> getWinnerIds() {
        return winnerIds.clone()
    }
}
