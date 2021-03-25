package com.guava.qps.v2.producer;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class Producer {


    public static void send(DelayQueue delayQueue, Delayed delayed) {
        delayQueue.offer(delayed);
    }
}
