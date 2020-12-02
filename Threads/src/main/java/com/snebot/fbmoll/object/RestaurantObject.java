package com.snebot.fbmoll.object;

import java.awt.*;
import java.util.Random;

public class RestaurantObject implements Runnable {
    private final Thread thread = new Thread(this);
    private final Random random = new Random();
    private RestaurantObjectRoutine routine = null;
    protected volatile boolean running = true;

    public int x = 0;
    public int y = 0;
    public int width = 10;
    public int height = 10;
    public int minTime = 2000;
    public int maxTime = 9000;
    public int speed = 10;
    public int step = 1;
    public Color color = Color.WHITE;

    public int getRandomTime() {
        return random.nextInt((maxTime + 1 - minTime) + minTime);
    }

    public void start(RestaurantObjectRoutine routine) {
        this.routine = routine;
        thread.start();
    }

    public void stop() {
        running = false;
    }

    public void interrupt() {
        running = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        running = true;
        while (running && routine != null) {
            routine.routine();
        }
    }
}
