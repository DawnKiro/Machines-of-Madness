package net.voxelden.machinesOfMadness.client;

import net.fabricmc.api.ClientModInitializer;
import net.voxelden.machinesOfMadness.client.render.machine.MachineRenderManager;

public class MachinesOfMadnessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MachineRenderManager.register();
    }
}
