package net.voxelden.machinesOfMadness.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.shape.VoxelShape;

import java.util.Map;

public class ItemPipeBlock extends AbstractPipeBlock<ItemStack> {
    private static final Map<EnumProperty<PipeConnection>, VoxelShape> CONNECTIONS = Map.of(
            DOWN_CONNECTION,
            Block.createCuboidShape(4, 0, 4, 12, 4, 12),
            UP_CONNECTION,
            Block.createCuboidShape(4, 12, 4, 12, 16, 12),
            NORTH_CONNECTION,
            Block.createCuboidShape(4, 4, 0, 12, 12, 4),
            SOUTH_CONNECTION,
            Block.createCuboidShape(4, 4, 12, 12, 12, 16),
            WEST_CONNECTION,
            Block.createCuboidShape(0, 4, 4, 4, 12, 12),
            EAST_CONNECTION,
            Block.createCuboidShape(12, 4, 4, 16, 12, 12)
    );

    public ItemPipeBlock(Settings settings) {
        super(settings);
    }

    @Override
    Map<EnumProperty<PipeConnection>, VoxelShape> getConnections() {
        return CONNECTIONS;
    }
}