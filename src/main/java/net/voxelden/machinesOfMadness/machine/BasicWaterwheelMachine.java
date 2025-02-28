package net.voxelden.machinesOfMadness.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.voxelden.machinesOfMadness.util.Position;

public class BasicWaterwheelMachine extends PositionalMachine {
    public static final MapCodec<BasicWaterwheelMachine> CODEC = RecordCodecBuilder.mapCodec(instance -> PositionalMachine.addPosition(instance).and(
            Codec.INT.fieldOf("number").forGetter(BasicWaterwheelMachine::getNumber)
    ).apply(instance, BasicWaterwheelMachine::new));
    private int number;

    public BasicWaterwheelMachine(Position pos) {
        this(pos, 0);
    }

    public BasicWaterwheelMachine(Position pos, int number) {
        super(pos);
        this.number = number;
    }

    private Integer getNumber() {
        return number;
    }

    @Override
    public MapCodec<BasicWaterwheelMachine> codec() {
        return CODEC;
    }

    @Override
    public NbtCompound getSyncedDataCompound(NbtCompound data) {
        data.putInt("number", number);
        return data;
    }

    @Override
    public void tickMachine() {
        number++;
        markNbtDirty();
    }
}
