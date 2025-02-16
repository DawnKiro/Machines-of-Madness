package net.voxelden.machinesOfMadness.block.base;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface HideableBlock {
    default void show(BlockState state, World world, BlockPos pos) {
        setHidden(state, world, pos, false);
    }

    default void hide(BlockState state, World world, BlockPos pos) {
        setHidden(state, world, pos, true);
    }

    boolean isHidden(BlockState state);

    void setHidden(BlockState state, World world, BlockPos pos, boolean hidden);
}
