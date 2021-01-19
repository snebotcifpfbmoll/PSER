package com.snebot.fbmoll.graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlackHole extends VisibleObject implements Runnable {
    private static final int MAX_BALL_COUNT = 1;
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private final List<Ball> balls = new ArrayList<>();

    public BlackHole() {
        this.size.width = 200;
        this.size.height = 100;
        this.color = Color.BLACK;
    }

    /**
     * Put a ball that's crossing the black hole.
     *
     * @param ball Crossing ball.
     */
    public synchronized void put(Ball ball) {
        if (checkBall(ball)) return;
        while (balls.size() >= MAX_BALL_COUNT) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        this.balls.add(ball);
        notifyAll();
    }

    /**
     * Remove a ball that has crossed the black hole.
     *
     * @param ball Crossed ball.
     */
    public synchronized void remove(Ball ball) {
        this.balls.remove(ball);
        notifyAll();
    }

    /**
     * Check if ball is inside black hole.
     *
     * @param ball Ball to check.
     * @return True if ball is inside, false otherwise.
     */
    public boolean checkBall(Ball ball) {
        for (Ball item : this.balls) if (ball.equals(item)) return true;
        return false;
    }

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
        g.fillRect(this.point.x, this.point.y, this.size.width, this.size.height);
    }
}