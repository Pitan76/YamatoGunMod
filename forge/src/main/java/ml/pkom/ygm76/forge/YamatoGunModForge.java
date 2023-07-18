package ml.pkom.ygm76.forge;

import dev.architectury.platform.forge.EventBuses;
import ml.pkom.mcpitanlibarch.api.util.PlatformUtil;
import ml.pkom.ygm76.YamatoGunMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YamatoGunMod.MOD_ID)
public class YamatoGunModForge {
    public YamatoGunModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(YamatoGunMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        YamatoGunMod.init();

        if (PlatformUtil.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(YamatoGunForgeClientMod::clientInit);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(YamatoGunForgeClientMod::renderInit);
        }
    }
}