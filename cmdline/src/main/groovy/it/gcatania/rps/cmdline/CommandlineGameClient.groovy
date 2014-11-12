/**
 *
 */
package it.gcatania.rps.cmdline

import it.gcatania.rps.cmdline.io.CharProvider
import it.gcatania.rps.game.Game
import it.gcatania.rps.game.GameSettings
import it.gcatania.rps.game.GameStatus
import it.gcatania.rps.game.HandOutcome
import it.gcatania.rps.game.Player
import it.gcatania.rps.game.Sign
import it.gcatania.rps.game.players.DumbAi
import it.gcatania.rps.game.players.UnpredictableAi
import jline.console.ConsoleReader


/**
 * @author ilCatania
 */
class CommandlineGameClient {

    private static final int TWO_COLS_WIDTH = 15

    private final CharProvider provider
    private final ConsoleReader console

    CommandlineGameClient(ConsoleReader console) {
        provider = new CharProvider(console)
        this.console = console
    }

    void quickPlayAgainstAi() {
        run(new Game(GameSettings.DEFAULT_RPS, [
            new KeyboardInputPlayer("Player", 'qwe'),
            new UnpredictableAi()
        ] as Player[]))
    }

    void quickPlayAgainstFriend() {
        run(new Game(GameSettings.DEFAULT_RPS, [
            new KeyboardInputPlayer("Player left", 'qwe'),
            new KeyboardInputPlayer("Player right", 'iop')
        ] as Player[]))
    }

    void quickSkirmishAgainstAi() {
        run(new Game(GameSettings.DEFAULT_RPSLS, [
            new KeyboardInputPlayer("Player", 'qwert'),
            new UnpredictableAi(),
            new UnpredictableAi(),
            new DumbAi()
        ] as Player[]))
    }

    void quickSkirmishAgainstFriends() {
        run(new Game(GameSettings.DEFAULT_RPSLS, [
            new KeyboardInputPlayer("Alice", 'qwasz'),
            new KeyboardInputPlayer("Bob", 'erdfc'),
            new KeyboardInputPlayer("Carl", 'tyghb'),
            new KeyboardInputPlayer("Dave", 'uijkm')
        ] as Player[]))
    }

    void playCustom(GameSettings settings, Player[] players) {
        run(new Game(settings, players))
    }

    private void run(Game game) {
        game.players.findAll({ it instanceof KeyboardInputPlayer })*.provider = provider
        printPlayerKeyBindings(game.players)

        while(true) {
            println "Press any key to start hand number ${game.handCount}"
            console.readCharacter()
            provider.startPolling(5)
            showCountdownAsync(game.settings.handTimeoutMillis)
            HandOutcome o = game.dealHand()
            provider.stopPolling()

            // FIXME next 3 lines are a dirty trick to hide the poller
            // being stuck on the last character read
            println "Time's up! Press any key to view the results"
            println()
            provider.joinPollerThread()

            showChoices(game.players*.name, o.playerChoices)
            if(game.status == GameStatus.COMPLETED) {
                printScores(game.players, o.scores, game.scores)
                game.winnerIds.each { println "${game.players[it].name} wins!" }
                println()
                if(promptRestart()) {
                    game.restart()
                    continue
                }  else {
                    return
                }
            }

            char action = promptEndOfHandAction()
            while(action != 'n') {
                switch(action) {
                    case 's':
                        printScores(game.players, o.scores, game.scores)
                        break
                    case 'k':
                        printPlayerKeyBindings(game.players)
                        break
                    case 'a':
                        return
                }
                action = promptEndOfHandAction()
            }
        }
    }

    private void printPlayerKeyBindings(def players) {
        def kPlayers = players.findAll { it instanceof KeyboardInputPlayer }
        if(kPlayers.isEmpty()) {
            println 'No key bindings to display'
            println()
            return
        }
        List<String> keyBindings = kPlayers*.keyBindings

        println 'Key bindings:'

        def rows = [
            ['Sign', kPlayers*.name].flatten()
        ]
        int l = keyBindings[0].length()
        for(int i = 0; i < l; i++) {
            def row = [new Sign(i).name]
            row.addAll(keyBindings*.charAt(i))
            rows.add row
        }
        Utils.table(rows, true)
        println()
    }

    private void showCountdownAsync(long millis) {
        Thread.start {
            def afterSecondsSuffix = " seconds..."
            int seconds = (int) (((double) millis) / 1000d)
            def initialPrompt =
                    print "Players, pick your signs! Time out in ${seconds}... "
            while(seconds > 1) {
                Thread.sleep(1000)
                print "${--seconds}... "
            }
            Thread.sleep(1000)
            println '0!'
        }
    }

    private void printScores(def players, int[] handScores, int[] totalScores) {
        def scoreTable = [
            [
                'Player',
                'Hand score',
                'Total score'
            ],
        ]
        scoreTable.addAll([
            players*.name,
            handScores,
            totalScores
        ].transpose())
        Utils.table(scoreTable, true)
        println()
    }

    private char promptEndOfHandAction() {
        println('(N)ext hand | (S)cores | (K)ey bindings | (A)bort')
        println()
        return console.readCharacter(['n', 's', 'k', 'a'] as char[])
    }

    private boolean promptRestart() {
        println 'Play again ? (Y)es | (N)o'
        int confirm = console.readCharacter(['y', 'n'] as char[])
        println()
        return ((char) confirm)== 'y'
    }

    private void showChoices(List<String> playerNames, Sign[] choices) {
        println 'Picks:'
        [playerNames, choices as List].transpose().each { String name, Sign choice ->
            println (choice != null ?
                    "\t${name} picked:\t${choice.name}" :
                    "\t${name} didn't pick in time!")
        }
        println()
        Set<String> outcomes = new HashSet()
        for(int i = 0;i < choices.length-1; i++) {
            for(int j = 1; j < choices.length; j++) {
                def (s1, s2) = choices[i, j]
                if(Sign.outcome(s1, s2) != 0) {
                    outcomes.add(Sign.outcomeString(s1, s2))
                }
            }
        }
        if(outcomes.isEmpty()) {
            println 'Outcome: tie!'
        } else {
            println 'Outcome:'
            outcomes.each { println '\t' + it }
        }
        println()
    }

}