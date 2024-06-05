package earlybirds.ModelTest.UiTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.GameState;
import earlybirds.Model.GameStateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
public class GameStateTest {

    @Test
    public void testDefaultState() {
        GameState gameState = new GameState();
        assertEquals(GameStateEnum.SETUP, gameState.getGameStateEnum());
    }

    @Test
    public void testSetGameState() {
        GameState gameState = new GameState();

        gameState.setGameState(GameStateEnum.PLAYING);
        assertEquals(GameStateEnum.PLAYING, gameState.getGameStateEnum());

        gameState.setGameState(GameStateEnum.GAMEOVER);
        assertEquals(GameStateEnum.GAMEOVER, gameState.getGameStateEnum());

        gameState.setGameState(GameStateEnum.EXIT);
        assertEquals(GameStateEnum.EXIT, gameState.getGameStateEnum());

        gameState.setGameState(GameStateEnum.RESTART);
        assertEquals(GameStateEnum.RESTART, gameState.getGameStateEnum());

        gameState.setGameState(GameStateEnum.SETUP);
        assertEquals(GameStateEnum.SETUP, gameState.getGameStateEnum());
    }

}
