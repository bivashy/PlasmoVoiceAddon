package com.ubivashka.plasmovoice.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class SelfCancellableScheduledTask implements Runnable {
    private final ScheduledExecutorService scheduledExecutorService;

    public SelfCancellableScheduledTask(ScheduledExecutorService scheduledExecutorService) {
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public SelfCancellableScheduledTask() {
        this(Executors.newSingleThreadScheduledExecutor());
    }

    public void scheduleAtFixedRate(long initialDelay, long period, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleAtFixedRate(this, initialDelay, period, timeUnit);
    }

    public void scheduleWithFixedDelay(long initialDelay, long period, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleWithFixedDelay(this, initialDelay, period, timeUnit);
    }


    public void schedule(long initialDelay, TimeUnit timeUnit) {
        scheduledExecutorService.schedule(this, initialDelay, timeUnit);
    }


    public void cancel() {
        scheduledExecutorService.shutdown();
    }
}
