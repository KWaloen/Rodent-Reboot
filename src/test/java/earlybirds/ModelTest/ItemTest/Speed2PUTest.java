package earlybirds.ModelTest.ItemTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Types.Speed2PU;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.After;

@RunWith(GdxTestRunner.class)
public class Speed2PUTest {

    private Texturepack texturepack;
    private Speed2PU speed2PU;
    private Player player;
    private Texture texture;

    @Before
    public void setUp() {
        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);

        speed2PU = new Speed2PU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        texture = null;
        speed2PU = null;
        player = null;
    }

    @Test
    public void createSpeed2PUObjectTest() {
        assertNotNull(speed2PU);
    }

    @Test
    public void setSpeed2PUPositionTest() {
        speed2PU.setPos(0, 0);
        assertEquals(0, speed2PU.getSprite().getX() + speed2PU.getSprite().getWidth() / 2, 0.01);
        assertEquals(0, speed2PU.getSprite().getY() + speed2PU.getSprite().getHeight() / 2, 0.01);

        speed2PU.setPos(10, 10);
        assertEquals(10, speed2PU.getSprite().getX() + speed2PU.getSprite().getWidth() / 2, 0.01);
        assertEquals(10, speed2PU.getSprite().getY() + speed2PU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void doPowerUpEffectSpeed2PUTest() {
        float playerSpeed = player.getSpeed();
        float speedBoost = speed2PU.getSpeedBoost();
        assertEquals(5f, playerSpeed, 0.0001f);

        speed2PU.doPowerUpEffect(player);
        assertEquals(playerSpeed + speedBoost, player.getSpeed(), 0.0001f);

        assertNotEquals(player.getSpeed(), playerSpeed, 0.0001f);

    }

    @Test
    public void removePowerUpEffectSpeed2PUTest() {

        speed2PU.doPowerUpEffect(player);

        speed2PU.removeSpeedPU(player);

        assertEquals(5f, player.getSpeed(), 0.0001f);

    }

    @Test
    public void getSpriteSpeedPUTest() {
        assertNotNull(speed2PU.getSprite());
    }

}
