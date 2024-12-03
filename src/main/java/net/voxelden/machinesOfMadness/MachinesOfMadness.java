package net.voxelden.machinesOfMadness;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MachinesOfMadness implements ModInitializer {
    public static final String MOD_ID = "machines_of_madness";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("merp");

        Blocks.register();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
