package earlybirds.ModelTest.MovableTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Texturepack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import org.junit.After;

public class BulletTest {
    private Bullet bullet;
    private Texturepack texturepack;
    private Texture texture;

    @Before
    public void setUp() {
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getDeltaTime()).thenReturn(1f); // Mock the delta time

        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        Mockito.when(texturepack.getTexture(any(String.class))).thenReturn(texture);

        bullet = new Bullet('A', 6, 10, new Vector2(0, 0), texturepack.getTexture("bulletGreen"));
    }

    @After
    public void tearDown() {
        Mockito.reset(Gdx.graphics, texturepack, texture);
        bullet = null;
        texturepack = null;
        texture = null;
    }

    @Test
    public void createBulletObjectTest() {
        assertNotNull(bullet); // Bullet should be initialized
        assertEquals('A', bullet.getSymbol()); // Bullet should have symbol 'A'
        assertEquals(6, bullet.getSpeed(), 0.0); // Bullet should have speed 6
        assertEquals(10, bullet.getDamage(), 0.0); // Bullet should have 10 damage
        assertNotNull(bullet.bulletSprite); // Bullet sprite should be initialized
        assertEquals(new Vector2(0, 0), bullet.getPos()); // Initial position should be (0, 0)
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBulletWithInvalidSymbolTest() {
        // Attempt to create a bullet with an invalid symbol
        Bullet.createBullet('X', 5, 10, texturepack, new Vector2(0, 0));
    }

    @Test
    public void updatePositionTest() {
        Vector2 initialPosition = bullet.getPos();
        Vector2 direction = new Vector2(1, 0);
        bullet.setDirection(direction);
        bullet.update();
        Vector2 expectedPosition = initialPosition.add(direction.scl(60 * bullet.getSpeed()));
        assertEquals(expectedPosition, bullet.getPos()); // Position should be updated
    }

    @Test
    public void directionSettingTest() {
        Vector2 direction = new Vector2(10, 10);
        bullet.setDirection(direction);
        assertEquals(1, direction.len(), 0.001); // Direction should be normalized
    }

    @Test
    public void createBulletTest() {
        Bullet bullet = Bullet.createBullet('A', 6, 10, texturepack, new Vector2(0, 0));
        assertNotNull(bullet);
        assertEquals('A', bullet.getSymbol());
        assertEquals(6, bullet.getSpeed(), 0.0);
        assertEquals(10, bullet.getDamage(), 0.0);
        assertNotNull(bullet.bulletSprite);
        assertEquals(new Vector2(0, 0), bullet.getPos());
    }

    @Test
    public void testSetPos() {
        bullet.setPos(10, 5);
        assertEquals(new Vector2(10, 5), bullet.getPos());
    }

    @Test
    public void UpdatePositionTest() {
        Vector2 initialPosition = new Vector2(bullet.getPos());
        Vector2 direction = new Vector2(1, 0);
        bullet.setDirection(direction);
        bullet.update();

        // Calculate expected position without altering the initial position
        Vector2 expectedPosition = new Vector2(initialPosition).add(new Vector2(direction).scl(60 * bullet.getSpeed()));
        assertEquals(expectedPosition, bullet.getPos());
    }

    @Test
    public void GetSpriteTest() {
        assertNotNull(bullet.getSprite());
    }

}
