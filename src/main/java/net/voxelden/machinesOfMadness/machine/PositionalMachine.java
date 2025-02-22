package net.voxelden.machinesOfMadness.machine;

import com.mojang.datafixers.Products;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.voxelden.machinesOfMadness.factory.Factory;
import net.voxelden.machinesOfMadness.util.Position;
import net.voxelden.machinesOfMadness.util.Positional;

public abstract class PositionalMachine extends Machine implements Positional {
    private final Position pos;

    public PositionalMachine(Position pos) {
        this.pos = pos;
    }

    protected static <P extends PositionalMachine> Products.P1<RecordCodecBuilder.Mu<P>, Position> addPosition(RecordCodecBuilder.Instance<P> codec) {
        return codec.group(Position.CODEC.fieldOf("pos").forGetter(PositionalMachine::getPos));
    }

    public Position getPos() {
        return pos;
    }

    @Override
    public void syncNbt() {
        super.syncNbt();
        Position pos = getPos();
        Factory.withServerWorld(pos.world(), world -> world.getChunkManager().markForUpdate(pos.pos()));
    }

    public interface Builder {
        PositionalMachine create(Position pos);
    }
}
