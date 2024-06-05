package earlybirds.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import earlybirds.Model.ControllableModel;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.Movable.Movable;
import earlybirds.Model.Movable.Player;

/**
 * A controller for the player which has access to the model via the
 * ControllableModel interface.
 */
public class PlayerController extends InputAdapter implements Controller {
    private final OrthographicCamera camera;
    private ControllableModel model;
    private Movable player;
    private float faceAngle;
    private Input input;

    /**
     * Constructs PlayerController object.
     * input parameter is for testing purposes.
     *
     * @param model the model
     * @param input for testing purposes
     */
    public PlayerController(ControllableModel model, Input input, OrthographicCamera camera) {
        this.model = model;
        this.player = model.getControllablePlayer();
        this.input = input;
        this.camera = camera;

    }

    /**
     * Updates the player's position and angle.
     */
    @Override
    public void tick() {
        this.player = model.getControllablePlayer();
        movePlayer();
        calculateAngle();
        player.rotateToFace(faceAngle);
        model.doorCheck();
        model.itemCollisionCheck((Player) player);

    }

    /**
     * Moves player depending on the keys pressed and the player speed.
     * Multiple keys can be pressed at the same time to change direction.
     */
    public void movePlayer() {
        float timeDelta = 60 * Gdx.graphics.getDeltaTime();
        float speed = player.getSpeed();
        Vector2 movement = new Vector2();

        if (input.isKeyPressed(Input.Keys.W)) {
            movement.y += 1;
        }
        if (input.isKeyPressed(Input.Keys.S)) {
            movement.y -= 1;
        }
        if (input.isKeyPressed(Input.Keys.A)) {
            movement.x -= 1;
        }
        if (input.isKeyPressed(Input.Keys.D)) {
            movement.x += 1;
        }


        movement.nor();
        movement.x *= speed * timeDelta;
        movement.y *= speed * timeDelta;
        model.checkPosMove(movement.x, movement.y, player);
    }

    /**
     * Calculate faceAngle of the player in relation to the mouse.
     * <p>
     * Source:
     * https://www.reddit.com/r/libgdx/comments/m9bx70/libgdx_make_player_rotate_towards_mouse_pointer/
     * Kai Inf101 project BugShooter, 2023
     */
    private void calculateAngle() {
        float distanceX = (float) Gdx.graphics.getWidth() / 2 - getMousePos().x;
        float distanceY = (float) Gdx.graphics.getHeight() / 2 - getMousePos().y;
        this.faceAngle = MathUtils.radiansToDegrees * (float) Math.atan2(distanceY, distanceX);
    }

    /**
     * This method retrieves the mouse coordinates by using Gdx.input.getX() and
     * Gdx.input.getY().
     * getHeight() - getY() was required because the mouse and player coordinates
     * are flipped
     */
    private Vector2 getMousePos() {
        Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        return mousePosition;
    }

    /**
     * Shoots a bullet when the left mouse button is pressed.
     */
    private void shootBullet() {
        Vector2 bulletStartPosition = new Vector2(player.getPos().x - 7, player.getPos().y - 7);
        Vector2 bulletDirection = BulletDirection(bulletStartPosition);
        bulletStartPosition.add(bulletDirection.scl(player.getSprite().getWidth() / 2));

        Bullet bullet = Bullet.createBullet('A', Player.getBulletSpeed(), 10, model.getTexturepack(),
                bulletStartPosition);

        bullet.setDirection(bulletDirection);
        model.addBullet(bullet);
    }

    /**
     * Calculate the direction of the bullet based on the mouse position.
     *
     * @param bulletStartPosition the start position of the bullet
     * @return the direction of the bullet
     */
    private Vector2 BulletDirection(Vector2 bulletStartPosition) {
        Vector3 mousePosInWorld = getMousePositionOnScreen();
        float distanceX = mousePosInWorld.x - bulletStartPosition.x;
        float distanceY = mousePosInWorld.y - bulletStartPosition.y;
        float angle = MathUtils.radiansToDegrees * (float) Math.atan2(distanceY, distanceX);
        float rad = angle * MathUtils.degreesToRadians;
        return new Vector2(MathUtils.cos(rad), MathUtils.sin(rad));
    }

    /**
     * Get the mouse position in the world coordinates.
     */
    private Vector3 getMousePositionOnScreen() {
        Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        return camera.unproject(screenCoords);
    }

    /**
     * Shield the player when the right mouse button is pressed.
     */
    private void shieldPlayer() {
        if (!player.getShield().isImmune()) {
            player.getShield().activate();
        }
    }

    /**
     * Shoots a bullet when the left mouse button is pressed.
     * Shields the player when the right mouse button is pressed.
     */
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            shootBullet();
        }
        if(button == Input.Buttons.RIGHT) {
            shieldPlayer();
        }
        return true;

    }

    /**
     * Activates the player controller, so it can receive input from mouse
     */
    public void activate() {
        Gdx.input.setInputProcessor(null);
        Gdx.input.setInputProcessor(this);
    }
}
