package ml.pkom.ygm76.forge;

import ml.pkom.ygm76.YamatoGunClientMod;
import ml.pkom.ygm76.entity.YGEntityType;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class YamatoGunForgeClientMod {
    public static void clientInit(FMLClientSetupEvent event) {
        YamatoGunClientMod.init();
    }

    public static void renderInit(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(YGEntityType.BULLET_ENTITY.get(), (ctx -> (EntityRenderer) new FlyingItemEntityRenderer<>(ctx)));
    }
}
