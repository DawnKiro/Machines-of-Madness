package net.voxelden.machinesOfMadness.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.machine.Connection;
import net.voxelden.machinesOfMadness.machine.Machine;
import net.voxelden.machinesOfMadness.util.Tickable;
import net.voxelden.machinesOfMadness.util.TickableConcurrentHashMap;
import net.voxelden.machinesOfMadness.util.WorldGetter;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class Factory extends PersistentState implements Tickable {
    private static final int VERSION = 1;
    public static final Codec<Factory> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("version").forGetter(Factory::getVersion), Codec.unboundedMap(Uuids.STRING_CODEC, Machine.TYPE_CODEC).fieldOf("machines").forGetter(Factory::getMachines), Codec.unboundedMap(Uuids.STRING_CODEC, Connection.CODEC).fieldOf("connections").forGetter(Factory::getConnections)).apply(instance, Factory::new));
    private static final Type<Factory> TYPE = new Type<>(Factory::new, Factory::readNbt, null);
    private static final int ERROR_VERSION = -1;
    private static WorldGetter worldGetter;
    private final TickableConcurrentHashMap<Machine> machines;
    private final TickableConcurrentHashMap<Connection> connections;

    public Factory() {
        this(VERSION);
    }

    public Factory(int version) {
        this(version, Map.of(), Map.of());
    }

    public Factory(int version, Map<UUID, Machine> machines, Map<UUID, Connection> connections) {
        if (version != VERSION)
            MachinesOfMadness.LOGGER.warn("Existing data is of different format version, errors and corruptions may occur!");
        this.machines = new TickableConcurrentHashMap<>(machines);
        this.connections = new TickableConcurrentHashMap<>(connections);
    }

    public static Factory get(MinecraftServer server) {
        worldGetter = server::getWorld;
        return server.getOverworld().getPersistentStateManager().getOrCreate(TYPE, MachinesOfMadness.MOD_ID);
    }

    public static Factory readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        Optional<Factory> result = Factory.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, nbt)).result();
        if (result.isEmpty()) MachinesOfMadness.LOGGER.warn("Failed to parse machines from NBT!");
        return result.orElseGet(() -> new Factory(ERROR_VERSION));
    }

    public static World getWorld(RegistryKey<World> key) {
        return worldGetter.getWorld(key);
    }

    public static void withServerWorld(RegistryKey<World> key, Consumer<ServerWorld> consumer) {
        if (getWorld(key) instanceof ServerWorld world) consumer.accept(world);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Optional<NbtElement> result = CODEC.encodeStart(NbtOps.INSTANCE, this).result();
        if (result.isEmpty()) MachinesOfMadness.LOGGER.warn("Failed to save machines to NBT!");
        return (NbtCompound) result.orElse(nbt);
    }

    private Integer getVersion() {
        return VERSION;
    }

    public TickableConcurrentHashMap<Machine> getMachines() {
        return this.machines;
    }

    public TickableConcurrentHashMap<Connection> getConnections() {
        return this.connections;
    }

    public Optional<Machine> getMachine(UUID id) {
        return Optional.ofNullable(machines.get(id));
    }

    public Optional<Connection> getConnection(UUID id) {
        return Optional.ofNullable(connections.get(id));
    }

    public UUID machineUUID() {
        return MachinesOfMadness.UUID(machines);
    }

    public UUID connectionUUID() {
        return MachinesOfMadness.UUID(connections);
    }

    public void tick() {
        machines.tick();
        connections.tick();
    }
}
