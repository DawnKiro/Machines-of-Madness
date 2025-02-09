package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.block.multiblock.PartMultiblock;
import net.voxelden.machinesOfMadness.block.multiblock.SimpleMultiblock;
import net.voxelden.machinesOfMadness.factory.machine.CheatMachine;

public class Blocks {
    public static final ItemPipeBlock ITEM_PIPE_BLOCK = register("item_pipe", true, new ItemPipeBlock(AbstractBlock.Settings.create()));
    public static final SimpleMultiblock TEMPLATE_SIMPLE_MULTIBLOCK = register("template_multiblock", true, new SimpleMultiblock(AbstractBlock.Settings.create(), SimpleMultiblock.Settings.of(MachinesOfMadness.id("template"), 2, 2, 2, 8, 8, 8)));
    public static final SimpleMultiblock TEMPLATE_SIMPLE_MULTIBLOCK_MACHINE = register("template_multiblock_machine", true, new SimpleMachineMultiblock(
            AbstractBlock.Settings.create(),
            SimpleMultiblock.Settings.of(MachinesOfMadness.id("template"), 2, 2, 2, 8, 8, 8),
            () -> new CheatMachine(1)
    ));
    public static final PartMultiblock TEMPLATE_PART_MULTIBLOCK = register("template_part", true, new PartMultiblock(AbstractBlock.Settings.create()));

    public static void register() {
    }

    private static <T extends Block> T register(String name, boolean withItem, T block) {
        return register(MachinesOfMadness.id(name), withItem, block);
    }

    private static <T extends Block> T register(Identifier id, boolean withItem, T block) {
        if (withItem) Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, id, block);
    }
}
