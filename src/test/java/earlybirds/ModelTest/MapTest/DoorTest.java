package earlybirds.ModelTest.MapTest;

import org.junit.After;
import org.junit.Before;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Texturepack;
import earlybirds.Model.Map.Tiles.TileSize;
import earlybirds.Model.Map.Tiles.Types.Door;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class DoorTest {

    Texturepack texturepack;
    Texture texture;
    TileSize tileSize;
    Door door;

    @Before
    public void setUp() {

        texturepack = mock(Texturepack.class);
        texture = mock(Texture.class);
        when(texturepack.getTexture("door")).thenReturn(texture);

        tileSize = TileSize.VERYSMALL_TILE;
        door = new Door(0, 0, tileSize, texturepack);
    }

    @After
    public void tearDown() {
        Mockito.reset(texturepack, texture);
        texturepack = null;
        texture = null;
        tileSize = null;
        door = null;
    }

    @Test
    public void createDoorTest() {
        assertNotNull(door);
    }

    @Test
    public void isWalkableTest() {
        assertEquals(true, door.isWalkable());
    }

    @Test
    public void getXCordTest() {
        assertEquals(0f, door.getXCord(), 0.001);
    }

    @Test
    public void getYCordTest() {
        assertEquals(0f, door.getYCord(), 0.001);
    }

    @Test
    public void getBoundsTest() {
        Rectangle expectedRectangle = new Rectangle(0, 0, tileSize.getWidth(), tileSize.getHeight());
        assertEquals(expectedRectangle, door.getBounds());
    }

    @Test
    public void getTileSpriteTest() {
        assertEquals(texture, door.getTileSprite().getTexture());
    }

}
