package net.voxelden.machinesOfMadness.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;
import net.voxelden.machinesOfMadness.factory.FactoryThread;
import net.voxelden.machinesOfMadness.machine.MachineRegistry;
import net.voxelden.machinesOfMadness.util.Position;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MachineBlockEntity extends BlockEntity {
    public static final List<Block> machineBlocks = new ArrayList<>();
    private static final String nbtPathUUID = "id";
    private static final String nbtPathType = "type";
    private static final String nbtPathData = "data";
    public static BlockEntityType<MachineBlockEntity> INSTANCE;
    private UUID uuid;
    private Identifier type;
    private NbtCompound data;

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        super(INSTANCE, pos, state);
    }

    public static void register() {
        INSTANCE = Registry.register(Registries.BLOCK_ENTITY_TYPE, MachinesOfMadness.id("machine"), FabricBlockEntityTypeBuilder.create(MachineBlockEntity::new, machineBlocks.toArray(new Block[0])).build());
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound compound = new NbtCompound();
        if (world != null) {
            ChunkAttachment.getMachineID(Position.of(world, pos)).ifPresent(uuid -> FactoryThread.FACTORY.getMachine(uuid).ifPresent(machine -> {
                Identifier type = MachineRegistry.REGISTRY.getId(machine.codec());
                if (type != null) {
                    compound.putUuid(nbtPathUUID, uuid);
                    compound.putString(nbtPathType, type.toString());
                    compound.put(nbtPathData, machine.getSyncedDataCompound(new NbtCompound()));
                }
            }));
        }
        return compound;
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.containsUuid(nbtPathUUID)) uuid = nbt.getUuid(nbtPathUUID);
        if (nbt.contains(nbtPathType, NbtElement.STRING_TYPE)) type = Identifier.tryParse(nbt.getString(nbtPathType));
        if (nbt.contains(nbtPathData, NbtElement.COMPOUND_TYPE)) data = nbt.getCompound(nbtPathData);
    }

    public UUID getMachineUUID() {
        return uuid;
    }

    public Identifier getMachineType() {
        return type;
    }

    public NbtCompound getMachineData() {
        return data;
    }

    public boolean isRenderable() {
        return uuid != null && type != null && data != null;
    }
}
