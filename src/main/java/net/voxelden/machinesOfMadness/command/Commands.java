package net.voxelden.machinesOfMadness.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.datafixers.util.Function3;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;

public class Commands {
    private static final List<Function3<CommandDispatcher<ServerCommandSource>, CommandRegistryAccess, CommandManager.RegistrationEnvironment, LiteralArgumentBuilder<ServerCommandSource>>> commandRegistrars = List.of(
            ChunkDataCommand::register,
            MachineCommand::register
    );

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        commandRegistrars.forEach(commandRegistrar -> dispatcher.register(commandRegistrar.apply(dispatcher, registryAccess, environment)));
    }
}
