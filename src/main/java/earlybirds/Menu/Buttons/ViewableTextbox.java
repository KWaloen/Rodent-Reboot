package earlybirds.Menu.Buttons;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Interface which limits Textbox class to only methods related to displaying
 * the information.
 */
public interface ViewableTextbox {

    /**
     * @return button text
     */
    public String getText();

    /**
     * @return the middle posiotion of the button
     */
    public Vector2 middlePos();

    /**
     * @return the bounds of the button
     */
    public Rectangle getBounds();

    /**
     * @return The font
     */
    public BitmapFont getFont();

}
