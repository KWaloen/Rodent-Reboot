package earlybirds.Model.UI;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Model.Movable.Player;
import earlybirds.Menu.FontPack;
import earlybirds.Menu.Buttons.Textbox;
import earlybirds.Model.Texturepack;

/**
 * Simple class for a healthbar
 */
public class HealthBar {
    Player player;
    Sprite sprite;
    Textbox healthLabel;
    private static FontPack fontPack = new FontPack();
    @SuppressWarnings("unused")
    private float defaultWidth;
    private float defaultBoundWidth;

    /**
     * Creates a healthbar which is connected to a Player
     * 
     * @param player the player
     * @param pack   the texturepack
     * @param x      x position
     * @param y      y position
     * @param sizex  the width of the healthbar when full
     * @param sizey  the height of the healthbar
     */
    public HealthBar(Player player, Texturepack pack, float x, float y, float sizex, float sizey) {
        this.player = player;
        this.sprite = new Sprite(pack.getTexture("redbar"));
        sprite.setBounds(x, y, sizex, sizey);
        defaultWidth = sprite.getRegionWidth();
        defaultBoundWidth = sprite.getWidth();
        healthLabel = new Textbox(5, "Health", fontPack.getStandard(), x, y + sizey + 20, 100, 20);
    }

    /**
     * Updates the healthbar with new info from player
     */
    public void healthUpdate() {
        float fraction = (float) player.getHealth() / (float) 100f;
        sprite.setRegion(0, 0, fraction, 1);
        sprite.setBounds(sprite.getX(), sprite.getY(), defaultBoundWidth * fraction, sprite.getHeight());
    }

    /**
     * @return the healthbar sprite
     */
    public Sprite getSprite() {
        return sprite;
    }

}
