package earlybirds.Model;

import java.util.HashMap;

public class Soundpack {
    private final HashMap<String, String> sounds;

    /**
     * Constructor for Soundpack, creates the sounds for the different events.
     */
    public Soundpack() {
        sounds = new HashMap<>();
        sounds.put("musicBackground", "src/main/resources/sounds/neon-fury-clipped.wav");
        sounds.put("regularShot", "src/main/resources/sounds/laser.wav");
        sounds.put("itemPickup", "src/main/resources/sounds/itemCollected.wav");
        sounds.put("doorOpen", "src/main/resources/sounds/door.wav");
        sounds.put("playerDamage", "src/main/resources/sounds/playerDamage.wav");
        sounds.put("playerDefeat", "src/main/resources/sounds/playerDefeat.wav");
        sounds.put("enemyDeath", "src/main/resources/sounds/enemyDeath.wav");
        sounds.put("highScore", "src/main/resources/sounds/highScore.wav");
    }

    /**
     * Returns the sound for the given key.
     *
     * @param key The key for the sound.
     * @return The sound.
     */
    public String getSound(String key) {
        return sounds.get(key);
    }


}
