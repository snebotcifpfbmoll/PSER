package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.data.Statistics;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel implements Runnable {
    private int vWidth = 1;
    private int vHeight = 1;
    public boolean run = true;
    public boolean update = true;
    private int delay = 40;

    private static final int SMALL_INSET = 5;
    private static final String BALL_COUNT_FORMAT = "Ball count: \t%d";
    private static final String OUTSIDE_BALL_COUNT_FORMAT = "Outside ball count: \t%d";
    private static final String INSIDE_BALL_COUNT_FORMAT = "Inside count: \t%d";
    private static final String STOPPED_BALL_COUNT_FORMAT = "Stopped ball count: \t%d";

    private final JLabel ballCountLabel = new JLabel(String.format(BALL_COUNT_FORMAT, 0));
    private final JLabel outsideBallCountLabel = new JLabel(String.format(OUTSIDE_BALL_COUNT_FORMAT, 0));
    private final JLabel insideBallCountLabel = new JLabel(String.format(INSIDE_BALL_COUNT_FORMAT, 0));
    private final JLabel stoppedBallCountLabel = new JLabel(String.format(STOPPED_BALL_COUNT_FORMAT, 0));

    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private Statistics statistics = new Statistics();
    private ControlPanelDelegate delegate = null;

    public ControlPanelDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ControlPanelDelegate delegate) {
        this.delegate = delegate;
    }

    private ControlPanel() {
    }

    public ControlPanel(int width, int height) {
        super();
        this.vWidth = width;
        this.vHeight = height;
        setup();
        this.thread.start();
    }

    private void setup() {
        this.setLayout(new GridBagLayout());
        setBackground(Color.LIGHT_GRAY);

        int ylevel = 0;
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = ylevel;
        constraints.gridwidth = 2;
        constraints.weightx = 0.1;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(SMALL_INSET, SMALL_INSET, 0, SMALL_INSET);
        constraints.anchor = GridBagConstraints.PAGE_START;

        add(this.ballCountLabel, constraints);
        ylevel += 1;
        constraints.gridy = ylevel;
        constraints.insets = new Insets(0, SMALL_INSET, 0, SMALL_INSET);
        add(this.outsideBallCountLabel, constraints);
        ylevel += 1;
        constraints.gridy = ylevel;
        add(this.insideBallCountLabel, constraints);
        ylevel += 1;
        constraints.gridy = ylevel;
        add(this.stoppedBallCountLabel, constraints);
        ylevel += 1;
        constraints.gridy = ylevel;
        constraints.insets = new Insets(SMALL_INSET, SMALL_INSET, SMALL_INSET, SMALL_INSET);

        constraints.gridwidth = 1;
        ylevel += constraints.gridheight;
        constraints.gridy = ylevel;

        JButton play = new JButton("Play");
        add(play, constraints);

        constraints.gridx = 1;

        JButton pause = new JButton("Pause");
        add(pause, constraints);

        ylevel += constraints.gridheight;
        constraints.gridy = ylevel;
        constraints.gridx = 0;

        JButton stop = new JButton("Stop");
        add(stop, constraints);

        constraints.gridx = 1;

        JButton restart = new JButton("Restart");
        add(restart, constraints);

        play.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.PLAY);
        });

        pause.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.PAUSE);
        });

        stop.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.STOP);
        });

        restart.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.RESTART);
        });
    }

    public void updateStatistics() {
        if (this.statistics == null || this.delegate == null) return;
        this.statistics = this.delegate.getStatistics();
        this.ballCountLabel.setText(String.format(BALL_COUNT_FORMAT, this.statistics.ballCount));
        this.outsideBallCountLabel.setText(String.format(OUTSIDE_BALL_COUNT_FORMAT, this.statistics.outsideBallCount));
        this.insideBallCountLabel.setText(String.format(INSIDE_BALL_COUNT_FORMAT, this.statistics.insideBallCount));
        this.stoppedBallCountLabel.setText(String.format(STOPPED_BALL_COUNT_FORMAT, this.statistics.stoppedBallCount));
    }

    @Override
    public void run() {
        while (this.run) {
            try {
                if (this.update) updateStatistics();
                Thread.sleep(this.delay);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.vWidth, this.vHeight);
    }
}