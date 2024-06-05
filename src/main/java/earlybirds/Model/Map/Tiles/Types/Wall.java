package earlybirds.Model.Map.Tiles.Types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import earlybirds.Model.Map.Tiles.TileSize;

/**
 * Wall is a class that represents a wall tile on the map.
 */
public class Wall implements Tile {
    private float xCord;
    private float yCord;
    private Rectangle bounds;
    private Sprite spriteWall;

    /**
     * Constructor for Wall, creates the wall tile.
     *
     * @param x           x-coordinate
     * @param y           y-coordinate
     * @param tileEnum    enum for the size of the tile
     * @param texture texture to use
     */
    public Wall(float x, float y, TileSize tileEnum, Texture texture) {
        this.xCord = x;
        this.yCord = y;
        this.bounds = new Rectangle(x, y, tileEnum.getWidth(), tileEnum.getHeight());

        this.spriteWall = new Sprite(texture);
        this.spriteWall.setSize(tileEnum.getWidth(), tileEnum.getHeight());
        this.spriteWall.setPosition(x, y);
    }

    /**
     * Draws the wall tile.
     */
    public void drawTile(SpriteBatch batch) {
        spriteWall.draw(batch);
    }

    /**
     * Returns whether the wall is walkable or not.
     */
    public boolean isWalkable() {
        return false; // Walls are not walkable
    }

    /**
     * Returns the x-coordinate of the wall.
     */
    public float getXCord() {
        return xCord;
    }

    /**
     * Returns the y-coordinate of the wall.
     */
    public float getYCord() {
        return yCord;
    }

    /**
     * Returns the bounds of the wall.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Returns the sprite of the wall.
     */
    @Override
    public Sprite getTileSprite() {
        return spriteWall;
    }
}
