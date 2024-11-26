package net.pitan76.ygm76.fix;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.pitan76.mcpitanlib.api.util.CustomDataUtil;
import net.pitan76.mcpitanlib.api.util.MCVersionUtil;
import net.pitan76.mcpitanlib.api.util.NbtUtil;

public class NbtFixer {
    public static void fix(ItemStack stack) {
        if (MCVersionUtil.getProtocolVersion() >= 766) return;

        try {
            if (!stack.hasNbt()) return;
            NbtCompound nbt = stack.getNbt();
            if (NbtUtil.has(nbt, "bullet")) {
                int bullet = NbtUtil.getInt(nbt, "bullet");
                CustomDataUtil.set(stack, "bullet", bullet);
                NbtUtil.remove(nbt, "bullet");
            }
        } catch (NoSuchMethodError ignore) { }

    }
}
