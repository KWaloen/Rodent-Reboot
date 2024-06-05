package earlybirds.ModelTest;

import com.badlogic.gdx.math.Vector2;
import earlybirds.EventBus.Eventbus;
import earlybirds.GameHandler;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Item.Types.HealthPU;
import earlybirds.Model.Item.Types.Speed3PU;
import earlybirds.Model.Map.Tiles.TileSize;
import earlybirds.Model.Map.Tiles.Types.Door;
import earlybirds.Model.Map.Tiles.Types.Floor;
import earlybirds.Model.Map.Tiles.Types.Wall;
import earlybirds.Model.Model;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Enemy.Enemy;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.ScoreKeeper;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(GdxTestRunner.class)
public class CreateGameObjectsTest {
    Texturepack texturepack;

    @Before
    public void setUp() {
        texturepack = new Texturepack();
    }

    @After
    public void tearDown() {
        texturepack = null;
    }

    @Test
    public void getEnemyObject() {
        Movable enemy = new Enemy(1);
        assertNotNull(enemy);
    }

    @Test
    public void getBulletObject() {
        Movable bullet = new Bullet('E', 4, 4, new Vector2(0, 0), texturepack.getTexture("bulletOrange"));
        assertNotNull(bullet);
    }

    @Test
    public void getSpeedPUObject() {
        Item speedPU = new Speed3PU();
        assertNotNull(speedPU);
    }

    @Test
    public void getHealthPUObject() {
        Item healthPU = new HealthPU();
        assertNotNull(healthPU);
    }

    @Test
    public void getGateObject() {
        Door door = new Door(0, 0, TileSize.VERYSMALL_TILE, texturepack);
        assertNotNull(door);
    }

    @Test
    public void getWallObject() {
        Wall wall = new Wall(0, 0, TileSize.VERYSMALL_TILE, texturepack.getTexture("wallMiddle"));
        assertNotNull(wall);
    }

    @Test
    public void getFloorObject() {
        Floor floor = new Floor(0, 0, TileSize.VERYSMALL_TILE, texturepack);
        assertNotNull(floor);
    }

    @Test
    public void getGameHandler() {
        GameHandler gameHandler = new GameHandler();
        assertNotNull(gameHandler);
    }

    @Test
    public void getPlayerObject() {
        Movable player = new Player(texturepack);
        Assert.assertNotNull(player);
    }

    @Test
    public void getModel() {
        Model model = new Model(new Eventbus(), new ScoreKeeper());
        Assert.assertNotNull(model);
    }

}
