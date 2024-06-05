package earlybirds.Model;

/**
 * A simple gamestate class which stores a gamestate-enum value and allows it to
 * be changed.
 */
public class GameState {
    private GameStateEnum gameState;

    /**
     * Constructor for gamestate. The default value is Setup.
     */
    public GameState() {
        gameState = GameStateEnum.SETUP;
    }

    /**
     * Sets the gameStateEnum value to the given value
     * 
     * @param gameStateEnum
     */
    public void setGameState(GameStateEnum gameStateEnum) {
        this.gameState = gameStateEnum;

    }

    /**
     * @return the gameStateEnum value
     */
    public GameStateEnum getGameStateEnum() {
        return gameState;
    }

}
