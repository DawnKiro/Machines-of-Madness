package net.voxelden.machinesOfMadness.block.base;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class HideableBlockWithEntity extends BlockWithEntity implements HideableBlock {
    public static final BooleanProperty HIDDEN = BooleanProperty.of("hidden");
    private final BlockEntityType.BlockEntityFactory<?> blockEntityFactory;

    public HideableBlockWithEntity(AbstractBlock.Settings settings, BlockEntityType.BlockEntityFactory<?> blockEntityFactory) {
        super(settings.pistonBehavior(PistonBehavior.BLOCK));
        setDefaultState(getDefaultState().with(HIDDEN, false));
        this.blockEntityFactory = blockEntityFactory;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityFactory.create(pos, state);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return world.getMaxLightLevel();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return state.get(HIDDEN) ? BlockRenderType.INVISIBLE : BlockRenderType.MODEL;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(HIDDEN);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return state.get(HIDDEN) ? VoxelShapes.empty() : VoxelShapes.fullCube();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HIDDEN);
    }

    @Override
    public boolean isHidden(BlockState state) {
        return state.get(HIDDEN);
    }

    @Override
    public void setHidden(BlockState state, World world, BlockPos pos, boolean hidden) {
        world.setBlockState(pos, state.with(HIDDEN, hidden));
    }
}
