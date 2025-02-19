package net.voxelden.machinesOfMadness.machine;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.MachinesOfMadness;

public class MachineRegistry {
    public static final RegistryKey<Registry<MapCodec<? extends Machine>>> KEY = RegistryKey.ofRegistry(MachinesOfMadness.id("machine"));
    public static final Registry<MapCodec<? extends Machine>> REGISTRY = FabricRegistryBuilder.createSimple(KEY).buildAndRegister();

    public static final Identifier TEMPLATE_MACHINE = register("template", CheatMachine.CODEC);

    public static void register() {}

    public static Identifier register(String name, MapCodec<? extends Machine> codec) {
        Identifier id = MachinesOfMadness.id(name);
        Registry.register(REGISTRY, id, codec);
        return id;
    }
}
