package net.voxelden.machinesOfMadness.factory.machine;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.voxelden.machinesOfMadness.MachinesOfMadness;

public class MachineRegistry {
    public static final RegistryKey<Registry<MapCodec<? extends Machine>>> KEY = RegistryKey.ofRegistry(MachinesOfMadness.id("machine"));
    public static final Registry<MapCodec<? extends Machine>> REGISTRY = FabricRegistryBuilder.createSimple(KEY).buildAndRegister();

    public static void register() {
        Registry.register(REGISTRY, MachinesOfMadness.id("template"), CheatMachine.CODEC);
    }
}
