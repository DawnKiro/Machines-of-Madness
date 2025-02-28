package net.voxelden.machinesOfMadness.pollution;

import com.mojang.serialization.Codec;

import java.nio.ByteBuffer;

public class PollutionRegion {
    public static final Codec<PollutionRegion> CODEC = Codec.BYTE_BUFFER.xmap(PollutionRegion::new, PollutionRegion::toBuffer);

    public PollutionRegion(ByteBuffer buffer) {

    }

    private ByteBuffer toBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        return buffer;
    }
}
