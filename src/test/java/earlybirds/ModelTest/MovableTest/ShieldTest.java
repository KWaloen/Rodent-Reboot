package earlybirds.ModelTest.MovableTest;

import org.junit.After;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Shield;
import earlybirds.Model.Texturepack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ShieldTest {
    private Shield shield;
    private Movable player;
    private Texturepack texturepack;
    private Texture texture;
    private int immunityDuration = 5;
    private int cooldownDuration = 10;

    @Before
    public void setUp() {
        // Mock necessary LibGDX components
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getDeltaTime()).thenReturn(1f); // Mock the delta time

        // Mock the Texture
        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        Mockito.when(texturepack.getTexture("shield")).thenReturn(texture);

        player = Mockito.mock(Movable.class);
        // Define behavior for getPos()
        when(player.getPos()).thenReturn(new Vector2(0, 0));
        shield = new Shield(texturepack, player, immunityDuration, cooldownDuration);
    }

    @After
    public void tearDown() {
        Mockito.reset(Gdx.graphics, texturepack, texture);
        shield = null;
        player = null;
        texturepack = null;
        texture = null;
    }

    @Test
    public void testActivate() {
        shield.activate();
        assertTrue(shield.isImmune());
    }

    @Test
    public void testImmunityDuration() throws InterruptedException {
        shield.activate();
        Thread.sleep(immunityDuration + 1);
        assertFalse(shield.isImmune());
    }

    @Test
    public void testCooldownDuration() throws InterruptedException {
        shield.activate();
        assertTrue(shield.isImmune());
        Thread.sleep(immunityDuration + 1);
        shield.activate();
        assertFalse(shield.isImmune());
        Thread.sleep(cooldownDuration + 1);
        shield.activate();
        assertTrue(shield.isImmune());
    }
}
