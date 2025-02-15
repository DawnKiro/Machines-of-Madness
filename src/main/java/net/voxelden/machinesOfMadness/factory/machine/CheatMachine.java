package net.voxelden.machinesOfMadness.factory.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class CheatMachine extends Machine {
    public static final MapCodec<CheatMachine> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("number").forGetter(CheatMachine::getNumber)
    ).apply(instance, CheatMachine::new));
    private int number;

    public CheatMachine(int number) {
        this.number = number;
    }

    private Integer getNumber() {
        return number;
    }

    @Override
    public MapCodec<CheatMachine> codec() {
        return CODEC;
    }

    @Override
    public void tick() {
        number ++;
    }
}
