package net.pitan76.ygm76;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;
import net.pitan76.mcpitanlib.api.network.PacketByteUtil;
import net.pitan76.mcpitanlib.api.network.ServerNetworking;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.ygm76.entity.YGEntityType;
import net.pitan76.ygm76.item.YGItems;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class YamatoGunMod extends CommonModInitializer {
    public static final String MOD_ID = "ygm76";
    public static final String MOD_NAME = "YamatoGunMod";

    public static CreativeTabBuilder GUN_GROUP;

    public static YamatoGunMod INSTANCE;

    public void init() {
        INSTANCE = this;

        GUN_GROUP = CreativeTabBuilder.create(id("guns")).setIcon(() -> ItemStackUtil.create(YGItems.YG1_ITEM.get(), 1));
        registry.registerItemGroup(GUN_GROUP);

        YGEntityType.init();
        YGItems.init();

        ServerNetworking.registerReceiver(id("left_click_on_holding_gun"), ((server, p, buf) -> {
            Player player = new Player(p);
            boolean isMainHand = PacketByteUtil.readBoolean(buf);
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

    @Override
    public String getId() {
        return MOD_ID;
    }

    @Override
    public String getName() {
        return MOD_NAME;
    }
}