package earlybirds.Model.Map.Tiles.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import earlybirds.Model.Texturepack;
import earlybirds.Model.Map.Tiles.TileSize;

/**
 * Door is a class that represents a door tile on the map.
 */
public class Door implements Tile {
    private float xCord;
    private float yCord;
    private Rectangle bounds;
    private Sprite spriteDoor;

    /**
     * Constructor for Door, creates the door tile.
     *
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param tileEnum    enum for the size of the tile
     * @param texturepack texturepack to use
     */
    public Door(float x, float y, TileSize tileEnum, Texturepack texturepack) {
        this.xCord = x;
        this.yCord = y;
        this.bounds = new Rectangle(x, y, tileEnum.getWidth(), tileEnum.getHeight());

        this.spriteDoor = new Sprite(texturepack.getTexture("door"));
        this.spriteDoor.setSize(tileEnum.getWidth(), tileEnum.getHeight());
        this.spriteDoor.setPosition(x, y);
    }

    /**
     * Draws the door tile.
     */
    public void drawTile(SpriteBatch batch) {
        spriteDoor.draw(batch);
    }

    /**
     * Returns whether the door is walkable or not.
     */
    public boolean isWalkable() {
        return true;
    }

    /**
     * Returns the x-coordinate of the door.
     */
    public float getXCord() {
        return xCord;
    }

    /**
     * Returns the y-coordinate of the door.
     */
    public float getYCord() {
        return yCord;
    }

    /**
     * Returns the bounds of the door.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Returns the sprite of the door.
     */
    @Override
    public Sprite getTileSprite() {
        return spriteDoor;
    }
}
