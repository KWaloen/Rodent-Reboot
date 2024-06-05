package earlybirds.Model.Item;

import earlybirds.Model.Item.Types.*;

import java.util.function.Supplier;

/**
 * This is the Item Registry. It uses a Supplier to retrieve item types from the
 * item Registry.
 */
public enum ItemRegistry {
    SPEED1(Speed1PU::new),
    SPEED2(Speed2PU::new),
    SPEED3(Speed3PU::new),
    HEALTH(HealthPU::new),
    FULLHEALTH(FullHealthPU::new),
    BULLETSPEED(BulletSpeedPU::new),
    ARMOR(ArmorPU::new);

    private final Supplier<Item> itemSupplier;

    ItemRegistry(Supplier<Item> itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

    /**
     * @return the item.
     */
    public Item getItem() {
        return itemSupplier.get();
    }
}
