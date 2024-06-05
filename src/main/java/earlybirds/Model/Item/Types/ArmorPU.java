package earlybirds.Model.Item.Types;

import com.badlogic.gdx.graphics.g2d.Sprite;
import earlybirds.Model.Item.Item;
import earlybirds.Model.Movable.Player;


/**
 * Item class for armour power up!
 * Grants the player 90% damage reduction for a limited time.
 */

public class ArmorPU implements Item {
    private Sprite armorPUSprite;
    private float damageReduction = 90;

    /**
     * Constructs ArmourPU (Armour PowerUp) item.
     */
    public ArmorPU() {
        this.armorPUSprite = new Sprite(ItemsTexturePack.getTexture("armorPU"));
        this.armorPUSprite.setSize(100, 100);
    }

    /**
     * Remove power up effect for time limited power up
     */
    public void removePowerUpEffect() {
        Player.setDamageReduction(0f);
    }

    @Override
    public void setPos(float x, float y) {
        this.armorPUSprite.setPosition(x - armorPUSprite.getWidth() / 2, y - armorPUSprite.getHeight() / 2);
    }

    @Override
    public void doPowerUpEffect(Player player) {
        Player.setDamageReduction(damageReduction);
    }

    @Override
    public Sprite getSprite() {
        return armorPUSprite;
    }

    /**
     * For testing purposes
     * 
     * @return
     */
    public float getDamageReduction() {
        return damageReduction;
    }
}
