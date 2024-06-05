package earlybirds.Model.Movable.Bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;
import earlybirds.Model.Movable.Shield;
import earlybirds.Model.Texturepack;

/**
 * Class for the bullets that the player and enemies can shoot
 */
public class Bullet implements Movable {
    public char symbol;
    private float speed;
    private final float damage;
    public Sprite bulletSprite;
    private final Vector2 position; // Current position of the bullet
    private Vector2 direction; // Direction towards the target
    Circle hitBox;

    /**
     * Constructs a bullet object based on the given symbol with specified speed and
     * picture
     *
     * @param symbol the symbol of the bullet
     * @param damage the damage the bullet does
     */
    public Bullet(char symbol, float speed, float damage, Vector2 initialPosition, Texture bulletTexture) {
        this.symbol = symbol;
        this.speed = speed;
        this.damage = damage;
        this.position = new Vector2(initialPosition); 
        this.bulletSprite = new Sprite(bulletTexture); 
        this.bulletSprite.setPosition(initialPosition.x, initialPosition.y); 
        hitBox = new Circle(0, 0, (float) 0.51 * bulletSprite.getWidth());
    }

    /**
     * Creates a bullet object based on the given symbol
     *
     * @param symbol the symbol of the bullet
     * @return a new bullet object
     */
    public static Bullet createBullet(char symbol, float speed, float damage, Texturepack texturepack,
            Vector2 initialPosition) {
        return switch (symbol) {
            case 'A' -> new Bullet(symbol, speed, 10, initialPosition, texturepack.getTexture("bulletOrange"));
            case 'E' -> new Bullet(symbol, speed, damage - (damage * Player.getDamageReduction() / 100),
                    initialPosition, texturepack.getTexture("bulletGreen"));
            default -> throw new IllegalArgumentException("Invalid bullet symbol: " + symbol);
        };
    }

    /**
     * Method to update the bullet's position
     */
    public void update() {
        float deltaTime = 60 * Gdx.graphics.getDeltaTime();
        Vector2 movement = new Vector2(direction).scl(speed * deltaTime);
        position.add(movement);
        bulletSprite.setPosition(position.x, position.y);
        hitBox.setPosition(position.x, position.y);
        float angle = direction.angleDeg(); 
        bulletSprite.setRotation(angle);
    }

    /**
     * @return the damage the bullet does
     */
    public float getDamage() {
        return this.damage;
    }

    /**
     * @return the symbol of the bullet
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Sets the direction of the bullet. The direction vector will be normalized.
     *
     * @param bulletDirection The new direction for the bullet.
     */
    public void setDirection(Vector2 bulletDirection) {
        this.direction = bulletDirection.nor();
    }

    @Override
    public Vector2 getPos() {
        return position;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setPos(float x, float y) {
        this.position.set(x, y);
        hitBox.setPosition(position.x, position.y);
    }

    @Override
    public void rotateToFace(float faceAngle) {
    }

    @Override
    public void move(float xChange, float yChange) {
    }

    @Override
    public Shape2D getCollisionHitbox(float xChange, float yChange) {
        return hitBox;
    }

    @Override
    public Sprite getSprite() {
        return bulletSprite;
    }

    @Override
    public Shield getShield() {
        return null;
    }
}
