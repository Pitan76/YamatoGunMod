package net.pitan76.ygm76.fix;

import net.minecraft.item.ItemStack;
import net.pitan76.mcpitanlib.api.util.CustomDataUtil;

public class NbtFixer {
    public static void fix(ItemStack stack) {
        try {
            if (!stack.hasNbt()) return;
            if (stack.getNbt().contains("bullet")) {
                int bullet = stack.getNbt().getInt("bullet");
                CustomDataUtil.set(stack, "bullet", bullet);
                stack.getNbt().remove("bullet");
            }
        } catch (NoSuchMethodError ignore) { }

    }
}
