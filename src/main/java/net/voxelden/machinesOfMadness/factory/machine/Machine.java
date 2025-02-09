package net.voxelden.machinesOfMadness.factory.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import java.util.function.Function;

public abstract class Machine {
    public static final Codec<Machine> TYPE_CODEC = MachineRegistry.REGISTRY.getCodec().dispatch(Machine::codec, Function.identity());
    private boolean ticked = false;

    public abstract MapCodec<? extends Machine> codec();

    public boolean canTick() {
        return !ticked;
    }

    public void tick() {
        ticked = true;
    }

    public interface Holder {
        Machine getMachine();
    }
}
