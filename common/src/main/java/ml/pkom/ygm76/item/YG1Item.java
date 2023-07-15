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
        return 1f;
    }

    @Override
    public int getShootTick() {
        return 5;
    }
}
