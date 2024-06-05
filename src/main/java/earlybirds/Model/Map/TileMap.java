package earlybirds.Model.Map;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import earlybirds.Model.Map.String.StringMap;
import earlybirds.Model.Map.Tiles.*;
import earlybirds.Model.Map.Tiles.Types.Door;
import earlybirds.Model.Map.Tiles.Types.Floor;
import earlybirds.Model.Map.Tiles.Types.Tile;
import earlybirds.Model.Map.Tiles.Types.Wall;
import earlybirds.Model.Texturepack;

/**
 * A class representing a map of tiles.
 * The class creates a TileMap based on a given number of random rooms and a
 * texture pack.
 * To get the map, use the getMapTiles method on the TileMap object.
 * <p>
 * Stores playerSpawn, itemSpawnLocations and enemySpawnLocations
 */
public class TileMap {
    private TileSize tileSize;
    private ArrayList<Tile> mapTiles;
    private Vector2 playerSpawn;
    private ArrayList<Vector2> itemSpawnLocations;
    private ArrayList<Vector2> enemySpawnLocations;

    /**
     * Constructs a TileMap and builds the map based on the given number of random
     * rooms and texture pack.
     * <p>
     * Spawn locations for player, items and enemies are also stored.
     *
     * @param numberOfRandomRooms Number of random rooms to generate.
     * @param texturepack         Texture pack to use for tiles.
     * @param tileSize            Size of the tiles.
     */
    public TileMap(int numberOfRandomRooms, Texturepack texturepack, TileSize tileSize) {
        String roomString = new StringMap(numberOfRandomRooms).getMapString();
        itemSpawnLocations = new ArrayList<>();
        enemySpawnLocations = new ArrayList<>();
        this.tileSize = tileSize;
        buildMap(roomString, texturepack);
    }

    /**
     * Constructs a TileMap and builds the map based on the given map name and
     * texture pack.
     *
     * @param mapName     the name of the map to build
     * @param texturepack Texture pack to use for tiles.
     * @param tileSize    Size of the tiles.
     * @param testing     if true the map is a test map, otherwise it is a named
     *                    map.
     */
    public TileMap(String mapName, Texturepack texturepack, TileSize tileSize, boolean testing) {
        String roomString = new StringMap(mapName, testing).getMapString();
        itemSpawnLocations = new ArrayList<>();
        enemySpawnLocations = new ArrayList<>();
        this.tileSize = tileSize;
        buildMap(roomString, texturepack);
    }

    /**
     * Creates a list of tile-segments based on input string.
     *
     * @param roomRepresentation 2D-string representation of the room.
     * @param texturepack        Texture pack to use for tiles.
     */
    private void buildMap(String roomRepresentation, Texturepack texturepack) {
        mapTiles = new ArrayList<>();
        String[] rows = roomRepresentation.split("\n");

        for (int y = 0; y < rows.length; y++) {
            String row = rows[y];
            for (int x = 0; x < row.length(); x++) {
                char tileChar = row.charAt(x);
                float xPos = x * tileSize.getWidth();
                float yPos = (rows.length - y - 1) * tileSize.getHeight(); // Invert y-axis to match libGDX
                float tileWidth = tileSize.getWidth();
                float tileHeight = tileSize.getHeight();
                switch (tileChar) {
                    case 'X': // wall
                        Wall wall = new Wall(xPos, yPos, tileSize, getWallTile(x, y, texturepack, rows));
                        mapTiles.add(wall);
                        break;
                    case 'F': // floor
                        Tile floor = new Floor(xPos, yPos, tileSize, texturepack);
                        mapTiles.add(floor);
                        break;
                    case 'P': // Store player spawn location, creates a floor tile as well
                        playerSpawn = new Vector2(xPos + tileWidth / 2, yPos + tileHeight / 2);
                        Tile floorPlayer = new Floor(xPos, yPos, tileSize, texturepack);
                        mapTiles.add(floorPlayer);
                        break;
                    case 'I': // Store item spawn location, creates a floor tile as well
                        itemSpawnLocations.add(new Vector2(xPos + tileWidth / 2, yPos + tileHeight / 2));
                        Tile floorItem = new Floor(xPos, yPos, tileSize, texturepack);
                        mapTiles.add(floorItem);
                        break;
                    case 'S': // enemy spawn, creates a floor tile as well
                        enemySpawnLocations.add(new Vector2(xPos + tileWidth / 2, yPos + tileHeight / 2));
                        Tile floorEnemy = new Floor(xPos, yPos, tileSize, texturepack);
                        mapTiles.add(floorEnemy);
                        break;
                    case 'D': // door
                        Tile door = new Door(xPos, yPos, tileSize, texturepack);
                        mapTiles.add(door);
                        break;
                    default: // Default does nothing
                        break;
                }
            }
        }
    }

    /**
     * Get the correct wall tile based on the surrounding tiles.
     * 
     * @param x           x-coordinate of the tile in the mapstring
     * @param y           y-coordinate of the tile in the mapstring
     * @param Texturepack Texturepack to use for tiles.
     * @param rows        The mapstring split into rows
     */
    private Texture getWallTile(int x, int y, Texturepack texturepack, String[] rows) {
        char up = y > 0 ? rows[y - 1].charAt(x) : ' ';
        char down = y < rows.length - 1 ? rows[y + 1].charAt(x) : ' ';
        char left = x > 0 ? rows[y].charAt(x - 1) : ' ';
        char right = x < rows[y].length() - 1 ? rows[y].charAt(x + 1) : ' ';
        // Check for all possible wall combinations, and returns the correct texture
        if (up == 'X' && down == 'X' && left == 'X' && right == 'X') {
            return texturepack.getTexture("wallMiddle");
        } else if (up == 'X' && down == 'X' && left == 'X') {
            return texturepack.getTexture("wallClosedRight");
        } else if (up == 'X' && down == 'X' && right == 'X') {
            return texturepack.getTexture("wallClosedLeft");
        } else if (up == 'X' && left == 'X' && right == 'X') {
            return texturepack.getTexture("wallClosedDown");
        } else if (down == 'X' && left == 'X' && right == 'X') {
            return texturepack.getTexture("wallClosedTop");
        } else if (up == 'X' && down == 'X') {
            return texturepack.getTexture("wallVertical");
        } else if (left == 'X' && right == 'X') {
            return texturepack.getTexture("wallHorizontal");
        } else if (up == 'X' && right == 'X') {
            return texturepack.getTexture("wallLeftUp");
        } else if (up == 'X' && left == 'X') {
            return texturepack.getTexture("wallRightUp");
        } else if (down == 'X' && right == 'X') {
            return texturepack.getTexture("wallLeftDown");
        } else if (down == 'X' && left == 'X') {
            return texturepack.getTexture("wallRightDown");
        } else if (up == 'X') {
            return texturepack.getTexture("wallEndDown");
        } else if (down == 'X') {
            return texturepack.getTexture("wallEndUp");
        } else if (left == 'X') {
            return texturepack.getTexture("wallEndRight");
        } else if (right == 'X') {
            return texturepack.getTexture("wallEndLeft");
        } else {
            return texturepack.getTexture("wallSingle");
        }
    }

    /**
     * Get the list of tiles representing the map.
     *
     * @return List of tiles representing the map.
     */
    public ArrayList<Tile> getMapTiles() {
        return mapTiles;
    }

    /**
     * Get the player spawn location.
     *
     * @return Player spawn location.
     */
    public Vector2 getPlayerSpawn() {
        return playerSpawn;
    }

    /**
     * Get the item spawn locations.
     *
     * @return List of item spawn locations.
     */
    public ArrayList<Vector2> getItemSpawnLocations() {
        return itemSpawnLocations;
    }

    /**
     * Get the enemy spawn locations.
     *
     * @return List of enemy spawn locations.
     */
    public ArrayList<Vector2> getEnemySpawnLocations() {
        return enemySpawnLocations;
    }

    /**
     * @return Sprites from the map tiles
     */
    public List<Sprite> getMapTileSprites() {
        List<Sprite> sprites = new ArrayList<>();
        for (Tile tile : mapTiles) {
            sprites.add(tile.getTileSprite());
        }
        return sprites;

    }

    /**
     * @return Sprites from tiles the player can't walk over.
     */

    public List<Sprite> getCollisionSprites() {
        List<Sprite> sprites = new ArrayList<>();
        for (Tile tile : mapTiles) {
            if (!tile.isWalkable()) {
                sprites.add(tile.getTileSprite());

            }
        }
        return sprites;
    }

    /**
     * @return the sprite of the door
     */
    public Sprite getDoor() {
        for (Tile tile : mapTiles) {
            if (tile instanceof Door) {
                return tile.getTileSprite();
            }
        }
        return null;
    }
}
