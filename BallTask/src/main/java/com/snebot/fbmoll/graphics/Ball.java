package com.snebot.fbmoll.graphics;

import java.awt.*;

public class Ball extends VisibleObject implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private volatile boolean animate = false;
    public BallDelegate delegate = null;

    public Ball() {
    }

    /**
     * Start ball thread.
     */
    public void start() {
        this.thread.start();
    }

    /**
     * Stop ball thread.
     */
    public void stop() {
        this.animate = false;
        try {
            this.thread.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        this.animate = true;
        while (this.animate && delegate != null) {
            try {
                if (delegate.ballCanMove(this)) this.step();
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.point.x, this.point.y, this.size.width, this.size.height);
    }
}
