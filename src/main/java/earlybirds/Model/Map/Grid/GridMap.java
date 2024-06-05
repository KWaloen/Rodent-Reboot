package earlybirds.Model.Map.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class representing a grid for mapping rooms in a game.
 * Should be used in the MapBuilder class, in order to place the individual
 * rooms correctly.
 * The symbol 'S' represents the starting room, 'R' represents a random room,
 * and 'E' represents the exit room.
 */
public class GridMap {
    private int gridSize;
    private char[][] grid;
    private List<GridPosition> filledPositions;

    /**
     * Constructs a MapGrid with a fixed size and places the starting
     * room at the center.
     */
    public GridMap(int roomsToAdd) {
        gridSize = 100;
        grid = new char[gridSize][gridSize];
        filledPositions = new ArrayList<>();
        int middleOfGrid = gridSize / 2;

        addRoom(middleOfGrid, middleOfGrid, 'S');

        for (int i = 0; i < roomsToAdd; i++) {
            addRoomRandomPosition('R');
        }
        addRoomRandomPosition('E');
        removeEmptyRowsAndColumns();
    }

    /**
     * Adds a room to the grid at the specified position.
     *
     * @param row  the starting row index of the room placement
     * @param col  the starting column index of the room placement
     * @param room the single character representing the room to add
     */
    private void addRoom(int row, int col, char room) {
        if (isValidPosition(row, col)) {
            grid[row][col] = room;
            filledPositions.add(new GridPosition(row, col));
        }
    }

    /**
     * Adds a room to a random position in the grid where it can be placed without
     * overlapping existing rooms
     * and ensuring adjacency to another room.
     *
     * @param room the string representation of the room to add
     * @throws IllegalStateException if a valid position for placing the room cannot
     *                               be found
     */
    private void addRoomRandomPosition(Character room) {
        Random random = new Random();
        List<GridPosition> potentialPositions = new ArrayList<>();
        for (GridPosition filledPos : filledPositions) {
            int row = filledPos.getRow();
            int col = filledPos.getCol();
            if (canPlaceRoom(row + 1, col)) {
                potentialPositions.add(new GridPosition(row + 1, col));
            }
            if (canPlaceRoom(row - 1, col)) {
                potentialPositions.add(new GridPosition(row - 1, col));
            }
            if (canPlaceRoom(row, col + 1)) {
                potentialPositions.add(new GridPosition(row, col + 1));
            }
            if (canPlaceRoom(row, col - 1)) {
                potentialPositions.add(new GridPosition(row, col - 1));
            }

        }
        if (potentialPositions.isEmpty()) {
            throw new IllegalStateException("Failed to find a valid position to place the room.");
        }
        GridPosition randomPos = potentialPositions.get(random.nextInt(potentialPositions.size()));
        addRoom(randomPos.getRow(), randomPos.getCol(), room);
    }

    /**
     * Checks if a room can be placed at the specified position without overlapping
     * existing rooms and ensuring adjacency to another room.
     *
     * @param row the starting row index of the room placement
     * @param col the starting column index of the room placement
     * @return true if the room can be placed without overlapping and adjacent to
     *         another room, false otherwise
     */
    private boolean canPlaceRoom(int row, int col) {

        if (!isValidPosition(row, col)) {
            return false;
        }

        if (grid[row][col] != '\0') {
            return false;
        }

        // True if adjacent to another room, not diagonally
        if (isValidPosition(row - 1, col) && grid[row - 1][col] != '\0') {
            return true;
        }
        if (isValidPosition(row + 1, col) && grid[row + 1][col] != '\0') {
            return true;
        }
        if (isValidPosition(row, col - 1) && grid[row][col - 1] != '\0') {
            return true;
        }
        if (isValidPosition(row, col + 1) && grid[row][col + 1] != '\0') {
            return true;
        }

        return false;
    }

    /**
     * Checks if the specified position is within the grid bounds.
     *
     * @param row the row index
     * @param col the column index
     * @return true if the position is within the grid bounds, false otherwise
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    /**
     * Retrieves the room character at the specified position in the grid.
     *
     * @param row the row index of the room
     * @param col the column index of the room
     * @return the character representing the room at the specified position,
     *         or '\0' if the position is empty or out of bounds
     */
    public char getRoom(int row, int col) {
        if (isValidPosition(row, col)) {
            return grid[row][col];
        }
        return '\0'; // Return null character if position is empty or out of bounds
    }

    /**
     * Retrieves the entire grid representing the map.
     *
     * @return a 2D char array representing the entire grid
     */
    public char[][] getGrid() {
        return grid;
    }

    /**
     * Retrieves the size of the grid.
     *
     * @return the size of the grid
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     * Converts the grid to a string representation, ignoring empty columns and
     * rows.
     *
     * @return a string representation of the grid
     */
    public String gridToString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < gridSize; row++) {
            boolean rowHasContent = false;
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] != '\0') {
                    rowHasContent = true;
                    break;
                }
            }
            if (rowHasContent) {
                for (int col = 0; col < gridSize; col++) {
                    sb.append(grid[row][col]);
                }
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Removes empty rows and columns from the grid.
     */
    private void removeEmptyRowsAndColumns() {
        List<Integer> nonEmptyRows = new ArrayList<>();
        for (int row = 0; row < gridSize; row++) {
            boolean rowHasContent = false;
            for (int col = 0; col < gridSize; col++) {
                if (grid[row][col] != '\0') {
                    rowHasContent = true;
                    break;
                }
            }
            if (rowHasContent) {
                nonEmptyRows.add(row);
            }
        }

        List<Integer> nonEmptyCols = new ArrayList<>();
        for (int col = 0; col < gridSize; col++) {
            boolean colHasContent = false;
            for (int row = 0; row < gridSize; row++) {
                if (grid[row][col] != '\0') {
                    colHasContent = true;
                    break;
                }
            }
            if (colHasContent) {
                nonEmptyCols.add(col);
            }
        }

        int maxNonEmptyRow = nonEmptyRows.size();
        int maxNonEmptyCol = nonEmptyCols.size();
        int newSize = Math.max(maxNonEmptyRow, maxNonEmptyCol);
        char[][] newGrid = new char[newSize][newSize];
        for (int i = 0; i < maxNonEmptyRow; i++) {
            for (int j = 0; j < maxNonEmptyCol; j++) {
                newGrid[i][j] = grid[nonEmptyRows.get(i)][nonEmptyCols.get(j)];
            }
        }

        grid = newGrid;
        gridSize = newSize;
    }
}
