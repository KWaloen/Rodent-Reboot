package earlybirds.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import earlybirds.EventBus.Eventbus;
import earlybirds.Menu.MenuController.MenuController;
import earlybirds.Menu.MenuModels.GameOverMenuModel;
import earlybirds.Menu.MenuModels.StartMenuModel;
import earlybirds.Menu.MenuView.MenuView;
import earlybirds.Model.ScoreKeeper;
import earlybirds.Model.Texturepack;

/**
 * Consolidating all functionality and data of menus to one class which is
 * simple to create and use
 * in gamehandler class.
 */
public class MenuHandler {
    private MenuController menuController;
    private MenuView menuView;
    private StartMenuModel startMenu;
    private GameOverMenuModel gameOverMenu;
    private Texturepack texturepack = new Texturepack();

    /**
     * Creates a MenuHandler
     *
     * @param eventbus the gamestate which can be changed by menucontroller
     * @param batch    the batch used by menuView
     * @param fontPack the fontpack used for text
     */
    public MenuHandler(Eventbus eventbus, SpriteBatch batch, FontPack fontPack, ScoreKeeper scoreKeeper) {
        startMenu = new StartMenuModel(fontPack, texturepack);
        gameOverMenu = new GameOverMenuModel(fontPack, scoreKeeper, texturepack);
        menuController = new MenuController(eventbus, startMenu);
        menuView = new MenuView(batch);

    }

    /**
     * Sets the menu within the controller to startmenu.
     */
    public void setToStartMenu() {
        menuController.setMenu(startMenu);
    }

    /**
     * Sets the menu within controller to the gameOver menu
     */
    public void setToGameOverMenu() {
        menuController.setMenu(gameOverMenu);
    }

    /**
     * Runs the menu-view render method.
     */

    public void render() {
        menuView.renderModel(menuController.getMenu());
    }

    /**
     * Sets the menucontroller, useful in testing
     *
     * @param menuController
     */
    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    /**
     * Activates the menucontroller to allow it to respond to input from mouse
     */
    public void activate() {
        Gdx.input.setInputProcessor(null);
        Gdx.input.setInputProcessor(menuController);
    }

}
