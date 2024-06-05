package earlybirds.ModelTest.MapTest;

import org.junit.After;
import earlybirds.Model.Map.Grid.GridMap;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class GridMapTest {
    private static GridMap mapGrid;

    @After
    public void tearDown() {
        mapGrid = null;
    }

    /**
     * Tests that the mapGrid is not null after initialization
     */

    @Test
    public void testSetUp() {
        mapGrid = new GridMap(0); // Set up the mapGrid with only the starting room and exit room
        assertNotNull("Grid should not be null after initialization", mapGrid.getGrid());
        assertTrue(mapGrid.getGridSize() > 0, "Grid size should be greater than 0");
    }

    /**
     * Adds one room, and checks that it is adjacent to other rooms
     */
    @Test
    public void testAddRandomRoom() {
        // Set up the mapGrid with one rooms, in addition to the starting room and exit
        // room
        mapGrid = new GridMap(1);

        // Check each added room for adjacency to other rooms
        for (int i = 0; i < mapGrid.getGridSize(); i++) {
            for (int j = 0; j < mapGrid.getGridSize(); j++) {
                if (mapGrid.getRoom(i, j) == 'R') {
                    assertTrue(isAdjacentToOtherRoom(i, j),
                            "Room 'R' at position (" + i + ", " + j + ") should be adjacent to another room");
                }
            }
        }

    }

    /**
     * Adds five rooms, and checks that each room is adjacent to other rooms
     */
    @Test
    public void testAddFiveRandomRooms() {
        // Set up the mapGrid with five rooms, in addition to the starting room and exit
        // room
        mapGrid = new GridMap(5);

        // Check each added room for adjacency to other rooms
        for (int i = 0; i < mapGrid.getGridSize(); i++) {
            for (int j = 0; j < mapGrid.getGridSize(); j++) {
                if (mapGrid.getRoom(i, j) == 'R') {
                    assertTrue(isAdjacentToOtherRoom(i, j),
                            "Room 'R' at position (" + i + ", " + j + ") should be adjacent to another room");
                }
            }
        }

    }

    /**
     * Checks if a room at the specified position is adjacent to another room.
     *
     * @param row the row index of the room
     * @param col the column index of the room
     * @return true if the room is adjacent to another room, false otherwise
     */
    private boolean isAdjacentToOtherRoom(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i != row || j != col) { // Skip the current cell
                    if (mapGrid.isValidPosition(i, j) && mapGrid.getRoom(i, j) != '\0') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Tests the randomness of room placement by adding 100 rooms to 20 different
     * maps and checking that the maps are not identical.
     */
    @Test
    public void testRandomnessOfRoomPlacement() {
        List<String> roomStrings = new ArrayList<>();
        int roomsToAdd = 100;
        for (int m = 0; m < 20; m++) {
            GridMap tempGrid = new GridMap(roomsToAdd);
            roomStrings.add(tempGrid.gridToString());
        }

        // Check that none of the roomStrings are identical
        for (int i = 0; i < roomStrings.size() - 1; i++) {
            String currentString = roomStrings.get(i);
            for (int j = i + 1; j < roomStrings.size(); j++) {
                String compareString = roomStrings.get(j);
                assertNotEquals(currentString, compareString, "Map configurations should not be identical.");
            }
        }
    }
}
