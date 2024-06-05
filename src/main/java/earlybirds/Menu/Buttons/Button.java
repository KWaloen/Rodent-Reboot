package earlybirds.Menu.Buttons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A class for a clickable button
 */

public class Button extends TextboxWithSprite {

    ButtonEnum type;

    /**
     * Creates a button
     *
     * @param type     the type of buttons. The options are within the ButtonEnum.
     * @param fontSize the size of the font
     * @param font     the bitmap font
     * @param text     text on the button
     * @param sprite   button sprite
     * @param x        position of the lower left corner of button
     * @param y        position of the lower left corner of button
     * @param sizeX    size in x direction
     * @param sizeY    soze in y direction
     */
    public Button(float fontSize, ButtonEnum type, String text, BitmapFont font, Sprite sprite, float x, float y,
                  float sizeX, float sizeY) {
        super(fontSize, text, font, sprite, x, y, sizeX, sizeY);
        this.type = type;
    }

    /**
     * Checks if a button is overlapping position given
     *
     * @param x pos
     * @param y pos
     * @return true if overlapping
     */
    public Boolean overlap(float x, float y) {
        return super.sprite.getBoundingRectangle().contains(x, y);
    }

    /**
     * Sets the sprite color to its default meant for when mouse is hovering over
     * it.
     */
    public void Hover() {
        super.sprite.setColor(1, 1, 1, 1);
    }

    /**
     * Makes the colors darker. Meant to be the case when mouse is not hovering over
     * it.
     */
    public void unHover() {
        super.sprite.setColor(0.8f, 0.8f, 0.8f, 1);
    }

    /**
     * @return the button type
     */
    public ButtonEnum getButtonType() {
        return type;
    }
}
