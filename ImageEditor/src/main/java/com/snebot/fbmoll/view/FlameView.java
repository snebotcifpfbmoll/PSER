package com.snebot.fbmoll.view;

import com.snebot.fbmoll.view.fire.Flame;

import java.awt.*;

public class FlameView extends Canvas implements Runnable {
    private Flame flame = null;
    private int speed = 0;
    public boolean animate = true;

    public Flame getFlame() {
        return flame;
    }

    public void setFlame(Flame flame) {
        this.flame = flame;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public FlameView() {
        super();
    }

    public FlameView(Flame flame, int speed) {
        super();
        this.flame = flame;
        this.speed = speed;
    }

    private void paint() {
        Graphics g = this.getGraphics();
        if (g == null || flame == null) return;
        Image flameImage = flame.process();
        g.drawImage(flameImage, 0, 0, null);
    }

    @Override
    public void run() {
        while (this.animate) {
            try {
                this.paint();
                Thread.sleep(this.speed);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (flame != null) return new Dimension(flame.getWidth(), flame.getHeight());
        return super.getPreferredSize();
    }
}
