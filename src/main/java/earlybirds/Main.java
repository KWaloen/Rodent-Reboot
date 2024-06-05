package earlybirds;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Main class for the game
 */
public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("Rodent Reboot");
        cfg.setWindowedMode(1920, 1080);
        cfg.setResizable(false);
        new Lwjgl3Application(new GameHandler(), cfg);
    }
}
