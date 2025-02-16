package net.voxelden.machinesOfMadness.block.pipe;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public abstract class AbstractPipeBlock extends Block {
    public static final EnumProperty<PipeConnection> DOWN_CONNECTION = EnumProperty.of("down", PipeConnection.class);
    public static final EnumProperty<PipeConnection> UP_CONNECTION = EnumProperty.of("up", PipeConnection.class);
    public static final EnumProperty<PipeConnection> NORTH_CONNECTION = EnumProperty.of("north", PipeConnection.class);
    public static final EnumProperty<PipeConnection> SOUTH_CONNECTION = EnumProperty.of("south", PipeConnection.class);
    public static final EnumProperty<PipeConnection> WEST_CONNECTION = EnumProperty.of("west", PipeConnection.class);
    public static final EnumProperty<PipeConnection> EAST_CONNECTION = EnumProperty.of("east", PipeConnection.class);

    private final Map<BlockState, VoxelShape> shapeMap;

    public AbstractPipeBlock(Settings settings) {
        super(settings.strength(0.2f).nonOpaque().allowsSpawning(net.minecraft.block.Blocks::never).suffocates(net.minecraft.block.Blocks::never).blockVision(net.minecraft.block.Blocks::never));
        setDefaultState(stateManager.getDefaultState()
                .with(DOWN_CONNECTION, PipeConnection.NONE)
                .with(UP_CONNECTION, PipeConnection.NONE)
                .with(NORTH_CONNECTION, PipeConnection.NONE)
                .with(SOUTH_CONNECTION, PipeConnection.NONE)
                .with(WEST_CONNECTION, PipeConnection.NONE)
                .with(EAST_CONNECTION, PipeConnection.NONE)
        );
        shapeMap = buildShapeMap();
    }

    abstract VoxelShape getCenterShape();

    abstract Map<EnumProperty<PipeConnection>, VoxelShape> getConnections();

    abstract PipeConnection.Type getType();

    private Map<BlockState, VoxelShape> buildShapeMap() {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();
        for (BlockState state : stateManager.getStates()) {
            VoxelShape shape = getCenterShape();
            for (Map.Entry<EnumProperty<PipeConnection>, VoxelShape> entry : getConnections().entrySet())
                if (state.get(entry.getKey()).isConnected()) shape = VoxelShapes.union(shape, entry.getValue());
            builder.put(state, shape);
        }
        return builder.build();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shapeMap.get(state);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                    state.with(NORTH_CONNECTION, state.get(SOUTH_CONNECTION)).with(EAST_CONNECTION, state.get(WEST_CONNECTION)).with(SOUTH_CONNECTION, state.get(NORTH_CONNECTION)).with(WEST_CONNECTION, state.get(EAST_CONNECTION));
            case COUNTERCLOCKWISE_90 ->
                    state.with(NORTH_CONNECTION, state.get(EAST_CONNECTION)).with(EAST_CONNECTION, state.get(SOUTH_CONNECTION)).with(SOUTH_CONNECTION, state.get(WEST_CONNECTION)).with(WEST_CONNECTION, state.get(NORTH_CONNECTION));
            case CLOCKWISE_90 ->
                    state.with(NORTH_CONNECTION, state.get(WEST_CONNECTION)).with(EAST_CONNECTION, state.get(NORTH_CONNECTION)).with(SOUTH_CONNECTION, state.get(EAST_CONNECTION)).with(WEST_CONNECTION, state.get(SOUTH_CONNECTION));
            default -> state;
        };
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT ->
                    state.with(NORTH_CONNECTION, state.get(SOUTH_CONNECTION)).with(SOUTH_CONNECTION, state.get(NORTH_CONNECTION));
            case FRONT_BACK ->
                    state.with(EAST_CONNECTION, state.get(WEST_CONNECTION)).with(WEST_CONNECTION, state.get(EAST_CONNECTION));
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    protected void appendProperties(net.minecraft.state.StateManager.Builder<Block, BlockState> builder) {
        builder.add(DOWN_CONNECTION, UP_CONNECTION, SOUTH_CONNECTION, NORTH_CONNECTION, WEST_CONNECTION, EAST_CONNECTION);
    }

    public enum PipeConnection implements StringIdentifiable {
        NONE("none", false),
        NORMAL("normal", true),
        PUSH("push", true),
        PULL("pull", true);

        private final String name;
        private final boolean isConnected;

        PipeConnection(String name, boolean isConnected) {
            this.name = name;
            this.isConnected = isConnected;
        }

        public String toString() {
            return this.asString();
        }

        @Override
        public String asString() {
            return this.name;
        }

        public boolean isConnected() {
            return isConnected;
        }

        public enum Type implements StringIdentifiable {
            ITEM("item", object -> object instanceof ItemStack), FLUID("fluid", object -> false), ENERGY("energy", object -> false), GAS("gas", object -> false), PLASMA("plasma", object -> false), QUANTUM("quantum", object -> false), ALL("all", object -> !QUANTUM.canTransfer(object));

            private final String name;
            private final Predicate<Object> predicate;

            Type(String name, Predicate<Object> predicate) {
                this.name = name;
                this.predicate = predicate;
            }

            public String toString() {
                return this.asString();
            }

            @Override
            public String asString() {
                return this.name;
            }

            public boolean canTransfer(Object object) {
                return predicate.test(object);
            }
        }
    }
}
