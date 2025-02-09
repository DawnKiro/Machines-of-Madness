package net.voxelden.machinesOfMadness.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.util.Uuids;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TickableConcurrentHashMap<T extends Tickable> extends ConcurrentHashMap<UUID, T> {
    public TickableConcurrentHashMap(Map<UUID, T> factories) {
        super(factories);
    }

    public static <C> UnboundedMapCodec<UUID, C> codecOf(Codec<C> codec) {
        return Codec.unboundedMap(Uuids.STRING_CODEC, codec);
    }

    public void tick() {
        forEach((id, tickable) -> tickable.tick());
    }
}
