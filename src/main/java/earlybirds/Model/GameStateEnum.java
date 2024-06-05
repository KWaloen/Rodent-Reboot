package earlybirds.Model;

/**
 * Enum class to store all possible ggamestates
 */
public enum GameStateEnum {
    /**
     * When the game first starts and you see the setup screen
     */
    SETUP,
    /**
     * When the game is actively being played
     */
    PLAYING,
    /**
     * When player has died and the game is ending.
     */
    GAMEOVER,
    /**
     * When the exit button has been pressed and the program terminates.
     */
    EXIT,
    /**
     * Restarts the game.
     */
    RESTART

}
