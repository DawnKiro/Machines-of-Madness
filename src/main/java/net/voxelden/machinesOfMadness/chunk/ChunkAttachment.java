package net.voxelden.machinesOfMadness.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public record ChunkAttachment(HashMap<BlockPos, UUID> machines) {
    private static final Codec<ChunkAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.unboundedMap(Uuids.STRING_CODEC, BlockPos.CODEC).fieldOf("machines").forGetter(ChunkAttachment::machinesFlipped)).apply(instance, ChunkAttachment::new));
    public static final AttachmentType<ChunkAttachment> TYPE = AttachmentRegistry.<ChunkAttachment>builder().persistent(CODEC).initializer(ChunkAttachment::new).buildAndRegister(MachinesOfMadness.id("machines_data"));

    ChunkAttachment() {
        this(new HashMap<BlockPos, UUID>());
    }

    ChunkAttachment(Map<UUID, BlockPos> machines) {
        this(Util.flipMap(new HashMap<>(machines)));
    }

    public static void register() {
    }

    public static void markDirty(Chunk chunk) {
        chunk.setNeedsSaving(true);
    }

    public static ChunkAttachment get(Chunk chunk) {
        return chunk.getAttachedOrCreate(TYPE);
    }

    public static ChunkAttachment get(World world, BlockPos pos) {
        return ChunkAttachment.get(world.getChunk(pos));
    }

    public static void set(Chunk chunk, ChunkAttachment attachment) {
        chunk.setAttached(TYPE, attachment);
        markDirty(chunk);
    }

    public static void set(World world, BlockPos pos, ChunkAttachment attachment) {
        ChunkAttachment.set(world.getChunk(pos), attachment);
    }

    public static void operate(Chunk chunk, Consumer<ChunkAttachment> consumer) {
        ChunkAttachment attachment = get(chunk);
        consumer.accept(attachment);
        set(chunk, attachment);
    }

    public static void operate(World world, BlockPos pos, Consumer<ChunkAttachment> consumer) {
        ChunkAttachment.operate(world.getChunk(pos), consumer);
    }

    public static HashMap<BlockPos, UUID> getMachines(World world, BlockPos pos) {
        return get(world, pos).machines();
    }

    public static Optional<UUID> getMachineID(World world, BlockPos pos) {
        return Optional.ofNullable(getMachines(world, pos).get(pos));
    }

    public static void setMachineID(World world, BlockPos pos, UUID machine) {
        ChunkAttachment.operate(world, pos, attachment -> attachment.machines().put(pos, machine));
    }

    private HashMap<UUID, BlockPos> machinesFlipped() {
        return Util.flipMap(machines);
    }
}
