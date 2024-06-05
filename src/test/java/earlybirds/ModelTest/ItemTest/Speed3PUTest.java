package earlybirds.ModelTest.ItemTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Types.Speed3PU;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.junit.After;

@RunWith(GdxTestRunner.class)
public class Speed3PUTest {

    private Texturepack texturepack;
    private Speed3PU speed3PU;
    private Player player;

    @Before
    public void setUp() {
        texturepack = new Texturepack();
        speed3PU = new Speed3PU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        texturepack = null;
        speed3PU = null;
        player = null;
    }

    @Test
    public void createSpeed3PUObjectTest() {
        assertNotNull(speed3PU);
    }

    @Test
    public void setSpeed3PUPositionTest() {
        speed3PU.setPos(0, 0);
        assertEquals(0, speed3PU.getSprite().getX() + speed3PU.getSprite().getWidth() / 2, 0.01);
        assertEquals(0, speed3PU.getSprite().getY() + speed3PU.getSprite().getHeight() / 2, 0.01);

        speed3PU.setPos(10, 10);
        assertEquals(10, speed3PU.getSprite().getX() + speed3PU.getSprite().getWidth() / 2, 0.01);
        assertEquals(10, speed3PU.getSprite().getY() + speed3PU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void doPowerUpEffectSpeed3PUTest() {
        float playerSpeed = player.getSpeed();
        float speedBoost = speed3PU.getSpeedBoost();
        assertEquals(5f, playerSpeed, 0.0001f);

        speed3PU.doPowerUpEffect(player);
        assertEquals(playerSpeed + speedBoost, player.getSpeed(), 0.0001f);

        assertNotEquals(player.getSpeed(), playerSpeed, 0.0001f);

    }

    @Test
    public void removePowerUpEffectSpeed3PUTest() {

        speed3PU.doPowerUpEffect(player);

        speed3PU.removeSpeedPU(player);

        assertEquals(5f, player.getSpeed(), 0.0001f);

    }

    @Test
    public void getSpriteSpeedPUTest() {
        assertNotNull(speed3PU.getSprite());
    }

}
