package net.voxelden.machinesOfMadness.pollution;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.PersistentState;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.util.Util;

import java.util.HashMap;
import java.util.Map;

public class Pollution extends PersistentState {
    private static final String NAME = "pollution";
    private static final Codec<Pollution> CODEC = Codec.unboundedMap(ChunkPos.CODEC, PollutionRegion.CODEC).xmap(Pollution::new, Pollution::getRegions);
    public static Pollution INSTANCE;
    protected final Map<ChunkPos, PollutionRegion> regions;

    private Pollution() {
        this(new HashMap<>());
    }

    private Pollution(Map<ChunkPos, PollutionRegion> regions) {
        this.regions = regions;
    }

    private static final PersistentState.Type<Pollution> TYPE = new PersistentState.Type<>(Pollution::new, Pollution::readNbt, null);

    public static void open(MinecraftServer server) {
        INSTANCE = server.getOverworld().getPersistentStateManager().getOrCreate(TYPE, MachinesOfMadness.MOD_ID + "_pollution");
    }

    private static Pollution readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        return Util.readNbtCodec(nbt, Pollution::new, NAME, CODEC);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        return Util.writeNbtCodec(nbt, this, NAME, CODEC);
    }

    private Map<ChunkPos, PollutionRegion> getRegions() {
        return regions;
    }
}
