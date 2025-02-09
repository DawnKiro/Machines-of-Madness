package net.voxelden.machinesOfMadness.block.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PartMultiblock extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public PartMultiblock(Settings settings) {
        super(settings.pistonBehavior(PistonBehavior.BLOCK));
        setDefaultState(getDefaultState().with(ACTIVE, false));
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return world.getMaxLightLevel();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(ACTIVE) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(ACTIVE);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return state.get(ACTIVE) ? VoxelShapes.empty() : VoxelShapes.fullCube();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
