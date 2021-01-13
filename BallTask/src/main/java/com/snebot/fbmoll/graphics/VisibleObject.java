package com.snebot.fbmoll.graphics;

import java.awt.*;

public abstract class VisibleObject {
    protected int x = 0;
    protected int y = 0;
    protected Dimension size = new Dimension(40, 40);
    protected int delay = 20;
    protected Color color = Color.RED;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

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
     * Check if value is in range.
     *
     * @param num Value to check.
     * @param min Minimun value range.
     * @param max Maximum value range.
     * @return Boolean.
     */
    private boolean inRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    /**
     * Check if touches with an other object.
     *
     * @param object Object to check.
     * @return Boolean value.
     */
    public boolean touches(VisibleObject object) {
        return (inRange(this.x, object.x, object.x + object.size.width) &&
                inRange(this.y, object.y, object.y + object.size.height)) ||
                (inRange(this.x + this.size.width, object.x, object.x + object.size.width) &&
                        inRange(this.y + this.size.height, object.y, object.y + object.size.height));
    }

    /**
     * Paint visible object.
     *
     * @param g Graphics context.
     */
    public abstract void paint(Graphics g);
}
