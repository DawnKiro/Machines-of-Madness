package net.voxelden.machinesOfMadness.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.factory.Factory;
import net.voxelden.machinesOfMadness.util.Position;
import net.voxelden.machinesOfMadness.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public record ChunkAttachment(HashMap<BlockPos, UUID> machines) {
    private static final Codec<ChunkAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.unboundedMap(Uuids.STRING_CODEC, BlockPos.CODEC).fieldOf("machines").forGetter(ChunkAttachment::machinesFlipped)).apply(instance, ChunkAttachment::new));
    public static final AttachmentType<ChunkAttachment> TYPE = AttachmentRegistry.create(MachinesOfMadness.id("machines_data"),
            attachment -> attachment.initializer(ChunkAttachment::new).persistent(CODEC));

    ChunkAttachment() {
        this(new HashMap<BlockPos, UUID>());
    }

    ChunkAttachment(Map<UUID, BlockPos> machines) {
        this(Util.flipMap(new HashMap<>(machines)));
    }

    public static void register() {
    }

    public static void markDirty(Chunk chunk) {
        chunk.markNeedsSaving();
    }

    public static ChunkAttachment get(Chunk chunk) {
        return chunk.getAttachedOrCreate(TYPE);
    }

    public static ChunkAttachment get(Position pos) {
        return ChunkAttachment.get(getChunk(pos));
    }

    public static void set(Chunk chunk, ChunkAttachment attachment) {
        chunk.setAttached(TYPE, attachment);
        markDirty(chunk);
    }

    public static void set(Position pos, ChunkAttachment attachment) {
        ChunkAttachment.set(getChunk(pos), attachment);
    }

    public static void operate(Chunk chunk, Consumer<ChunkAttachment> consumer) {
        ChunkAttachment attachment = get(chunk);
        consumer.accept(attachment);
        set(chunk, attachment);
    }

    public static void operate(Position pos, Consumer<ChunkAttachment> consumer) {
        ChunkAttachment.operate(getChunk(pos), consumer);
    }

    public static HashMap<BlockPos, UUID> getMachines(Position pos) {
        return get(pos).machines();
    }

    public static Optional<UUID> getMachineID(Position pos) {
        return Optional.ofNullable(getMachines(pos).get(pos.pos()));
    }

    public static void setMachineID(Position pos, UUID machine) {
        ChunkAttachment.operate(pos, attachment -> attachment.machines().put(pos.pos(), machine));
    }

    public static Optional<UUID> removeMachineID(Position pos) {
        ChunkAttachment attachment = get(pos);
        set(pos, attachment);
        return Optional.ofNullable(attachment.machines().remove(pos.pos()));
    }

    private static Chunk getChunk(Position pos) {
        return Factory.getWorld(pos.world()).getChunk(pos.pos());
    }

    private HashMap<UUID, BlockPos> machinesFlipped() {
        return Util.flipMap(machines);
    }
}
