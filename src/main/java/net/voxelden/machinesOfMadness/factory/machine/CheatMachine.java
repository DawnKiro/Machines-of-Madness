package net.voxelden.machinesOfMadness.factory.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CheatMachine extends Machine {
    public static final MapCodec<CheatMachine> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("number").forGetter(CheatMachine::getVersion)
    ).apply(instance, CheatMachine::new));

    public CheatMachine(int version) {
    }

    private Integer getVersion() {
        return 2;
    }

    @Override
    public MapCodec<CheatMachine> codec() {
        return CODEC;
    }

    @Override
    public void tick() {

    }
}
