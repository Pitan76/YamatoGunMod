package ml.pkom.ygm76.item;

import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;
import net.minecraft.item.Item;

import static ml.pkom.ygm76.YamatoGunMod.*;

public class YGItems {
    public static RegistryEvent<Item> BULLET_ITEM;
    public static RegistryEvent<Item> YG1_ITEM;

    public static void init() {
        BULLET_ITEM = registry.registerItem(id("bullet"), () -> new BulletItem(new CompatibleItemSettings()));
        YG1_ITEM = registry.registerItem(id("yg1"), () -> new YG1Item(new CompatibleItemSettings()));
    }
}
