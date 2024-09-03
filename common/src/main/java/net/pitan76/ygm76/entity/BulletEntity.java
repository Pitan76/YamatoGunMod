package net.pitan76.ygm76.entity;

import net.pitan76.mcpitanlib.api.entity.CompatThrownItemEntity;
import net.pitan76.mcpitanlib.api.event.entity.CollisionEvent;
import net.pitan76.mcpitanlib.api.event.entity.EntityHitEvent;
import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.ItemStackUtil;
import net.pitan76.mcpitanlib.api.util.ParticleEffectUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.ygm76.item.YGItems;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class BulletEntity extends CompatThrownItemEntity {

    public GunItem item;
    public float addedDamage = 0f;
    public LivingEntity owner;

    public BulletEntity(World world, LivingEntity owner, GunItem item) {
        super((EntityType<? extends CompatThrownItemEntity>) YGEntityType.BULLET_ENTITY.get(), owner, world);
        this.item = item;
        this.owner = owner;
    }

    public BulletEntity(EntityType<?> entityType, World world) {
        super((EntityType<? extends CompatThrownItemEntity>) entityType, world);
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.callGetItem();
        return ParticleEffectUtil.itemStack.create(ParticleTypes.ITEM, ItemStackUtil.isEmpty(itemStack) ? ItemStackUtil.create(getDefaultItemOverride()) : itemStack);
    }

    @Override
    public void callHandleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                WorldUtil.addParticle(EntityUtil.getWorld(this), particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    @Override
    public void onEntityHit(EntityHitEvent e) {
        super.onEntityHit(e);
        Entity entity = e.getEntity();

        if (item == null) return;
        EntityUtil.damageWithThrownProjectile(entity, item.getShootDamage() + getAddedDamage(), this, this.owner == null ? this : this.owner);
    }

    public float getAddedDamage() {
        return addedDamage;
    }

    public void setAddedDamage(float addedDamage) {
        this.addedDamage = addedDamage;
    }

    @Override
    public void onCollision(CollisionEvent e) {
        super.onCollision(e);
        World world = EntityUtil.getWorld(this);
        if (world != null && !WorldUtil.isClient(world)) {
            WorldUtil.sendEntityStatus(world, this, (byte)3);
            try {
                EntityUtil.discard(this);
            } catch (ArrayIndexOutOfBoundsException ignore) {}
        }
    }

    @Override
    public Item getDefaultItemOverride() {
        return YGItems.BULLET_ITEM.get();
    }
}
