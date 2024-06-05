package earlybirds.ModelTest.MapTest;

import org.junit.After;
import org.junit.Before;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Map.TileMap;
import earlybirds.Model.Texturepack;
import earlybirds.Model.Map.Tiles.TileSize;
import earlybirds.Model.Map.Tiles.Types.Tile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class TileMapTest {

    Texturepack texturepack;
    Texture texture;

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
    public void createTileMapTest() {
        TileMap tileMap = new TileMap(5, texturepack, TileSize.VERYSMALL_TILE);
        assertNotNull(tileMap);
    }

    @Test
    public void getMapTilesTest() {
        TileMap tileMap = new TileMap(5, texturepack, TileSize.VERYSMALL_TILE);
        assertNotNull(tileMap.getMapTiles());
    }

    @Test
    public void buildMapTest() {
        TileMap tileMap = new TileMap(1, texturepack, TileSize.LARGE_TILE);

        ArrayList<Tile> mapTiles = tileMap.getMapTiles();
        assertEquals(243, mapTiles.size());

    }

}
