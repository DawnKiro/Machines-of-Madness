package net.voxelden.machinesOfMadness.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.voxelden.machinesOfMadness.MachinesOfMadness;
import net.voxelden.machinesOfMadness.chunk.ChunkAttachment;
import net.voxelden.machinesOfMadness.factory.FactoryThread;
import net.voxelden.machinesOfMadness.factory.machine.MachineRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MachineBlockEntity extends BlockEntity {
    public static final List<Block> machineBlocks = new ArrayList<>();
    public static BlockEntityType<MachineBlockEntity> INSTANCE;

    public MachineBlockEntity(BlockPos pos, BlockState state) {
        super(INSTANCE, pos, state);
    }

    public static void register() {
        INSTANCE = Registry.register(Registries.BLOCK_ENTITY_TYPE, MachinesOfMadness.id("machine"), BlockEntityType.Builder.create(MachineBlockEntity::new, machineBlocks.toArray(new Block[0])).build(null));
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound compound = new NbtCompound();
        ChunkAttachment.getMachineID(world, pos).ifPresent(uuid -> FactoryThread.FACTORIES.getMachine(uuid)
            .ifPresent(machine -> {
                Identifier type = MachineRegistry.REGISTRY.getId(machine.codec());
                if (type != null) {
                    compound.putUuid("id", uuid);
                    compound.putString("type", type.toString());
                    compound.put("data", machine.getSyncedDataCompound());
                }
            })
        );
        return compound;
    }
}
