package net.pitan76.ygm76.item.base;

import net.pitan76.mcpitanlib.api.entity.Player;
import net.pitan76.mcpitanlib.api.event.item.ItemUseEvent;
import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;
import net.pitan76.mcpitanlib.api.item.ExtendItem;
import net.pitan76.mcpitanlib.api.util.CustomDataUtil;
import net.pitan76.mcpitanlib.api.util.TimerUtil;
import net.pitan76.mcpitanlib.api.util.WorldRandomUtil;
import net.pitan76.mcpitanlib.api.util.WorldUtil;
import net.pitan76.mcpitanlib.api.util.math.PosUtil;
import net.pitan76.ygm76.entity.BulletEntity;
import net.pitan76.ygm76.fix.NbtFixer;
import net.pitan76.ygm76.item.YGItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


public abstract class GunItem extends ExtendItem {
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

    public void playSoundWithTimer(Player player, SoundEvent event, float volume, float pitch, int ticks) {
        if (player.getWorld().isClient) return;
        TimerUtil.addTimer((ServerWorld) player.getWorld(), ticks, () -> {
            BlockPos $pos = player.getBlockPos();
            WorldUtil.playSound(player.getWorld(), null, PosUtil.flooredBlockPos($pos.getX(), $pos.getY(), $pos.getZ()), event, SoundCategory.NEUTRAL, volume, pitch);
            return true;
        });
    }

    public void playSoundOnReload(Player player) {
        playSoundWithTimer(player, SoundEvents.ITEM_FLINTANDSTEEL_USE, 1, 1, 4);
        playSoundWithTimer(player, SoundEvents.BLOCK_IRON_DOOR_OPEN, 1, 2, 6);
        playSoundWithTimer(player, SoundEvents.ITEM_FLINTANDSTEEL_USE, 1, 1, 16);
        playSoundWithTimer(player, SoundEvents.ENTITY_PLAYER_HURT, 1, 0, 17);
        playSoundWithTimer(player, SoundEvents.BLOCK_IRON_DOOR_CLOSE, 1, 2, getReloadTick());
    }

    public void reload(ItemStack stack, Player player) {
        if (getMaxBulletCount() == getBulletCount(stack)) return;

        if (player.getInventory().contains(new ItemStack(getBulletItem()))) {
            playSoundOnReload(player);

            player.itemCooldown.set(this, getReloadTick());
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
                $invStack.decrement($r);
                $c -= $r;
            }

            increaseBulletCount(stack, $usableCount);
        }
    }

    public void playSoundOnShoot(Player player) {
        if (player.isClient()) return;
        BlockPos $pos = player.getBlockPos();
        WorldUtil.playSound(player.getWorld(), null, $pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.NEUTRAL, 0.5F, 0.3F / (WorldRandomUtil.nextFloat(player.getWorld()) * 0.4F + 0.8F));
    }

    public void playSoundOnRightShoot(Player player) {
        if (player.isClient()) return;
        BlockPos $pos = player.getBlockPos();
        WorldUtil.playSound(player.getWorld(), null, $pos, SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.NEUTRAL, 0.5F, 0.3F / (WorldRandomUtil.nextFloat(player.getWorld()) * 0.4F + 0.8F));
    }

    public void onLeftClick(Player $user, Hand hand) {
        ItemStack $stack = $user.getStackInHand(hand);
        NbtFixer.fix($stack);

        if (!($stack.getItem().equals(this))) return;

        World $world = $user.getWorld();

        if (isBulletCountEmpty($stack)) {
            reload($stack, $user);
            return;
        }

        if (!$world.isClient) {
            NbtCompound nbt = new NbtCompound();
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
    public TypedActionResult<ItemStack> onRightClick(ItemUseEvent event) {
        Player $user = event.user;
        World $world = event.world;

        ItemStack $stack = $user.getStackInHand(event.hand);
        NbtFixer.fix($stack);
        if (!isEnabledRightShoot()) return TypedActionResult.fail($stack);

        if (isBulletCountEmpty($stack)) {
            reload($stack, $user);
            return TypedActionResult.fail($stack);
        }

        if (!$world.isClient) {
            NbtCompound nbt = new NbtCompound();
            if (CustomDataUtil.hasNbt($stack)) {
                nbt = CustomDataUtil.getNbt($stack);
                if (nbt.contains("canUse") && !nbt.getBoolean("canUse")) return TypedActionResult.fail($stack);
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

            playSoundOnRightShoot($user);

            shootRight($user, $stack);
        }

        $user.incrementStat(Stats.USED, this);

        return TypedActionResult.success($stack, false);
    }

    public boolean isEnabledRightShoot() {
        return true;
    }

    public void shoot(Player $user, ItemStack $stack) {
        World $world = $user.getWorld();

        BulletEntity bulletEntity = new BulletEntity($world, $user.getEntity(), this);
        bulletEntity.setVelocity($user.getEntity(), $user.getPitch(), $user.getYaw(), getShootRoll(), getShootSpeed(), getShootDivergence());
        decreaseBulletCount($stack);
        $world.spawnEntity(bulletEntity);
    }

    public void shootRight(Player $user, ItemStack $stack) {
        if (!isEnabledRightShoot()) return;
        World $world = $user.getWorld();

        BulletEntity bulletEntity = new BulletEntity($world, $user.getEntity(), this);
        bulletEntity.setVelocity($user.getEntity(), $user.getPitch(), $user.getYaw(), getShootRoll(), getShootSpeed(), getShootDivergence());
        bulletEntity.setAddedDamage(getRightShootDamage());
        decreaseBulletCount($stack);
        $world.spawnEntity(bulletEntity);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) getBulletCount(stack) * 13.0F / (float) getMaxBulletCount());
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return CustomDataUtil.hasNbt(stack);
    }
}
