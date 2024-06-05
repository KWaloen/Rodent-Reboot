package earlybirds.ModelTest.MapTest;

import org.junit.After;
import org.junit.Before;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Map.Tiles.TileSize;
import earlybirds.Model.Map.Tiles.Types.Wall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
public class WallTest {

    Texture texture;
    TileSize tileSize;
    Wall wall;

    @Before
    public void setUp() {
        texture = mock(Texture.class);
        tileSize = TileSize.VERYSMALL_TILE;
        wall = new Wall(0, 0, tileSize, texture);
    }

    @After
    public void tearDown() {
        Mockito.reset(texture);
        texture = null;
        tileSize = null;
        wall = null;
    }

    @Test
    public void createWallTest() {
        assertNotNull(wall);
    }

    @Test
    public void isWalkableTest() {
        assertEquals(false, wall.isWalkable());
    }

    @Test
    public void getXCordTest() {
        assertEquals(0f, wall.getXCord(), 0.001);
    }

    @Test
    public void getYCordTest() {
        assertEquals(0f, wall.getYCord(), 0.001);
    }

    @Test
    public void getBoundsTest() {
        Rectangle expectedRectangle = new Rectangle(0, 0, tileSize.getWidth(), tileSize.getHeight());
        assertEquals(expectedRectangle, wall.getBounds());
    }

    @Test
    public void getTileSpriteTest() {
        assertEquals(texture, wall.getTileSprite().getTexture());
    }

}
