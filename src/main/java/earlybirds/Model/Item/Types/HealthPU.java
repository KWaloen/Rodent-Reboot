package earlybirds.Model.Item.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Movable.Player;



/**
 * Item class for health power up (healing)!
 */
public class HealthPU implements Item {
    private Sprite healthPUSprite;
    private float healAmount = 10;
    private float maxHealth = 100;

    /**
     * Constructs HealthPU (Health PowerUp) item.
     */
    public HealthPU() {
        this.healthPUSprite = new Sprite(ItemsTexturePack.getTexture("healthPU"));
        this.healthPUSprite.setSize(100, 100);
    }

    @Override
    public void setPos(float x, float y) {
        this.healthPUSprite.setPosition(x - healthPUSprite.getWidth() / 2, y - healthPUSprite.getHeight() / 2);
    }

    @Override
    public void doPowerUpEffect(Player player) {
        player.setHealth(player.getHealth() + healAmount);
        if (player.getHealth() > maxHealth) {
            player.setHealth(maxHealth);
        }
    }

    @Override
    public Sprite getSprite() {
        return healthPUSprite;
    }

}
