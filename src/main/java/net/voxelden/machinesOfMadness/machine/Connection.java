package net.voxelden.machinesOfMadness.machine;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringIdentifiable;
import net.voxelden.machinesOfMadness.block.pipe.AbstractPipeBlock;
import net.voxelden.machinesOfMadness.util.Tickable;

import java.util.List;

public record Connection(AbstractPipeBlock.PipeConnection.Type type, List<Integer> connected) implements Tickable {
    public static final Codec<Connection> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            StringIdentifiable.createCodec(AbstractPipeBlock.PipeConnection.Type::values).fieldOf("type").forGetter(Connection::type),
            Codec.INT.listOf().fieldOf("connected").forGetter(Connection::connected)
    ).apply(instance, Connection::new));

    public void tick() {

    }
}
