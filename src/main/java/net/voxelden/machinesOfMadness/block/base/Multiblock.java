package net.voxelden.machinesOfMadness.block.base;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;

public interface Multiblock {
    Settings getMultiblockSettings();

    record Settings(TagKey<Block> connecting, Vec3i minSize, Vec3i maxSize) {
        public static Settings of(Identifier connecting, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
            return new Settings(TagKey.of(RegistryKeys.BLOCK, connecting.withPrefixedPath("multiblock_connecting/")), new Vec3i(minX, minY, minZ), new Vec3i(maxX, maxY, maxZ));
        }
    }
}
