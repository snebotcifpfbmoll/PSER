package com.snebot.fbmoll.data;

public class Statistics {
    private int ballCount = 0;
    private int outsideBallCount = 0;
    private int insideBallCount = 0;
    private int stoppedBallCount = 0;

    public int getBallCount() {
        return ballCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public int getOutsideBallCount() {
        return outsideBallCount;
    }

    public void setOutsideBallCount(int outsideBallCount) {
        this.outsideBallCount = outsideBallCount;
    }

    public int getInsideBallCount() {
        return insideBallCount;
    }

    public void setInsideBallCount(int insideBallCount) {
        this.insideBallCount = insideBallCount;
    }

    public int getStoppedBallCount() {
        return stoppedBallCount;
    }

    public void setStoppedBallCount(int stoppedBallCount) {
        this.stoppedBallCount = stoppedBallCount;
    }

    public Statistics() {
    }

    public Statistics(int ballCount, int outsideBallCount, int insideBallCount, int stoppedBallCount) {
        this.ballCount = ballCount;
        this.outsideBallCount = outsideBallCount;
        this.insideBallCount = insideBallCount;
        this.stoppedBallCount = stoppedBallCount;
    }
}
