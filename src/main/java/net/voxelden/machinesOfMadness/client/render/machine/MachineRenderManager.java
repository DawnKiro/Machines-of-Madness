package net.voxelden.machinesOfMadness.client.render.machine;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;
import net.voxelden.machinesOfMadness.machine.MachineRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MachineRenderManager implements BlockEntityRenderer<MachineBlockEntity> {
    private static final Map<Identifier, MachineRenderer> renderers = new HashMap<>();
    private final BlockEntityRendererFactory.Context renderContext;

    public MachineRenderManager(BlockEntityRendererFactory.Context renderContext) {
        this.renderContext = renderContext;
    }

    public static void register() {
        BlockEntityRendererFactories.register(MachineBlockEntity.INSTANCE, MachineRenderManager::new);
        renderers.put(MachineRegistry.TEMPLATE_MACHINE, new CheatMachineRenderer());
        renderers.put(MachineRegistry.BASIC_WATERWHEEL_MACHINE, new WaterwheelMachineRenderer());
    }

    @Override
    public void render(MachineBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider consumers, int light, int overlay) {
        if (blockEntity.isRenderable()) {
            Optional.ofNullable(renderers.get(blockEntity.getMachineType())).ifPresent(renderer -> {
                matrices.push();
                renderer.render(blockEntity.getCachedState(), blockEntity.getMachineUUID(), blockEntity.getMachineData(), new MachineRenderer.RenderHelper(renderContext, matrices, consumers, light, overlay, tickDelta));
                matrices.pop();
            });
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(MachineBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean isInRenderDistance(MachineBlockEntity blockEntity, Vec3d pos) {
        return true;
    }
}
