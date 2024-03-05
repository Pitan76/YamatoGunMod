package net.pitan76.ygm76;

import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;
import net.pitan76.mcpitanlib.api.network.ServerNetworking;
import net.pitan76.mcpitanlib.api.registry.CompatRegistry;
import net.pitan76.ygm76.entity.YGEntityType;
import net.pitan76.ygm76.item.YGItems;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class YamatoGunMod {
    public static final String MOD_ID = "ygm76";

    public static final CompatRegistry registry = CompatRegistry.createRegistry(MOD_ID);

    public static final CreativeTabBuilder GUN_GROUP = CreativeTabBuilder.create(id("guns")).setIcon(() -> new ItemStack(YGItems.YG1_ITEM.get(), 1));

    @SuppressWarnings("PatternVariableCanBeUsed")
    public static void init() {
        registry.registerItemGroup(GUN_GROUP);

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

        registry.allRegister();
    }

    public static Identifier id(String name) {
        return new Identifier(MOD_ID, name);
    }
}