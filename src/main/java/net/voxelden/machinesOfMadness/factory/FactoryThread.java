package net.voxelden.machinesOfMadness.factory;

import net.minecraft.server.MinecraftServer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FactoryThread implements AutoCloseable {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int TICK_RATE = 1000 / 20;

    public static Factory FACTORIES;

    public static void start(MinecraftServer server) {
        FACTORIES = Factory.get(server);
        EXECUTOR_SERVICE.scheduleAtFixedRate(FactoryThread::tick, TICK_RATE, TICK_RATE, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        EXECUTOR_SERVICE.shutdown();
    }

    @Override
    public void close() {
        stop();
    }

    public static void tick() {
        FACTORIES.tick();
    }
}
