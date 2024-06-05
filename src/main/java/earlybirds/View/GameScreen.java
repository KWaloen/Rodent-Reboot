package earlybirds.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import earlybirds.Menu.Buttons.Textbox;
import earlybirds.Model.Movable.Bullets.Bullet;
import earlybirds.Model.ViewableModel;

/**
 * This is the main view-object which takes information from the model and
 * displays it on the screen.
 */

public class GameScreen {
    private SpriteBatch batch;
    private ViewableModel model;
    OrthographicCamera gameCam;
    Viewport gameViewport;
    OrthographicCamera uiCam;

    Viewport uiViewport;

    /**
     * Constructs GameScreen object.
     * Creates new batch object
     * param model the model
     * param batch the spritebatch used for rendering
     */
    public GameScreen(ViewableModel model, SpriteBatch batch) {
        this.batch = batch;
        this.model = model;
        float w = (float) (Gdx.graphics.getWidth() / 1.5);
        float h = (float) (Gdx.graphics.getHeight() / 1.5);
        gameCam = new OrthographicCamera(w, h);
        gameViewport = new FitViewport(w, h, gameCam);

        uiCam = new OrthographicCamera();
        uiCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiViewport = new ScreenViewport(uiCam);

    }

    /**
     * Libgdx uses "batches" to more effectively draw sprites where the sprites are
     * drawn together and not one by one.
     * This method draws the sprites from the model.
     */
    public void renderModel() {
        ScreenUtils.clear(0, 0, 0, 1, true);
        updateCam();

        batch.setProjectionMatrix(gameCam.combined);

        batch.begin();
        for (Sprite sprite : model.getMapTileSprites()) {
            sprite.draw(batch);
        }
        for (Sprite sprite : model.getSprites()) {
            sprite.draw(batch);
        }
        for (Sprite sprite : model.getItemSprites()) {
            sprite.draw(batch);
        }
        for (Bullet bullet : model.getBullets()) {
            bullet.getSprite().setPosition(bullet.getPos().x, bullet.getPos().y);
            bullet.getSprite().draw(batch);
        }

        batch.end();

        batch.setProjectionMatrix(uiCam.combined);
        batch.begin();
        for (Sprite uiSprites : model.getViewableUi().getSprites()) {
            uiSprites.draw(batch);
        }
        Textbox levelText = model.getViewableUi().getLevelTextbox();
        Textbox scoreText = model.getViewableUi().getScoreTextbox();
        BitmapFont font = levelText.getFont();

        GlyphLayout levelLayout = new GlyphLayout();
        levelLayout.setText(font, levelText.getText(), Color.WHITE, 0, Align.left, false);
        levelLayout.setText(font, levelText.getText(), Color.WHITE, levelLayout.width, Align.left, true);
        font.draw(batch, levelLayout, levelText.middlePos().x - levelLayout.width / 2,
                levelText.middlePos().y + levelLayout.height / 2);

        GlyphLayout scoreLayout = new GlyphLayout();
        scoreLayout.setText(font, scoreText.getText(), Color.WHITE, 0, Align.left, false);
        scoreLayout.setText(font, scoreText.getText(), Color.WHITE, scoreLayout.width, Align.left, true);
        font.draw(batch, scoreLayout, scoreText.middlePos().x - scoreLayout.width / 2,
                scoreText.middlePos().y + scoreLayout.height / 2);

        batch.end();

    }

    /**
     * Removes resources which the application has used before it terminates.
     */
    public void dispose() {
        batch.dispose();
    }

    /**
     * Updates the position of the camera to be centered around the player.
     */
    public void updateCam() {
        Sprite sprite = model.getPlayerSprite();
        gameCam.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
        gameCam.update();

    }

    /**
     * @return the camera used for the game
     */
    public OrthographicCamera getGameCam() {
        return gameCam;
    }

}
