package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.block.base.SimpleMachineBlock;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;
import net.voxelden.machinesOfMadness.block.pipe.ItemPipeBlock;
import net.voxelden.machinesOfMadness.machine.CheatMachine;

public class Blocks {
    public static final ItemPipeBlock ITEM_PIPE_BLOCK = register("item_pipe", true, new ItemPipeBlock(AbstractBlock.Settings.create()));
    public static final SimpleMachineBlock TEMPLATE_SIMPLE_MACHINE_BLOCK = register("template_multiblock_machine", true, new SimpleMachineBlock(AbstractBlock.Settings.create(), () -> new CheatMachine(0)));

    public static void register() {
        MachineBlockEntity.register();
    }

    private static <T extends Block> T register(String name, boolean withItem, T block) {
        return register(MachinesOfMadness.id(name), withItem, block);
    }

    private static <T extends Block> T register(Identifier id, boolean withItem, T block) {
        if (withItem) Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, id, block);
    }
}
