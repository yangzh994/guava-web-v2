package com.guava.qps.v2.dealy;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Message implements Delayed {

    private Integer task;
    private Integer time;
    private long runTime;

    public Message(Integer task, Integer time) {
        this.task = task;
        this.time = time;
        this.runTime = System.nanoTime() + TimeUnit.NANOSECONDS.convert(time, TimeUnit.SECONDS);
    }

    public Integer getTask() {
        return task;
    }

    public void setTask(Integer task) {
        this.task = task;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return runTime - System.nanoTime();
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Message{" +
                "task=" + task +
                ", time=" + time +
                ", runTime=" + runTime +
                '}';
    }
}
