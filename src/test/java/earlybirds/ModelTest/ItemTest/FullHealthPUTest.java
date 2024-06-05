package earlybirds.ModelTest.ItemTest;

import com.badlogic.gdx.graphics.Texture;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Item.Types.FullHealthPU;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class FullHealthPUTest {

    private Texturepack texturepack;
    private FullHealthPU fullHealthPU;
    private Player player;
    private Texture texture;

    @Before
    public void setUp() {

        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);

        fullHealthPU = new FullHealthPU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        fullHealthPU = null;
        player = null;
        texture = null;
    }

    @Test
    public void createFullHealthPUObjectTest() {
        assertNotNull(fullHealthPU);
        assertTrue(fullHealthPU instanceof Item);
    }

    @Test
    public void setPosTest() {
        fullHealthPU.setPos(10, 20);
        assertEquals(10, fullHealthPU.getSprite().getX() + fullHealthPU.getSprite().getWidth() / 2, 0.01);
        assertEquals(20, fullHealthPU.getSprite().getY() + fullHealthPU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void getSpriteFullHealthPUTest() {
        assertNotNull(fullHealthPU.getSprite());
    }

    @Test
    public void doPowerUpEffectAtFullHealthTest() {
        player.setHealth(100);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);
    }

    @Test
    public void doPowerUpEffectAtLessThanFullHealthTest() {
        player.setHealth(10);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(20);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(30);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(40);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(50);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(60);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(70);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(80);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);

        player.setHealth(90);
        fullHealthPU.doPowerUpEffect(player);
        assertEquals(100, player.getHealth(), 0.01);
    }
}
