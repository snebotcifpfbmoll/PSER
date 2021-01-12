package com.snebot.fbmoll.graphics;

import java.awt.*;

public class BlackHole extends VisibleObject implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private Ball ball = null;

    public BlackHole() {
        this.width = 200;
        this.height = 100;
        this.color = Color.BLACK;
    }

    /**
     * Put a ball that's crossing the black hole.
     *
     * @param ball Crossing ball.
     */
    public synchronized void put(Ball ball) {}

    /**
     * Remove a ball that has crossed the black hole.
     *
     * @param ball Crossed ball.
     */
    public synchronized void remove(Ball ball) {}

    /**
     * Start black hole thread.
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
        g.fillRect(this.x, this.y, this.width, this.height);
    }
}