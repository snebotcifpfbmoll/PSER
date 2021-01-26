package com.snebot.fbmoll.graphic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlackHole extends VisibleObject {
    private static final int MAX_BALL_COUNT = 1;
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
        ball.stopped = true;
        while (balls.size() >= MAX_BALL_COUNT) {
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        ball.stopped = false;
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
     * Remove all balls that are crossing the black hole.
     */
    public synchronized void removeAll() {
        this.balls.clear();
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
     * Get amount of balls inside black hole.
     *
     * @return Ball count.
     */
    public int ballCount() {
        return this.balls.size();
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