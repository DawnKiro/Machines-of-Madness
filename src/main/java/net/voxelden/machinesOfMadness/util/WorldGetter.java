package net.voxelden.machinesOfMadness.util;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public interface WorldGetter {
    World getWorld(RegistryKey<World> key);
}
