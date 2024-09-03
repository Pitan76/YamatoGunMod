package net.pitan76.ygm76;

import dev.architectury.event.events.common.InteractionEvent;
import net.pitan76.mcpitanlib.api.client.registry.EntityRendererRegistry;
import net.pitan76.mcpitanlib.api.client.registry.KeybindingRegistry;
import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.network.ClientNetworking;
import net.pitan76.mcpitanlib.api.network.PacketByteUtil;
import net.pitan76.ygm76.entity.YGEntityType;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;

import static net.pitan76.ygm76.YamatoGunMod.INSTANCE;

public class YamatoGunClientMod {
    public static void init() {
        EntityRendererRegistry.registerEntityRendererAsFlyingItem(() -> YGEntityType.BULLET_ENTITY.get());
        InteractionEvent.CLIENT_LEFT_CLICK_AIR.register(((p, hand) -> {
            Player player = new Player(p);

            ItemStack stack = player.getStackInHand(hand);
            if (stack.getItem() instanceof GunItem) {
                PacketByteBuf buf = PacketByteUtil.create();
                PacketByteUtil.writeBoolean(buf, hand.equals(Hand.MAIN_HAND));
                ClientNetworking.send(INSTANCE.id("left_click_on_holding_gun"), buf);
            }
        }));

        KeybindingRegistry.registerOnLevelWithNetwork(new KeyBinding("key.ygm76.reload", GLFW.GLFW_KEY_R, "category.ygm76.title"), INSTANCE.id("reload_gun"));
    }
}
