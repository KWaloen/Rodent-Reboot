package earlybirds.ModelTest;

import org.junit.jupiter.api.Test;

import earlybirds.Model.ScoreKeeper;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreKeeperTest {
    @Test
    public void testInitialScoreIsZero() {
        ScoreKeeper scoreKeeper = new ScoreKeeper();
        assertEquals(0, scoreKeeper.getScore());
    }

    @Test
    public void testEnemyKilledIncreaseScore() {
        ScoreKeeper scoreKeeper = new ScoreKeeper();
        scoreKeeper.enemyKilledIncreaseScore(1);
        assertEquals(10, scoreKeeper.getScore());

        scoreKeeper.enemyKilledIncreaseScore(2);
        assertEquals(30, scoreKeeper.getScore());
    }

    @Test
    public void testInitialHighScoreIsNotNull() {
        ScoreKeeper scoreKeeper = new ScoreKeeper();
        assertNotNull(scoreKeeper.getHighScore());
    }

    @Test
    public void testHighScoreUpdatesCorrectly() {
        ScoreKeeper scoreKeeper = new ScoreKeeper();
        int initialHighScore = scoreKeeper.getHighScore();

        scoreKeeper.enemyKilledIncreaseScore(1);
        assertEquals(initialHighScore, scoreKeeper.getHighScore());
        assertFalse(scoreKeeper.isNewHighScore());

        scoreKeeper.enemyKilledIncreaseScore(2);
        assertEquals(initialHighScore, scoreKeeper.getHighScore());
        assertFalse(scoreKeeper.isNewHighScore());

        // Score decreases, but high score should remain the same
        scoreKeeper.enemyKilledIncreaseScore(-1);
        assertEquals(initialHighScore, scoreKeeper.getHighScore());
        assertFalse(scoreKeeper.isNewHighScore());

        // Score increases, high score should update
        scoreKeeper.enemyKilledIncreaseScore(500000);
        assertNotEquals(initialHighScore, scoreKeeper.getHighScore());
        assertEquals(scoreKeeper.getScore(), scoreKeeper.getHighScore());
        assertTrue(scoreKeeper.isNewHighScore());

        // Reset high score
        scoreKeeper.setHighScore(initialHighScore);
        assertEquals(initialHighScore, scoreKeeper.getHighScore());
    }

    @Test
    public void testResetScore() {
        ScoreKeeper scoreKeeper = new ScoreKeeper();
        scoreKeeper.enemyKilledIncreaseScore(1);
        scoreKeeper.resetScore();
        assertEquals(0, scoreKeeper.getScore());
    }

}
