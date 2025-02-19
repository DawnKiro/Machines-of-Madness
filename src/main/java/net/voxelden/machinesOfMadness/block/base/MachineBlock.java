package net.voxelden.machinesOfMadness.block.base;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voxelden.machinesOfMadness.machine.Machine;

public interface MachineBlock {
    Machine getMachine(World world, BlockPos pos);
}
