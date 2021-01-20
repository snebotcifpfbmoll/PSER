package com.snebot.fbmoll.ui;

import com.snebot.fbmoll.data.Statistics;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ControlPanel extends JPanel implements Runnable {
    private int vWidth = 1;
    private int vHeight = 1;
    public boolean update = true;
    private int delay = 40;

    private static final int SMALL_INSET = 5;

    private final Thread thread = new Thread(this, getClass().getSimpleName());
    private final JTable table = new JTable();
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
        //this.thread.start();
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
        constraints.insets = new Insets(SMALL_INSET, SMALL_INSET, SMALL_INSET, SMALL_INSET);
        constraints.anchor = GridBagConstraints.PAGE_START;

        JScrollPane scroll = new JScrollPane(this.table);
        scroll.setMinimumSize(new Dimension(100, 100));
        scroll.setMaximumSize(new Dimension(150, 200));
        add(scroll, constraints);
        updateTable();

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

        play.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.PLAY);
        });

        pause.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.PAUSE);
        });

        stop.addActionListener(e -> {
            if (this.delegate != null) this.delegate.didPress(ControlPanelAction.STOP);
        });
    }

    public void updateTable() {
        if (this.statistics == null) return;
        String[][] data = this.statistics.toArray();
        String[] columns = new String[]{"Name", "Count"};
        DefaultTableModel model = new DefaultTableModel(data, columns);
        this.table.setModel(model);
    }

    @Override
    public void run() {
        while (this.update) {
            try {
                if (this.delegate != null) {
                    this.statistics = this.delegate.getStatistics();
                    updateTable();
                }
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