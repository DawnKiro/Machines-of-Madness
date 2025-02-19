package net.voxelden.machinesOfMadness.client.render.machine;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public abstract class MachineRenderer {
    public abstract void render(BlockState state, UUID uuid, NbtCompound nbt, RenderHelper renderHelper);

    public record RenderHelper(BlockEntityRendererFactory.Context renderContext, MatrixStack matrices, VertexConsumerProvider consumers, int light, int overlay, float tickDelta) {}
}
