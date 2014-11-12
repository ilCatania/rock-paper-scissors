/**
 *
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
        .addItem('ADVANCED_GAME_SETUP', 'Set up a customized game')
        .addItem('TUTORIAL', 'Show the tutorial')
        .addItem('QUIT', 'Exit the game')

while(true) {
    switch (mainMenu.prompt(console)) {
        case 'QUICK_PLAY_AI': gameClient.quickPlayAgainstAi(); break
        case 'QUICK_PLAY_HUMAN': gameClient.quickPlayFriend(); break
        // case 'ADVANCED_GAME_SETUP': gameClient.playCustom( TODO ); break
        case 'TUTORIAL': showTutorial() ; break
        case 'QUIT': return
    }
}