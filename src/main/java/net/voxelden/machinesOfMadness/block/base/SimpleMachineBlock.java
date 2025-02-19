package net.voxelden.machinesOfMadness.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;
import net.voxelden.machinesOfMadness.factory.FactoryThread;
import net.voxelden.machinesOfMadness.machine.Machine;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class SimpleMachineBlock extends HideableBlockWithEntity implements MachineBlock {
    private final Supplier<Machine> builder;

    public SimpleMachineBlock(Settings settings, Supplier<Machine> machineBuilder) {
        super(settings, MachineBlockEntity::new);
        MachineBlockEntity.machineBlocks.add(this);
        builder = machineBuilder;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        UUID uuid = FactoryThread.FACTORIES.machineUUID();
        FactoryThread.FACTORIES.getMachines().put(uuid, getMachine(world, pos));
        ChunkAttachment.operate(world, pos, attachment -> attachment.machines().put(pos, uuid));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);
        ChunkAttachment.removeMachineID(world, pos).ifPresent(uuid -> FactoryThread.FACTORIES.getMachines().remove(uuid));
    }

    @Override
    public Machine getMachine(World world, BlockPos pos) {
        Optional<UUID> id = ChunkAttachment.getMachineID(world, pos);
        if (id.isPresent()) return FactoryThread.FACTORIES.getMachine(id.get()).orElseGet(builder);
        return builder.get();
    }
}
