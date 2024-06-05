package earlybirds.Model.Item.Types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Texturepack is a class that holds the textures for the items.
 * getTexture is called statically to avoid parameterization.
 */
public class ItemsTexturePack {

    private static HashMap<String, Texture> textures = new HashMap<>();

    static {
        textures.put("speed1PU", new Texture(Gdx.files.internal("itemSprites/speed1PU.png")));
        textures.put("speed2PU", new Texture(Gdx.files.internal("itemSprites/speed2PU.png")));
        textures.put("speed3PU", new Texture(Gdx.files.internal("itemSprites/speed3PU.png")));
        textures.put("healthPU", new Texture(Gdx.files.internal("itemSprites/healthPU.png")));
        textures.put("fullHealthPU", new Texture(Gdx.files.internal("itemSprites/fullHealthPU.png")));
        textures.put("bulletSpeedPU", new Texture(Gdx.files.internal("itemSprites/bulletSpeedPU.png")));
        textures.put("armorPU", new Texture(Gdx.files.internal("itemSprites/armorPU.png")));
    }

    /**
     * Gets the texture with the given name
     *
     * @param name the name of the texture
     * @return the texture with the given name, or null if not found
     */
    public static Texture getTexture(String name) {
        return textures.get(name);
    }
}
