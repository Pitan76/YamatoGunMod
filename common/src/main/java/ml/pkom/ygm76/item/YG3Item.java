package ml.pkom.ygm76.item;

import ml.pkom.mcpitanlibarch.api.item.CompatibleItemSettings;

public class YG3Item extends YG2Item {
    public YG3Item(CompatibleItemSettings settings) {
        super(settings);
    }

    @Override
    public int getMaxBulletCount() {
        return 60;
    }

    @Override
    public float getShootDamage() {
        return 10f;
    }

    @Override
    public float getRightShootDamage() {
        return 15f;
    }

    @Override
    public int getShootTick() {
        return 5;
    }
}
