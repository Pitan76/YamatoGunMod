package net.pitan76.ygm76.forge;

import dev.architectury.platform.forge.EventBuses;
import net.pitan76.mcpitanlib.api.util.PlatformUtil;
import net.pitan76.ygm76.YamatoGunMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(YamatoGunMod.MOD_ID)
public class YamatoGunModForge {
    public YamatoGunModForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(YamatoGunMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        new YamatoGunMod();

        if (PlatformUtil.isClient()) {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(YamatoGunForgeClientMod::renderInit);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(YamatoGunForgeClientMod::clientInit);
        }
    }
}