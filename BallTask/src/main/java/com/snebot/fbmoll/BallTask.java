package com.snebot.fbmoll;

import com.snebot.fbmoll.data.Statistics;
import com.snebot.fbmoll.graphic.*;
import com.snebot.fbmoll.helper.BallTaskHelper;
import com.snebot.fbmoll.ui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BallTask extends JFrame implements BallDelegate, ControlPanelDelegate, ViewerDelegate {
    private static final int VIEW_WIDTH = 1200;
    private static final int VIEW_HEIGHT = 600;
    private static final int MIN_BALL_COUNT = 15;
    private static final int MAX_BALL_COUNT = 20;
    private static final int BALL_SPEED = 5;
    private static final int MIN_BALL_SPEED = -BALL_SPEED;
    private static final int MAX_BALL_SPEED = BALL_SPEED;
    private static final int BLACK_HOLE_COUNT = 2;
    private static final int[][] BLACK_HOLE_COORDS = new int[BLACK_HOLE_COUNT][2];

    static {
        BLACK_HOLE_COORDS[0][0] = 100;
        BLACK_HOLE_COORDS[0][1] = 0;
        BLACK_HOLE_COORDS[1][0] = 800;
        BLACK_HOLE_COORDS[1][1] = 300;
    }

    private final Viewer viewer = new Viewer(this);
    private final ControlPanel controlPanel = new ControlPanel(150, VIEW_HEIGHT);
    private final List<VisibleObject> balls = new ArrayList<>();
    private final List<VisibleObject> blackHoles = new ArrayList<>();

    public BallTask() {
        setupUI();
        this.viewer.start();
    }

    /**
     * Setup UI and make frame visible.
     */
    private void setupUI() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 1.0;
        this.controlPanel.setDelegate(this);
        pane.add(this.controlPanel, constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.9;
        pane.add(this.viewer, constraints);

        pack();
        setVisible(true);
    }

    /**
     * Setup black holes in a predetermined configuration.
     */
    void setupBlackHoles() {
        for (int i = 0; i < BLACK_HOLE_COUNT; i++) {
            BlackHole blackHole = new BlackHole();
            blackHole.point.x = BLACK_HOLE_COORDS[i][0];
            blackHole.point.y = BLACK_HOLE_COORDS[i][1];
            this.blackHoles.add(blackHole);
        }
    }

    /**
     * Generate a random delta within range that's non zero.
     *
     * @param min Minimum value.
     * @param max Maximum value.
     * @return Non zero delta.
     */
    public int nonZeroDelta(int min, int max) {
        int result = 0;
        do {
            result = BallTaskHelper.random(min, max);
        } while (result == 0);
        return result;
    }

    /**
     * Generate an amount of balls.
     *
     * @param list  Ball list.
     * @param count Ball count.
     */
    public void generateBall(List<VisibleObject> list, int count) {
        for (int i = 0; i < count; i++) {
            Ball ball = new Ball();
            ball.delegate = this;
            ball.point.x = BallTaskHelper.random(0, this.viewer.getWidth() - ball.size.width);
            ball.point.y = BallTaskHelper.random(0, this.viewer.getHeight() - ball.size.height);
            ball.deltaX = nonZeroDelta(MIN_BALL_SPEED, MAX_BALL_SPEED);
            ball.deltaY = nonZeroDelta(MIN_BALL_SPEED, MAX_BALL_SPEED);
            list.add(ball);
        }
    }

    @Override
    public List<VisibleObject> getVisibleObjects() {
        return Stream.concat(this.blackHoles.stream(), this.balls.stream()).collect(Collectors.toList());
    }

    @Override
    public Statistics getStatistics() {
        Statistics stats = new Statistics();
        stats.ballCount = this.balls.size();
        this.blackHoles.forEach(obj -> {
            if (obj instanceof BlackHole) {
                BlackHole hole = (BlackHole) obj;
                stats.insideBallCount += hole.ballCount();
            }
        });
        stats.outsideBallCount = stats.ballCount - stats.insideBallCount;
        for (VisibleObject obj : this.balls) {
            if (obj instanceof Ball) {
                Ball ball = (Ball) obj;
                if (ball.stopped) stats.stoppedBallCount += 1;
            }
        }
        return stats;
    }

    public boolean willTouchBlackHole(Ball ball) {
        boolean touch = false;
        for (int i = 0; i < this.blackHoles.size() && !touch; i++) {
            BlackHole blackHole = (BlackHole) this.blackHoles.get(i);
            touch = ball.intersects(blackHole);
            boolean inside = blackHole.checkBall(ball);
            if (touch) blackHole.put(ball);
            if (!touch && inside) blackHole.remove(ball);
        }
        return touch;
    }

    @Override
    public boolean canMove(Ball ball) {
        boolean move = false;
        boolean inside = false;
        for (int i = 0; i < this.blackHoles.size() && !move; i++) {
            BlackHole blackHole = (BlackHole) this.blackHoles.get(i);
            move = ball.intersects(blackHole);
            inside = blackHole.checkBall(ball);
            if (move) blackHole.put(ball);
            if (!move && inside) blackHole.remove(ball);
        }
        ball.setColor(inside ? Color.BLUE : Color.RED);
        ball.bounce(detectWall(ball));
        return move;
    }

    public WallPosition detectWall(Ball ball) {
        if (ball.intersects(0, 0, this.viewer.getWidth(), 1)) {
            return WallPosition.TOP;
        } else if (ball.intersects(0, this.viewer.getHeight(), this.viewer.getWidth(), 1)) {
            return WallPosition.BOTTOM;
        } else if (ball.intersects(0, 0, 1, this.viewer.getHeight())) {
            return WallPosition.LEFT;
        } else if (ball.intersects(this.viewer.getWidth(), 0, 1, this.viewer.getHeight())) {
            return WallPosition.RIGHT;
        }
        return WallPosition.NONE;
    }

    public void play() {
        if (this.blackHoles.isEmpty()) setupBlackHoles();
        if (this.balls.isEmpty()) generateBall(this.balls, BallTaskHelper.random(MIN_BALL_COUNT, MAX_BALL_COUNT));
        this.balls.forEach(VisibleObject::start);
        this.controlPanel.update = true;
    }

    public void pause() {
        this.balls.forEach(VisibleObject::pause);
        this.controlPanel.update = false;
    }

    public void stop() {
        this.balls.forEach(VisibleObject::stop);
        this.blackHoles.clear();
        this.balls.clear();
        this.controlPanel.update = false;
        this.controlPanel.updateStatistics();
    }

    @Override
    public void didPress(ControlPanelAction action) {
        switch (action) {
            case PLAY:
                play();
                break;
            case PAUSE:
                pause();
                break;
            case STOP:
                stop();
                break;
            case RESTART:
                stop();
                play();
                break;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(VIEW_WIDTH, VIEW_HEIGHT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BallTask::new);
    }
}