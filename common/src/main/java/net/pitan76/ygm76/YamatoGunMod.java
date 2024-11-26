package net.pitan76.ygm76;

import net.pitan76.mcpitanlib.api.CommonModInitializer;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.CreativeTabBuilder;
import net.pitan76.mcpitanlib.api.network.PacketByteUtil;
import net.pitan76.mcpitanlib.api.network.v2.ServerNetworking;
import net.pitan76.mcpitanlib.api.util.CompatIdentifier;
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

        ServerNetworking.registerReceiver(_id("left_click_on_holding_gun"), ((e) -> {
            Player player = e.getPlayer();
            boolean isMainHand = PacketByteUtil.readBoolean(e.getBuf());
            Hand hand = isMainHand ? Hand.MAIN_HAND : Hand.OFF_HAND;

            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GunItem) {
                if (player.getItemCooldown().isCoolingDown(stack.getItem())) return;
                GunItem item = (GunItem) stack.getItem();
                item.onLeftClick(player, hand);
            }
        }));

        ServerNetworking.registerReceiver(_id("reload_gun"), ((e) -> {
            Player player = e.getPlayer();
            if (!player.getCurrentHandItem().isPresent()) return;
            ItemStack stack = player.getCurrentHandItem().get();
            if (stack.getItem() instanceof GunItem) {
                if (player.getItemCooldown().isCoolingDown(stack.getItem())) return;
                GunItem item = (GunItem) stack.getItem();
                item.reload(stack, player);
            }
        }));
    }

    public static CompatIdentifier _id(String path) {
        return new CompatIdentifier(MOD_ID, path);
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