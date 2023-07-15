package ml.pkom.ygm76.fabric;

import ml.pkom.ygm76.YamatoGunClientMod;
import net.fabricmc.api.ClientModInitializer;

public class YamatoGunModClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        YamatoGunClientMod.init();
    }
}
