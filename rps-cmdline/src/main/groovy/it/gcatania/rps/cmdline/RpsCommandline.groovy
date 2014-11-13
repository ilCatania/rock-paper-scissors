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

import jline.console.ConsoleReader


void showTutorial() {
    print getClass().getResourceAsStream('tutorial.txt').text
    println()
    println()
}

def console = new ConsoleReader()
def gameClient = new CommandlineGameClient(console)
def mainMenu = new Menu('Welcome to Rock-Paper-Scissors!')
        .addItem('QUICK_PLAY_AI', 'Play a quick 1v1 match against the computer')
        .addItem('QUICK_PLAY_HUMAN', 'Play a quick 1v1 local match against a friend')
        .addItem('QUICK_SKIRMISH_AI', 'Play a 4-player, 5-sign skirmish against the computer')
        .addItem('QUICK_SKIRMISH_HUMAN', 'Play a 4-player, 5-sign skirmish against your friends')
        // .addItem('ADVANCED_GAME_SETUP', 'Set up a customized game') TODO implement
        .addItem('TUTORIAL', 'Show the tutorial')
        .addItem('QUIT', 'Exit the game')

while(true) {
    switch (mainMenu.prompt(console)) {
        case 'QUICK_PLAY_AI': gameClient.quickPlayAgainstAi(); break
        case 'QUICK_PLAY_HUMAN': gameClient.quickPlayAgainstFriend(); break
        case 'QUICK_SKIRMISH_AI': gameClient.quickSkirmishAgainstAi(); break
        case 'QUICK_SKIRMISH_HUMAN': gameClient.quickSkirmishAgainstFriends(); break
        // case 'ADVANCED_GAME_SETUP': gameClient.playCustom( TODO ); break
        case 'TUTORIAL': showTutorial() ; break
        case 'QUIT': return
    }
}