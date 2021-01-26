package com.snebot.fbmoll.graphic;

public interface BallDelegate {
    /**
     * Determine if ball will touch a black hole.
     *
     * @param ball Ball to check.
     * @return True on touch, false otherwise.
     */
    boolean willTouchBlackHole(Ball ball);
    /**
     * Determine if a given ball will bounce.
     *
     * @param ball Ball to check.
     */
    void willBounce(Ball ball);
}
