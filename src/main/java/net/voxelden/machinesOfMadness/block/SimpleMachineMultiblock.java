package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voxelden.machinesOfMadness.block.multiblock.SimpleMultiblock;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;
import net.voxelden.machinesOfMadness.factory.FactoryThread;
import net.voxelden.machinesOfMadness.factory.machine.Machine;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class SimpleMachineMultiblock extends SimpleMultiblock implements MachineBlock {
    private final Supplier<Machine> builder;

    public SimpleMachineMultiblock(AbstractBlock.Settings abstractBlockSettings, Settings multiblockSettings, Supplier<Machine> machineBuilder) {
        super(abstractBlockSettings, multiblockSettings);
        builder = machineBuilder;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient()) ChunkAttachment.setMachineID(world, pos, FactoryThread.FACTORIES.machineUUID());
    }

    @Override
    public Machine getMachine(World world, BlockPos pos) {
        Optional<UUID> id = ChunkAttachment.getMachineID(world, pos);
        if (id.isPresent()) return FactoryThread.FACTORIES.getMachine(id.get()).orElseGet(builder);
        return builder.get();
    }
}
