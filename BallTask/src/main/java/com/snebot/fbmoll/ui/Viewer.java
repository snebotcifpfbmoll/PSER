package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.graphics.Ball;
import com.snebot.fbmoll.graphics.VisibleObject;

import java.awt.*;
import java.util.List;

public class Viewer extends Canvas implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private int vwidth = 0;
    private int vheight = 0;
    private List<VisibleObject> balls = null;
    private List<VisibleObject> blackHoles = null;
    private volatile boolean running = false;
    private int delay = 40;

    public int getVWidth() {
        return vwidth;
    }

    public void setVWidth(int vwidth) {
        this.vwidth = vwidth;
    }

    public int getVHeight() {
        return vheight;
    }

    public void setVHeight(int vheight) {
        this.vheight = vheight;
    }

    public List<VisibleObject> getBalls() {
        return balls;
    }

    public void setBalls(List<VisibleObject> balls) {
        this.balls = balls;
    }

    public List<VisibleObject> getBlackHoles() {
        return blackHoles;
    }

    public void setBlackHoles(List<VisibleObject> blackHoles) {
        this.blackHoles = blackHoles;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public Viewer() {
    }

    private void paint() {
        Graphics g = getGraphics();
        if (g == null) return;
        this.balls.forEach(ball -> {
            ball.paint(g);
        });
        this.blackHoles.forEach(blackHole -> {
            blackHole.paint(g);
        });
    }

    public void start() {
        this.thread.start();
    }

    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            try {
                paint();
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.vwidth, this.vheight);
    }
}