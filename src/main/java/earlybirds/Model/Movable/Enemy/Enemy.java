package earlybirds.Model.Movable.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.Texturepack;
import earlybirds.Model.Movable.Shield;

/**
 * Enemy Class, represents a basic form of an enemy within the game.
 */

public class Enemy implements EnemyInterface {
    Sprite enemySprite;
    float speed;
    Circle hitBox;
    Vector2 lastSeenPlayerPos;
    private int currentMapLevel;
    static Texturepack texturepack = new Texturepack();

    /**
     * Creates enemy object at given position
     * 
     * @param x coordinate of position
     * @param y coordinate of position
     */
    public Enemy(float x, float y, Texture texture, int currentMapLevel) {
        this.currentMapLevel = currentMapLevel;
        setSpeed();
        enemySprite = new Sprite(texture);
        enemySprite.setSize(80, 80);
        hitBox = new Circle(0,0,(float)0.35*enemySprite.getWidth());
        setPos(x,y);
    }

    /**
     * Create an Enemy at (0,0) position.
     */
    public Enemy(int currentMapLevel) {
        this.currentMapLevel = currentMapLevel;
        enemySprite = new Sprite(texturepack.getTexture("enemy"));
        setSpeed();
        hitBox = new Circle(0, 0, (float) 0.51 * enemySprite.getWidth());
        setPos(0, 0);
    }

    /**
     * Set the speed of the enemy based on the current map level.
     */
    private void setSpeed(){
        if (currentMapLevel < 3) {
            speed = 2;
        } else if (currentMapLevel >= 3 && currentMapLevel <= 5) {
            speed = 3;
        } else if (currentMapLevel >= 6 && currentMapLevel <= 7) {
            speed = 4;
        } else {
            speed = 5;
        }
    }


    @Override
    public Vector2 getPos() {
        return new Vector2(enemySprite.getX() + enemySprite.getWidth() / 2,
                enemySprite.getY() + enemySprite.getHeight() / 2);
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setPos(float x, float y) {
        enemySprite.setPosition(x - enemySprite.getWidth() / 2, y - enemySprite.getHeight() / 2);
        hitBox.setPosition(x, y);
    }

    @Override
    public void move(float xChange, float yChange) {
        enemySprite.setPosition(enemySprite.getX() + xChange, enemySprite.getY() + yChange);
        hitBox.setPosition(hitBox.x + xChange, hitBox.y + yChange);
    }

    @Override
    public void rotateToFace(float faceAngle) {
        enemySprite.setOriginCenter();
        enemySprite.setRotation((float) Math.toDegrees(faceAngle - Math.PI));
    }

    /**
     * @return Enemy Sprite
     */
    @Override
    public Sprite getSprite() {
        return enemySprite;
    }

    @Override
    public void setLastSeenPlayerPos(Vector2 lastSeenPos) {
        this.lastSeenPlayerPos = lastSeenPos;
    }

    @Override
    public Vector2 getLastSeenPlayerPos() {
        return lastSeenPlayerPos;
    }

    @Override
    public float getLastSeenPosAngle() {
        Vector2 direction = new Vector2(lastSeenPlayerPos.x - getPos().x, lastSeenPlayerPos.y - getPos().y);
        float angle = (float) Math.atan2(direction.y, direction.x);
        return angle;
    }

    @Override
    public Shape2D getCollisionHitbox(float xChange, float yChange) {
        return new Circle(hitBox.x + xChange, hitBox.y + yChange, hitBox.radius);
    }

    @Override
    public Shield getShield() {
        return null;
    }
}
