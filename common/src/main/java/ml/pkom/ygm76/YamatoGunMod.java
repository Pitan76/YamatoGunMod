package ml.pkom.ygm76;

import ml.pkom.mcpitanlibarch.api.item.CreativeTabBuilder;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import ml.pkom.ygm76.entity.YGEntityType;
import ml.pkom.ygm76.item.YGItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class YamatoGunMod {
    public static final String MOD_ID = "ygm76";

    public static final ArchRegistry registry = ArchRegistry.createRegistry(MOD_ID);

    public static final ItemGroup GUN_GROUP = CreativeTabBuilder.create(id("guns")).setIcon(() -> new ItemStack(YGItems.YG1_ITEM.get(), 1)).build();

    public static void init() {
        YGEntityType.init();
        YGItems.init();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}