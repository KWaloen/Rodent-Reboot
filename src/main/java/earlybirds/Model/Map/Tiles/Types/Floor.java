package earlybirds.Model.Map.Tiles.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import earlybirds.Model.Texturepack;
import earlybirds.Model.Map.Tiles.TileSize;

/**
 * Floor is a class that represents a floor tile on the map.
 */
public class Floor implements Tile {
    private float xCord;
    private float yCord;
    private Rectangle bounds;
    private Sprite spriteFloor;

    /**
     * Constructor for Floor, creates the floor tile.
     *
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param tileEnum    enum for the size of the tile
     * @param texturepack texturepack to use
     */
    public Floor(float x, float y, TileSize tileEnum, Texturepack texturepack) {
        this.xCord = x;
        this.yCord = y;
        this.bounds = new Rectangle(x, y, tileEnum.getWidth(), tileEnum.getHeight());

        this.spriteFloor = new Sprite(texturepack.getTexture("floor"));
        this.spriteFloor.setSize(tileEnum.getWidth(), tileEnum.getHeight());
        this.spriteFloor.setPosition(x, y);
    }

    /**
     * Draws the floor tile.
     */
    public void drawTile(SpriteBatch batch) {
        spriteFloor.draw(batch);
    }

    /**
     * Returns whether the floor is walkable or not.
     */
    public boolean isWalkable() {
        return true; // Floors are walkable
    }

    /**
     * Returns the x-coordinate of the floor.
     */
    public float getXCord() {
        return xCord;
    }

    /**
     * Returns the y-coordinate of the floor.
     */
    public float getYCord() {
        return yCord;
    }

    /**
     * Returns the bounds of the floor.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Returns the sprite of the floor.
     */
    @Override
    public Sprite getTileSprite() {
        return spriteFloor;
    }
}
