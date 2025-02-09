package net.voxelden.machinesOfMadness.factory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.voxelden.machinesOfMadness.MachinesOfMadness;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryHolder extends PersistentState {
    private static final int VERSION = 1;
    private static final int ERROR_VERSION = -1;

    public static final Codec<FactoryHolder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("version").forGetter(FactoryHolder::getVersion),
            Codec.unboundedMap(Codec.STRING, Factory.CODEC).fieldOf("factories").forGetter(FactoryHolder::getFactories)
    ).apply(instance, FactoryHolder::new));

    private static final Type<FactoryHolder> TYPE = new Type<>(FactoryHolder::new, FactoryHolder::createFromNbt, null);
    public final ConcurrentHashMap<String, Factory> factories;

    public FactoryHolder() {
        this(VERSION);
    }

    public FactoryHolder(int version) {
        this(version, Map.of());
    }

    public FactoryHolder(int version, Map<String, Factory> factories) {
        if (version != VERSION)
            MachinesOfMadness.LOGGER.warn("Existing data is of different format version, errors and corruptions may occur!");
        this.factories = new ConcurrentHashMap<>(factories);
    }

    public static FactoryHolder get(MinecraftServer server) {
        return server.getOverworld().getPersistentStateManager().getOrCreate(TYPE, MachinesOfMadness.MOD_ID);
    }

    private Integer getVersion() {
        return VERSION;
    }

    private Map<String, Factory> getFactories() {
        return this.factories;
    }

    public static FactoryHolder createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        DataResult<FactoryHolder> result = FactoryHolder.CODEC.parse(registryLookup.getOps(NbtOps.INSTANCE), nbt);

        if (result.isSuccess()) return result.getOrThrow();
        else {
            MachinesOfMadness.LOGGER.warn("Failed to parse machines from NBT!");
            return new FactoryHolder(ERROR_VERSION);
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        DataResult<NbtElement> result = CODEC.encodeStart(registryLookup.getOps(NbtOps.INSTANCE), this);

        if (result.isSuccess() && result.getOrThrow() instanceof NbtCompound compound) return compound;
        else {
            MachinesOfMadness.LOGGER.warn("Failed to save machines to NBT!");
            return nbt;
        }
    }
}
