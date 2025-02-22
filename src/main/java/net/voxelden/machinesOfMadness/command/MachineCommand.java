package net.voxelden.machinesOfMadness.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.voxelden.machinesOfMadness.factory.FactoryThread;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class MachineCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("machine");
        LiteralArgumentBuilder<ServerCommandSource> countBranch = literal("count");
        countBranch.executes(MachineCommand::count);
        return command.then(countBranch);
    }

    private static int count(CommandContext<ServerCommandSource> context) {
        int machines = FactoryThread.FACTORY.getMachines().size();
        context.getSource().sendFeedback(() -> Text.literal("There are " + machines + " machines"), false);
        return machines;
    }
}
