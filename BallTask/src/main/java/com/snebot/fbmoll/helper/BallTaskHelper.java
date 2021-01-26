package com.snebot.fbmoll.helper;

public class BallTaskHelper {
    /**
     * Generate random number with range.
     *
     * @param min Minimum range.
     * @param max Maximum range.
     * @return Random number.
     */
    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }
}
