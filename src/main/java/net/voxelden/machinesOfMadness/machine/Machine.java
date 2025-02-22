package net.voxelden.machinesOfMadness.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.voxelden.machinesOfMadness.util.NbtSyncable;
import net.voxelden.machinesOfMadness.util.Tickable;

import java.util.function.Function;

public abstract class Machine implements Tickable, NbtSyncable {
    public static final Codec<Machine> TYPE_CODEC = MachineRegistry.REGISTRY.getCodec().dispatch(Machine::codec, Function.identity());
    private boolean nbtDirty;

    public Machine() {
    }

    public abstract MapCodec<? extends Machine> codec();

    abstract void tickMachine();

    @Override
    public void tick() {
        tickMachine();
        if (needsNbtSync()) syncNbt();
    }

    @Override
    public boolean needsNbtSync() {
        return nbtDirty;
    }

    @Override
    public void markNbtDirty() {
        nbtDirty = true;
    }

    @Override
    public void syncNbt() {
        nbtDirty = false;
    }
}
