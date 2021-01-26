package com.snebot.fbmoll.graphic;

import com.snebot.fbmoll.thread.ThreadedObject;

import java.awt.*;

public abstract class VisibleObject extends ThreadedObject {
    public Point point = new Point(0, 0);
    public Dimension size = new Dimension(40, 40);
    public int deltaX = 1;
    public int deltaY = 1;
    protected int delay = 20;
    protected Color color = Color.RED;

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

    public VisibleObject() {
    }

    public VisibleObject(int width, int height) {
        this.size.width = width;
        this.size.height = height;
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
     * Determine if an object intersects with an other.
     *
     * @param object VisibleObject to check intersection.
     * @return True on intersection, false otherwise.
     */
    public boolean intersects(VisibleObject object) {
        return intersects(object.point.x, object.point.y, object.size.width, object.size.height);
    }

    /**
     * Determine if an object intersects with a rect.
     *
     * @param x X position of rect.
     * @param y Y position of rect.
     * @param width Width of rect.
     * @param height Height of rect.
     * @return True on intersection, false otherwise.
     */
    public boolean intersects(int x, int y, int width, int height) {
        int tw = this.size.width;
        int th = this.size.height;
        int rw = width;
        int rh = height;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) return false;
        int tx = this.point.x;
        int ty = this.point.y;
        rw += x;
        rh += y;
        tw += tx;
        th += ty;
        return ((rw < x || rw > tx) &&
                (rh < y || rh > ty) &&
                (tw < tx || tw > x) &&
                (th < ty || th > y));
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
}