package net.pitan76.ygm76.neoforge;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.pitan76.ygm76.YamatoGunClientMod;
import net.pitan76.ygm76.entity.YGEntityType;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class YamatoGunNeoForgeClientMod {
    public static void clientInit(FMLClientSetupEvent event) {
        YamatoGunClientMod.init();
    }

    public static void renderInit(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(YGEntityType.BULLET_ENTITY.get(), (ctx -> (EntityRenderer) new FlyingItemEntityRenderer<>(ctx)));
    }
}
