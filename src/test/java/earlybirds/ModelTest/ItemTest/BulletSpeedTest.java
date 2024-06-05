package earlybirds.ModelTest.ItemTest;

import com.badlogic.gdx.graphics.Texture;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Types.BulletSpeedPU;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class BulletSpeedTest {

    private Texturepack texturepack;
    private BulletSpeedPU bulletSpeedPU;
    private Player player;
    private Texture texture;

    @Before
    public void setUp() {

        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);

        bulletSpeedPU = new BulletSpeedPU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        bulletSpeedPU = null;
        player = null;
        texture = null;
    }

    @Test
    public void createBulletSpeedPUObjectTest() {
        assertNotNull(bulletSpeedPU);
    }

    @Test
    public void setBulletSpeedPositionTest() {
        bulletSpeedPU.setPos(0, 0);
        assertEquals(0, bulletSpeedPU.getSprite().getX() + bulletSpeedPU.getSprite().getWidth() / 2, 0.01);
        assertEquals(0, bulletSpeedPU.getSprite().getY() + bulletSpeedPU.getSprite().getHeight() / 2, 0.01);

        bulletSpeedPU.setPos(10, 10);
        assertEquals(10, bulletSpeedPU.getSprite().getX() + bulletSpeedPU.getSprite().getWidth() / 2, 0.01);
        assertEquals(10, bulletSpeedPU.getSprite().getY() + bulletSpeedPU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void doPowerUpEffectBulletSpeedTest() {
        float initialBulletSpeed = Player.getBulletSpeed();

        bulletSpeedPU.doPowerUpEffect(player);

        assertNotEquals(initialBulletSpeed, Player.getBulletSpeed(), 0.01);
        assertEquals(initialBulletSpeed + bulletSpeedPU.getBulletSpeedIncrease(), Player.getBulletSpeed(), 0.01);
    }

    @Test
    public void removePowerUpEffectBulletSpeedTest() {

        float initialBulletSpeed = Player.getBulletSpeed();

        bulletSpeedPU.doPowerUpEffect(player);

        assertNotEquals(initialBulletSpeed, Player.getBulletSpeed(), 0.01);
        assertEquals(initialBulletSpeed + bulletSpeedPU.getBulletSpeedIncrease(), Player.getBulletSpeed(), 0.01);

        bulletSpeedPU.removePowerUpEffect();

        assertEquals(initialBulletSpeed, Player.getBulletSpeed(), 0.01);
        assertNotEquals(initialBulletSpeed + bulletSpeedPU.getBulletSpeedIncrease(), Player.getBulletSpeed(), 0.01);
    }

    @Test
    public void getSpriteBulletSpeedPUTest() {
        assertNotNull(bulletSpeedPU.getSprite());
    }

}
