package earlybirds.ModelTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import earlybirds.EventBus.Eventbus;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Map.TileMap;
import earlybirds.Model.Map.Tiles.TileSize;
import earlybirds.Model.Model;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Enemy.EnemyInterface;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.ScoreKeeper;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ModelTest {
    private Texturepack texturepack;
    private Model model;
    private Player player;

    @Before
    public void setUp() {
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getDeltaTime()).thenReturn(0.5f); // Mock the delta time
        texturepack = new Texturepack();
        // Use an empty map, since we change the position of the player and enemy for
        // some tests, we do not want them to crash with walls created by the random
        // map.
        TileMap emptyMap = new TileMap("Empty", texturepack, TileSize.LARGE_TILE, false);
        model = new Model(new Eventbus(), new ScoreKeeper(), emptyMap);
        player = model.getPlayer();
    }

    @After
    public void tearDown() {
        Mockito.reset(Gdx.graphics);
        model = null;
        player = null;
        texturepack = null;
    }

    @Test
    public void getSpritesTest() {
        List<Sprite> spriteList = model.getSprites();

        assertNotNull(model.getSprites());
        assertEquals(spriteList.get(0), model.getPlayerSprite());

        // Check that the shield is added to the sprite list
        if (model.getPlayer().getShield().isImmune()) {
            assertNotNull(model.getShield());
        }
    }

    @Test
    public void getPlayerSpriteTest() {
        List<Sprite> spriteList = model.getSprites();

        Sprite playerSprite = model.getPlayerSprite();
        Sprite modelPlayerSprite = spriteList.get(0);
        assertEquals(playerSprite, modelPlayerSprite);
    }

    @Test
    public void getControllablePlayer() {
        assertNotNull(model.getControllablePlayer());
        assertTrue(model.getControllablePlayer() instanceof Player);
    }

    @Test
    public void enemyBulletCollisionWithPlayerTest() {
        player.setPos(0, 0);
        Bullet bullet = Bullet.createBullet('E', 5, 10, texturepack, new Vector2(0, 0));
        bullet.setDirection(new Vector2(0, 1));
        model.addBullet(bullet);

        Array<Bullet> bulletsBeforeCollision = model.getBullets();
        int bulletsBeforeCollisionSize = bulletsBeforeCollision.size;
        float initialHealth = player.getHealth();

        // Trigger update which processes bullet collisions
        model.updateBullets();

        Array<Bullet> bulletsAfterCollision = model.getBullets();

        assertTrue(player.getHealth() < initialHealth);
        assertTrue(bulletsBeforeCollisionSize > bulletsAfterCollision.size);
    }

    @Test
    public void playerBulletCollisionWithPlayerTest() {
        player.setPos(0, 0);
        Bullet bullet = Bullet.createBullet('A', 6, 10, texturepack, new Vector2(0, 0));
        bullet.setDirection(new Vector2(0, 1));
        model.addBullet(bullet);

        Array<Bullet> bulletsBeforeCollision = model.getBullets();
        int bulletsBeforeCollisionSize = bulletsBeforeCollision.size;
        float initialHealth = player.getHealth();

        // Trigger update which processes bullet collisions
        model.updateBullets();

        Array<Bullet> bulletsAfterCollision = model.getBullets();

        assertEquals(player.getHealth(), initialHealth, 0);
        assertEquals(bulletsBeforeCollisionSize, bulletsAfterCollision.size);
        assertEquals(bullet.getSymbol(), 'A');
    }

    @Test
    public void playerBulletCollisionWithEnemyTest() {
        EnemyInterface enemy = model.getEnemies().get(0);
        enemy.setPos(0, 0);
        Bullet bullet = Bullet.createBullet('A', 6, 10, texturepack, new Vector2(0, 0));
        bullet.setDirection(new Vector2(0, 1));
        model.addBullet(bullet);

        Array<Bullet> bulletsBeforeCollision = model.getBullets();
        // Check how many enemies there are before collision
        int enemiesBeforeCollisionSize = model.getEnemies().size();
        int bulletsBeforeCollisionSize = bulletsBeforeCollision.size;

        // Trigger update which processes bullet collisions
        model.updateBullets();

        Array<Bullet> bulletsAfterCollision = model.getBullets();
        assertTrue(bulletsBeforeCollisionSize > bulletsAfterCollision.size);
        assertTrue(enemiesBeforeCollisionSize > model.getEnemies().size());
    }

    @Test
    public void enemyBulletCollisionWithEnemyTest() {
        EnemyInterface enemy = model.getEnemies().get(0);
        enemy.setPos(0, 0);
        Bullet bullet = Bullet.createBullet('E', 5, 10, texturepack, new Vector2(0, 0));
        bullet.setDirection(new Vector2(0, 1));
        model.addBullet(bullet);

        Array<Bullet> bulletsBeforeCollision = model.getBullets();
        int bulletsBeforeCollisionSize = bulletsBeforeCollision.size;

        // Trigger update which processes bullet collisions
        model.updateBullets();

        Array<Bullet> bulletsAfterCollision = model.getBullets();
        assertEquals(bulletsBeforeCollisionSize, bulletsAfterCollision.size);
        assertEquals(bullet.getSymbol(), 'E');
    }

    @Test
    public void playerHasFullHealthTest() {
        Player player = model.getPlayer();
        player.setHealth(100);
        assertTrue(model.playerHasFullHealth());
        player.setHealth(50);
        assertFalse(model.playerHasFullHealth());
    }

    @Test
    public void bulletCollisionWithWallTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        model.getPlayer().setPos(0, 0);
        model.getEnemies().get(0).setPos(0, 0);
        // Find a wall sprite to collide with
        for (Sprite wall : model.getMap().getCollisionSprites()) {
            Vector2 wallPos = new Vector2(wall.getX(), wall.getY());

            model.getBullets().clear();

            Bullet bullet = Bullet.createBullet('A', 4, 0, texturepack, wallPos);
            bullet.setDirection(new Vector2(0, 1));
            // model.addBullet(Bullet.createBullet('A', 4, 0, texturepack, wallPos));
            model.addBullet(bullet);

            model.updateBullets();

            assertEquals(0, model.getBullets().size);
            model.addBullet(Bullet.createBullet('E', 4, 0, texturepack, wallPos));
            model.updateBullets();
            assertEquals(0, model.getBullets().size);
        }
    }

    @Test
    public void getShieldTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        assertNotNull(model.getShield());
    }

    @Test
    public void getMapTileSpritesTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        assertNotNull(model.getMapTileSprites());
    }

    @Test
    public void getItemSpritesTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        assertNotNull(model.getItemSprites());
    }

    @Test
    public void getSoundpackTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        assertNotNull(model.getSoundpack());
    }

    @Test
    public void getTexturepackTest() {

        Model model = new Model(new Eventbus(), new ScoreKeeper());

        assertNotNull(model.getTexturepack());
    }

    @Test
    public void getScoreKeeperTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        assertNotNull(model.getScoreKeeper());
    }

    @Test
    public void getScoreTest() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        int initialScore = model.getScore();
        assertEquals(0, initialScore);
        ScoreKeeper scoreKeeper = model.getScoreKeeper();
        scoreKeeper.enemyKilledIncreaseScore(1);
        assertNotEquals(initialScore, model.getScore()); // checks the score has changed
    }
}
