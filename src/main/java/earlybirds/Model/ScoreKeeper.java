package earlybirds.Model;

import java.io.*;

/**
 * Class to keep track of the score and high score of the game.
 */
public class ScoreKeeper {
    private int score;
    private int basePointsPerEnemy = 10;
    private int highScore;
    private File file;
    private boolean newHighScore;

    /**
     * Constructor for ScoreKeeper.
     * Handles the score and high score of the game.
     */
    public ScoreKeeper() {
        this.newHighScore = false;
        this.score = 0;

        // Set up high scores file if it does not exist
        File directory = new File(System.getProperty("user.home") + "/Rodent Reboot");
        if (!directory.exists()) {
            directory.mkdir();
        }
        this.file = new File(directory, "highscores.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("High scores file created: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error creating high scores file");
            }
        }
        loadHighScore();
    }

    /**
     * Increase the score when an enemy is killed.
     * 
     * @param level the level of the current map.
     */
    public void enemyKilledIncreaseScore(int level) {
        score += basePointsPerEnemy * level;
        if (score > highScore) {
            highScore = score;
            saveHighScore();
            newHighScore = true;
        }
    }

    /**
     * Get the current score.
     * 
     * @return the current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the high score.
     * 
     * @return the high score.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Load the high score from the file.
     */
    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading high score from file");
        }
    }

    /**
     * Save the high score to the file.
     */
    private void saveHighScore() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(highScore);
        } catch (IOException e) {
            System.out.println("Error writing high score to file");
        }
    }

    /**
     * Set the high score to a specific value, for testing.
     * 
     * @param scoreToSet the value to set the high score to.
     */
    public void setHighScore(int scoreToSet) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(scoreToSet);
            this.highScore = scoreToSet;
        } catch (IOException e) {
            System.out.println("Error writing high score to file");
        }
    }

    /**
     * @return true if the current score is a new high score, false otherwise.
     */
    public boolean isNewHighScore() {
        return newHighScore;
    }

    /**
     * Reset the score to 0 and set newHighScore to false.
     */
    public void resetScore() {
        score = 0;
        newHighScore = false;
    }
}
