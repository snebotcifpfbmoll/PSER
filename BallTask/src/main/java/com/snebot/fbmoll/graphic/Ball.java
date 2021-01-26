package com.snebot.fbmoll.graphic;

import java.awt.*;

public class Ball extends VisibleObject {
    private Thread thread = null;
    public BallDelegate delegate = null;
    public volatile boolean stopped = false;

    public Ball() {
        this.delay = 10;
    }

    @Override
    public void run() {
        this.run = true;
        while (this.run && this.delegate != null) {
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
