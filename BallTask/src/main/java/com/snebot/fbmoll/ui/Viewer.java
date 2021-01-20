package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.graphics.VisibleObject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Viewer extends JComponent implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private List<VisibleObject> balls = null;
    private List<VisibleObject> blackHoles = null;
    private volatile boolean running = false;
    private int delay = 16;

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
        super();
        setIgnoreRepaint(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.blackHoles.forEach(blackHole -> {
            blackHole.paint(g);
        });
        this.balls.forEach(ball -> {
            ball.paint(g);
        });
    }

    /**
     * Start viewer thread.
     */
    public void start() {
        this.thread.start();
    }

    /**
     * Stop viewer thread.
     */
    public void stop() {
        this.running = false;
        try {
            this.thread.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.running = true;
        while (this.running) {
            try {
                repaint();
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}