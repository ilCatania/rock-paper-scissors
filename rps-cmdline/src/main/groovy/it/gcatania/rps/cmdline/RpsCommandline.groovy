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
package it.gcatania.rps.cmdline

import it.gcatania.rps.game.GameSettings
import it.gcatania.rps.game.players.DumbAi
import it.gcatania.rps.game.players.UnpredictableAi
import jline.console.ConsoleReader

def console = new ConsoleReader()
def gameClient = new CommandlineGameClient(console)
def mainMenu = new Menu('Welcome to Rock-Paper-Scissors!')
        .addItem('QUICK_PLAY_AI', 'Play a quick 1v1 match against the computer')
        .addItem('QUICK_PLAY_HUMAN', 'Play a quick 1v1 local match against a friend')
        .addItem('QUICK_SKIRMISH_AI', 'Play a 4-player, 5-sign skirmish against the computer')
        .addItem('QUICK_SKIRMISH_HUMAN', 'Play a 4-player, 5-sign skirmish against your friends')
        .addItem('ADVANCED_GAME_SETUP', 'Set up a customized game')
        .addItem('TUTORIAL', 'Show the tutorial')
        .addItem('QUIT', 'Exit the game')

while(true) {
    switch (mainMenu.prompt(console)) {
        case 'QUICK_PLAY_AI': gameClient.quickPlayAgainstAi(); break
        case 'QUICK_PLAY_HUMAN': gameClient.quickPlayAgainstFriend(); break
        case 'QUICK_SKIRMISH_AI': gameClient.quickSkirmishAgainstAi(); break
        case 'QUICK_SKIRMISH_HUMAN': gameClient.quickSkirmishAgainstFriends(); break
        case 'ADVANCED_GAME_SETUP':
            def (settings, players) = promptForCustomSettings(console)
            gameClient.playCustom( settings, players )
            break
        case 'TUTORIAL': showTutorial() ; break
        case 'QUIT': return
    }
}

def promptForCustomSettings(ConsoleReader console) {
    def intParser = { String valueStr, Closure otherChecks ->
        int i
        try {
            i = Integer.parseInt(valueStr)
        } catch(NullPointerException | NumberFormatException e) {
            println "Invalid number: ${valueStr}"
            return null
        }
        return otherChecks(i)
    }

    int maxSigns = promptForValue(console, 'Number of available hand signs (3, 5, 7, ...): ', {
        return  intParser(it, { i ->
            if(i >= 3 && (i % 2)== 1) return i
            println 'Available hand signs should be an odd value greater than or equal to 3'
            return null
        })
    })

    long handTimeoutMillis = promptForValue(console, 'Hand timeout (seconds): ', {
        return  intParser(it, { i ->
            if(i > 0) return ((long) i) * 1000L
            println 'Hand timeout should be 1 second or greater'
        } )
    })

    int scoreTreshold = promptForValue(console, 'Maximum game score: ', {
        def i = intParser(it, {i ->
            if(i > 0) return i
            println 'Maximum game score should be 1 or greater'
            return null
        })
    })

    println()

    def players = []
    def playerRosterMenu = new Menu('Player roster')
            .addItem('ADD_HUMAN', 'Add new human player')
            .addItem('ADD_RANDOM_AI', 'Add new unpredictable AI (will play randomly)')
            .addItem('ADD_DUMB_AI', 'Add new dumb AI (will pick a sign randomly and keep playing it forever)')
            .addItem('REMOVE_LAST', 'Remove the last added player')
            .addItem('CLEAR_ALL', 'Clear the player roster')
            .addItem('DONE', 'Start the game!')

    while(true) {

        println()
        println "Players in roster: ${players.size()}"
        println()

        switch(playerRosterMenu.prompt(console)) {
            case 'ADD_HUMAN':
                def humanPlayer = setupNewHumanPlayer(console, maxSigns)
                players.add(humanPlayer)
                println "Added ${humanPlayer}."
                break
            case 'ADD_RANDOM_AI':
                players.add(new UnpredictableAi())
                println 'Unpredictable AI added.'
                break
            case 'ADD_DUMB_AI':
                players.add(new DumbAi())
                println 'Dumb AI added.'
                break
            case 'REMOVE_LAST':
                if(players.isEmpty()) println 'Nothing to remove.'
                else {
                    def removed = players.pop()
                    println "Removed: ${removed.name}"
                }
                break
            case 'CLEAR_ALL':
                players.clear()
                println('Players cleared.')
                break
            case 'DONE':
                if(players.size() < 2) {
                    println 'You need at least 2 players!'
                    break
                }

                return [
                    new GameSettings(maxSigns: maxSigns, handTimeoutMillis: handTimeoutMillis, scoreTreshold: scoreTreshold),
                    players
                ]
        }
    }
}

def setupNewHumanPlayer(ConsoleReader console, int maxSigns) {
    String name = promptForValue(console, 'Player name: ', Closure.IDENTITY)
    String keyBindings = promptForValue(console, "Key bindings (at least ${maxSigns} different keys): ", { String valueStr ->
        def chars = valueStr.chars as List
        def uniqueChars = new HashSet()
        def dupes = chars.collect({ return uniqueChars.add(it) ? null : it }).findAll({ it != null }).unique(false)
        if(!dupes.isEmpty()) {
            println "Duplicate characters: ${dupes}"
            return null
        }
        if(chars.size() < maxSigns) {
            println "Not enough characters provided - ${maxSigns} needed"
            return null
        }
        return valueStr.substring(0, maxSigns)
    })

    return new KeyboardInputPlayer(name, keyBindings)
}

def promptForValue(ConsoleReader console, String prompt, Closure converter) {
    String line = console.readLine(prompt)
    def result = converter(line)
    while(result == null) {
        line = console.readLine(prompt)
        result = converter(line)
    }
    return result
}


void showTutorial() {
    print getClass().getResourceAsStream('tutorial.txt').text
    println()
    println()
}

