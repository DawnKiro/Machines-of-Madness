package net.voxelden.machinesOfMadness.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringIdentifiable;
import net.voxelden.machinesOfMadness.block.AbstractPipeBlock;

import java.util.List;

public record Connection(AbstractPipeBlock.PipeConnection.Type type, List<Integer> connected) {
    public static final Codec<Connection> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            StringIdentifiable.createCodec(AbstractPipeBlock.PipeConnection.Type::values).fieldOf("type").forGetter(Connection::type),
            Codec.INT.listOf().fieldOf("connected").forGetter(Connection::connected)
    ).apply(instance, Connection::new));
}
