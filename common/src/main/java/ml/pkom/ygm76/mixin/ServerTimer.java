package ml.pkom.ygm76.mixin;

import ml.pkom.ygm76.timer.ServerTimerAccess;
import ml.pkom.ygm76.timer.TimerItem;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mixin(ServerWorld.class)
public class ServerTimer implements ServerTimerAccess {
    @Unique
    private final List<TimerItem> timerItems = new ArrayList<>();

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (timerItems.isEmpty()) return;
        List<TimerItem> items = new ArrayList<>(timerItems);

        for (TimerItem item : items) {
            if (--item.ticksUntilSomething == 0L) {
                if (item.executeSupplier.get())
                    timerItems.remove(item);
            }
        }
    }
    @Override
    public void ymg76_setTimer(long ticksUntilSomething, Supplier<Boolean> executeSupplier) {
        timerItems.add(new TimerItem(ticksUntilSomething, executeSupplier));
    }
}
