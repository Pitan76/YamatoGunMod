package ml.pkom.ygm76;

import ml.pkom.mcpitanlibarch.api.client.registry.ArchRegistryClient;
import ml.pkom.ygm76.entity.YGEntityType;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class YamatoGunClientMod {
    public static void init() {
        ArchRegistryClient.registerEntityRenderer(() -> YGEntityType.BULLET_ENTITY.get(), (ctx -> (EntityRenderer) new FlyingItemEntityRenderer<>(ctx)));
    }
}
