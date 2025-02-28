package net.voxelden.machinesOfMadness.client.render.machine;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.RotationAxis;

import java.util.UUID;

public class WaterwheelMachineRenderer extends MachineRenderer {
    @Override
    public void render(BlockState state, UUID uuid, NbtCompound nbt, RenderHelper renderHelper) {
        int number = nbt.getInt("number");
        renderHelper.matrices().translate(0.5f, 0.5f, 0.5f);
        renderHelper.matrices().multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(number + renderHelper.tickDelta())));
        renderHelper.matrices().translate(-0.5f, -0.5f, -0.5f);
        renderHelper.renderContext().getRenderManager().renderBlockAsEntity(Blocks.OAK_PLANKS.getDefaultState(), renderHelper.matrices(), renderHelper.consumers(), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);
    }
}
