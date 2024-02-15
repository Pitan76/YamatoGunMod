package net.pitan76.ygm76.item;

import net.pitan76.mcpitanlib.api.item.CompatibleItemSettings;

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
