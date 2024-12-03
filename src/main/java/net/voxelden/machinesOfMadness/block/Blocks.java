package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.voxelden.machinesOfMadness.MachinesOfMadness;

public class Blocks {
    public static final ItemPipeBlock ITEM_PIPE_BLOCK = register("item_pipe", new ItemPipeBlock(AbstractBlock.Settings.create()));

    public static void register() {
    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registries.BLOCK, MachinesOfMadness.id(name), block);
    }
}
