package net.voxelden.machinesOfMadness.block;

import net.minecraft.util.StringIdentifiable;

public enum PipeConnection implements StringIdentifiable {
    NONE("none", false),
    NORMAL("normal", true),
    PUSH("push", true),
    PULL("pull", true);

    private final String name;
    private final boolean isConnected;

    PipeConnection(String name, boolean isConnected) {
        this.name = name;
        this.isConnected = isConnected;
    }

    public String toString() {
        return this.asString();
    }

    @Override
    public String asString() {
        return this.name;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
