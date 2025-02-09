package net.voxelden.machinesOfMadness.factory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FactoryThread {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final int TICK_RATE = 1000 / 20;

    public static final FactoryHolder FACTORIES = new FactoryHolder();

    public static void start() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(FactoryThread::tick, TICK_RATE, TICK_RATE, TimeUnit.MILLISECONDS);
    }

    public static void stop() {
        EXECUTOR_SERVICE.shutdown();
    }

    public static void tick() {
        System.out.println("Tick at " + System.currentTimeMillis());
    }
}
