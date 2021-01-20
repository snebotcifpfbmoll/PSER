package com.snebot.fbmoll.graphics;

import java.awt.*;

public class Ball extends VisibleObject implements Runnable {
    private Thread thread = null;
    private volatile boolean animate = false;
    private volatile boolean paused = false;
    public BallDelegate delegate = null;

    public Ball() {
        this.delay = 10;
    }

    /**
     * Start ball thread.
     */
    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this, getClass().getSimpleName());
            this.thread.start();
        } else {
            resume();
        }
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    /**
     * Stop ball thread.
     */
    public void stop() {
        if (this.thread == null) return;
        this.animate = false;
        try {
            this.thread.join(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        this.thread = null;
    }

    @Override
    public void run() {
        this.animate = true;
        while (this.animate && this.delegate != null) {
            try {
                if (!this.paused) {
                    this.color = this.delegate.willTouchBlackHole(this) ? Color.BLUE : Color.red;
                    this.delegate.willBounce(this);
                    this.step();
                }
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.point.x, this.point.y, this.size.width, this.size.height);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Ball)) return false;
        return super.equals(obj);
    }
}
