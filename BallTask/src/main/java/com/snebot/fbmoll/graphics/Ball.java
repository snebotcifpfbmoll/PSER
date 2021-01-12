package com.snebot.fbmoll.graphics;

import java.awt.*;

public class Ball extends VisibleObject implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private float vx = 0.f;
    private float vy = 0.f;

    public Ball() {}

    /**
     * Start ball thread.
     */
    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
}
