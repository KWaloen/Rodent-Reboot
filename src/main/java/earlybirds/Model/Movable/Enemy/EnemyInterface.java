package earlybirds.Model.Movable.Enemy;

import com.badlogic.gdx.math.Vector2;
import earlybirds.Model.Movable.Movable;

/**
 * An interface which extends Movable and giving extra functionality for
 * Enemies.
 */
public interface EnemyInterface extends Movable {

    /**
     * Sets the last position an Enemy has seen the Player in.
     * 
     * @param lastSeenPlayerPos
     */
    public void setLastSeenPlayerPos(Vector2 lastSeenPlayerPos);

    /**
     *
     * @return The last position the enemy has seen the player in
     */
    public Vector2 getLastSeenPlayerPos();

    /**
     * @return The angle to the last seen position of the Player.
     */
    public float getLastSeenPosAngle();

}
