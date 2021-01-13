package com.snebot.fbmoll.graphics;

public interface BallDelegate {
    /**
     * Determine if a given ball can move to its next position.
     *
     * @param ball Ball to test.
     * @return True if the ball can move, false otherwise.
     */
    boolean ballCanMove(Ball ball);
}
