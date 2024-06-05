package earlybirds.Model;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import earlybirds.Menu.FontPack;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.UI.ViewableUi;

import java.util.List;

/**
 * Interface which limits which parts of the model the view classes,like
 * GameScreen, has access to.
 */

public interface ViewableModel {

    /**
     * Gets the sprites from the model which will be used by view-objects like
     * GameScreen.
     * 
     * @return a list of sprites.
     */
    public List<Sprite> getSprites();

    /**
     * @return Sprite from the model.
     */
    public Sprite getPlayerSprite();

    /**
     * @return List of sprites from the map
     */

    public List<Sprite> getMapTileSprites();

    /**
     * @return Array of bullets from the model.
     */
    public Array<Bullet> getBullets();

    /**
     * @return List of sprites from the Items
     */
    public List<Sprite> getItemSprites();

    /**
     * @return the ShieldSprite
     */

    public Sprite getShield();

    /**
     * @return the current level
     */

    public Integer getCurrentLevel();

    /**
     * @return the fontpack
     */
    public FontPack getFontPack();

    /***
     *
     * @return The Viewable Ui.
     */

    public ViewableUi getViewableUi();
}
