package com.snebot.fbmoll.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ThreadedObject implements Runnable {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Thread thread = null;
    protected volatile boolean run = false;
    protected volatile boolean paused = false;

    public ThreadedObject() {
    }

    /**
     * Start thread.
     */
    public void start() {
        if (this.thread == null) {
            this.thread = new Thread(this, getClass().getSimpleName());
            this.run = true;
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

    public boolean join() {
        return join(-1);
    }

    public boolean join(int millis) {
        if (this.thread == null) return false;
        try {
            if (millis <= 0) {
                this.thread.join();
            } else {
                this.thread.join(millis);
            }
        } catch (Exception e) {
            log.error("failed to join thread: ", e);
            return false;
        }
        return true;
    }

    /**
     * Stop thread.
     */
    public void stop() {
        this.run = false;
        this.thread = null;
    }
}
