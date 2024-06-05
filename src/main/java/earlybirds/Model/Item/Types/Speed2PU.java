package earlybirds.Model.Item.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Movable.Player;

/**
 * Item class for medium speed power up!
 */

public class Speed2PU implements Item {
    private Sprite speedPUSprite;
    private float speedBoost = 2;

    /**
     * Constructs a SpeedPU (Speed PowerUp) item
     */

    public Speed2PU() {
        this.speedPUSprite = new Sprite(ItemsTexturePack.getTexture("speed2PU"));
        this.speedPUSprite.setSize(100, 100);
    }

    @Override
    public void setPos(float x, float y) {
        this.speedPUSprite.setPosition(x - speedPUSprite.getWidth() / 2, y - speedPUSprite.getHeight() / 2);
    }

    @Override
    public void doPowerUpEffect(Player player) {

        player.setSpeed(player.getSpeed() + speedBoost);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                removeSpeedPU(player);
            }
        }, 30);
    }

    /**
     * Remove power up effect for time limited power up
     *
     * @param player
     */

    public void removeSpeedPU(Player player) {
        player.setSpeed(player.getSpeed() - speedBoost);
    }

    @Override
    public Sprite getSprite() {
        return speedPUSprite;
    }

    /**
     * For testing purposes
     * 
     * @return speedboost
     */
    public float getSpeedBoost() {
        return speedBoost;
    }
}
