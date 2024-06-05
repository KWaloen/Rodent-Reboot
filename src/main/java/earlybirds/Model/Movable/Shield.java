package earlybirds.Model.Movable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import earlybirds.Model.Texturepack;


/**
 * Shield class for the player's shield
 */
public class Shield {
    static private int shieldSize = 150;
    private long immunityDuration;
    private long cooldownDuration;
    private long lastActivationTime;
    private Sprite shieldSprite;
    private Movable player;

    private Circle hitbox;


    public Shield(Texturepack texturePack, Movable player, int immunityDuration, int cooldownDuration) {
        this.immunityDuration = immunityDuration;
        this.cooldownDuration = cooldownDuration;
        this.lastActivationTime = System.currentTimeMillis() - cooldownDuration;
        this.shieldSprite = new Sprite(texturePack.getTexture("shield"));
        this.shieldSprite.setPosition(player.getPos().x - (float) shieldSize / 2, player.getPos().y - shieldSize / 2);
        shieldSprite.setSize(shieldSize, shieldSize);
        this.player = player;
        hitbox = new Circle(player.getPos().x, player.getPos().y, shieldSprite.getWidth() / 2);

    }

    /**
     * Method to update the bullet's position
     */
    public void update() {
        hitbox.setPosition(player.getPos().x, player.getPos().y);
        this.shieldSprite.setPosition(player.getPos().x - shieldSize / 2, player.getPos().y - shieldSize / 2);
    }

    /**
     * Activate the shield
     */
    public void activate() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastActivationTime >= cooldownDuration) {
            lastActivationTime = currentTime;
        }
    }

    /**
     * Check if the shield is active
     *
     * @return true if the shield is active, false otherwise
     */
    public boolean isImmune() {
        long currentTime = System.currentTimeMillis();
        return currentTime - lastActivationTime <= immunityDuration;
    }

    /**
     * Get the sprite of the shield
     *
     * @return the sprite of the shield
     */
    public Sprite getSprite() {
        return shieldSprite;
    }

    /**
     * Get the size of the shield
     *
     * @return the size of the shield
     */
    public long getImmunityDuration() {
        return immunityDuration;
    }

    /**
     * Method to get the remaining cooldown time
     *
     * @return the remaining cooldown time in milliseconds
     */
    public long getRemainingCooldown() {
        long remainingCooldown = cooldownDuration - (System.currentTimeMillis() - lastActivationTime);
        return Math.max(remainingCooldown, 0);
    }

    public float getRemainingImmunity() {
        long currentTime = System.currentTimeMillis();
        long timesincelast = currentTime - lastActivationTime;
        if (isImmune())
            return (immunityDuration - timesincelast);
        else if ((currentTime - lastActivationTime >= cooldownDuration))
            return immunityDuration;
        else
            return 0f;
    }


    public Circle getHitbox() {
        return hitbox;

    }
}



