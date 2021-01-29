package com.snebot.fbmoll;

import com.snebot.fbmoll.graphic.WallPosition;
import com.snebot.fbmoll.thread.ThreadedObject;

public class MultiWindowBallTask {
    public static void main(String[] args) {
        ThreadedObject b1 = new ThreadedObject() {
            @Override
            public void run() {
                BallTask ballTask = new BallTask(3411, "127.0.0.1", 3412);
                ballTask.setWormhole(WallPosition.RIGHT);
            }
        };
        ThreadedObject b2 = new ThreadedObject() {
            @Override
            public void run() {
                BallTask ballTask = new BallTask(3412, "127.0.0.1", 3411);
                ballTask.setWormhole(WallPosition.LEFT);
            }
        };

        b1.start();
        b2.start();

        b1.join();
        b2.join();
    }
}
