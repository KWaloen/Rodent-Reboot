package earlybirds.Menu.MenuView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import earlybirds.Menu.Buttons.ViewableButton;
import earlybirds.Menu.Buttons.ViewableTextbox;
import earlybirds.Menu.MenuModels.ViewableMenu;

/**
 * Class which has the task of displaying a menu.
 */

public class MenuView {

    Batch batch;
    OrthographicCamera cam;

    /**
     * Makes a MenuView objects
     *
     * @param batch The spritebatch used for rendering.
     */
    public MenuView(SpriteBatch batch) {
        this.batch = batch;
        cam = new OrthographicCamera();
    }

    /**
     * Renders a menu
     *
     * @param menu the menu which should be rendered
     */
    public void renderModel(ViewableMenu menu) {
        ScreenUtils.clear(0, 0, 0, 1, true);
        cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        menu.getBackground().setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        menu.getBackground().draw(batch);

        GlyphLayout layout = new GlyphLayout();

        batch.enableBlending();
        for (ViewableTextbox buttons : menu.getViewableButtons()) {
            BitmapFont font = buttons.getFont();
            if (buttons instanceof ViewableButton) {
                ((ViewableButton) buttons).getSprite().draw(batch);
            }
            layout.setText(font, buttons.getText(), Color.WHITE, buttons.getBounds().width, Align.left, true);
            font.draw(batch, layout, buttons.middlePos().x - layout.width / 2,
                    buttons.middlePos().y + layout.height / 2);
        }
        batch.end();
    }
}
