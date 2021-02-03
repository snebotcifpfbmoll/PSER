package com.snebot.fbmoll;

import com.snebot.fbmoll.graphic.WallPosition;

public class BallTask2 {
    public static void main(String[] args) {
        BallTask ballTask = new BallTask(3412, "127.0.0.1", 3411);
        ballTask.setWormhole(WallPosition.LEFT);
    }
}