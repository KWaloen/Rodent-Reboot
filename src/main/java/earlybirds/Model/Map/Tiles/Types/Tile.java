package earlybirds.Model.Map.Tiles.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Interface for the different tiles on the map.
 */
public interface Tile {
    void drawTile(SpriteBatch batch);

    boolean isWalkable();

    float getXCord();

    float getYCord();

    Rectangle getBounds();

    Sprite getTileSprite();
}
