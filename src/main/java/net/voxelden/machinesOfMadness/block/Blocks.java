package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.block.base.SimpleMachineBlock;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;
import net.voxelden.machinesOfMadness.block.pipe.ItemPipeBlock;
import net.voxelden.machinesOfMadness.machine.CheatMachine;
import net.voxelden.machinesOfMadness.machine.PositionalMachine;
import net.voxelden.machinesOfMadness.machine.BasicWaterwheelMachine;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Blocks {
    public static final ItemPipeBlock ITEM_PIPE_BLOCK = register("item_pipe", true, ItemPipeBlock::new);
    public static final SimpleMachineBlock TEMPLATE_SIMPLE_MACHINE_BLOCK = registerMachineBlock("template_multiblock_machine", SimpleMachineBlock::new, CheatMachine::new);
    public static final SimpleMachineBlock BASIC_WATER_WHEEL_BLOCK = registerMachineBlock("basic_waterwheel", SimpleMachineBlock::new, BasicWaterwheelMachine::new);

    public static void register() {
        MachineBlockEntity.register();
    }

    private static <T extends Block> T register(String name, boolean withItem, Function<AbstractBlock.Settings, T> block) {
        return register(MachinesOfMadness.id(name), withItem, block);
    }

    private static <T extends Block> T register(Identifier id, boolean withItem, Function<AbstractBlock.Settings, T> block) {
        return register(id, withItem, block.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, id))));
    }

    private static <T extends Block> T registerMachineBlock(String name, BiFunction<AbstractBlock.Settings, PositionalMachine.Builder, T> block, PositionalMachine.Builder machineBuilder) {
        return registerMachineBlock(MachinesOfMadness.id(name), block, machineBuilder);
    }

    private static <T extends Block> T registerMachineBlock(Identifier id, BiFunction<AbstractBlock.Settings, PositionalMachine.Builder, T> block, PositionalMachine.Builder machineBuilder) {
        return register(id, true, block.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, id)), machineBuilder));
    }

    private static <T extends Block> T register(Identifier id, boolean withItem, T block) {
        if (withItem) Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, id))));
        return Registry.register(Registries.BLOCK, id, block);
    }
}
