package earlybirds.ModelTest.ItemTest;

import earlybirds.GdxTestRunner;
import earlybirds.Model.Item.GameItems;
import earlybirds.Model.Map.TileMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class GameItemsTest {

    private GameItems gameItems;
    private TileMap tileMap;

    @Before
    public void setUp() {
        tileMap = Mockito.mock(TileMap.class);
        gameItems = new GameItems(tileMap);
    }

    @After
    public void tearDown() {
        Mockito.reset(tileMap);
        tileMap = null;
        gameItems = null;
    }

    @Test
    public void getSpawnedItemListDoesNotReturnNull() {
        assertNotNull(gameItems.getSpawnedItemList());
    }

    @Test
    public void getItemPositionsOnMapTest() {
        gameItems.initializeGameItems(tileMap);
        assertNotNull(gameItems.getItemPositions());
    }

    @Test
    public void correctNumberOfMinimumLocationsTest() {
        gameItems.initializeGameItems(tileMap);

        if (gameItems.getItemPositions().size() < gameItems.getMinimumNumberOfSpawnedItems()) {
            assertTrue(gameItems.getSpawnedItemList().size() == gameItems.getItemPositions().size());
        }

        if (gameItems.getItemPositions().size() >= gameItems.getMinimumNumberOfSpawnedItems()) {
            assertTrue(gameItems.getSpawnedItemList().size() >= gameItems.getMinimumNumberOfSpawnedItems());
        }
    }
}
