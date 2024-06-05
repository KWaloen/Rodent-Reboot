package earlybirds.Model.Movable;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Interface for objects that can move around in our world.
 */

public interface Movable {
    /**
     * @return Vector containing the X and Y coordinate respectively
     */
    public Vector2 getPos();

    /**
     * @return speed of the movable object
     */
    public float getSpeed();

    /**
     * Sets the center of the movable object to a new position given by the X and Y
     * coordinates
     *
     * @param x coordinate of new position
     * @param y coordinate of new position
     */
    public void setPos(float x, float y);

    /**
     * Rotates the movable object to an angle given by the faceangle
     *
     * @param faceAngle Is the angle of the sprite. If the rotation has never been
     *                  changed this angle is zero.
     */
    public void rotateToFace(float faceAngle);

    /**
     * Moves the object X positions to the right and Y positions upwards.
     *
     * @param xChange Distance moved to the right
     * @param yChange Distance moved upwards.
     */
    public void move(float xChange, float yChange);

    /**
     * Checks if two objects are equal
     *
     * @param obj The object to compare to
     * @return True if the objects are equal, false otherwise
     */
    public boolean equals(Object obj);

    /**
     * Returns a hitbox for the movable. This is either a Circle or Rectangle object
     * which is cast to
     * Shape2D depending on which shape fits the Movable better.
     *
     * @param xChange The change in x position
     * @param yChange The change in y position
     * @return A Shape2D object representing the hitbox of the Movable
     */
    public Shape2D getCollisionHitbox(float xChange, float yChange);

    /**
     * @return The sprite of the Movable
     */
    public Sprite getSprite();

    /**
     * @return The shield of the Movable
     */
    public Shield getShield();
}
