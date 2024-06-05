package earlybirds.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

/**
 * Texturepack is a class that holds the textures for the different tiles.
 * Currently only one pack is used, but it is possible to add more.
 */
public class Texturepack {
    private final Map<String, Texture> textures;

    public Texturepack() {
        textures = new HashMap<>();

        textures.put("gameOverScreen", new Texture(Gdx.files.internal("menuSprites/gameoverPanel.png")));
        textures.put("menuScreen", new Texture(Gdx.files.internal("menuSprites/panel.png")));
        textures.put("button", new Texture(Gdx.files.internal("menuSprites/button.png")));
        textures.put("yellowbar", new Texture(Gdx.files.internal("menuSprites/yellowBar.png")));
        textures.put("greenbar", new Texture(Gdx.files.internal("menuSprites/greenBar.png")));
        textures.put("redbar", new Texture(Gdx.files.internal("menuSprites/redBar.png")));
        textures.put("wallMiddle", new Texture(Gdx.files.internal("mapSprites/wallMiddle.png")));
        textures.put("wallSingle", new Texture(Gdx.files.internal("mapSprites/wallSingle.png")));
        textures.put("wallClosedTop", new Texture(Gdx.files.internal("mapSprites/wallClosedTop.png")));
        textures.put("wallClosedDown", new Texture(Gdx.files.internal("mapSprites/wallClosedDown.png")));
        textures.put("wallClosedLeft", new Texture(Gdx.files.internal("mapSprites/wallClosedLeft.png")));
        textures.put("wallClosedRight", new Texture(Gdx.files.internal("mapSprites/wallClosedRight.png")));
        textures.put("wallRightDown", new Texture(Gdx.files.internal("mapSprites/wallRightDown.png")));
        textures.put("wallRightUp", new Texture(Gdx.files.internal("mapSprites/wallRightUp.png")));
        textures.put("wallLeftDown", new Texture(Gdx.files.internal("mapSprites/wallLeftDown.png")));
        textures.put("wallLeftUp", new Texture(Gdx.files.internal("mapSprites/wallLeftUp.png")));
        textures.put("wallEndDown", new Texture(Gdx.files.internal("mapSprites/wallEndDown.png")));
        textures.put("wallEndLeft", new Texture(Gdx.files.internal("mapSprites/wallEndLeft.png")));
        textures.put("wallEndRight", new Texture(Gdx.files.internal("mapSprites/wallEndRight.png")));
        textures.put("wallEndUp", new Texture(Gdx.files.internal("mapSprites/wallEndUp.png")));
        textures.put("wallHorizontal", new Texture(Gdx.files.internal("mapSprites/wallHorizontal.png")));
        textures.put("wallVertical", new Texture(Gdx.files.internal("mapSprites/wallVertical.png")));
        textures.put("bulletGreen", new Texture(Gdx.files.internal("bulletSprites/bulletGreen.png")));
        textures.put("bulletOrange", new Texture(Gdx.files.internal("bulletSprites/bulletOrange.png")));
        textures.put("floor", new Texture(Gdx.files.internal("mapSprites/floor.jpg")));
        textures.put("door", new Texture(Gdx.files.internal("mapSprites/door.png")));
        textures.put("player", new Texture(Gdx.files.internal("characterSprites/player.png")));
        textures.put("enemy", new Texture(Gdx.files.internal("characterSprites/enemy1.png")));
        textures.put("shield", new Texture(Gdx.files.internal("characterSprites/shield.png")));


    }

    /**
     * Gets the texture with the given name
     *
     * @param name the name of the texture
     * @return the texture with the given name
     */
    public Texture getTexture(String name) {
        return textures.get(name);
    }
}
