package ml.pkom.ygm76.item;

import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;
import ml.pkom.ygm76.item.base.GunItem;

public class YG1Item extends GunItem {
    public YG1Item(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public int getMaxBulletCount() {
        return 30;
    }

    @Override
    public int getReloadTick() {
        return super.getReloadTick();
    }

    @Override
    public float getShootDamage() {
        return 3f;
    }

    @Override
    public float getRightShootDamage() {
        return 5f;
    }

    @Override
    public int getShootTick() {
        return 10;
    }

    @Override
    public float getShootRoll() {
        return super.getShootRoll();
    }

    @Override
    public float getShootSpeed() {
        return super.getShootSpeed();
    }

    @Override
    public float getShootDivergence() {
        return super.getShootDivergence();
    }
}
