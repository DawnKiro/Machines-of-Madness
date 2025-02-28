package net.voxelden.machinesOfMadness.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.voxelden.machinesOfMadness.MachinesOfMadness;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class Util {
    public static <A, B> HashMap<B, A> flipMap(HashMap<A, B> map) {
        HashMap<B, A> flipped = new HashMap<>();
        for (Map.Entry<A, B> entry : map.entrySet()) {
            flipped.put(entry.getValue(), entry.getKey());
        }
        return flipped;
    }

    public static <T> NbtCompound writeNbtCodec(NbtCompound nbt, T object, String name, Codec<T> codec) {
        Optional<NbtElement> result = codec.encodeStart(NbtOps.INSTANCE, object).result();
        if (result.isEmpty()) MachinesOfMadness.LOGGER.warn("Failed to write {} to NBT!", name);
        return (NbtCompound) result.orElse(nbt);
    }

    public static <T> T readNbtCodec(NbtCompound nbt, Supplier<T> constructor, String name, Codec<T> codec) {
        Optional<T> result = codec.parse(new Dynamic<>(NbtOps.INSTANCE, nbt)).result();
        if (result.isEmpty()) MachinesOfMadness.LOGGER.warn("Failed to read {} from NBT!", name);
        return result.orElseGet(constructor);
    }
}
