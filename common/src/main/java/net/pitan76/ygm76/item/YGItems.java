package net.pitan76.ygm76.item;

import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
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
        BULLET_ITEM = registry.registerItem(id("bullet"), () -> new BulletItem(new CompatibleItemSettings().addGroup(GUN_GROUP, id("bullet"))));
        YG1_ITEM = registry.registerItem(id("yg1"), () -> new YG1Item(new CompatibleItemSettings().addGroup(GUN_GROUP, id("yg1"))));
        YG2_ITEM = registry.registerItem(id("yg2"), () -> new YG2Item(new CompatibleItemSettings().addGroup(GUN_GROUP, id("yg2"))));
        YG3_ITEM = registry.registerItem(id("yg3"), () -> new YG3Item(new CompatibleItemSettings().addGroup(GUN_GROUP, id("yg3"))));
        SAKAI_GUN_ITEM = registry.registerItem(id("sakai_gun"), () -> new SakaiGunItem(new CompatibleItemSettings().addGroup(GUN_GROUP, id("sakai_gun"))));
    }
}
