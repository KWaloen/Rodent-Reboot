package earlybirds.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.ControllableModel;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Enemy.EnemyInterface;

import java.util.HashMap;

/**
 * Controller class for Enemy objects. This will be able to control Enemy
 * objects within the model.
 */

public class EnemyController implements Controller {
    private int bulletDamage;
    private int bulletSpeed;
    private float bulletInterval;
    private int previousMapLevel;
    private int bulletFireDistance;
    private int agroRange;
    ControllableModel model;
    private final HashMap<EnemyInterface, Float> lastShootTime = new HashMap<>();

    /**
     * Creates an Enemy controller
     *
     * @param model It gets access to the Enemies via Controllable model interface
     */
    public EnemyController(ControllableModel model) {
        previousMapLevel = 0;
        this.model = model;
    }

    /**
     * Tick method for the Enemy controller. This will be called every frame.
     */
    @Override
    public void tick() {
        if (this.previousMapLevel != model.getCurrentLevel()) {
            scaleDifficulty();
            this.previousMapLevel = model.getCurrentLevel();
        }
        move();
    }

    /**
     * Takes care of the movement one step forward in time.
     * As well as shooting bullets in the direction of the player.
     */
    public void move() {
        float timeDelta = Gdx.graphics.getDeltaTime();
        for (EnemyInterface enemy : model.getEnemies()) {
            if (model.distanceToPlayer(enemy.getPos().x, enemy.getPos().y) > agroRange) {
                continue;
            }
            if (model.lineOfSight(enemy)) {
                enemy.setLastSeenPlayerPos(model.getControllablePlayer().getPos());
                enemy.rotateToFace(enemy.getLastSeenPosAngle());
            }
            if (!(enemy.getLastSeenPlayerPos() == null)) {
                float angle = enemy.getLastSeenPosAngle();
                float x = enemy.getSpeed() * (float) Math.cos(angle) * 60 * timeDelta;
                float y = enemy.getSpeed() * (float) Math.sin(angle) * 60 * timeDelta;
                if (enemy.getPos().dst(enemy.getLastSeenPlayerPos()) > 5) {
                    model.checkPosMove(x, y, enemy);
                }
                enemy.rotateToFace(enemy.getLastSeenPosAngle());
                shootBullet(angle, enemy);
            }
        }
    }

    /**
     * Shoots a bullet in the direction of the player.
     *
     * @param angle The angle the enemy is facing
     * @param enemy The enemy which might shoot
     */
    public void shootBullet(float angle, EnemyInterface enemy) {
        if (!lastShootTime.containsKey(enemy)) {
            lastShootTime.put(enemy, 0.0f);
        }

        float timeSinceLastShot = lastShootTime.get(enemy) + Gdx.graphics.getDeltaTime();
        lastShootTime.put(enemy, timeSinceLastShot);

        // Shoot a bullet if the enemy has line of sight to the player and the time
        // since the last shot is greater than 1 second and the player is within n units
        if (model.lineOfSight(enemy) && timeSinceLastShot >= bulletInterval
                && model.distanceToPlayer(enemy.getPos().x, enemy.getPos().y) < bulletFireDistance) {

            Bullet bullet = Bullet.createBullet('E', bulletSpeed, bulletDamage, model.getTexturepack(), enemy.getPos());
            Vector2 bulletStartPosition = enemy.getPos();

            Vector2 bulletDirection = new Vector2(MathUtils.cos(angle), MathUtils.sin(angle));
            bullet.setDirection(bulletDirection);

            bulletStartPosition.add(new Vector2(bulletDirection).scl(enemy.getSprite().getHeight() / 2));
            bullet.setPos(bulletStartPosition.x, bulletStartPosition.y);

            model.addBullet(bullet);

            lastShootTime.put(enemy, 0.0f);

        }
    }

    /**
     * Scales the difficulty of the game based on the current level.
     */
    private void scaleDifficulty() {
        switch (model.getCurrentLevel()) {
            case 1:
                this.bulletDamage = 10;
                this.bulletSpeed = 5;
                this.bulletInterval = 1f;
                this.bulletFireDistance = 450;
                this.agroRange = 1000;
                break;
            case 2:
                this.bulletDamage = 12;
                this.bulletSpeed = 6;
                this.bulletInterval = 1f;
                this.bulletFireDistance = 600;
                break;
            case 3:
                this.bulletDamage = 15;
                this.bulletSpeed = 6;
                this.bulletInterval = 0.75f;
                this.bulletFireDistance = 750;
                break;
            case 4:
                this.bulletDamage = 17;
                this.bulletSpeed = 7;
                this.bulletInterval = 0.75f;
                this.bulletFireDistance = 1000;
                this.agroRange = 1200;
                break;
            case 5:
                this.bulletDamage = 20;
                this.bulletSpeed = 7;
                this.bulletInterval = 0.75f;
                this.bulletFireDistance = 1250;
                this.agroRange = 1400;
                break;
            case 6:
                this.bulletDamage = 20;
                this.bulletSpeed = 7;
                this.bulletInterval = 0.5f;
                this.bulletFireDistance = 1500;
                this.agroRange = 1600;
                break;
            case 7:
                this.bulletDamage = 30;
                this.bulletSpeed = 9;
                this.bulletInterval = 0.5f;
                this.bulletFireDistance = 2000;
                this.agroRange = 9001;
            default:
                this.bulletDamage += 10;
                break;
        }
    }

    /**
     * Get the bullet damage, for testing
     *
     * @return the bullet damage
     */
    public int getBulletDamage() {
        return bulletDamage;
    }
}
