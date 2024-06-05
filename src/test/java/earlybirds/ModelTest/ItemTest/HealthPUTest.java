package earlybirds.ModelTest.ItemTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Item.Types.HealthPU;
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
public class HealthPUTest {

    private Texturepack texturepack;
    private HealthPU healthPU;
    private Player player;
    private Texture texture;

    @Before
    public void setUp() {
        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);

        healthPU = new HealthPU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        texture = null;
        healthPU = null;
        player = null;
    }

    @Test
    public void createHealthPUObjectTest() {
        assertNotNull(healthPU);
        assertTrue(healthPU instanceof Item);
    }

    @Test
    public void setPosTest() {
        healthPU.setPos(10, 20);
        assertEquals(10, healthPU.getSprite().getX() + healthPU.getSprite().getWidth() / 2, 0.01);
        assertEquals(20, healthPU.getSprite().getY() + healthPU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void getSpriteHealthPUTest() {
        assertNotNull(healthPU.getSprite());
    }

    @Test
    public void doPowerUpEffectAtFullHealthTest() {
        player.setHealth(100);
        healthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);
    }

    @Test
    public void doPowerUpEffectAtLessThanFullHealthTest() {
        player.setHealth(10);
        healthPU.doPowerUpEffect(player);
        assertEquals(20, player.getHealth(), 0.01);

        player.setHealth(20);
        healthPU.doPowerUpEffect(player);
        assertEquals(30, player.getHealth(), 0.01);

        player.setHealth(30);
        healthPU.doPowerUpEffect(player);
        assertEquals(40, player.getHealth(), 0.01);

        player.setHealth(40);
        healthPU.doPowerUpEffect(player);
        assertEquals(50, player.getHealth(), 0.01);

        player.setHealth(50);
        healthPU.doPowerUpEffect(player);
        assertEquals(60, player.getHealth(), 0.01);

        player.setHealth(60);
        healthPU.doPowerUpEffect(player);
        assertEquals(70, player.getHealth(), 0.01);

        player.setHealth(70);
        healthPU.doPowerUpEffect(player);
        assertEquals(80, player.getHealth(), 0.01);

        player.setHealth(80);
        healthPU.doPowerUpEffect(player);
        assertEquals(90, player.getHealth(), 0.01);

        player.setHealth(90);
        healthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);
    }
}
