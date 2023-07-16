package ml.pkom.ygm76;

import ml.pkom.mcpitanlibarch.api.entity.Player;
import ml.pkom.mcpitanlibarch.api.item.CreativeTabBuilder;
import ml.pkom.mcpitanlibarch.api.network.ServerNetworking;
import ml.pkom.mcpitanlibarch.api.registry.ArchRegistry;
import ml.pkom.ygm76.entity.YGEntityType;
import ml.pkom.ygm76.item.YGItems;
import ml.pkom.ygm76.item.base.GunItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class YamatoGunMod {
    public static final String MOD_ID = "ygm76";

    public static final ArchRegistry registry = ArchRegistry.createRegistry(MOD_ID);

    public static final ItemGroup GUN_GROUP = CreativeTabBuilder.create(id("guns")).setIcon(() -> new ItemStack(YGItems.YG1_ITEM.get(), 1)).build();

    @SuppressWarnings("PatternVariableCanBeUsed")
    public static void init() {
        registry.registerItemGroup(id("guns"), () -> GUN_GROUP);

        YGEntityType.init();
        YGItems.init();

        ServerNetworking.registerReceiver(id("left_click_on_holding_gun"), ((server, p, buf) -> {
            boolean isMainHand = buf.readBoolean();
            Player player = new Player(p);
            Hand hand = isMainHand ? Hand.MAIN_HAND : Hand.OFF_HAND;
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GunItem) {
                if (p.getItemCooldownManager().isCoolingDown(stack.getItem())) return;
                GunItem item = (GunItem) stack.getItem();
                item.onLeftClick(player, hand);
            }
        }));

        ServerNetworking.registerReceiver(id("reload_gun"), ((server, p, buf) -> {
            Player player = new Player(p);
            ItemStack stack = player.getStackInHand(p.getActiveHand());
            if (stack.getItem() instanceof GunItem) {
                if (p.getItemCooldownManager().isCoolingDown(stack.getItem())) return;
                GunItem item = (GunItem) stack.getItem();
                item.reload(stack, player);
            }
        }));
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}