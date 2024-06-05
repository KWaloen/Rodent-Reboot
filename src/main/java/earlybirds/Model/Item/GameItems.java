package earlybirds.Model.Item;

import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.Map.TileMap;

import java.util.*;

/**
 * This class is responsible for retrieving the designated spawn locations from
 * the TileMap class and drawing
 * a random type of item in the designated location.
 */

public class GameItems {

    private ArrayList<Item> spawnedItemList;
    private ArrayList<Vector2> itemPositionsOnMap;
    private Integer chanceForSpawningItem;
    private Integer minimumNumberOfSpawnedItems;

    /**
     * Constructs GameItems
     * Adjustable parameters:
     * Chance for spawning item on location
     * Minimum number of items spawning
     */
    public GameItems(TileMap tileMap) {
        this.spawnedItemList = new ArrayList<>();
        this.chanceForSpawningItem = 50;
        this.minimumNumberOfSpawnedItems = 5;
        this.itemPositionsOnMap = tileMap.getItemSpawnLocations();
    }

    /**
     * Retrives new item spawn locations.
     * Then spawns the random items on the locations if they pass the chance test.
     *
     */
    public void initializeGameItems(TileMap tileMap) {
        getItemPositionsOnMap(tileMap);
        loadItemsToMap();
    }

    /**
     * This method retrieves the spawn locations from the TileMap class and shuffles
     * them before spawning items by
     * selecting an item type randomly.
     * If there are more spawn locations than the minimum number of spawned items
     * parameter the chance for item to spawn
     * comes into play for each item above the minimum.
     * Note: Some maps have less item spawn locations than the minimum number of
     * spawned items parameter.
     */
    private void loadItemsToMap() {
        spawnedItemList = new ArrayList<>();
        Collections.shuffle(itemPositionsOnMap);
        try {

            if (itemPositionsOnMap.size() <= minimumNumberOfSpawnedItems) {
                for (int i = 0; i <= itemPositionsOnMap.size() - 1; i++) {
                    addItemToSpawnedItemList(i);
                }
            } else {
                for (int i = 0; i < minimumNumberOfSpawnedItems; i++) {
                    addItemToSpawnedItemList(i);
                }
                for (int i = minimumNumberOfSpawnedItems; i < itemPositionsOnMap.size(); i++) {
                    if (chanceForItemToSpawn() <= chanceForSpawningItem) {
                        addItemToSpawnedItemList(i);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Caught in loadItemsToMap method. Double check numberOfItemTypes parameter.");
        }
    }

    /**
     * Pick a random item type and set its coordinates before adding it to the
     * spawnedItemList.
     * 
     * @param i
     */
    private void addItemToSpawnedItemList(int i) {
        Item randomItem = pickRandomPU();
        randomItem.setPos(itemPositionsOnMap.get(i).x, itemPositionsOnMap.get(i).y);
        spawnedItemList.add(randomItem);
    }

    /**
     * This method is responsible for the chance aspect of the item spawn.
     *
     * @return
     */
    private Integer chanceForItemToSpawn() {
        Random rand = new Random();
        int randInt = rand.nextInt(100);
        return randInt;
    }

    /**
     * This method retrieves a random item type from the ItemRegistry and returns
     * it.
     *
     * @return
     */
    private Item pickRandomPU() {
        Random rand = new Random();
        int randInt = rand.nextInt(ItemRegistry.values().length);
        return ItemRegistry.values()[randInt].getItem();
    }

    /**
     * This method is the getter for the list of spawned items.
     *
     * @return
     */
    public ArrayList<Item> getSpawnedItemList() {

        return spawnedItemList;
    }

    /**
     * Retrives the item spawn positions from the TileMap.
     */
    private void getItemPositionsOnMap(TileMap tileMap) {
        this.itemPositionsOnMap = tileMap.getItemSpawnLocations();
    }

    /**
     * For testing purposes.
     *
     * @return
     */
    public int getMinimumNumberOfSpawnedItems() {
        return minimumNumberOfSpawnedItems;
    }

    /**
     * For testing purposes.
     *
     * @return
     */
    public ArrayList<Vector2> getItemPositions() {
        return itemPositionsOnMap;
    }

}
