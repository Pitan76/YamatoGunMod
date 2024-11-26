package net.pitan76.ygm76;

import net.pitan76.mcpitanlib.api.client.option.KeyCodes;
import net.pitan76.mcpitanlib.api.client.registry.EntityRendererRegistry;
import net.pitan76.mcpitanlib.api.client.registry.v2.KeybindingRegistry;
import net.pitan76.mcpitanlib.api.event.v0.InteractionEventRegistry;
import net.pitan76.mcpitanlib.api.network.v2.ClientNetworking;
import net.pitan76.mcpitanlib.api.network.PacketByteUtil;
import net.pitan76.ygm76.entity.YGEntityType;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;

import static net.pitan76.ygm76.YamatoGunMod._id;

public class YamatoGunClientMod {
    public static void init() {
        EntityRendererRegistry.registerEntityRendererAsFlyingItem(() -> YGEntityType.BULLET_ENTITY.get());
        InteractionEventRegistry.registerClientLeftClickAir((player, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GunItem) {
                PacketByteBuf buf = PacketByteUtil.create();
                PacketByteUtil.writeBoolean(buf, hand.equals(Hand.MAIN_HAND));
                ClientNetworking.send(_id("left_click_on_holding_gun"), buf);
            }
        });

        KeybindingRegistry.registerOnLevelWithNetwork("key.ygm76.reload", KeyCodes.KEY_R, "category.ygm76.title", _id("reload_gun"));
    }
}
