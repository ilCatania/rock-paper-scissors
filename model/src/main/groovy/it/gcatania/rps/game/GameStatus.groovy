package it.gcatania.rps.game

/**
 * @author ilCatania
 */
enum GameStatus {

    /**
     * the game session has just been created and is yet to be started
     */
    NEW,

    /**
     * the game session is started
     */
    ONGOING,

    /**
     * the game session has a winner
     */
    COMPLETED
}
