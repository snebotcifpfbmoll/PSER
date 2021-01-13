package com.snebot.fbmoll.graphics;

import java.awt.*;

public abstract class VisibleObject {
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
        return (inRange(this.point.x, object.point.x, object.point.x + object.size.width) ||
                inRange(this.point.x + this.size.width, object.point.x, object.point.x + object.size.width)) &&
                        (inRange(this.point.y, object.point.y, object.point.y + object.size.height) ||
                inRange(this.point.y + this.size.height, object.point.y, object.point.y + object.size.height));
    }

    /**
     * Paint visible object.
     *
     * @param g Graphics context.
     */
    public abstract void paint(Graphics g);
}
