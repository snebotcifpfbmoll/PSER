package com.snebot.fbmoll.helper;

import java.io.Closeable;

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

    /**
     * Close a closeable object.
     *
     * @param closeable Closeable object.
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
