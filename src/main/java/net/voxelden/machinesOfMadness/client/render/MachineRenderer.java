package net.voxelden.machinesOfMadness.client.render;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;

public class MachineRenderer implements BlockEntityRenderer<MachineBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public MachineRenderer(BlockEntityRendererFactory.Context ctx) {
        blockRenderManager = ctx.getRenderManager();
    }

    @Override
    public void render(MachineBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider consumers, int light, int overlay) {
        matrices.push();
        blockRenderManager.renderBlockAsEntity(Blocks.IRON_BLOCK.getDefaultState(), matrices, consumers, LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);
        matrices.pop();
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
