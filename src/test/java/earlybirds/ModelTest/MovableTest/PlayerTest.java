package earlybirds.ModelTest.MovableTest;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import earlybirds.EventBus.Eventbus;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Model;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.ScoreKeeper;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(GdxTestRunner.class)

public class PlayerTest {

    private Texturepack texturepack;
    private Texture texture;

    @Before
    public void setUp() {
        texturepack = Mockito.mock(Texturepack.class);
        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        texture = null;
    }

    @Test
    public void playerHasSprite() {
        Player player = new Player(texturepack);
        assertNotNull(player.getSprite());
    }

    @Test
    public void playerHasSpeedLargerThanZero() {
        Player player = new Player(texturepack);
        assertNotNull(player.getSprite());
        assertTrue(player.getSpeed() > 0);
    }

    @Test
    public void setAndGetPlayerPosTest() {
        Player player = new Player(texturepack);
        // player starting pos is 0,0
        player.setPos(0, 0);
        assertEquals(new Vector2(0, 0), player.getPos());
    }

    @Test
    public void setPlayerPosTest() {
        Player player = new Player(texturepack);

        player.setPos(0, 0);
        assertEquals(new Vector2(0, 0), player.getPos());

        player.setPos(10, 10);
        assertEquals(new Vector2(10, 10), player.getPos());

        player.setPos(20, 20);
        assertEquals(new Vector2(20, 20), player.getPos());

        player.setPos(120, 0);
        assertEquals(new Vector2(120, 0), player.getPos());
    }

    @Test
    public void movePlayerTest() {
        Player player = new Player(texturepack);

        player.setPos(0, 0);
        player.move(10, 10);
        assertEquals(new Vector2(10, 10), player.getPos());

        player.setPos(10, 10);
        player.move(10, 10);
        assertEquals(new Vector2(20, 20), player.getPos());

        player.setPos(20, 20);
        player.move(10, 10);
        assertEquals(new Vector2(30, 30), player.getPos());
    }

    @Test
    public void playerLosesHealthWhenHitByBullet() {
        Eventbus eventBus = new Eventbus();
        Model model = new Model(eventBus, new ScoreKeeper());
        model.reset();
        Player player = model.getPlayer();
        player.setPos(0, 0);
        float speed = 3;
        float damage = 10;
        float initialHealth = player.getHealth();
        Bullet bullet = Bullet.createBullet('E', speed, damage, texturepack, new Vector2(0, 0));
        bullet.setDirection(new Vector2(0, 1));
        model.addBullet(bullet);

        // Update the bullet in the model
        model.updateBullets();

        // Check that the player's health decreased
        assertTrue(player.getHealth() < initialHealth);
    }

    @Test
    public void playerImmuneWhenShielded() {
        Eventbus eventBus = new Eventbus();
        Model model = new Model(eventBus, new ScoreKeeper());
        model.reset();
        Player player = model.getPlayer();
        player.setPos(0, 0);
        float speed = 3;
        float damage = 10;
        float initialHealth = player.getHealth();
        Bullet bullet = Bullet.createBullet('E', speed, damage, texturepack, new Vector2(0, 0));
        bullet.setDirection(new Vector2(0, 1));
        model.addBullet(bullet);

        // Activates the shield and updates the bullet in the model
        player.getShield().activate();
        model.updateBullets();

        // Check that the player's health didnt decrease
        assertTrue(player.getHealth() == initialHealth);
    }

    @Test
    public void testArmorDeactivatesAfterCertainHits() {
        Player player = new Player(new Texturepack());
        float initialHealth = player.getHealth();
        float damage = 10;
        Player.setDamageReduction(50); // Set damage reduction to 50%

        for (int i = 0; i < 10; i++) {
            player.setHealth(initialHealth - damage);
            initialHealth = player.getHealth();
        }

        assertEquals(0, Player.getDamageReduction(), "Armor should be deactivated after certain number of hits");
    }


    @Test
    public void testGetCollisionHitbox() {
        Player player = new Player(texturepack);
        assertNotNull(player.getCollisionHitbox(1, 2));
    }
}
