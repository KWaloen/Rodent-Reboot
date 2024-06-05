package earlybirds.Menu.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import earlybirds.EventBus.Eventbus;
import earlybirds.EventBus.Events.GameStateEvent;
import earlybirds.Menu.Buttons.Button;
import earlybirds.Menu.Buttons.ButtonEnum;
import earlybirds.Menu.MenuModels.ControllableMenu;
import earlybirds.Menu.MenuModels.ViewableMenu;
import earlybirds.Model.GameStateEnum;

/**
 * Controller for menus
 */
public class MenuController extends InputAdapter {

    ControllableMenu menu;
    Eventbus eventbus;

    /**
     * @param eventbus the gamestate is given to the controller
     * @param menu     The menu the controller starts with. It can be changed
     *                 later.
     */
    public MenuController(Eventbus eventbus, ControllableMenu menu) {
        this.eventbus = eventbus;
        this.menu = menu;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (button != Input.Buttons.LEFT) {
            return true;
        }
        Vector2 mousePos = new Vector2(x, Gdx.graphics.getHeight() - y);
        for (Button buttons : menu.getButtons()) {
            if (buttons.overlap(mousePos.x, mousePos.y)) {
                if (buttons.getButtonType().equals(ButtonEnum.EXITBUTTON)) {
                    eventbus.action(new GameStateEvent(GameStateEnum.EXIT));
                } else if (buttons.getButtonType().equals(ButtonEnum.STARTBUTTON)) {
                    eventbus.action(new GameStateEvent(GameStateEnum.PLAYING));

                } else if (buttons.getButtonType().equals(ButtonEnum.RESTARTBUTTON)) {
                    eventbus.action(new GameStateEvent(GameStateEnum.RESTART));
                }
            }
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        Vector2 mousePos = new Vector2(x, Gdx.graphics.getHeight() - y);

        for (Button buttons : menu.getButtons()) {
            if (buttons.overlap(mousePos.x, mousePos.y)) {
                buttons.Hover();
            } else buttons.unHover();
        }
        return true;
    }

    /**
     * @return the current menu.
     */
    public ViewableMenu getMenu() {
        return (ViewableMenu) this.menu;
    }

    /**
     * Set the menu model which can be controlled
     *
     * @param menu the menu
     */
    public void setMenu(ControllableMenu menu) {
        this.menu = menu;
    }

}
