package earlybirds.Model.UI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Menu.Buttons.Textbox;
import java.util.List;

/**
 * Interface for a user interface which is possible to display
 */
public interface ViewableUi {
    /**
     *
     * @return All the sprites in the UI
     */
    public List<Sprite> getSprites();

    /**
     * @return the textbox for the level
     */
    public Textbox getLevelTextbox();

    /**
     * @return the textbox for the score
     */
    public Textbox getScoreTextbox();
}
