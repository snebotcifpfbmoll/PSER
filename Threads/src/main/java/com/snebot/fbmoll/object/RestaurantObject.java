package com.snebot.fbmoll.object;

import java.awt.*;
import java.util.Random;

public class RestaurantObject implements Runnable {
    private final Thread thread = new Thread(this);
    private RestaurantObjectRoutine routine = null;
    protected volatile boolean running = true;
    public int x = 0;
    public int y = 0;
    public int width = 10;
    public int height = 10;
    public int minTime = 5000;
    public int maxTime = 9000;
    public Color color = Color.WHITE;

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
        Random random = new Random();
        while (running && routine != null) {
            try {
                Thread.sleep(random.nextInt(maxTime + 1 - minTime) + minTime);
                routine.routine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
