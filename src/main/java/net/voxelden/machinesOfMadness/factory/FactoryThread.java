package net.voxelden.machinesOfMadness.factory;

import net.minecraft.server.MinecraftServer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FactoryThread implements AutoCloseable {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int TICK_RATE = 1000 / 20;
    public static Factory FACTORIES;
    private static ScheduledFuture<?> FUTURE;

    public static void start(MinecraftServer server) {
        FACTORIES = Factory.get(server);
        FUTURE = EXECUTOR_SERVICE.scheduleAtFixedRate(FactoryThread::tick, TICK_RATE, TICK_RATE, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        FUTURE.cancel(false);
        FACTORIES.setDirty(true);
    }

    public static void tick() {
        FACTORIES.tick();
    }

    @Override
    public void close() {
        stop();
        EXECUTOR_SERVICE.close();
    }
}
