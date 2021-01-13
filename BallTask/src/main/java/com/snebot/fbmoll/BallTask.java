package com.snebot.fbmoll;

import com.snebot.fbmoll.data.Statistics;
import com.snebot.fbmoll.data.StatisticsDataSource;
import com.snebot.fbmoll.graphics.Ball;
import com.snebot.fbmoll.graphics.BlackHole;
import com.snebot.fbmoll.graphics.VisibleObject;
import com.snebot.fbmoll.ui.ControlPanel;
import com.snebot.fbmoll.ui.Viewer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BallTask extends JFrame implements StatisticsDataSource {
    private static final int VIEW_WIDTH = 1200;
    private static final int VIEW_HEIGHT = 600;
    private static final int MIN_BALL_COUNT = 10;
    private static final int MAX_BALL_COUNT = 20;
    private static final int BLACK_HOLE_COUNT = 2;
    private static final int[][] BLACK_HOLE_COORDS = new int[BLACK_HOLE_COUNT][2];

    static {
        BLACK_HOLE_COORDS[0][0] = 200;
        BLACK_HOLE_COORDS[0][1] = 100;
        BLACK_HOLE_COORDS[1][0] = 800;
        BLACK_HOLE_COORDS[1][1] = 300;
    }

    private final Viewer viewer = new Viewer();
    private final ControlPanel controlPanel = new ControlPanel();
    private final List<VisibleObject> balls = new ArrayList<>();
    private final List<VisibleObject> blackHoles = new ArrayList<>();

    public BallTask() {
        setupUI();

        for (int i = 0; i < BLACK_HOLE_COUNT; i++) {
            BlackHole blackHole = new BlackHole();
            blackHole.point.x = BLACK_HOLE_COORDS[i][0];
            blackHole.point.y = BLACK_HOLE_COORDS[i][1];
            this.blackHoles.add(blackHole);
        }

        for (int i = 0; i < getRandom(MIN_BALL_COUNT, MAX_BALL_COUNT); i++) {
            Ball ball = new Ball();
            ball.point.x = getRandom(0, getWidth() - ball.size.width);
            ball.point.y = getRandom(0, getHeight() - ball.size.height);
            ball.start();
            this.balls.add(ball);

            this.blackHoles.forEach(blackHole -> {
                if (ball.touches(blackHole)) ball.setColor(Color.BLUE);
            });
        }

        this.viewer.setBalls(this.balls);
        this.viewer.setBlackHoles(this.blackHoles);
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
        constraints.gridx = constraints.gridwidth;
        constraints.gridy = 0;
        constraints.weightx = 0.1;
        constraints.weighty = 1;
        constraints.gridwidth = 6;
        pane.add(this.viewer, constraints);

        pack();
        setVisible(true);
    }

    /**
     * Generate random number with range.
     *
     * @param min Minimum range.
     * @param max Maximum range.
     * @return Random number.
     */
    public int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Add a new ball to ball list.
     *
     * @param ball New ball.
     */
    public void addBall(Ball ball) {
    }

    /**
     * Remove ball from ball list.
     *
     * @param ball Ball to remove.
     */
    public void removeBall(Ball ball) {
    }

    /**
     * Generate an amount of balls.
     *
     * @param count Ball count.
     */
    public void generateBall(int count) {
    }

    /**
     * Add a new black hole to black hole list.
     *
     * @param blackHole New black hole.
     */
    public void addBlackHole(BlackHole blackHole) {
    }

    /**
     * Remove a black hole from black hole list.
     *
     * @param blackHole Black hole to remove.
     */
    public void removeBlackHole(BlackHole blackHole) {
    }

    /**
     * Generate an amount of black holes.
     *
     * @param count Black hole count.
     */
    public void generateBlackHole(int count) {
    }

    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(VIEW_WIDTH, VIEW_HEIGHT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BallTask ballTask = new BallTask();
        });
    }
}