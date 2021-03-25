package com.guava.qps.v2.runnables;

import java.util.concurrent.DelayQueue;

public abstract class DelayRunnable implements Runnable {

    protected DelayQueue delayQueue;

    public DelayRunnable(DelayQueue delayQueue) {
        this.delayQueue = delayQueue;
    }
}
