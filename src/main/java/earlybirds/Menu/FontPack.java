package earlybirds.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontPack {
    BitmapFont standard;

    /**
     * Constructor for the FontPack class
     */
    public FontPack() {
        standard = new BitmapFont(Gdx.files.internal("menuSprites/font.fnt"));
    }

    /**
     * @return the standard font
     */
    public BitmapFont getStandard() {
        return new BitmapFont(Gdx.files.internal("menuSprites/font.fnt"));
    }
}
