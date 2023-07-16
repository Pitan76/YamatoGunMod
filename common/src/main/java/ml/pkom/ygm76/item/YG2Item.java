package ml.pkom.ygm76.item;

import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;

public class YG2Item extends YG1Item {
    public YG2Item(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public int getMaxBulletCount() {
        return 45;
    }

    @Override
    public float getShootDamage() {
        return 5f;
    }

    @Override
    public float getRightShootDamage() {
        return 8f;
    }

    @Override
    public int getShootTick() {
        return 5;
    }
}
