package earlybirds.ControllerTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import earlybirds.Controller.PlayerController;
import earlybirds.GdxTestRunner;
import earlybirds.Model.ControllableModel;
import earlybirds.Model.Model;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Enemy.EnemyInterface;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Texturepack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class PlayerControllerTest {

    private Player player;
    private PlayerController playerController;
    private ControllableModel controllableModel;
    private Texturepack texturepack;
    @SuppressWarnings("unused")
    private OrthographicCamera camera;
    @SuppressWarnings("unused")
    private Array<Bullet> bullets;

    @Mock
    private Input input;

    @Before
    public void setUpPlayerController() {
        Gdx.graphics = Mockito.mock(Graphics.class);
        Mockito.when(Gdx.graphics.getDeltaTime()).thenReturn(0.5f);

        texturepack = new Texturepack();

        MockitoAnnotations.openMocks(this);
        player = new Player(texturepack);
        controllableModel = mock(Model.class);

        when(controllableModel.getControllablePlayer()).thenReturn(player);
        when(controllableModel.validMovableMove(any(Movable.class), any(float.class), any(float.class)))
                .thenReturn(true);
        when(controllableModel.getAngleFromPosToPlayer(any(Vector2.class))).thenReturn((float) 0);
        when(controllableModel.lineOfSight(any(EnemyInterface.class))).thenReturn(true);
        doCallRealMethod().when(controllableModel).checkPosMove(any(float.class), any(float.class), any(Movable.class));

        playerController = new PlayerController(controllableModel, input, null);
    }

    @After
    public void tearDown() {
        Mockito.reset(Gdx.graphics, input, controllableModel);
        player = null;
        playerController = null;
        controllableModel = null;
        texturepack = null;
        camera = null;
        bullets = null;
    }

    @Test
    public void testPlayerControllerNotNull() {
        assertNotNull(playerController);
    }

    @Test
    public void testWKeyPressed() {

        controllableModel.getControllablePlayer().setPos(0, 0);
        Vector2 startingPos = new Vector2(0, 0);
        assertEquals(startingPos, player.getPos());

        when(input.isKeyPressed(Input.Keys.W)).thenReturn(true);
        playerController.tick();

        assertNotEquals(startingPos, player.getPos());
        assertTrue(player.getPos().y > startingPos.y);
        assertFalse(player.getPos().y < startingPos.y);
        assertFalse(player.getPos().y == startingPos.y);

        assertTrue(player.getPos().x == startingPos.x);
    }

    @Test
    public void testSKeyPressed() {
        player.setPos(0, 0);
        Vector2 startingPos = new Vector2(0, 0);
        assertEquals(startingPos, player.getPos());

        when(input.isKeyPressed(Input.Keys.S)).thenReturn(true);
        playerController.tick();

        assertNotEquals(startingPos, player.getPos());
        assertTrue(player.getPos().y < startingPos.y);
        assertFalse(player.getPos().y > startingPos.y);
        assertFalse(player.getPos().y == startingPos.y);

        assertTrue(player.getPos().x == startingPos.x);
    }

    @Test
    public void testAKeyPressed() {
        player.setPos(0, 0);
        Vector2 startingPos = new Vector2(0, 0);
        assertEquals(startingPos, player.getPos());

        when(input.isKeyPressed(Input.Keys.A)).thenReturn(true);
        playerController.tick();

        assertNotEquals(startingPos, player.getPos());
        assertTrue(player.getPos().x < startingPos.x);
        assertFalse(player.getPos().x > startingPos.x);
        assertFalse(player.getPos().x == startingPos.x);
        assertTrue(player.getPos().y == startingPos.y);
    }

    @Test
    public void testDKeyPressed() {
        player.setPos(0, 0);
        Vector2 startingPos = new Vector2(0, 0);
        assertEquals(startingPos, player.getPos());

        when(input.isKeyPressed(Input.Keys.D)).thenReturn(true);
        playerController.tick();

        assertNotEquals(startingPos, player.getPos());
        assertTrue(player.getPos().x > startingPos.x);
        assertFalse(player.getPos().x < startingPos.x);
        assertFalse(player.getPos().x == startingPos.x);
        assertTrue(player.getPos().y == startingPos.y);
    }

}
