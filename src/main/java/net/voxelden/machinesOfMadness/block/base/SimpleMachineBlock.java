package net.voxelden.machinesOfMadness.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;
import net.voxelden.machinesOfMadness.factory.FactoryThread;
import net.voxelden.machinesOfMadness.machine.Machine;
import net.voxelden.machinesOfMadness.machine.PositionalMachine;
import net.voxelden.machinesOfMadness.util.Position;

import java.util.Optional;
import java.util.UUID;

public class SimpleMachineBlock extends HideableBlockWithEntity implements MachineBlock {
    private final PositionalMachine.Builder builder;

    public SimpleMachineBlock(Settings settings, PositionalMachine.Builder machineBuilder) {
        super(settings, MachineBlockEntity::new);
        MachineBlockEntity.machineBlocks.add(this);
        builder = machineBuilder;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos blockPos, BlockState oldState, boolean notify) {
        if (!world.isClient()) {
            UUID uuid = FactoryThread.FACTORY.machineUUID();
            Position pos = Position.of(world, blockPos);
            FactoryThread.FACTORY.getMachines().put(uuid, getMachine(pos));
            ChunkAttachment.setMachineID(pos, uuid);
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos blockPos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, blockPos, newState, moved);
        if (!newState.isOf(state.getBlock())) ChunkAttachment.removeMachineID(Position.of(world, blockPos)).ifPresent(uuid -> FactoryThread.FACTORY.getMachines().remove(uuid));
    }

    @Override
    public Machine getMachine(Position pos) {
        Optional<UUID> id = ChunkAttachment.getMachineID(pos);
        if (id.isPresent()) return FactoryThread.FACTORY.getMachine(id.get()).orElse(builder.create(pos));
        return builder.create(pos);
    }
}
