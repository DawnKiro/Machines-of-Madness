package net.voxelden.machinesOfMadness.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.voxelden.machinesOfMadness.util.NbtSyncable;
import net.voxelden.machinesOfMadness.util.Position;
import net.voxelden.machinesOfMadness.util.Tickable;

public abstract class Machine implements Tickable, NbtSyncable {
    public static final Codec<Machine> TYPE_CODEC = MachineRegistry.REGISTRY.getCodec().dispatch(Machine::codec, MapCodec::codec);

    private boolean nbtDirty;

    public abstract MapCodec<? extends Machine> codec();

    abstract void tickMachine();

    @Override
    public void tick() {
        tickMachine();
        if (needsNbtSync()) syncNbt();
    }

    private Position getPos() {
        return null;
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
        //world.getChunkManager().markForUpdate(pos);
    }
}
