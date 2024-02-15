package net.pitan76.ygm76.fabric;

import net.pitan76.ygm76.YamatoGunMod;
import net.fabricmc.api.ModInitializer;

public class YamatoGunModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YamatoGunMod.init();
    }
}