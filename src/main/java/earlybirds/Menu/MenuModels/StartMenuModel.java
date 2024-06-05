package earlybirds.Menu.MenuModels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Menu.Buttons.Button;
import earlybirds.Menu.Buttons.ButtonEnum;
import earlybirds.Menu.Buttons.Textbox;
import earlybirds.Menu.Buttons.ViewableTextbox;
import earlybirds.Menu.FontPack;
import earlybirds.Model.Texturepack;
import java.util.ArrayList;
import java.util.List;

/**
 * Model for a start-menu
 */
public class StartMenuModel implements ControllableMenu, ViewableMenu {
    Button startButton;
    Button quitButton;
    Textbox title;
    Textbox howTo;
    private Sprite background;

    /**
     * Simple class to create a startMenu
     *
     * @param fontPack the fontpack used
     */
    public StartMenuModel(FontPack fontPack, Texturepack texturepack) {
        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();

        Texture texture = texturepack.getTexture("button");
        Sprite sprite = new Sprite(texture);
        sprite.setRegion(0f, 0.2f, 1f, 0.8f);
        startButton = new Button(2, ButtonEnum.STARTBUTTON, "Start", fontPack.getStandard(), sprite, x / 4,
                y - 270 / 1080f * y, 300 / 1920f * x, 150 / 1080f * y);
        quitButton = new Button(2, ButtonEnum.EXITBUTTON, "Quit", fontPack.getStandard(), sprite, x / 4, y / 4,
                300 / 1920f * x, 150 / 1080f * y);
        background = new Sprite(texturepack.getTexture("menuScreen"));
        title = new Textbox(5, "RodentReboot", fontPack.getStandard(), 1325 / 1920f * x, y / 2, 300 / 1920f * x,
                200 / 1080f * y);
        howTo = new Textbox(2, "Run with WASD.\nShoot with left click.\nShield with right click.",
                fontPack.getStandard(), 320 / 1920f * x, y / 2, 300 / 1920f * x, 200 / 1080f * y);
    }

    @Override
    public List<Button> getButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(startButton);
        buttons.add(quitButton);
        return buttons;
    }

    @Override
    public List<ViewableTextbox> getViewableButtons() {
        List<ViewableTextbox> buttons = new ArrayList<>();
        buttons.add(startButton);
        buttons.add(quitButton);
        buttons.add((title));
        buttons.add(howTo);
        return buttons;
    }

    @Override
    public Sprite getBackground() {
        return background;
    }
}
