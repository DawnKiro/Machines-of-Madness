package net.voxelden.machinesOfMadness.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.voxelden.machinesOfMadness.factory.machine.Machine;

public interface MachineBlock {
    Machine getMachine(World world, BlockPos pos);
}
