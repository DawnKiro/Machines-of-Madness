package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.AbstractBlock;
import net.voxelden.machinesOfMadness.block.multiblock.SimpleMultiblock;
import net.voxelden.machinesOfMadness.factory.machine.Machine;

import java.util.function.Supplier;

public class SimpleMachineMultiblock extends SimpleMultiblock implements Machine.Holder {
    private final Supplier<Machine> builder;

    public SimpleMachineMultiblock(AbstractBlock.Settings abstractBlockSettings, Settings multiblockSettings, Supplier<Machine> machineBuilder) {
        super(abstractBlockSettings, multiblockSettings);
        builder = machineBuilder;
    }

    @Override
    public Machine getMachine() {
        return builder.get();
    }
}
