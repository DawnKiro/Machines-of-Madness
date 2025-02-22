package net.voxelden.machinesOfMadness.factory;

import net.minecraft.server.MinecraftServer;
import net.voxelden.machinesOfMadness.MachinesOfMadness;

import java.util.concurrent.*;

public class FactoryThread implements AutoCloseable {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int TICK_RATE = 1000 / 20;
    private static final int SAVE_DELAY = 6000;
    public static Factory FACTORY;
    private static ScheduledFuture<?> FUTURE;
    private static int saveCooldown = 0;
    private static boolean shouldTick = true;

    public static void start(MinecraftServer server) {
        if (FACTORY == null) FACTORY = Factory.get(server);
        stop();
        FUTURE = EXECUTOR_SERVICE.scheduleAtFixedRate(FactoryThread::tick, TICK_RATE, TICK_RATE, TimeUnit.MILLISECONDS);
    }

    public static void tick() {
        if (shouldTick) FACTORY.tick();
    }

    public static void heartbeat(MinecraftServer server) {
        if (FUTURE != null) {
            Future.State state = FUTURE.state();
            if (state == Future.State.CANCELLED || state == Future.State.FAILED) {
                try {
                    FUTURE.get();
                } catch (Exception e) {
                    MachinesOfMadness.LOGGER.error(e.getMessage());
                }
            }
            shouldTick = server.getTickManager().shouldTick();
            if (saveCooldown >= SAVE_DELAY) save();
            saveCooldown++;
        } else {
            stop();
            start(server);
        }
    }

    public static void save() {
        if (FACTORY != null) FACTORY.setDirty(true);
        saveCooldown = 0;
    }

    public static void stop() {
        if (FUTURE != null) FUTURE.cancel(false);
        FUTURE = null;
        save();
    }

    @Override
    public void close() {
        stop();
        EXECUTOR_SERVICE.close();
    }
}
