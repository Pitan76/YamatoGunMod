package ml.pkom.ygm76.timer;

import java.util.function.Supplier;

public interface ServerTimerAccess {
    void ymg76_setTimer(long ticksUntilSomething, Supplier<Boolean> supplier);
}