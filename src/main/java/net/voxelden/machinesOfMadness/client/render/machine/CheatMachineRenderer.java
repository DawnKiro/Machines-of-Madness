package net.voxelden.machinesOfMadness.client.render.machine;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class CheatMachineRenderer extends MachineRenderer {
    @Override
    public void render(BlockState state, UUID uuid, NbtCompound nbt, RenderHelper renderHelper) {
        int number = nbt.getInt("number");
        renderHelper.renderContext().getRenderManager().renderBlockAsEntity(Blocks.IRON_BLOCK.getDefaultState(), renderHelper.matrices(), renderHelper.consumers(), LightmapTextureManager.MAX_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV);
        renderHelper.renderContext().getTextRenderer().draw("number: " + number, 0, 0, -1, false, renderHelper.matrices().peek().getPositionMatrix(), renderHelper.consumers(), TextRenderer.TextLayerType.NORMAL, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
    }
}
