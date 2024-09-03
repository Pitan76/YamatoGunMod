package net.pitan76.ygm76.entity;

import net.pitan76.mcpitanlib.api.entity.EntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.pitan76.mcpitanlib.api.registry.result.RegistryResult;

import static net.pitan76.ygm76.YamatoGunMod.*;

public class YGEntityType {
    public static RegistryResult<EntityType<?>> BULLET_ENTITY;

    public static void init() {
        BULLET_ENTITY = INSTANCE.registry.registerEntity(INSTANCE.compatId("bullet"), () -> EntityTypeBuilder.create(SpawnGroup.MISC, BulletEntity::new).build());
    }
}
