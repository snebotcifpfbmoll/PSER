package com.snebot.fbmoll.graphics;

import java.awt.*;

public class Ball extends VisibleObject implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private int vx = 1;
    private int vy = 1;
    private volatile boolean animate = false;

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public Ball() {
    }

    /**
     * Start ball thread.
     */
    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        this.animate = true;
        while (this.animate) {
            try {
                this.x += this.vx;
                this.y += this.vy;
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.x, this.y, this.size.width, this.size.height);
    }
}
