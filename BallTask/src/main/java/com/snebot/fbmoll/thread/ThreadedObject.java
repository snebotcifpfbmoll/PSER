package com.snebot.fbmoll.thread;

public abstract class ThreadedObject implements Runnable {
    private Thread thread = null;
    protected volatile boolean run = false;
    protected volatile boolean paused = false;

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
        this.run = false;
        this.thread = null;
    }
}
