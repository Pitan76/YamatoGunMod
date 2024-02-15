package net.pitan76.ygm76.fabric;

import net.pitan76.ygm76.YamatoGunClientMod;
import net.fabricmc.api.ClientModInitializer;

public class YamatoGunModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        YamatoGunClientMod.init();
    }
}
