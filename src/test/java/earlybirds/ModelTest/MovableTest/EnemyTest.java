package earlybirds.ModelTest.MovableTest;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import earlybirds.GdxTestRunner;
import earlybirds.Model.Movable.Enemy.Enemy;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.junit.After;

@RunWith(GdxTestRunner.class)
public class EnemyTest {
    Enemy enemy;

    @Before
    public void setup() {
        enemy = new Enemy(1);
    }

    @After
    public void tearDown() {
        enemy = null;
    }

    @Test
    public void moveEnemyTest() {

        enemy.setPos(0, 0);
        enemy.move(10, 10);
        assertEquals(new Vector2(10, 10), enemy.getPos());

        enemy.setPos(10, 10);
        enemy.move(10, 10);
        assertEquals(new Vector2(20, 20), enemy.getPos());

        enemy.setPos(20, 20);
        enemy.move(10, 10);
        assertEquals(new Vector2(30, 30), enemy.getPos());
    }

    @Test
    public void enemyLineOfSightTest() {
        enemy.setLastSeenPlayerPos(new Vector2(0, 50));
        float angleToPlayer = enemy.getLastSeenPosAngle();
        assertEquals(Math.PI / 2, angleToPlayer, 0.1);

        enemy.setLastSeenPlayerPos(new Vector2(200, 0));
        angleToPlayer = enemy.getLastSeenPosAngle();
        assertEquals(0, angleToPlayer, 0.1);

        enemy.setLastSeenPlayerPos(new Vector2(200, 200));
        angleToPlayer = enemy.getLastSeenPosAngle();
        assertEquals(Math.PI / 4, angleToPlayer, 0.1);

        enemy.setLastSeenPlayerPos(new Vector2(-200, -200));
        angleToPlayer = enemy.getLastSeenPosAngle();
        assertEquals(-Math.PI * 3 / 4, angleToPlayer, 0.1);

        enemy.setLastSeenPlayerPos(new Vector2(200, -200));
        angleToPlayer = enemy.getLastSeenPosAngle();
        assertEquals(-Math.PI * 1 / 4, angleToPlayer, 0.1);

    }

    @Test
    public void enemyHitBoxTest() {
        Shape2D hitbox = enemy.getCollisionHitbox(0, 0);
        assertTrue(hitbox instanceof Circle);
        assertFalse(hitbox instanceof Rectangle);

    }

}
