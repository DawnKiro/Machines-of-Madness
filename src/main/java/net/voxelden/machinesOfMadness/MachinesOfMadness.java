package net.voxelden.machinesOfMadness;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.voxelden.machinesOfMadness.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MachinesOfMadness implements ModInitializer {
    public static final String MOD_ID = "machines_of_madness";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Random RANDOM = new Random();
    public static final String[] INIT_LOGS = {
            "Excess is not enough",
            "It's never enough POWAHHHHHH",
            "I bet I can get it over 9000",
            "A MASSIVE ERROR HAS OCCURRED\n\t\tjust kidding ;3"
    };

    @Override
    public void onInitialize() {
        LOGGER.info(INIT_LOGS[RANDOM.nextInt(INIT_LOGS.length - 1)]);

        Blocks.register();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }
}
