package earlybirds.Model.Item.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Movable.Player;

/**
 * Item class for FullHealth power up!
 */
public class FullHealthPU implements Item {

    private Sprite fullHealthPUSprite;
    private float maxHealth = 100;

    /**
     * Constructs FullHealthPU (FullHealth PowerUp) item.
     */
    public FullHealthPU() {
        this.fullHealthPUSprite = new Sprite(ItemsTexturePack.getTexture("fullHealthPU"));
        this.fullHealthPUSprite.setSize(100, 100);
    }

    @Override
    public void setPos(float x, float y) {
        this.fullHealthPUSprite.setPosition(x - fullHealthPUSprite.getWidth() / 2,
                y - fullHealthPUSprite.getHeight() / 2);
    }

    @Override
    public void doPowerUpEffect(Player player) {
        player.setHealth(maxHealth);
    }

    @Override
    public Sprite getSprite() {
        return fullHealthPUSprite;
    }

}
