package com.snebot.fbmoll.data;

public class Statistics {
    public int ballCount = 0;
    public int outsideBallCount = 0;
    public int insideBallCount = 0;
    public int stoppedBallCount = 0;

    public Statistics() {
    }

    public Statistics(int ballCount, int outsideBallCount, int insideBallCount, int stoppedBallCount) {
        this.ballCount = ballCount;
        this.outsideBallCount = outsideBallCount;
        this.insideBallCount = insideBallCount;
        this.stoppedBallCount = stoppedBallCount;
    }

    public String[][] toArray() {
        return new String[][]{{"Ball count", String.valueOf(ballCount)},
                {"Outside ball count", String.valueOf(outsideBallCount)},
                {"Inside ball count", String.valueOf(insideBallCount)},
                {"Stopped ball count", String.valueOf(stoppedBallCount)}};
    }
}
