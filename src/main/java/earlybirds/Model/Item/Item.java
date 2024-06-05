package earlybirds.Model.Item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Model.Movable.Player;

public interface Item {

    /**
     * Sets the position of the item
     * 
     * @param x
     * @param y
     */
    void setPos(float x, float y);

    /**
     * Triggers the effect of the specific item.
     * 
     * @param player
     */
    void doPowerUpEffect(Player player);

    /**
     * Return the sprite for the item.
     * 
     * @return
     */
    Sprite getSprite();

}
