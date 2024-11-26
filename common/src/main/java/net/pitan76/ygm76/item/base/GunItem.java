package net.pitan76.ygm76.item.base;

import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.ItemBarStepArgs;
import net.pitan76.mcpitanlib.api.event.item.ItemBarVisibleArgs;
import net.pitan76.mcpitanlib.api.event.item.ItemUseEvent;
import net.pitan76.mcpitanlib.api.item.v2.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.v2.CompatItem;
import net.pitan76.mcpitanlib.api.sound.CompatSoundCategory;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvent;
import net.pitan76.mcpitanlib.api.sound.CompatSoundEvents;
import net.pitan76.mcpitanlib.api.util.*;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;
import net.pitan76.ygm76.entity.BulletEntity;
import net.pitan76.ygm76.fix.NbtFixer;
import net.pitan76.ygm76.item.YGItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class GunItem extends CompatItem  {
    public GunItem(CompatibleItemSettings settings) {
        super(settings);
    }

    public int getMaxBulletCount() {
        return 5;
    }

    public int getShootTick() {
        return 60;
    }

    public float getShootSpeed() {
        return 5f;
    }

    public int getReloadTick() {
        return 60;
    }

    public float getShootDamage() {
        return 1f;
    }

    public float getRightShootDamage() {
        return 5f;
    }

    public float getShootDivergence() {
        return 1f;
    }

    public float getShootRoll() {
        return 0f;
    }

    public static int getBulletCount(ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof GunItem) || !CustomDataUtil.hasNbt(stack) || !CustomDataUtil.contains(stack, "bullet")) return 0;
        return CustomDataUtil.getNbt(stack).getInt("bullet");
    }

    public static void setBulletCount(ItemStack stack, int count) {
        if (stack == null || !(stack.getItem() instanceof GunItem)) return;
        CustomDataUtil.set(stack, "bullet", count);
    }

    public static void increaseBulletCount(ItemStack stack, int count) {
        setBulletCount(stack, getBulletCount(stack) + count);
    }

    public static void decreaseBulletCount(ItemStack stack, int count) {
        setBulletCount(stack, getBulletCount(stack) - count);
    }

    public static void increaseBulletCount(ItemStack stack) {
        increaseBulletCount(stack, 1);
    }

    public static void decreaseBulletCount(ItemStack stack) {
        decreaseBulletCount(stack, 1);
    }

    public static boolean isBulletCountEmpty(ItemStack stack) {
        return getBulletCount(stack) == 0;
    }

    public Item getBulletItem() {
        return YGItems.BULLET_ITEM.get();
    }

    public void playSoundWithTimer(Player player, CompatSoundEvent event, float volume, float pitch, int ticks) {
        if (player.isClient()) return;
        TimerUtil.addTimer((ServerWorld) player.getWorld(), ticks, () -> {
            BlockPos $pos = player.getBlockPos();
            WorldUtil.playSound(player.getWorld(), null, PosUtil.flooredBlockPos($pos.getX(), $pos.getY(), $pos.getZ()), event, CompatSoundCategory.NEUTRAL, volume, pitch);
            return true;
        });
    }

    public void playSoundOnReload(Player player) {
        playSoundWithTimer(player, CompatSoundEvents.ITEM_FLINTANDSTEEL_USE, 1, 1, 4);
        playSoundWithTimer(player, CompatSoundEvents.BLOCK_IRON_DOOR_OPEN, 1, 2, 6);
        playSoundWithTimer(player, CompatSoundEvents.ITEM_FLINTANDSTEEL_USE, 1, 1, 16);
        playSoundWithTimer(player, CompatSoundEvents.ENTITY_PLAYER_HURT, 1, 0, 17);
        playSoundWithTimer(player, CompatSoundEvents.BLOCK_IRON_DOOR_CLOSE, 1, 2, getReloadTick());
    }

    public void reload(ItemStack stack, Player player) {
        if (getMaxBulletCount() == getBulletCount(stack)) return;

        if (player.getInventory().contains(ItemStackUtil.create(getBulletItem()))) {
            playSoundOnReload(player);

            player.getItemCooldown().set(this, getReloadTick());
            int $bulletCount = 0;
            List<ItemStack> $bulletList = new ArrayList<>();

            List<ItemStack> $list = new ArrayList<>(player.getMain());
            $list.add(player.getStackInHand(Hand.MAIN_HAND));
            $list.add(player.getStackInHand(Hand.OFF_HAND));

            for (ItemStack $invStack : $list) {
                if ($invStack.getItem().equals(getBulletItem())) {
                    $bulletCount += $invStack.getCount();
                    $bulletList.add($invStack);
                }
            }

            int $usableCount = Math.min(getMaxBulletCount() - getBulletCount(stack), $bulletCount);
            int $c = $usableCount;

            for (ItemStack $invStack : $bulletList) {
                if ($c == 0) break;
                int $r = Math.min($c, $invStack.getCount());
                ItemStackUtil.decrementCount($invStack, $r);
                $c -= $r;
            }

            increaseBulletCount(stack, $usableCount);
        }
    }

    public void playSoundOnShoot(Player player) {
        if (player.isClient()) return;
        BlockPos $pos = player.getBlockPos();
        WorldUtil.playSound(player.getWorld(), null, $pos, CompatSoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, CompatSoundCategory.NEUTRAL, 0.5F, 0.3F / (WorldRandomUtil.nextFloat(player.getWorld()) * 0.4F + 0.8F));
    }

    public void playSoundOnRightShoot(Player player) {
        if (player.isClient()) return;
        BlockPos $pos = player.getBlockPos();
        WorldUtil.playSound(player.getWorld(), null, $pos, CompatSoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, CompatSoundCategory.NEUTRAL, 0.5F, 0.3F / (WorldRandomUtil.nextFloat(player.getWorld()) * 0.4F + 0.8F));
    }

    public void onLeftClick(Player $user, Hand hand) {
        ItemStack $stack = $user.getStackInHand(hand);
        /*
        if (PlatformUtil.getGameVersion().contains()) {
            NbtFixer.fix($stack);

         */

        if (!($stack.getItem().equals(this))) return;

        World $world = $user.getWorld();

        if (isBulletCountEmpty($stack)) {
            reload($stack, $user);
            return;
        }

        if (!$world.isClient) {
            NbtCompound nbt = NbtUtil.create();
            if (CustomDataUtil.hasNbt($stack)) {
                nbt = CustomDataUtil.getNbt($stack);
                if (nbt.contains("canUse") && !nbt.getBoolean("canUse")) return;
            }

            nbt.putBoolean("canUse", false);

            int $c = $stack.getCount();
            $stack.setCount(0);
            CustomDataUtil.setNbt($stack, nbt);
            $stack.setCount($c);

            TimerUtil.addTimer(((ServerWorld) $world), getShootTick(), () -> {
                NbtCompound nbt2 = CustomDataUtil.getNbt($stack);
                nbt2.putBoolean("canUse", true);
                $stack.setCount(0);
                CustomDataUtil.setNbt($stack, nbt2);
                $stack.setCount($c);
                return true;
            });

            playSoundOnShoot($user);
            shoot($user, $stack);
        }

        $user.incrementStat(Stats.USED, this);
    }

    @Override
    public StackActionResult onRightClick(ItemUseEvent e) {
        Player $user = e.user;
        World $world = e.world;

        ItemStack $stack = $user.getStackInHand(e.hand);
        NbtFixer.fix($stack);
        if (!isEnabledRightShoot()) return e.fail();

        if (isBulletCountEmpty($stack)) {
            reload($stack, $user);
            return e.fail();
        }

        if (!$world.isClient) {
            NbtCompound nbt = NbtUtil.create();
            if (CustomDataUtil.hasNbt($stack)) {
                nbt = CustomDataUtil.getNbt($stack);
                if (NbtUtil.has(nbt, "canUse") && !NbtUtil.getBoolean(nbt, "canUse"))
                    return e.fail();
            }

            NbtUtil.set(nbt, "canUse", false);

            int $c = $stack.getCount();
            ItemStackUtil.setCount($stack, 0);
            CustomDataUtil.setNbt($stack, nbt);
            ItemStackUtil.setCount($stack, $c);

            TimerUtil.addTimer(((ServerWorld) $world), getShootTick(), () -> {
                NbtCompound nbt2 = CustomDataUtil.getNbt($stack);
                NbtUtil.set(nbt2, "canUse", true);
                ItemStackUtil.setCount($stack, 0);
                CustomDataUtil.setNbt($stack, nbt2);
                ItemStackUtil.setCount($stack, $c);
                return true;
            });

            playSoundOnRightShoot($user);

            shootRight($user, $stack);
        }

        $user.incrementStat(Stats.USED, this);

        return e.success();
    }

    public boolean isEnabledRightShoot() {
        return true;
    }

    public void shoot(Player $user, ItemStack $stack) {
        World $world = $user.getWorld();

        BulletEntity bulletEntity = new BulletEntity($world, $user.getEntity(), this);
        bulletEntity.callSetItem(ItemStackUtil.create(YGItems.BULLET_ITEM.get()));
        bulletEntity.setVelocity($user.getEntity(), $user.getPitch(), $user.getYaw(), getShootRoll(), getShootSpeed(), getShootDivergence());
        decreaseBulletCount($stack);
        WorldUtil.spawnEntity($world, bulletEntity);
    }

    public void shootRight(Player $user, ItemStack $stack) {
        if (!isEnabledRightShoot()) return;
        World $world = $user.getWorld();

        BulletEntity bulletEntity = new BulletEntity($world, $user.getEntity(), this);
        bulletEntity.callSetItem(ItemStackUtil.create(YGItems.BULLET_ITEM.get()));
        bulletEntity.setVelocity($user.getEntity(), $user.getPitch(), $user.getYaw(), getShootRoll(), getShootSpeed(), getShootDivergence());
        bulletEntity.setAddedDamage(getRightShootDamage());
        decreaseBulletCount($stack);
        WorldUtil.spawnEntity($world, bulletEntity);
    }

    @Override
    public int getItemBarStep(ItemBarStepArgs args) {
        return Math.round((float) getBulletCount(args.getStack()) * 13.0F / (float) getMaxBulletCount());
    }

    @Override
    public boolean isItemBarVisible(ItemBarVisibleArgs args) {
        return CustomDataUtil.hasNbt(args.getStack());
    }
}
