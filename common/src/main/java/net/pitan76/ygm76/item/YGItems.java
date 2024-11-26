package net.pitan76.ygm76.item;

import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.minecraft.item.Item;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.ygm76.YamatoGunMod.*;

public class YGItems {
    public static RegistryResult<Item> BULLET_ITEM;
    public static RegistryResult<Item> YG1_ITEM;
    public static RegistryResult<Item> YG2_ITEM;
    public static RegistryResult<Item> YG3_ITEM;
    public static RegistryResult<Item> SAKAI_GUN_ITEM;

    public static void init() {
        BULLET_ITEM = INSTANCE.registry.registerItem(_id("bullet"), () -> new BulletItem(new CompatibleItemSettings(_id("bullet")).addGroup(GUN_GROUP)));
        YG1_ITEM = INSTANCE.registry.registerItem(_id("yg1"), () -> new YG1Item(new CompatibleItemSettings(_id("yg1")).addGroup(GUN_GROUP)));
        YG2_ITEM = INSTANCE.registry.registerItem(_id("yg2"), () -> new YG2Item(new CompatibleItemSettings(_id("yg2")).addGroup(GUN_GROUP)));
        YG3_ITEM = INSTANCE.registry.registerItem(_id("yg3"), () -> new YG3Item(new CompatibleItemSettings(_id("yg3")).addGroup(GUN_GROUP)));
        SAKAI_GUN_ITEM = INSTANCE.registry.registerItem(_id("sakai_gun"), () -> new SakaiGunItem(new CompatibleItemSettings(_id("sakai_gun")).addGroup(GUN_GROUP)));
    }
}
