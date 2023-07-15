package ml.pkom.ygm76.entity;

import ml.pkom.mcpitanlibarch.api.entity.EntityTypeBuilder;
import ml.pkom.mcpitanlibarch.api.event.registry.RegistryEvent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import static ml.pkom.ygm76.YamatoGunMod.*;

public class YGEntityType {
    public static RegistryEvent<EntityType<?>> BULLET_ENTITY;

    public static void init() {
        BULLET_ENTITY = registry.registerEntity(id("bullet"), () -> EntityTypeBuilder.create(SpawnGroup.MISC, BulletEntity::new).build());
    }
}
