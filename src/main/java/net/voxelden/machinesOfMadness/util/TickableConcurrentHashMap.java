package net.voxelden.machinesOfMadness.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TickableConcurrentHashMap<T extends Tickable> extends ConcurrentHashMap<UUID, T> {
    public TickableConcurrentHashMap(Map<UUID, T> factories) {
        super(factories);
    }

    public void tick() {
        forEach((id, tickable) -> tickable.tick());
    }
}
