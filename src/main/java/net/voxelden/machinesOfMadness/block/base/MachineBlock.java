package net.voxelden.machinesOfMadness.block.base;

import net.voxelden.machinesOfMadness.machine.Machine;
import net.voxelden.machinesOfMadness.util.Position;

public interface MachineBlock {
    Machine getMachine(Position pos);
}
