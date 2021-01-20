package com.snebot.fbmoll.graphics;

import java.awt.*;

public class Ball extends VisibleObject implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private volatile boolean animate = false;
    public BallDelegate delegate = null;

    public Ball() {
        this.delay = 10;
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
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.animate = true;
        while (this.animate && this.delegate != null) {
            try {
                this.color = this.delegate.willTouchBlackHole(this) ? Color.BLUE : Color.red;
                this.delegate.willBounce(this);
                this.step();
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
        Ball ball = (Ball) obj;
        return this.thread.equals(ball.thread) &&
                this.delegate.equals(ball.delegate);
    }
}
