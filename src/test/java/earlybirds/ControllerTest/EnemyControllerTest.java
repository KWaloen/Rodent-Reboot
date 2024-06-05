package earlybirds.ControllerTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import earlybirds.Controller.EnemyController;
import earlybirds.GdxTestRunner;
import earlybirds.Model.ControllableModel;
import earlybirds.Model.Model;
import earlybirds.Model.Movable.Enemy.Enemy;
import earlybirds.Model.Movable.Enemy.EnemyInterface;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class EnemyControllerTest {
    ControllableModel model;
    EnemyController controller;
    Movable player;
    Texturepack texturepack;
    Texture texture;

    @Before
    public void setUp() {
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getDeltaTime()).thenReturn(0.5f); // Mock the delta time
        model = mock(Model.class);
        controller = new EnemyController(model);
        EnemyInterface enemy = new Enemy(1);
        List<EnemyInterface> enemies = new ArrayList<>();
        enemies.add(enemy);
        player = mock(Player.class);
        when(player.getPos()).thenReturn(new Vector2(100, 0));

        texturepack = Mockito.mock(Texturepack.class);
        when(model.getTexturepack()).thenReturn(texturepack);

        texture = Mockito.mock(Texture.class);
        when(texturepack.getTexture(any(String.class))).thenReturn(texture);
        when(texture.getWidth()).thenReturn(10);

        when(model.getEnemies()).thenReturn(enemies);
        when(model.validMovableMove(any(Movable.class), any(float.class), any(float.class))).thenReturn(true);
        when(model.getAngleFromPosToPlayer(any(Vector2.class))).thenReturn((float) 0);
        when(model.getControllablePlayer()).thenReturn(player);
        when(model.lineOfSight(any(EnemyInterface.class))).thenReturn(true);
        doCallRealMethod().when(model).checkPosMove(any(float.class), any(float.class), any(Movable.class));
    }

    @After
    public void tearDown() {

        Mockito.reset(Gdx.graphics, model, texturepack, texture);
        // Allow garbage collection
        model = null;
        controller = null;
        player = null;
        texturepack = null;
        texture = null;
    }

    @Test
    public void movementTestXDirection() {
        // Default position of enemy is (0, 0)
        Vector2 originalPos = new Vector2(0, 0);
        EnemyInterface enemy = model.getEnemies().get(0);

        // Player is in different x than enemey
        when(player.getPos()).thenReturn(new Vector2(100, 0));

        // Check that enemy is in expected position
        assertEquals(originalPos, enemy.getPos());

        // Check that enemy has moved and moved towards player
        float oldXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float oldYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        controller.tick();

        assertNotEquals(originalPos, enemy.getPos());

        float newXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float newYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        // Enemy should have only moved in x direction
        assertTrue(newXDistance <= oldXDistance);
        assertTrue(newYDistance == oldYDistance);

        // Check that enemy has not moved all the way to the player
        assertNotEquals(originalPos, model.getControllablePlayer().getPos());

    }

    @Test
    public void movementTestYDirection() {
        // Default position of enemy is (0, 0)
        Vector2 originalPos = new Vector2(0, 0);
        EnemyInterface enemy = model.getEnemies().get(0);

        // Player is in different y than enemey
        when(player.getPos()).thenReturn(new Vector2(0, 100));

        // Check that enemy is in expected position
        assertEquals(originalPos, enemy.getPos());

        // Check that enemy has moved and moved towards player
        float oldXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float oldYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        controller.tick();

        assertNotEquals(originalPos, enemy.getPos());

        float newXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float newYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        // Enemy should have only moved in y direction
        assertTrue(newXDistance == oldXDistance);
        assertTrue(newYDistance <= oldYDistance);

        // Check that enemy has not moved all the way to the player
        assertNotEquals(originalPos, model.getControllablePlayer().getPos());

    }

    @Test
    public void movementTestXYDirection() {
        // Default position of enemy is (0, 0)
        Vector2 originalPos = new Vector2(0, 0);
        EnemyInterface enemy = model.getEnemies().get(0);

        // Player is in different x and y than enemey
        when(player.getPos()).thenReturn(new Vector2(100, 100));

        // Check that enemy is in expected position
        assertEquals(originalPos, enemy.getPos());

        // Check that enemy has moved and moved towards player
        float oldXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float oldYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        controller.tick();

        assertNotEquals(originalPos, enemy.getPos());

        float newXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float newYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        // Enemy should have moved in both x and y direction
        assertTrue(newXDistance <= oldXDistance);
        assertTrue(newYDistance <= oldYDistance);

        // Check that enemy has not moved all the way to the player
        assertNotEquals(originalPos, model.getControllablePlayer().getPos());

    }

    @Test
    public void movementTestPlayerTooFarAway() {
        // Default position of enemy is (0, 0)
        Vector2 originalPos = new Vector2(0, 0);
        EnemyInterface enemy = model.getEnemies().get(0);

        // Player is far away from enemy
        when(model.distanceToPlayer(any(float.class), any(float.class))).thenReturn((float) 2000);

        // Check that enemy is in expected position
        assertEquals(originalPos, enemy.getPos());

        controller.tick();

        // Check that enemy is still in the same position
        assertEquals(originalPos, enemy.getPos());

    }

    @Test
    public void movementTestNoValidMoves() {
        // Default position of enemy is (0, 0)
        Vector2 originalPos = new Vector2(0, 0);
        EnemyInterface enemy = model.getEnemies().get(0);

        // There are no valid moves
        when(model.validMovableMove(any(Movable.class), any(float.class), any(float.class))).thenReturn(false);

        // Check that enemy is in expected position
        assertEquals(originalPos, enemy.getPos());

        controller.tick();

        // Check that enemy is still in the same position
        assertEquals(originalPos, enemy.getPos());

    }

    @Test
    public void movementTestMoveToLastSeenPlayer() {
        EnemyInterface enemy = model.getEnemies().get(0);

        // Player is at (100, 100)
        when(player.getPos()).thenReturn(new Vector2(100, 100));

        // Enemy gets a view of player
        controller.tick();

        // Cancel line of sight
        when(model.lineOfSight(any(EnemyInterface.class))).thenReturn(false);

        // Check that enemy move towards player, even with no line of
        // sight
        float oldXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float oldYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        controller.tick();

        float newXDistance = model.getControllablePlayer().getPos().x - enemy.getPos().x;
        float newYDistance = model.getControllablePlayer().getPos().y - enemy.getPos().y;

        // Enemy should have moved in both x and y direction
        assertTrue(newXDistance <= oldXDistance);
        assertTrue(newYDistance <= oldYDistance);

    }

    @Test
    public void testScaleDifficulty() {
        reset();

        float originalBulletDamage = controller.getBulletDamage();
        when(model.getCurrentLevel()).thenReturn(2);
        controller.tick();
        assertTrue(controller.getBulletDamage() > originalBulletDamage);

        float newBulletDamage = controller.getBulletDamage();
        when(model.getCurrentLevel()).thenReturn(3);
        controller.tick();
        assertTrue(controller.getBulletDamage() > newBulletDamage);
    }

}
