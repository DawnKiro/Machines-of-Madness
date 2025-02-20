package net.voxelden.machinesOfMadness.client.render.machine;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.RotationAxis;

import java.util.UUID;

public class CheatMachineRenderer extends MachineRenderer {
    @Override
    public void render(BlockState state, UUID uuid, NbtCompound nbt, RenderHelper renderHelper) {
        int number = nbt.getInt("number");
        renderHelper.renderContext().getRenderManager().renderBlockAsEntity(Blocks.IRON_BLOCK.getDefaultState(), renderHelper.matrices(), renderHelper.consumers(), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);

        renderHelper.matrices().translate(0.5f, 1f, 0.5f);
        float scale = 1f / 64;
        renderHelper.matrices().scale(scale, scale, scale);
        String text = "number: " + number;
        renderHelper.matrices().multiply(RotationAxis.POSITIVE_X.rotation((float) Math.toRadians(90)));
        renderHelper.matrices().translate(renderHelper.renderContext().getTextRenderer().getWidth(text) * -0.5f, 0f, -1f);
        renderHelper.renderContext().getTextRenderer().draw(text, 0, 0, 0, false, renderHelper.matrices().peek().getPositionMatrix(), renderHelper.consumers(), TextRenderer.TextLayerType.NORMAL, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }
}
