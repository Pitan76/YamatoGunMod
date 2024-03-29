package net.pitan76.ygm76.entity;

import net.pitan76.mcpitanlib.api.util.EntityUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.ygm76.item.YGItems;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BulletEntity extends ThrownItemEntity {

    public GunItem item;
    public float addedDamage = 0f;
    public LivingEntity owner;

    public BulletEntity(World world, LivingEntity owner, GunItem item) {
        super((EntityType<? extends ThrownItemEntity>) YGEntityType.BULLET_ENTITY.get(), owner, world);
        this.item = item;
        this.owner = owner;
    }

    public BulletEntity(EntityType<?> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>) entityType, world);
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack.isEmpty() ? new ItemStack(getDefaultItem()) : itemStack);
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                EntityUtil.getWorld(this).addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (item == null) return;
        EntityUtil.damageWithThrownProjectile(entity, item.getShootDamage() + getAddedDamage(), this, this.owner == null ? this : this.owner);
    }

    public float getAddedDamage() {
        return addedDamage;
    }

    public void setAddedDamage(float addedDamage) {
        this.addedDamage = addedDamage;
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        World world = EntityUtil.getWorld(this);
        if (world != null && !WorldUtil.isClient(world)) {
            WorldUtil.sendEntityStatus(world, this, (byte)3);
            try {
                this.discard();
            } catch (ArrayIndexOutOfBoundsException ignore) {}
        }
    }

    @Override
    protected Item getDefaultItem() {
        return YGItems.BULLET_ITEM.get();
    }
}
