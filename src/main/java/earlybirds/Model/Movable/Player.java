package earlybirds.Model.Movable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.Texturepack;

/**
 * A class for the player. This class is going to be a part of the model and
 * will be controlled by PlayerController.
 */
public class Player implements Movable {
    private Sprite playerSprite;
    private float speed;
    private Circle hitBox;
    private float health = 100;
    private Shield playerShield;
    private static int hitsTakenWithArmor = 0;
    private static int hitsBeforeNoArmor = 10;
    private static float bulletSpeed = 6;
    private static float damageReduction = 0; // damage reduction in percent
    private static final int SHIELD_DURATION = 1500;
    private static final int SHIELD_COOLDOWN = 15000;

    /**
     * Constructs player object.
     * Creates and assigns a new sprite object to the player and gives the player a
     * speed of 5.
     * The playerSprite size is set at 50x50.
     */
    public Player(Texturepack texturepack) {
        playerSprite = new Sprite(texturepack.getTexture("player"));
        speed = 5;
        playerSprite.setSize(70, 70);
        hitBox = new Circle(0, 0, (float) 0.51 * playerSprite.getWidth());
        this.playerShield = new Shield(texturepack, this, SHIELD_DURATION, SHIELD_COOLDOWN);
    }

    /**
     * @return the shield of the player
     */
    public Shield getShield() {
        return playerShield;
    }

    /**
     * @return Sprite of the player
     */
    @Override
    public Sprite getSprite() {
        return playerSprite;
    }

    /**
     * @return the health of the player
     */
    public float getHealth() {
        return health;
    }

    /**
     * Sets the health of the player
     *
     * @param newHealth the new health of the player
     */
    public void setHealth(float newHealth) {
        // Checks if armor is active, and handles the charges of the armor
        if (newHealth < this.health && Player.damageReduction > 0 && !playerShield.isImmune()) {
            Player.hitsTakenWithArmor++;
            if (Player.hitsTakenWithArmor >= Player.hitsBeforeNoArmor) {
                Player.setDamageReduction(0);
                Player.hitsTakenWithArmor = 0;
            }
        }
        this.health = newHealth;
    }

    @Override
    public Vector2 getPos() {
        return new Vector2(playerSprite.getX() + playerSprite.getWidth() / 2,
                playerSprite.getY() + playerSprite.getHeight() / 2);
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setPos(float x, float y) {
        playerSprite.setPosition(x - playerSprite.getWidth() / 2, y - playerSprite.getHeight() / 2);
        hitBox.setPosition(x, y);
    }

    @Override
    public void rotateToFace(float faceAngle) {
        playerSprite.setOriginCenter();
        playerSprite.setRotation(faceAngle);
    }

    @Override
    public void move(float xChange, float yChange) {
        hitBox.setPosition(hitBox.x + xChange, hitBox.y + yChange);
        playerSprite.setPosition(playerSprite.getX() + xChange, playerSprite.getY() + yChange);
    }

    @Override
    public Shape2D getCollisionHitbox(float xChange, float yChange) {
        return new Circle(hitBox.x + xChange, hitBox.y + yChange, hitBox.radius);
    }

    /**
     * Sets the speed of the player. Used by the speed power up item.
     *
     * @param speed the new speed of the player
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the speed of the bullets shot by the player
     *
     * @return the speed of the bullets
     */
    public static float getBulletSpeed() {
        return bulletSpeed;
    }

    /**
     * Sets the speed of the bullets shot by the player
     *
     * @param bulletSpeed the new speed of the bullets
     */
    public static void setBulletSpeed(float bulletSpeed) {
        Player.bulletSpeed = bulletSpeed;
    }

    /**
     * Gets the damage reduction of the player in percent
     *
     * @return the damage reduction of the player
     */
    public static float getDamageReduction() {
        return damageReduction;
    }

    /**
     * Sets the damage reduction of the player
     *
     * @param newDamageReduction the new damage reduction of the player
     */
    public static void setDamageReduction(float newDamageReduction) {
        Player.hitsTakenWithArmor = 0;
        Player.damageReduction = newDamageReduction;
    }
}
