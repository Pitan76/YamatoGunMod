package ml.pkom.ygm76.forge;

import ml.pkom.ygm76.YamatoGunClientMod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class YamatoGunForgeClientMod {
    public static void clientInit(FMLClientSetupEvent event) {
        YamatoGunClientMod.init();
    }
}
