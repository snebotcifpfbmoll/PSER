package com.snebot.fbmoll;

import com.snebot.fbmoll.graphic.WallPosition;

public class BallTask1 {
    public static void main(String[] args) {
        BallTask ballTask = new BallTask(3411, "127.0.0.1", 3412);
        ballTask.setWormhole(WallPosition.RIGHT);
    }
}