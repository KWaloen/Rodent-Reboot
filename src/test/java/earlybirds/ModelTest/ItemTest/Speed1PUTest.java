package earlybirds.ModelTest.ItemTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Types.Speed1PU;
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
public class Speed1PUTest {

    private Texturepack texturepack;
    private Speed1PU speed1PU;
    private Player player;
    private Texture texture;

    @Before
    public void setUp() {
        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);

        speed1PU = new Speed1PU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        texture = null;
        speed1PU = null;
        player = null;
    }

    @Test
    public void createSpeed1PUObjectTest() {
        assertNotNull(speed1PU);
    }

    @Test
    public void setSpeed1PUPositionTest() {
        speed1PU.setPos(0, 0);
        assertEquals(0, speed1PU.getSprite().getX() + speed1PU.getSprite().getWidth() / 2, 0.01);
        assertEquals(0, speed1PU.getSprite().getY() + speed1PU.getSprite().getHeight() / 2, 0.01);

        speed1PU.setPos(10, 10);
        assertEquals(10, speed1PU.getSprite().getX() + speed1PU.getSprite().getWidth() / 2, 0.01);
        assertEquals(10, speed1PU.getSprite().getY() + speed1PU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void doPowerUpEffectSpeed1PUTest() {
        float playerSpeed = player.getSpeed();
        float speedBoost = speed1PU.getSpeedBoost();
        assertEquals(5f, playerSpeed, 0.0001f);

        speed1PU.doPowerUpEffect(player);
        assertEquals(playerSpeed + speedBoost, player.getSpeed(), 0.0001f);

        assertNotEquals(player.getSpeed(), playerSpeed, 0.0001f);

    }

    @Test
    public void removePowerUpEffectSpeed1PUTest() {

        speed1PU.doPowerUpEffect(player);

        speed1PU.removeSpeedPU(player);

        assertEquals(5f, player.getSpeed(), 0.0001f);

    }

    @Test
    public void getSpriteSpeedPUTest() {
        assertNotNull(speed1PU.getSprite());
    }

}
