package earlybirds.Model.Map.Tiles;

/**
 * The different sizes of the tiles.
 * This 
 */
public enum TileSize {
    VERYSMALL_TILE(10, 10),
    LARGE_TILE(100, 100);

    private final int width;
    private final int height;

    TileSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
