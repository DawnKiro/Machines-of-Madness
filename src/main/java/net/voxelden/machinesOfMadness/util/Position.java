package net.voxelden.machinesOfMadness.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record Position(Identifier world, BlockPos pos) {
    public static final Codec<Position> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("world").forGetter(Position::world),
            BlockPos.CODEC.fieldOf("pos").forGetter(Position::pos)
    ).apply(instance, Position::new));
}
