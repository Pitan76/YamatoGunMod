package net.pitan76.ygm76.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.pitan76.mcpitanlib.api.util.PlatformUtil;
import net.pitan76.ygm76.YamatoGunMod;

import java.util.Objects;

@Mod(YamatoGunMod.MOD_ID)
public class YamatoGunModNeoForge {
    public YamatoGunModNeoForge(ModContainer modContainer) {
        IEventBus bus = modContainer.getEventBus();
        new YamatoGunMod();

        if (PlatformUtil.isClient() && bus != null) {
            bus.addListener(YamatoGunNeoForgeClientMod::renderInit);
            bus.addListener(YamatoGunNeoForgeClientMod::clientInit);
        }
    }
}