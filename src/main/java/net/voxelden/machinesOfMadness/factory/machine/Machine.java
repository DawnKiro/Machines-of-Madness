package net.voxelden.machinesOfMadness.factory.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.voxelden.machinesOfMadness.util.Tickable;

public abstract class Machine implements Tickable {
    public static final Codec<Machine> TYPE_CODEC = MachineRegistry.REGISTRY.getCodec().dispatch(Machine::codec, MapCodec::codec);

    public abstract MapCodec<? extends Machine> codec();
}
