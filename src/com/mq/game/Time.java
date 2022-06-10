package com.mq.game;

public class Time {
    public static final double TARGET_TPS = 60;
    public static final long TARGET_FPS = 60;
    public static final long NS_PER_SECOND = 1000000000;
    public static final long NS_PER_MS = 1000000;
    public static final double NS_PER_TICK = NS_PER_SECOND / TARGET_TPS;

    public static long currentTime(){
        return System.nanoTime();
    }
}
