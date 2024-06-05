package earlybirds.Menu.Buttons;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Interface to limit button functionality to only visual information.
 */

public interface ViewableButton extends ViewableTextbox {
    /**
     * @return buttons prite
     */
    public Sprite getSprite();

}
