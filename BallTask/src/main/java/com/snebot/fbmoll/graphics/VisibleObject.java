package com.snebot.fbmoll.graphics;

import java.awt.*;

public abstract class VisibleObject implements Runnable {
    private Thread thread = null;
    public Point point = new Point(0, 0);
    public Dimension size = new Dimension(40, 40);
    public int deltaX = 1;
    public int deltaY = 1;
    protected int delay = 20;
    protected Color color = Color.RED;
    protected volatile boolean animate = false;
    protected volatile boolean paused = false;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Change current object position by current delta.
     */
    public void step() {
        this.point.translate(this.deltaX, this.deltaY);
    }

    /**
     * Check if value is in range.
     *
     * @param num Value to check.
     * @param min Minimun value range.
     * @param max Maximum value range.
     * @return True if in range, false otherwise.
     */
    private boolean inRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    /**
     * Check if touches with an other object.
     *
     * @param object Object to check.
     * @return True if object touches, false otherwise.
     */
    public boolean touches(VisibleObject object) {
        return inBounds(object.point.x, object.point.y, object.size.width, object.size.height);
    }

    /**
     * Check if object is inside of rect.
     *
     * @param x      X position.
     * @param y      Y position.
     * @param width  Rect width.
     * @param height Rect height.
     * @return True if object is in bounds, false otherwise.
     */
    public boolean inBounds(int x, int y, int width, int height) {
        return (inRange(this.point.x, x, x + width) ||
                inRange(this.point.x + this.size.width, x, x + width)) &&
                (inRange(this.point.y, y, y + height) ||
                        inRange(this.point.y + this.size.height, y, y + height));
    }

    /**
     * Paint visible object.
     *
     * @param g Graphics context.
     */
    public abstract void paint(Graphics g);

    /**
     * Start thread.
     */
    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this, getClass().getSimpleName());
            this.thread.start();
        } else {
            resume();
        }
    }

    /**
     * Pause animation.
     */
    public void pause() {
        this.paused = true;
    }

    /**
     * Resume animation.
     */
    public void resume() {
        this.paused = false;
    }

    /**
     * Stop thread.
     */
    public void stop() {
        this.animate = false;
        this.thread = null;
    }
}