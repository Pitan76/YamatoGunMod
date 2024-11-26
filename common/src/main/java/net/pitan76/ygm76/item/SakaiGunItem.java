package net.pitan76.ygm76.item;

import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.ygm76.item.base.GunItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class SakaiGunItem extends GunItem {
    public SakaiGunItem(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public void playSoundOnShoot(Player player) {
        if (player.isClient()) return;
        BlockPos $pos = player.getBlockPos();
        WorldUtil.playSound(player.getWorld(), null, $pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.NEUTRAL, 0.5F, 0.5f);
        WorldUtil.playSound(player.getWorld(), null, $pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.NEUTRAL, 0.5F, 2f);
        WorldUtil.playSound(player.getWorld(), null, $pos, SoundEvents.BLOCK_ANVIL_FALL, SoundCategory.NEUTRAL, 0.5F, 2f);
    }

    @Override
    public boolean isEnabledRightShoot() {
        return false;
    }

    @Override
    public int getMaxBulletCount() {
        return 1;
    }

    @Override
    public int getReloadTick() {
        return super.getReloadTick();
    }

    @Override
    public float getShootDamage() {
        return 20f;
    }

    @Override
    public int getShootTick() {
        return 20;
    }

    @Override
    public float getShootRoll() {
        return 0.25f;
    }

    @Override
    public float getShootSpeed() {
        return 1.5f;
    }

    @Override
    public float getShootDivergence() {
        return 0.25f;
    }
}
