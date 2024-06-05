package earlybirds.Model.Item.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Movable.Player;

/**
 * Item class for BulletSpeed power up!
 */

public class BulletSpeedPU implements Item {

    private Sprite bulletSpeedPUSprite;
    private float bulletSpeedIncrease = 10;

    /**
     * Constructs BulletSpeedPU (BulletSpeed PowerUp) item.
     */

    public BulletSpeedPU() {
        this.bulletSpeedPUSprite = new Sprite(ItemsTexturePack.getTexture("bulletSpeedPU"));
        this.bulletSpeedPUSprite.setSize(100, 100);
    }

    /**
     * Remove power up effect for time limited power up
     */
    public void removePowerUpEffect() {
        Player.setBulletSpeed(Player.getBulletSpeed() - bulletSpeedIncrease);
    }

    @Override
    public void setPos(float x, float y) {
        this.bulletSpeedPUSprite.setPosition(x - bulletSpeedPUSprite.getWidth() / 2,
                y - bulletSpeedPUSprite.getHeight() / 2);
    }

    @Override
    public void doPowerUpEffect(Player player) {
        Player.setBulletSpeed(Player.getBulletSpeed() + bulletSpeedIncrease);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                removePowerUpEffect();
            }
        }, 30);
    }

    @Override
    public Sprite getSprite() {
        return bulletSpeedPUSprite;
    }

    /**
     * For testing purposes
     *
     * @return
     */
    public float getBulletSpeedIncrease() {
        return bulletSpeedIncrease;
    }
}
