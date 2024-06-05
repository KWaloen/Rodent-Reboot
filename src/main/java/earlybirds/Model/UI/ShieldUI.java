package earlybirds.Model.UI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Model.Movable.Shield;
import earlybirds.Model.Texturepack;

/**
 * A simple class for a UI bar for a shield.
 */
public class ShieldUI {
    Shield shield;
    Sprite sprite;
    Sprite shieldCharging;
    @SuppressWarnings("unused")
    private float defaultWidth;
    private float defaultBoundWidth;

    /**
     * Creates a shieldUI from a shield
     *
     * @param shield the shield
     * @param pack   the texturepack
     * @param x      coordinate pf lower left corner
     * @param y      coordinate of lower left corner
     * @param sizex  starting width of the sprite
     * @param sizey  the height of the sprite
     */
    public ShieldUI(Shield shield, Texturepack pack, float x, float y, float sizex, float sizey) {
        this.shield = shield;
        this.sprite = new Sprite(pack.getTexture("greenbar"));
        sprite.setBounds(x, y, sizex, sizey);
        defaultWidth = sprite.getRegionWidth();
        defaultBoundWidth = sprite.getWidth();
        shieldCharging = new Sprite(pack.getTexture("yellowbar"));
    }

    /**
     * Updates the shield bar with new info from the shield
     */
    public void shieldUpdate() {
        float fraction = shield.getRemainingImmunity() / (float) shield.getImmunityDuration();
        if (fraction > 0) {
            sprite.setRegion(0, 0, fraction, 1);
            sprite.setBounds(sprite.getX(), sprite.getY(), defaultBoundWidth * fraction, sprite.getHeight());
        } else {

            fraction = (15000f - shield.getRemainingCooldown()) / 15000f;
            shieldCharging.setRegion(0, 0, fraction, 1);
            shieldCharging.setBounds(sprite.getX(), sprite.getY(), defaultBoundWidth * fraction, sprite.getHeight());
        }
    }

    /**
     * @return if the shield can be activated it returns the normal shield sprite,
     * if not it returns
     * a sprite representing it recharging
     */
    public Sprite getSprite() {
        if (shield.getRemainingImmunity() > 0) {
            return sprite;
        } else
            return shieldCharging;
    }
}
