package ml.pkom.ygm76.fabric;

import ml.pkom.ygm76.YamatoGunMod;
import net.fabricmc.api.ModInitializer;

public class YamatoGunModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        YamatoGunMod.init();
    }
}