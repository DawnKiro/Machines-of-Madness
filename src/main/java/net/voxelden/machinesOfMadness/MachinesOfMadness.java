package net.voxelden.machinesOfMadness;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.block.Blocks;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;
import net.voxelden.machinesOfMadness.command.Commands;
import net.voxelden.machinesOfMadness.factory.FactoryThread;
import net.voxelden.machinesOfMadness.machine.MachineRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MachinesOfMadness implements ModInitializer {
    public static final String MOD_ID = "mm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Random RANDOM = new Random();
    public static final String[] INIT_LOGS = {
            "Excess is not enough",
            "It's never enough POWAHHHHHH",
            "I bet I can get it over 9000",
            "A MASSIVE ERROR HAS OCCURRED\n\t\tjust kidding ;3"
    };

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    public static UUID UUID(Collection<UUID> map) {
        UUID id;
        do id = UUID.randomUUID(); while (map.contains(id));
        return id;
    }

    public static UUID UUID(Map<UUID, ?> map) {
        UUID id;
        do id = UUID.randomUUID(); while (map.containsKey(id));
        return id;
    }

    @Override
    public void onInitialize() {
        LOGGER.info(INIT_LOGS[RANDOM.nextInt(INIT_LOGS.length - 1)]);

        MachineRegistry.register();
        Blocks.register();
        ChunkAttachment.register();

        CommandRegistrationCallback.EVENT.register(Commands::register);

        ServerLifecycleEvents.SERVER_STARTED.register(FactoryThread::start);
        ServerTickEvents.START_SERVER_TICK.register(FactoryThread::heartbeat);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> FactoryThread.stop());
    }
}
