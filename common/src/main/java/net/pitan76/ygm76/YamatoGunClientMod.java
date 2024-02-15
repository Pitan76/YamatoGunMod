package net.pitan76.ygm76;

import dev.architectury.event.events.common.InteractionEvent;
import net.pitan76.mcpitanlib.api.client.registry.ArchRegistryClient;
import net.pitan76.mcpitanlib.api.client.registry.KeybindingRegistry;
import net.pitan76.mcpitanlib.api.network.ClientNetworking;
import net.pitan76.mcpitanlib.api.network.PacketByteUtil;
import net.pitan76.ygm76.entity.YGEntityType;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

public class YamatoGunClientMod {
    public static void init() {
        ArchRegistryClient.registerEntityRenderer(() -> YGEntityType.BULLET_ENTITY.get(), (ctx -> (EntityRenderer) new FlyingItemEntityRenderer<>(ctx)));
        InteractionEvent.CLIENT_LEFT_CLICK_AIR.register(((player, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GunItem) {
                PacketByteBuf buf = PacketByteUtil.create();
                buf.writeBoolean(hand.equals(Hand.MAIN_HAND));
                ClientNetworking.send(YamatoGunMod.id("left_click_on_holding_gun"), buf);
            }
        }));

        KeybindingRegistry.registerOnLevelWithNetwork(new KeyBinding("key.ygm76.reload", GLFW.GLFW_KEY_R, "category.ygm76.title"), YamatoGunMod.id("reload_gun"));
    }
}
