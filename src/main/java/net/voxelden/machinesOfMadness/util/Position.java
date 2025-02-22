package net.voxelden.machinesOfMadness.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record Position(RegistryKey<World> world, BlockPos pos) {
    public static final Codec<Position> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            World.CODEC.fieldOf("world").forGetter(Position::world),
            BlockPos.CODEC.fieldOf("pos").forGetter(Position::pos)
    ).apply(instance, Position::new));

    public static Position of(World world, BlockPos pos) {
        return new Position(world.getRegistryKey(), pos);
    }
}
