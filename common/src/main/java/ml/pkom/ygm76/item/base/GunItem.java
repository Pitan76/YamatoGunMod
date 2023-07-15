package ml.pkom.ygm76.item.base;

import dev.architectury.event.EventResult;
import ml.pkom.mcpitanlibarch.api.entity.Player;
import ml.pkom.mcpitanlibarch.api.event.item.ItemUseEvent;
import ml.pkom.mcpitanlibarch.api.event.v0.AttackEntityEventRegistry;
import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;
import ml.pkom.mcpitanlibarch.api.item.ExtendItem;
import ml.pkom.ygm76.entity.BulletEntity;
import ml.pkom.ygm76.item.YGItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class GunItem extends ExtendItem {
    public GunItem(CompatibleItemSettings settings) {
        super(settings);
        AttackEntityEventRegistry.register((this::onLeftClick));
    }

    public int getMaxBulletCount() {
        return 5;
    }

    public int getShootTick() {
        return 60;
    }

    public float getShootSpeed() {
        return 1.5f;
    }

    public int getReloadTick() {
        return 60;
    }

    public float getShootDamage() {
        return 1f;
    }

    public float getShootDivergence() {
        return 1f;
    }

    public float getShootRoll() {
        return 0f;
    }

    public static int getBulletCount(ItemStack stack) {
        //noinspection DataFlowIssue
        if (stack == null || !(stack.getItem() instanceof GunItem) || !stack.hasNbt() || !stack.getNbt().contains("bullet")) return 0;
        return stack.getNbt().getInt("bullet");
    }

    public static void setBulletCount(ItemStack stack, int count) {
        if (stack == null || !(stack.getItem() instanceof GunItem)) return;
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("bullet", count);
        stack.setNbt(nbt);
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

    public EventResult onLeftClick(Player player, World world, Entity target, Hand hand, EntityHitResult result) {
        return EventResult.interruptFalse();
    }

    public Item getBulletItem() {
        return YGItems.BULLET_ITEM.get();
    }

    public void reload(ItemStack stack, Player player) {
        if (player.getInventory().contains(new ItemStack(getBulletItem()))) {
            player.getEntity().getItemCooldownManager().set(this, getReloadTick());
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

            int $usableCount = Math.min(getMaxBulletCount(), $bulletCount);
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

    @Override
    public TypedActionResult<ItemStack> onRightClick(ItemUseEvent event) {
        Player $user = event.user;
        World $world = event.world;
        BlockPos $pos = event.user.getBlockPos();

        ItemStack $stack = $user.getStackInHand(event.hand);

        if (isBulletCountEmpty($stack)) {
            reload($stack, $user);
            return TypedActionResult.fail($stack);
        }

        $world.playSound(null, $pos.getX(), $pos.getY(), $pos.getZ(), SoundEvents.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, SoundCategory.NEUTRAL, 0.5F, 0.3F / ($world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!$world.isClient) {
            BulletEntity bulletEntity = new BulletEntity($world, $user.getEntity(), this);
            bulletEntity.setVelocity($user.getEntity(), $user.getPitch(), $user.getYaw(), getShootRoll(), getShootSpeed(), getShootDivergence());
            decreaseBulletCount($stack);
            $world.spawnEntity(bulletEntity);
        }

        $user.getEntity().incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success($stack, false);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) getBulletCount(stack) * 13.0F / (float) getMaxBulletCount());
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true;
    }
}
