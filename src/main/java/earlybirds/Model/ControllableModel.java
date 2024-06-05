package earlybirds.Model;

import com.badlogic.gdx.math.Vector2;
import earlybirds.EventBus.EventHandler;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Enemy.EnemyInterface;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;

import java.util.List;

/**
 * Interface which limits what the controllers have access to.
 */
public interface ControllableModel {

    /**
     * @return Player object with the type Movable.
     */
    public Movable getControllablePlayer();

    /**
     * @return The list of enemies
     */
    public List<EnemyInterface> getEnemies();

    /**
     * Checks if the player sprite overlaps with the door sprite. If so, then go
     * through the door and load the next room.
     * param sprite
     */
    public void doorCheck();

    /**
     * Adds bullet to list of bullets.
     */
    void addBullet(Bullet bullet);

    /**
     * Checks if a move suggested for a Movable object is possible.
     * Return true if it doesn't collide with other objects.
     *
     * @param movable Movable object
     * @param x       x-coordinate
     * @param y       y-coordinate
     * @return boolean
     */
    public boolean validMovableMove(Movable movable, float x, float y);

    /**
     * @param pos the position
     * @return Angle for a line stretched between a position to the Player.
     */
    public float getAngleFromPosToPlayer(Vector2 pos);

    /**
     * @param enemy Enemy object
     * @return Boolean value for whether enemy can see Player.
     */
    public boolean lineOfSight(EnemyInterface enemy);

    /**
     * @param x coordinate of position
     * @param y coordinate of position
     * @return distance from a given position to the Player.
     */
    public float distanceToPlayer(float x, float y);

    /**
     * Checks that the new position of the movable doesn't overlap with walls and
     * moves the movable
     * xChange positions to the right and yChange positions upwards.
     * If moving diagonally into walls it allows for sliding along it.
     *
     * @param xChange distance moved in y direction (upwards)
     * @param yChange distance moved in x direction (to the right)
     * @param movable movable object
     */
    public void checkPosMove(float xChange, float yChange, Movable movable);

    /**
     * Checks if the item sprite overlaps with the player sprite. If so, then
     * trigger powerup effect.
     * param sprite
     * param player the player
     */
    public void itemCollisionCheck(Player player);

    /**
     * Checks the playerHealth and if it's zero or below the gamestate is set to
     * GameOver.
     */
    public void playerHealthCheck();

    /**
     * @return the Texturepack
     */
    Texturepack getTexturepack();

    public void registerEventHandler(EventHandler handler);

    /**
     * @return the current level
     */
    public Integer getCurrentLevel();
}
