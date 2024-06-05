package earlybirds.ModelTest.MapTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import earlybirds.Model.Map.String.StringMap;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import java.awt.Point;
import java.util.Queue;
import java.util.LinkedList;

public class StringMapTest {

    /**
     * Tests that the map string is not null for a minimal map
     */
    @Test
    public void testMinimalMap() {
        // Initialize MapBuilder with a known number of rooms to add
        StringMap mapBuilder = new StringMap(0); // For example, adding 5 rooms

        // Get the map string generated by buildMapString method
        String mapString = mapBuilder.getMapString();

        // Check that the map string is not null
        assertNotNull(mapString, "Map string should not be null");
    }

    /**
     * Tests that the map sting is not null for a larger map
     */
    @Test
    public void testLargerMap() {
        // Initialize MapBuilder with a known number of rooms to add
        StringMap mapBuilder = new StringMap(5); // For example, adding 5 rooms

        // Get the map string generated by buildMapString method
        String mapString = mapBuilder.getMapString();

        // Check that the map string is not null
        assertNotNull(mapString, "Map string should not be null");
    }

    /**
     * Test that the map is not null for a very large map
     */
    @Test
    public void testVeryLargeMap() {
        // Initialize MapBuilder with a known number of rooms to add
        StringMap mapBuilder = new StringMap(200); // For example, adding 5 rooms

        // Get the map string generated by buildMapString method
        String mapString = mapBuilder.getMapString();

        // Check that the map string is not null
        assertNotNull(mapString, "Map string should not be null");
    }

    /**
     * Tests that the map is contained withing the edge walls
     */
    @RepeatedTest(100)
    public void testEdgeWalls() {
        StringMap mapBuilder = new StringMap(5);
        String mapStringAfterEdgeWalls = mapBuilder.getMapString();
        String[] lines = mapStringAfterEdgeWalls.split("\n");

        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            for (int x = 0; x < line.length(); x++) {
                char currentChar = line.charAt(x);

                // Check the first and last characters of each line and the first and last lines
                if (x == 0 || x == line.length() - 1 || y == 0 || y == lines.length - 1) {
                    assertTrue(currentChar == '-' || currentChar == 'X',
                            "Characters at the edge of the map should be '-' or 'X'");
                }

                // Check adjacent characters next to '-'
                if (currentChar == '-') {
                    if (x > 0) {
                        char leftChar = line.charAt(x - 1);
                        assertTrue(leftChar == '-' || leftChar == 'X', "Characters next to '-' should be '-' or 'X'");
                    }
                    if (x < line.length() - 1) {
                        char rightChar = line.charAt(x + 1);
                        assertTrue(rightChar == '-' || rightChar == 'X', "Characters next to '-' should be '-' or 'X'");
                    }
                    if (y > 0) {
                        char upChar = lines[y - 1].charAt(x);
                        assertTrue(upChar == '-' || upChar == 'X', "Characters next to '-' should be '-' or 'X'");
                    }
                    if (y < lines.length - 1) {
                        char downChar = lines[y + 1].charAt(x);
                        assertTrue(downChar == '-' || downChar == 'X', "Characters next to '-' should be '-' or 'X'");
                    }
                }
            }
        }
    }

    /**
     * Checks that the map has a path from the spawn point to the door
     * This test only checks the testrooms, and is intended to proove the test
     * works.
     */
    @Test
    public void testPathFromSpawnToDoorTestMaps() {
        // Test for canExit map
        StringMap canExitMap = new StringMap("canExit", true);
        String canExitArray = canExitMap.getMapString();
        assertTrue(pathExists(canExitArray));

        // Test for cantExit map
        StringMap cantExitMap = new StringMap("cantExit", true);
        String cantExitArray = cantExitMap.getMapString();
        assertFalse(pathExists(cantExitArray));
    }

    /**
     * Tests that the map has a path from the spawn point to the door in regular
     * maps.
     */
    @RepeatedTest(100)
    public void testPathFromSpawnToDoorRandomMaps() {
        StringMap mapBuilder = new StringMap(5);
        String mapString = mapBuilder.getMapString();
        assertTrue(pathExists(mapString));
    }

    /**
     * Helper method to check if there is a path from the spawn point to the door
     *
     * @param mapString The map to check
     * @return True if there is a path from the spawn point to the door, false
     *         otherwise
     */
    private boolean pathExists(String mapString) {
        char[][] map = stringToCharArray(mapString);
        int width = map[0].length;
        int height = map.length;

        Point spawnPoint = findCharacter(map, 'P');
        if (spawnPoint == null) {
            throw new IllegalArgumentException("Start character not found in map");
        }

        Queue<Point> queue = new LinkedList<>();
        queue.add(spawnPoint);

        boolean[][] visited = new boolean[height][width];
        visited[spawnPoint.y][spawnPoint.x] = true;

        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            for (int[] direction : directions) {
                int newX = current.x + direction[0];
                int newY = current.y + direction[1];

                if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                    if (!visited[newY][newX] && map[newY][newX] != 'X' && map[newY][newX] != '-') {
                        if (map[newY][newX] == 'D') {
                            return true; // Found a path to the door
                        }
                        queue.add(new Point(newX, newY));
                        visited[newY][newX] = true;
                    }
                }
            }
        }

        return false; // No path found from spawn to door
    }

    /**
     * Helper method to find a character in the map
     *
     * @param map       The map to search
     * @param character The character to find
     * @return The point where the character was found, or null if not found
     */
    private Point findCharacter(char[][] map, char character) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == character) {
                    return new Point(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Helper method to convert a string to a 2D char array
     *
     * @param mapString The string to convert
     * @return The 2D char array
     */
    private char[][] stringToCharArray(String mapString) {
        String[] rows = mapString.split("\n");
        char[][] map = new char[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            map[i] = rows[i].trim().toCharArray(); // Trim leading and trailing spaces
        }
        return map;
    }

}