package earlybirds.MenuTest.MenuModelTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import earlybirds.GdxTestRunner;
import earlybirds.Menu.FontPack;
import earlybirds.Menu.MenuModels.GameOverMenuModel;
import earlybirds.Menu.MenuModels.StartMenuModel;
import earlybirds.Model.ScoreKeeper;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class menuModelTest {
    StartMenuModel startMenuModel;
    GameOverMenuModel gameOverMenuModel;

    @Before
    public void setup() {
        Gdx.graphics = Mockito.mock(Graphics.class);
        when(Gdx.graphics.getHeight()).thenReturn(1920);
        when(Gdx.graphics.getWidth()).thenReturn(1080);
        startMenuModel = new StartMenuModel(new FontPack(), new Texturepack());
        gameOverMenuModel = new GameOverMenuModel(new FontPack(), new ScoreKeeper(), new Texturepack());
    }

    @After
    public void tearDown() {
        Mockito.reset(Gdx.graphics);
        startMenuModel = null;
        gameOverMenuModel = null;
    }

    @Test
    public void getButtons() {
        assertTrue(startMenuModel.getButtons().size() == 2);
        assertTrue(startMenuModel.getViewableButtons().size() == 4);

        assertTrue(gameOverMenuModel.getButtons().size() == 2);
        assertTrue(gameOverMenuModel.getViewableButtons().size() == 4);
    }
}
