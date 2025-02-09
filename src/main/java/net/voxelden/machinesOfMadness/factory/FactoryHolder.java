package net.voxelden.machinesOfMadness.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Uuids;
import net.minecraft.world.PersistentState;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.util.TickableConcurrentHashMap;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FactoryHolder extends PersistentState {
    private static final int VERSION = 1;
    public static final Codec<FactoryHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("version").forGetter(FactoryHolder::getVersion),
            Codec.unboundedMap(Uuids.STRING_CODEC, Factory.CODEC).fieldOf("factories").forGetter(FactoryHolder::getFactories)
    ).apply(instance, FactoryHolder::new));
    private static final int ERROR_VERSION = -1;
    public final TickableConcurrentHashMap<Factory> factories;

    public FactoryHolder() {
        this(VERSION);
    }

    public FactoryHolder(int version) {
        this(version, Map.of());
    }

    public FactoryHolder(int version, Map<UUID, Factory> factories) {
        if (version != VERSION)
            MachinesOfMadness.LOGGER.warn("Existing data is of different format version, errors and corruptions may occur!");
        this.factories = new TickableConcurrentHashMap<>(factories);
    }

    public static FactoryHolder get(MinecraftServer server) {
        return server.getOverworld().getPersistentStateManager().getOrCreate(FactoryHolder::readNbt, FactoryHolder::new, MachinesOfMadness.MOD_ID);
    }

    public static FactoryHolder readNbt(NbtCompound nbt) {
        Optional<FactoryHolder> result = FactoryHolder.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, nbt)).result();
        if (result.isEmpty()) MachinesOfMadness.LOGGER.warn("Failed to parse machines from NBT!");
        return result.orElseGet(() -> new FactoryHolder(ERROR_VERSION));
    }

    private Integer getVersion() {
        return VERSION;
    }

    private TickableConcurrentHashMap<Factory> getFactories() {
        return this.factories;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        Optional<NbtElement> result = CODEC.encodeStart(NbtOps.INSTANCE, this).result();
        if (result.isEmpty()) MachinesOfMadness.LOGGER.warn("Failed to save machines to NBT!");
        return (NbtCompound) result.orElse(nbt);
    }

    public void tick() {
        factories.tick();
    }
}
