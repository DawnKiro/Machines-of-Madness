package net.voxelden.machinesOfMadness.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.voxelden.machinesOfMadness.factory.machine.Machine;

import java.util.ArrayList;
import java.util.List;

public class Factory {
    public static final Codec<Factory> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Machine.TYPE_CODEC.listOf().fieldOf("machines").forGetter(Factory::machines),
            Connection.CODEC.listOf().fieldOf("connections").forGetter(Factory::connections)
    ).apply(instance, Factory::new));

    private final List<Machine> machines;
    private final List<Connection> connections;

    public Factory() {
        this(List.of(), List.of());
    }

    public Factory(List<Machine> machines, List<Connection> connections) {
        this.machines = machines;
        this.connections = connections;
    }

    public void tick() {
        ArrayList<Machine> tickingMachines = new ArrayList<>(machines);

        boolean needsTick = true;
        while (needsTick) {
            needsTick = false;
            for (Machine machine : tickingMachines) {
                if (machine.canTick()) {
                    machine.tick();
                    needsTick = true;
                }
            }
        }
    }

    private List<Machine> machines() {
        return machines;
    }

    private List<Connection> connections() {
        return connections;
    }
}
