package earlybirds.Model.Map.Grid;

/**
 * Used by the GridMap class to represent the position of rooms in the grid.
 */
public class GridPosition {
    private final int row;
    private final int col;

    /**
     * Create a new position in the grid.
     *
     * @param row the row of the position
     * @param col the column of the position
     */
    public GridPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the row of the position.
     *
     * @return the row of the position
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the column of the position.
     *
     * @return the column of the position
     */
    public int getCol() {
        return col;
    }
}
