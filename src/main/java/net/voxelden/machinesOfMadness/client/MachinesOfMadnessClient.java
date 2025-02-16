package net.voxelden.machinesOfMadness.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.voxelden.machinesOfMadness.block.entity.MachineBlockEntity;
import net.voxelden.machinesOfMadness.client.render.MachineRenderer;

public class MachinesOfMadnessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(MachineBlockEntity.INSTANCE, MachineRenderer::new);
    }
}
