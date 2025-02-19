package net.voxelden.machinesOfMadness.util;

import net.minecraft.nbt.NbtCompound;

public interface NbtSyncable {
    NbtCompound getSyncedDataCompound(NbtCompound data);

    boolean needsNbtSync();

    void markNbtDirty();

    void syncNbt();
}
