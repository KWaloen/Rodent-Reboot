package earlybirds.Menu.MenuModels;

import earlybirds.Menu.Buttons.Button;

import java.util.List;

/**
 * Interface which limits a menu model to only methods relevant for controllers.
 */

public interface ControllableMenu {

    /**
     * @return List of buttons meant for menu controllers
     */
    public List<Button> getButtons();

}
