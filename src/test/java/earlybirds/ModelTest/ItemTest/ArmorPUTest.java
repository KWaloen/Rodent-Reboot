package earlybirds.ModelTest.ItemTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Types.ArmorPU;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.After;

@RunWith(GdxTestRunner.class)
public class ArmorPUTest {

    private Texturepack texturepack;
    private ArmorPU armorPU;
    private Player player;
    private Texture texture;

    @Before
    public void setUp() {
        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);
        when(texture.getWidth()).thenReturn(10);

        armorPU = new ArmorPU();
        player = new Player(texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        texture = null;
        armorPU = null;
        player = null;
    }

    @Test
    public void createArmourPUObjectTest() {
        assertNotNull(armorPU);
    }

    @Test
    public void setArmourPUPositionTest() {
        armorPU.setPos(0, 0);
        assertEquals(0, armorPU.getSprite().getX() + armorPU.getSprite().getWidth() / 2, 0.01);
        assertEquals(0, armorPU.getSprite().getY() + armorPU.getSprite().getHeight() / 2, 0.01);

        armorPU.setPos(10, 10);
        assertEquals(10, armorPU.getSprite().getX() + armorPU.getSprite().getWidth() / 2, 0.01);
        assertEquals(10, armorPU.getSprite().getY() + armorPU.getSprite().getHeight() / 2, 0.01);
    }

    @Test
    public void doPowerUpEffectArmourPUTest() {
        float initialDamageReduction = Player.getDamageReduction();
        assertEquals(0f, initialDamageReduction, 0.01);

        armorPU.doPowerUpEffect(player);

        assertNotEquals(initialDamageReduction, Player.getDamageReduction());
        assertEquals(Player.getDamageReduction(), (initialDamageReduction + Player.getDamageReduction()), 0.01);

        assertNotEquals(initialDamageReduction, Player.getDamageReduction());
        assertEquals(initialDamageReduction + armorPU.getDamageReduction(), armorPU.getDamageReduction(), 0.01);
        assertEquals(Player.getDamageReduction(), (initialDamageReduction + armorPU.getDamageReduction()), 0.01);

    }

    @Test
    public void removePowerUpEffectArmourPUTest() {

        armorPU.doPowerUpEffect(player);

        armorPU.removePowerUpEffect();
        assertEquals(0f, Player.getDamageReduction(), 0.01);

    }

    @Test
    public void getSpriteSpeedPUTest() {
        assertNotNull(armorPU.getSprite());
    }

}
