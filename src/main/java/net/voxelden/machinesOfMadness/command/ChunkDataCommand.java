package net.voxelden.machinesOfMadness.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.*;

public class ChunkDataCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("chunkdata");
        command.executes(ChunkDataCommand::test);
        return command;
    }

    private static int test(CommandContext<ServerCommandSource> context) {
        Chunk chunk = context.getSource().getWorld().getChunk(BlockPos.ofFloored(context.getSource().getPosition()));
        //noinspection UnstableApiUsage
        ChunkAttachment attachment = chunk.getAttachedOrCreate(ChunkAttachment.TYPE);
        return attachment.machines().size();
    }
}
