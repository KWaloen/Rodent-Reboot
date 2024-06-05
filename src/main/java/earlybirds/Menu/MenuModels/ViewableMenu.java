package earlybirds.Menu.MenuModels;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Menu.Buttons.ViewableTextbox;

import java.util.List;

/**
 * Interface to limit menu information to only parts relevant to displaying the
 * menu.
 */

public interface ViewableMenu {

    /**
     * @return List of viewable buttons meant for menu-view objects
     */
    public List<ViewableTextbox> getViewableButtons();

    /**
     * @return background of menu
     */
    public Sprite getBackground();
}
