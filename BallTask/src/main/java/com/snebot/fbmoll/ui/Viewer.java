package com.snebot.fbmoll.ui;

import javax.swing.*;
import java.awt.*;

public class Viewer extends JComponent implements Runnable {
    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private ViewerDelegate delegate = null;
    private volatile boolean running = false;
    private int delay = 16;

    public ViewerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ViewerDelegate delegate) {
        this.delegate = delegate;
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

    public Viewer(ViewerDelegate delegate) {
        super();
        this.delegate = delegate;
        setIgnoreRepaint(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.delegate != null) {
            this.delegate.getVisibleObjects().forEach(obj -> {
                obj.paint(g);
            });
        }
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
            this.thread.join(1000);
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