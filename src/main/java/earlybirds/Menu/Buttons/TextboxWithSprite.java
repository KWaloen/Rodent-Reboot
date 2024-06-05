package earlybirds.Menu.Buttons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Class to create textboxes
 */
public class TextboxWithSprite extends Textbox implements ViewableButton {

    Sprite sprite;

    /**
     * @param fontSize the font size
     * @param text     text to display
     * @param font     font to use
     * @param sprite   the sprite to use
     * @param x        x position of the center of the box
     * @param y        y position of the center of the box
     * @param sizeX    width
     * @param sizeY    height
     */
    public TextboxWithSprite(float fontSize, String text, BitmapFont font, Sprite sprite, float x, float y, float sizeX,
                             float sizeY) {
        super(fontSize, text, font, x, y, sizeX, sizeY);
        this.sprite = new Sprite(sprite);
        this.sprite.setBounds((int) x - sizeX / 2, (int) y - sizeY / 2, (int) sizeX, (int) sizeY);
    }

    /**
     * This uses a binary search to rescale the text size and space between the
     * lines.
     * If after 5 iterations it has not found a value within the threshold it picks
     * the
     * lowest boundary which is guaranteed to fit the box.
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

}
