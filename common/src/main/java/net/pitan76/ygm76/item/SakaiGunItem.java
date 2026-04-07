package net.pitan76.ygm76.item;

import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvents;
import net.pitan76.mcpitanlib.midohra.util.math.BlockPos;
import net.pitan76.mcpitanlib.midohra.world.World;
import net.pitan76.ygm76.item.base.GunItem;

public class SakaiGunItem extends GunItem {
    public SakaiGunItem(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public void playSoundOnShoot(Player player) {
        if (player.isClient()) return;

        World $world = player.getMidohraWorld();
        BlockPos $pos = player.getBlockPosM();

        $world.playSound(null, $pos, CompatSoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, CompatSoundCategory.NEUTRAL, 0.5F, 0.5f);
        $world.playSound(null, $pos, CompatSoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, CompatSoundCategory.NEUTRAL, 0.5F, 2f);
        $world.playSound(null, $pos, CompatSoundEvents.BLOCK_ANVIL_FALL, CompatSoundCategory.NEUTRAL, 0.5F, 2f);
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
