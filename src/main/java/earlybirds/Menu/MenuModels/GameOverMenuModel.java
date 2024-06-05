package earlybirds.Menu.MenuModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Menu.Buttons.Button;
import earlybirds.Menu.Buttons.ButtonEnum;
import earlybirds.Menu.Buttons.Textbox;
import earlybirds.Menu.Buttons.ViewableTextbox;
import earlybirds.Menu.FontPack;
import earlybirds.Model.ScoreKeeper;
import earlybirds.Model.Texturepack;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for a GameOver-Menu
 */
public class GameOverMenuModel implements ViewableMenu, ControllableMenu {

    Button quitButton;
    Button restartButton;
    Textbox scoreDisplay;
    Textbox highsScoreDisplay;
    private Sprite background;
    private ScoreKeeper scoreKeeper;

    /**
     * Model for a game over menu.
     *
     * @param fontPack Your chosen fontpack
     */
    public GameOverMenuModel(FontPack fontPack, ScoreKeeper scoreKeeper, Texturepack texturepack) {
        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();
        this.scoreKeeper = scoreKeeper;
        Texture texture = texturepack.getTexture("button");
        Sprite sprite = new Sprite(texture);
        sprite.setRegion(0f, 0.2f, 1f, 0.8f);
        restartButton = new Button(2, ButtonEnum.RESTARTBUTTON, "Restart", fontPack.getStandard(), sprite, x / 7, y - 270 / 1080f * y, 300 / 1920f * x, 150 / 1080f * y);
        quitButton = new Button(2, ButtonEnum.EXITBUTTON, "Quit", fontPack.getStandard(), sprite, x / 7, y / 4, 300 / 1920f * x, 150 / 1080f * y);
        background = new Sprite(texturepack.getTexture("gameOverScreen"));

        // Score displays
        scoreDisplay = new Textbox(2, "", fontPack.getStandard(), 6 / 7f * x, y - 270 / 1080f * y, 300 / 1920f * x, 200 / 1080f * y);
        highsScoreDisplay = new Textbox(2, "", fontPack.getStandard(), 8 / 9f * x, y / 4, 400 / 1920f * x, 200 / 1080f * y);
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(quitButton);
        buttons.add(restartButton);
        return buttons;
    }

    @Override
    public List<ViewableTextbox> getViewableButtons() {
        // Update the scoreDisplay text
        if (scoreKeeper.isNewHighScore()) {
            scoreDisplay.setText("New highscore!");
            highsScoreDisplay.setText(Integer.toString(scoreKeeper.getHighScore()));
        } else {
            scoreDisplay.setText("Score: " + scoreKeeper.getScore());
            highsScoreDisplay.setText("Highscore: " + Integer.toString(scoreKeeper.getHighScore()));
        }
        List<ViewableTextbox> buttons = new ArrayList<>();
        buttons.add(restartButton);
        buttons.add(quitButton);
        buttons.add(scoreDisplay);
        buttons.add(highsScoreDisplay);
        return buttons;
    }

    @Override
    public Sprite getBackground() {
        return background;
    }
}
