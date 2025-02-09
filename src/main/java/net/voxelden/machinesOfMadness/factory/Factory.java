package net.voxelden.machinesOfMadness.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.voxelden.machinesOfMadness.factory.machine.Machine;
import net.voxelden.machinesOfMadness.util.Tickable;
import net.voxelden.machinesOfMadness.util.TickableConcurrentHashMap;

import java.util.Map;
import java.util.UUID;

public record Factory(TickableConcurrentHashMap<Machine> machines, TickableConcurrentHashMap<Connection> connections) implements Tickable {
    public static final Codec<Factory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TickableConcurrentHashMap.codecOf(Machine.TYPE_CODEC).fieldOf("machines").forGetter(Factory::machines),
            TickableConcurrentHashMap.codecOf(Connection.CODEC).fieldOf("connections").forGetter(Factory::connections)
    ).apply(instance, Factory::new));

    public Factory() {
        this(Map.of(), Map.of());
    }

    public Factory(Map<UUID, Machine> machines, Map<UUID, Connection> connections) {
        this(new TickableConcurrentHashMap<>(machines), new TickableConcurrentHashMap<>(connections));
    }

    public void tick() {
        machines.tick();
        connections.tick();
    }
}
