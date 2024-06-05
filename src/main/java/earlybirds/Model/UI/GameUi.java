package earlybirds.Model.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Menu.Buttons.Textbox;
import earlybirds.Model.Model;
import earlybirds.Model.Movable.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple class which combined all the UI elements that is displayed during
 * gameplay
 */
public class GameUi implements ViewableUi {
    private HealthBar healthBar;
    private ShieldUI shieldUi;
    private Textbox levelText;
    private Textbox scoreText;
    private BitmapFont font;
    private Model model;
    GlyphLayout layout;

    /**
     * Creates the UI elements with information from the model
     * 
     * @param model the model
     */
    public GameUi(Model model) {
        this.model = model;
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        healthBar = new HealthBar((Player) model.getControllablePlayer(), model.getTexturepack(), width / 20f,
                height * 2 / 20f, width / 10f, 50);
        shieldUi = new ShieldUI(model.getControllablePlayer().getShield(), model.getTexturepack(), width / 20f,
                height * 3 / 20f, width / 10f, 50);

        font = model.getFontPack().getStandard();

        levelText = new Textbox(3, String.valueOf(model.getCurrentLevel()), font, width / 20f, height * 9 / 10f, 50,
                50);
        scoreText = new Textbox(3, String.valueOf(model.getScore()), font, width / 20f, height * 8 / 10f, 50, 50);
    }

    /**
     * @return a list of all the sprites that are part of the UI
     */
    public List<Sprite> getSprites() {
        List<Sprite> spriteList = new ArrayList<>();
        spriteList.add(healthBar.getSprite());
        spriteList.add(shieldUi.getSprite());
        return spriteList;
    }

    /**
     * Updates the ui for the healthbar
     */
    public void healthUpdate() {
        healthBar.healthUpdate();
    }

    /**
     * Updates the ui for the shield
     */
    public void ShieldUpdate() {
        shieldUi.shieldUpdate();
    }

    /**
     *
     * @return The textbox for the level
     */
    public Textbox getLevelTextbox() {
        levelText.setText("Level: " + String.valueOf(model.getCurrentLevel()));
        return levelText;
    }

    /**
     * @return The textbox for the score
     */
    @Override
    public Textbox getScoreTextbox() {
        scoreText.setText("Score: " + String.valueOf(model.getScore()));
        return scoreText;
    }
}
